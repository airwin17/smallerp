package purchase.models;

import base.models.Partner;
import base.models.Product;
import base.models.Uom;
import jakarta.inject.Inject;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;

import jakarta.persistence.OneToMany;

import jakarta.persistence.Transient;

import lombok.Data;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainObject;

import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.events.domain.ActionDomainEvent;

import purchase.repositories.PurchaseOrderLineRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@DomainObject(nature = Nature.ENTITY)
public class PurchaseOrder {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Partner supplier;

    private LocalDate orderDate;
    private boolean confirmed;
    private String name;                 // PO number, copied into picking.origin and invoice.origin

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseOrderLine> lines = new ArrayList<>();

    @Inject @Transient
    private PurchaseOrderLineRepository purchaseOrderLineRepository;

    public String title() {
        return name == null ? "Purchase" : name;
    }

    public BigDecimal getTotal() {
        return lines.stream()
                .map(PurchaseOrderLine::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String validateSupplier(Partner proposed) {
        if (proposed != null && !proposed.isSupplier()) return "Partner is not a supplier";
        return null;
    }

    public PurchaseOrder confirm() {
        if (lines.isEmpty()) throw new IllegalStateException("Order has no lines");
        confirmed = true;
        return this;
    }

    public String disableConfirm() {
        return confirmed ? "Already confirmed" : null;
    }

    @Action
    public PurchaseOrderLine addLine(Product product, BigDecimal quantity, Uom uom, BigDecimal priceUnit) {
        PurchaseOrderLine line = new PurchaseOrderLine();
        line.setOrder(this);
        line.setProduct(product);
        line.setQuantity(quantity);
        line.setUom(uom);
        line.setPriceUnit(priceUnit);
        lines.add(line);
        return purchaseOrderLineRepository.save(line);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id != null && id.equals(((PurchaseOrder) o).id);
    }

    @Override
    public int hashCode() { return getClass().hashCode(); }
}
