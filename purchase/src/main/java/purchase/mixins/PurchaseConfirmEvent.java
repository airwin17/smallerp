package purchase.mixins;

import org.apache.causeway.applib.events.domain.ActionDomainEvent;

import purchase.models.PurchaseOrder;

public class PurchaseConfirmEvent extends ActionDomainEvent<PurchaseOrder> {}