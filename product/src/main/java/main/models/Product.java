package main.models;

import java.math.BigDecimal;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.SemanticsOf;

import lombok.Data;

import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import main.managers.ProductManager;
import main.repositories.ProductRepository;
import static main.ProductApplication.NAMESPACE;


@Entity
@Data
@DomainObject(
        autoCompleteRepository = ProductRepository.class,
        autoCompleteMethod = "findByTemplateNameContainingIgnoreCase"
)
@Named(NAMESPACE+".product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Property
    @PropertyLayout(sequence = "2")
    private String barcode;
    @ManyToOne(optional = false)
    private ProductTemplate template;
    @ManyToOne(optional = false)
    private ProductCategory category;
    @Property
    private Uom uom;
    private BigDecimal salePrice;
    private BigDecimal costPrice;
    @Property
    @PropertyLayout(sequence = "1")
    @Transient
    public String getName() { return template.getName(); }

    @Property
    @Transient
    public void setName(String name) { this.template.setName(name); }
    @Action(semantics = SemanticsOf.SAFE)
    public ProductManager back(){
        return new ProductManager();
    }


}
