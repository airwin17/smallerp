package main.managers;

import java.util.List;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.message.MessageService;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import main.models.Product;
import main.models.ProductTemplate;
import main.repositories.ProductTemplateRepository;

import static main.ProductApplication.NAMESPACE;


@Named(NAMESPACE+".ProductTemplateManager")
@DomainObject(nature = Nature.VIEW_MODEL)
public class ProductTemplateManager {
    @Inject private MessageService messageService;
    @Inject private ProductTemplateRepository productTemplateRepository;
    @Action(semantics = SemanticsOf.SAFE)
    public List<ProductTemplate> getAllProductTemplate() {
        return  productTemplateRepository.findAll();
    }
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
    public ProductTemplateManager deleteProductTemplate(ProductTemplate productTemplate) {
        productTemplateRepository.delete(productTemplate);
        return this;
    }
    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public ProductTemplateManager addProductTemplate(String name, @Parameter(optionality = Optionality.OPTIONAL) List<Product> variants) {
        ProductTemplate productTemplate = new ProductTemplate();
        productTemplate.setName(name);
        if(variants!=null)
            productTemplate.setVariants(variants);

        productTemplateRepository.save(productTemplate);
        messageService.informUser("Product template added successfully");
        return this;
    }

}
