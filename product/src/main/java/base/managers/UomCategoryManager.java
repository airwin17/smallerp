package base.managers;

import base.models.UomCategory;
import base.repositories.UomCategoryRepository;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.message.MessageService;

import java.util.List;

import static base.ProductApplication.NAMESPACE;

@DomainObject(nature = Nature.VIEW_MODEL)
@Named(NAMESPACE+".UomCategoryManager")
public class UomCategoryManager {
    @Inject private MessageService messageService;
    @Inject private UomCategoryRepository uomCategoryRepository;
    @Action(semantics = SemanticsOf.SAFE)
    public List<UomCategory> getAllUomCategories() {
        return uomCategoryRepository.findAll();
    }
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public UomCategoryManager addUnit(String name) {
        uomCategoryRepository.save(new UomCategory(name));
        messageService.informUser("Unit added successfully");
        return this;
    }
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public UomCategoryManager removeUnit(UomCategory uomCategory) {
        uomCategoryRepository.delete(uomCategory);
        messageService.informUser("Unit removed successfully");
        return this;
    }

}
