package airwin17.stock.service;

import main.Services.UomService;
import airwin17.sale.event.OrderSaleConfirmEvent;
import airwin17.sale.models.SaleOrder;
import airwin17.sale.models.SaleOrderLine;
import airwin17.stock.models.State;
import airwin17.stock.models.StockLocation;
import airwin17.stock.models.StockMove;
import airwin17.stock.models.Usage;
import airwin17.stock.repositories.StockLocationRepository;
import airwin17.stock.repositories.StockMoveRepository;

import jakarta.inject.Inject;

import lombok.RequiredArgsConstructor;

import main.models.Product;

import main.models.Uom;

import org.apache.causeway.applib.events.domain.AbstractDomainEvent;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class StockService {

    @Inject private final StockMoveRepository stockMoveRepository;
    @Inject private final StockLocationRepository stockLocationRepository;
    @Inject private final UomService uomService;
    @EventListener(OrderSaleConfirmEvent.class)
    public void on(OrderSaleConfirmEvent ev) {
        // Only run during the DISABLE check phase before the user clicks
        /*if (ev.getEventPhase() == AbstractDomainEvent.Phase.DISABLE) {
            SaleOrder order = ev.getSource();
        }*/
        if(ev.getEventPhase()== AbstractDomainEvent.Phase.VALIDATE){
            SaleOrder order = ev.getSource();
            for (SaleOrderLine line : order.getSaleOrderLineList()) {
                BigDecimal qtyOnHand = onHand(line.getProduct());
                if (qtyOnHand.compareTo(line.getQuantity()) < 0) {
                    // Greys out the 'Confirm' button in Wicket and sets the tooltip message
                    ev.invalidate(String.format(
                            "Cannot confirm: Insufficient stock for %s (Available: %s, Required: %s)",
                            line.getProduct().getName(),
                            qtyOnHand,
                            line.getQuantity()
                    ));
                    return;
                }
            }
        }
    }
    /*@EventListener(PurchaseConfirmEvent.class)
    public void onPurchaseConfirm(PurchaseConfirmEvent ev) {

    }*/

    public BigDecimal onHand(Product product) {
        if (product == null || product.getUom() == null) {
            return BigDecimal.ZERO;
        }

        // 1. Moves arriving at an INVENTORY location
        List<StockMove> inStockMoves = stockMoveRepository
                .findByDestinationUsageAndProductAndState(Usage.INVENTORY, product, State.DONE);

        // 2. Moves departing from an INVENTORY location
        List<StockMove> outStockMoves = stockMoveRepository
                .findBySourceUsageAndProductAndState(Usage.INVENTORY, product, State.DONE);

        Uom targetUom = product.getUom();
        BigDecimal qty = BigDecimal.ZERO;

        for (StockMove move : inStockMoves) {
            // Skip transfers within the shop (INVENTORY -> INVENTORY)
            if (move.getSource().getUsage() != Usage.INVENTORY) {
                qty = qty.add(move.getUom().convertTo(move.getQuantity(), targetUom));
            }
        }

        for (StockMove move : outStockMoves) {
            // Skip transfers within the shop (INVENTORY -> INVENTORY)
            if (move.getDestination().getUsage() != Usage.INVENTORY) {
                qty = qty.subtract(move.getUom().convertTo(move.getQuantity(), targetUom));
            }
        }

        return qty;
    }

    public StockMove receive(Product product, BigDecimal qty) {
        return post(product, qty, location(Usage.SUPPLIER),
                location(Usage.INVENTORY));
    }

    public StockMove deliver(Product product, BigDecimal qty, Uom uom) {
        BigDecimal issued = toProductUom(qty, uom, product);
        if (onHand(product).compareTo(issued) < 0) {
            throw new IllegalStateException("Not enough stock for " + product.getName());
        }
        return post(product, qty, location(Usage.INVENTORY),
                location(Usage.CUSTOMER));
    }

    private StockMove post(Product product, BigDecimal qty,
                           StockLocation source, StockLocation destination) {
        if (!product.getUom().getCategory().equals(product.getUom().getCategory())) {
            throw new IllegalStateException("Unit categoery mismatch");
        }
        StockMove move = new StockMove();
        move.setProduct(product);
        move.setQuantity(qty);
        move.setUom(product.getUom());
        move.setSource(source);
        move.setDestination(destination);
        move.setState(State.DONE);
        move.setDoneOn(LocalDate.now());
        return stockMoveRepository.save(move);
    }

    private BigDecimal signed(StockMove move, Product product) {
        BigDecimal qty = toProductUom(move.getQuantity(), move.getUom(), product);
        if (move.getDestination().getUsage() == Usage.INVENTORY) return qty;
        if (move.getSource().getUsage() == Usage.INVENTORY) return qty.negate();
        return BigDecimal.ZERO;
    }

    private BigDecimal toProductUom(BigDecimal qty, Uom from, Product product) {
        return qty.multiply(from.getRatioToReference())
                .divide(product.getUom().getRatioToReference(), 6, RoundingMode.HALF_UP);
    }

    private StockLocation location(Usage usage) {
        return stockLocationRepository.findAll().stream()
                .filter(l -> l.getUsage() == usage)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Missing location " + usage));
    }
}
