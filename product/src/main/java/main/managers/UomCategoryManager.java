package main.managers;

import java.util.List;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.message.MessageService;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import main.models.UomCategory;
import main.repositories.UomCategoryRepository;

import static main.ProductApplication.NAMESPACE;


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
