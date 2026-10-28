package base.domainServices;

import base.managers.ProductCategoryManager;
import base.managers.ProductManager;
import base.managers.ProductTemplateManager;
import base.managers.UomCategoryManager;
import base.managers.UomManager;
import jakarta.inject.Named;

import lombok.RequiredArgsConstructor;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.DomainServiceLayout;
import org.apache.causeway.applib.annotation.SemanticsOf;

import static base.ProductApplication.NAMESPACE;

@DomainService
@Named(NAMESPACE+".Products")
@RequiredArgsConstructor
public class Products {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(cssClassFa = "fa-cubes", named = "Products")
    public ProductManager products() {
        return new ProductManager();
    }
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(cssClassFa = "fa-list")
    public ProductCategoryManager Categories() {
        return new ProductCategoryManager();
    }
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(cssClassFa = "fa-list")
    public ProductTemplateManager templates() {
        return new ProductTemplateManager();
    }
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(cssClassFa = "fa-boxes")
    public UomManager units() {
        return new UomManager();
    }
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(cssClassFa = "fa-list")
    public UomCategoryManager unitCategories() {
        return new UomCategoryManager();
    }


}
