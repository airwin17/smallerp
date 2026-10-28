package airwin17.sale.manegers;

import airwin17.sale.models.SaleOrder;
import airwin17.sale.repositories.SaleOrderRepository;
import base.models.Partner;
import base.models.Product;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.message.MessageService;

import java.util.List;

import static airwin17.sale.SaleApplication.NAMESPACE;

@Named(NAMESPACE+".SaleOrderManager")
@DomainObject(nature = Nature.VIEW_MODEL)
public class SaleOrderManager {
    @Inject private MessageService messageService;
    @Inject private SaleOrderRepository saleOrderRepository;
    @Action(semantics = SemanticsOf.SAFE)
    public List<SaleOrder> getSaleOrders() {
        return saleOrderRepository.findAll();
    }
    /*@Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public SaleOrderManager deleteSaleOrder(SaleOrder saleOrder) {
        saleOrderRepository.delete(saleOrder);
        messageService.informUser("Sale order deleted");
        return this;
    }*/
    @Action(semantics = SemanticsOf.SAFE)
    public SaleOrder addSaleOrder() {
        return new SaleOrder();
    }

}
