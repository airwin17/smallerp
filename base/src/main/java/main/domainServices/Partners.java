package main.domainServices;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.SemanticsOf;



import jakarta.inject.Inject;
import jakarta.inject.Named;
import main.managers.PartnerManager;
import main.managers.UserManager;
import main.repositories.PartnerRepository;

import static main.BaseApplication.NAMESPACE;


@DomainService
@Named(NAMESPACE+".Partners")
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
