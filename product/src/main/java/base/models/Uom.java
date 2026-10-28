package base.models;


import base.managers.UomManager;
import base.repositories.UomRepository;
import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.Title;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Named("product.Uom")
@DomainObject(
        autoCompleteRepository = UomRepository.class,
        autoCompleteMethod = "findByName"
)
@Data
public class Uom {

    @Id @GeneratedValue
    private Long id;

    @Title
    @Column(nullable = false)
    @Property(editing = Editing.ENABLED)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private UomCategory category;

    /** 1 this = ratioToReference * category reference */
    @Column(nullable = false, precision = 18, scale = 12)
    private BigDecimal ratioToReference = BigDecimal.ONE;

    @Column(nullable = false, precision = 18, scale = 6)
    private BigDecimal rounding = new BigDecimal("0.01");

    private boolean active = true;

    @Enumerated(EnumType.STRING)
    private UomType type = UomType.REFERENCE;

    public BigDecimal toReference(BigDecimal qty) {
        return qty.multiply(ratioToReference);
    }

    public BigDecimal fromReference(BigDecimal qtyRef) {
        return qtyRef.divide(ratioToReference, 12, RoundingMode.HALF_UP);
    }

    public BigDecimal convertTo(BigDecimal qty, Uom to) {
        if (this.equals(to)) {
            return round(qty, to.rounding);
        }
        if (!category.equals(to.getCategory())) {
            throw new IllegalArgumentException(
                    "Cannot convert " + name + " to " + to.getName());
        }
        return round(to.fromReference(toReference(qty)), to.rounding);
    }

    static BigDecimal round(BigDecimal q, BigDecimal step) {
        if (step.signum() <= 0) return q;
        return q.divide(step, 0, RoundingMode.HALF_UP).multiply(step);
    }
    @Action
    public UomManager back(){
        return new UomManager();
    }
}
