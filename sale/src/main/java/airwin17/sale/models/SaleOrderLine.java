package airwin17.sale.models;

import airwin17.sale.repositories.SaleOrderLineRepository;
import base.models.Product;
import base.models.Uom;
import jakarta.inject.Named;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;

import lombok.Data;

import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.Title;

import java.math.BigDecimal;

import static airwin17.sale.SaleApplication.NAMESPACE;
@Data
@Entity
@Named(NAMESPACE+".SaleOrderLine")
@DomainObject(
        nature = Nature.ENTITY
)
public class SaleOrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @Property(editing = Editing.ENABLED)
    private Product product;
    @ManyToOne(optional = false)
    private SaleOrder saleOrder;
    @Property(editing = Editing.ENABLED)
    private BigDecimal quantity;
    @ManyToOne(optional = false)
    @Property(editing = Editing.ENABLED)
    private Uom uom;
    @Property(editing = Editing.ENABLED)
    private BigDecimal unitPrice;
    public BigDecimal getSubTotal() {
        return quantity.multiply(unitPrice);
    }
    public void setProduct(Product product) {
        this.product = product;
        if (product != null) {
            this.uom = product.getUom();
            this.unitPrice = product.getSalePrice();
        }
    }
}
