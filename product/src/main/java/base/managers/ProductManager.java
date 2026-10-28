package base.managers;

import base.models.Product;
import base.models.ProductCategory;
import base.models.ProductTemplate;
import base.models.Uom;
import base.repositories.ProductRepository;
import base.repositories.ProductTemplateRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import lombok.Getter;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.HomePage;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.annotation.Title;
import org.apache.causeway.applib.services.message.MessageService;

import java.util.List;

import static base.ProductApplication.NAMESPACE;

@DomainObject(nature = Nature.VIEW_MODEL)
@Named(NAMESPACE + ".ProductManager")
@HomePage
public class ProductManager {
    @Inject private ProductTemplateRepository productTemplateRepository;
    @Inject private ProductRepository productRepository;
    @Inject private MessageService messageService;
    @Getter @Title private String title="products";
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(
            associateWith = "products",
            sequence = "1",
            named = "Add product",
            cssClassFa = "fa-plus")
    public ProductManager create(
            String name,
            String defaultCode,
            ProductCategory category,
            Uom uom,
            @Parameter(optionality = Optionality.OPTIONAL) ProductTemplate productTemplate) {
        var p = new Product();
        p.setBarcode(defaultCode);
        p.setCategory(category);
        p.setUom(uom);
        if(productTemplate != null) {
            p.setTemplate(productTemplate);
        }else{
            ProductTemplate pt = new ProductTemplate();
            p.setTemplate(productTemplateRepository.save(pt));
        }
        p.setName(name);
        productRepository.save(p);
        messageService.informUser("Product added successfully");
        return this;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(
            named = "List products",
            cssClassFa = "fa-list")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

}
