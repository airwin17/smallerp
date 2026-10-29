package main.managers;

import java.util.List;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.message.MessageService;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import main.models.ProductCategory;
import main.repositories.ProductCategoryRepository;

import static main.ProductApplication.NAMESPACE;


@DomainObject(nature = Nature.VIEW_MODEL)
@Named(NAMESPACE + ".ProductCategoryManager")
public class ProductCategoryManager {
    @Inject private ProductCategoryRepository productCategoryRepository;
    @Inject private MessageService messageService;
    @ObjectSupport
    public String title() { return "Categories Manager"; }
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public ProductCategoryManager addProductCategory(String name, @Parameter(optionality = Optionality.OPTIONAL) ProductCategory parent) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(name);
        if (parent != null) {
            productCategory.setParent(parent);
        }
        productCategoryRepository.save(productCategory);
        return this;
    }
    @Action(semantics = SemanticsOf.SAFE)
    public List<ProductCategory> getAllProductCategory() {
        return  productCategoryRepository.findAll();
    }
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
    public ProductCategoryManager deleteProductCategory(ProductCategory productCategory) {
        productCategoryRepository.delete(productCategory);
        messageService.informUser("Product Category deleted successfully");
        return this;
    }
}
