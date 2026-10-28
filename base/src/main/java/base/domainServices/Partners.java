package base.domainServices;

import base.managers.PartnerManager;
import base.managers.UserManager;
import base.repositories.PartnerRepository;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.DomainServiceLayout;
import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.applib.annotation.SemanticsOf;

import static org.apache.causeway.core.metamodel.CausewayModuleCoreMetamodel.NAMESPACE;


@DomainService
@Named(NAMESPACE+".Partners")
@Priority(PriorityPrecedence.EARLY)
@DomainServiceLayout(named = "Partnersmm")
public class Partners {
    final PartnerRepository partnerRepository;
    @Inject
    public Partners(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }
    @Action(semantics = SemanticsOf.SAFE)
    public UserManager users() {
        return new UserManager();
    }
    @Action(semantics = SemanticsOf.SAFE)
    public PartnerManager partners() {
        return new PartnerManager();
    }

}
