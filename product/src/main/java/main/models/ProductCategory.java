package main.models;

import java.util.List;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.annotation.Title;

import lombok.Data;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import main.managers.ProductCategoryManager;
import main.repositories.ProductCategoryRepository;

@Entity
@Named("product.ProductCategory")
@Data
@DomainObject(autoCompleteMethod = "findByNameContainingIgnoreCase",autoCompleteRepository = ProductCategoryRepository.class)
public class ProductCategory {
    @Inject @Transient private ProductCategoryRepository productCategoryRepository;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Title
    @Property(editing = Editing.ENABLED)
    private String name;

    @ManyToOne
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(
            named = "Parent Category",
            // Group fields inside a specific conceptual section
            fieldSetId = "hierarchy",
            sequence = "1.0"
    )
    private ProductCategory parent;
    @MemberSupport
    public List<ProductCategory> choicesParent() {
        return productCategoryRepository.findAll().stream()
                .filter(c -> !c.equals(this))
                .toList();
    }
    @MemberSupport
    /** hard stop if someone posts this anyway */
    public String validateParent(ProductCategory proposed) {
        if (proposed == null) {
            return null;
        }
        if (proposed.equals(this)) {
            return "A category cannot be its own parent";
        }
        if (isAncestorOf(proposed, this)) {
            return "Cannot create a cycle in the category tree";
        }
        return null; // ok
    }

    /** true if `node` is this or above this */
    private boolean isAncestorOf(ProductCategory node, ProductCategory start) {
        ProductCategory walk = start;
        int guard = 0;
        while (walk != null && guard++ < 64) {
            if (walk.equals(node)) {
                return true;
            }
            walk = walk.getParent();
        }
        return false;
    }
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(named = "Back")
    public ProductCategoryManager back() {
        return new ProductCategoryManager();
    }
    @Override
    public boolean equals(Object o) {

        return ((ProductCategory) o).getId().equals(this.getId())&&(o.getClass()==this.getClass());
    }
}
