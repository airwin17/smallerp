package airwin17.sale.domainServices;

import airwin17.sale.manegers.SaleOrderManager;
import jakarta.inject.Named;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.SemanticsOf;

import static airwin17.sale.SaleApplication.NAMESPACE;

@DomainService
@Named(NAMESPACE+".Sales")
public class Sales {
    @Action(semantics = SemanticsOf.SAFE)
    public SaleOrderManager saleOrderManager() {
        return new SaleOrderManager();
    }
}
