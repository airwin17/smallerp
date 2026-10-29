package main.domainServices;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.DomainServiceLayout;
import org.apache.causeway.applib.annotation.SemanticsOf;

import lombok.RequiredArgsConstructor;

import jakarta.inject.Named;
import main.managers.ProductCategoryManager;
import main.managers.ProductManager;
import main.managers.ProductTemplateManager;
import main.managers.UomCategoryManager;
import main.managers.UomManager;

import static main.ProductApplication.NAMESPACE;

@DomainService
@Named(NAMESPACE+".Products")
@DomainServiceLayout(named = "Products")
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
