package airwin17.account.models;

import base.models.Product;
import base.models.Uom;
import jakarta.inject.Named;
import jakarta.persistence.Entity;

import jakarta.persistence.FetchType;

import jakarta.persistence.GeneratedValue;

import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;

import lombok.Data;

import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Nature;

import java.math.BigDecimal;

@Entity
@DomainObject(nature = Nature.ENTITY)
@Named("account.InvoiceLine")
@Data
public class InvoiceLine {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Invoice invoice;

    @ManyToOne(optional = false)
    private Product product;

    private BigDecimal quantity;
    private Uom uom;
    private BigDecimal priceUnit;

    @ManyToOne
    private Account account;               // income, copied at selection

    public BigDecimal getSubtotal() {
        return priceUnit.multiply(quantity);
    }
}
