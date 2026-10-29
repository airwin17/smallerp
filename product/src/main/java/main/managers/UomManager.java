package main.managers;

import java.math.BigDecimal;
import java.util.List;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.message.MessageService;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import main.models.Uom;
import main.models.UomCategory;
import main.models.UomType;
import main.repositories.UomRepository;

import static main.ProductApplication.NAMESPACE;


@DomainObject(nature = Nature.VIEW_MODEL)
@Named(NAMESPACE+".UomManager")
public class UomManager {
    @Inject private UomRepository uomRepository;
    @Inject private MessageService messageService;
    @Action(semantics = SemanticsOf.SAFE)
    public List<Uom> getAllUnits() {
        return uomRepository.findAll();
    }
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public UomManager addUnit(
            UomCategory uomCategory,
            String name,
            BigDecimal ratioToReference,
            UomType uomType
    ){
        Uom uom = new Uom();
        uom.setCategory(uomCategory);
        uom.setName(name);
        uom.setRatioToReference(ratioToReference);
        uom.setType(uomType);
        uomRepository.save(uom);
        messageService.informUser("Unit added successfully");
        return this;
    }
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public UomManager deleteUnit(Uom uom){
        uomRepository.delete(uom);
        messageService.informUser("Unit deleted successfully");
        return this;
    }
}
