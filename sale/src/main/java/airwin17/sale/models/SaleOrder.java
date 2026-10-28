package airwin17.sale.models;

import airwin17.sale.event.OrderSaleConfirmEvent;
import airwin17.sale.repositories.SaleOrderLineRepository;
import base.models.Partner;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

import lombok.Data;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.message.MessageService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static airwin17.sale.SaleApplication.NAMESPACE;

@DomainObject(nature = Nature.ENTITY)
@Named(NAMESPACE+".SaleOrder")
@Entity
@Data
public class SaleOrder {
    @Inject @Transient private SaleOrderLineRepository saleOrderLineRepository;
    @Inject @Transient private MessageService messageService;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @Property(editing = Editing.ENABLED)
    private Partner client;
    @Property(editing = Editing.ENABLED)
    private String ShippingAddress;
    @Property(editing = Editing.DISABLED)
    private LocalDate date;
    @Property(editing = Editing.ENABLED)
    private boolean paid;
    @OneToMany
    List<SaleOrderLine> saleOrderLineList=new ArrayList<>();
    public BigDecimal getTotal(){
        BigDecimal res=BigDecimal.ZERO;
        for (SaleOrderLine saleOrderLine : saleOrderLineList) {
            res = saleOrderLine.getSubTotal().add(res);
        }
        return res;
    }
    @Action(semantics = SemanticsOf.SAFE)
    public List<SaleOrderLine> getAllSaleOrderLines(){
        return saleOrderLineRepository.findAll();
    }
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT,domainEvent = OrderSaleConfirmEvent.class)
    public SaleOrder addConfirmSaleOrder() {
        if (saleOrderLineList.isEmpty()) {
            throw new IllegalStateException("Order has no lines");
        }
        /*for (SaleOrderLine line : saleOrderLineList) {
            line.getProduct().issue(line.getQuantity(), line.getUom());
        }*/
        paid = true;
        messageService.warnUser("Confirm Sale Order");
        return this;
    }
}
