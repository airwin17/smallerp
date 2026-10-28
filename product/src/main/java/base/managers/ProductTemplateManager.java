package base.managers;

import base.models.Product;
import base.models.ProductTemplate;
import base.repositories.ProductTemplateRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.message.MessageService;

import java.math.BigDecimal;
import java.util.List;

import static base.ProductApplication.NAMESPACE;

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
