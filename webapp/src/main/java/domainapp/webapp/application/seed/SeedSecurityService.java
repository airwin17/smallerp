package domainapp.webapp.application.seed;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.applib.events.metamodel.MetamodelEvent;
import org.apache.causeway.applib.services.xactn.TransactionService;
import org.apache.causeway.core.config.environment.CausewaySystemEnvironment;
import org.apache.causeway.testing.fixtures.applib.fixturescripts.FixtureScripts;

import lombok.RequiredArgsConstructor;

@Service
@Priority(PriorityPrecedence.MIDPOINT + 10)
public class SeedSecurityService {

    private final TransactionService transactionService;
    private final CausewaySystemEnvironment causewaySystemEnvironment;
    public  SeedSecurityService(FixtureScripts fixtureScripts,TransactionService transactionService, CausewaySystemEnvironment causewaySystemEnvironment) {
        this.transactionService = transactionService;
        this.causewaySystemEnvironment = causewaySystemEnvironment;
    }

    @EventListener(MetamodelEvent.class)
    public void onMetamodelEvent(final MetamodelEvent event) {
        if (event.isPostMetamodel() && causewaySystemEnvironment.isPrototyping()) {

            transactionService.flushTransaction();
        }
    }


}
