package purchase.models;

import base.models.Product;
import base.models.Uom;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class PurchaseOrderLine {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PurchaseOrder order;

    @ManyToOne(optional = false)
    private Product product;

    @Column(nullable = false)
    private BigDecimal quantity;

    @ManyToOne(optional = false)
    private Uom uom;

    @Column(nullable = false)
    private BigDecimal priceUnit;        // typed, or defaulted from product.standardPrice

    public void setProduct(Product product) {
        this.product = product;
        if (product != null && uom == null) uom = product.getUom();
        if (product != null && priceUnit == null) priceUnit = product.getCostPrice();
    }

    public BigDecimal getSubtotal() {
        return priceUnit.multiply(quantity);
    }

    public String validateUom(Uom proposed) {
        if (product != null && proposed != null
                && !proposed.getCategory().equals(product.getUom().getCategory())) {
            return "Unit is not in the product category";
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id != null && id.equals(((PurchaseOrderLine) o).id);
    }

    @Override
    public int hashCode() { return getClass().hashCode(); }
}
