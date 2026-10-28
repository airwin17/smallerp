package base;

import base.Services.UserService;
import base.models.Partner;
import base.models.User;
import base.repositories.PartnerRepository;
import base.repositories.UserRepository;
import jakarta.inject.Inject;

import org.apache.causeway.applib.services.xactn.TransactionService;
import org.apache.causeway.core.runtimeservices.CausewayModuleCoreRuntimeServices;

import org.apache.causeway.viewer.wicket.viewer.CausewayModuleViewerWicketViewer;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.apache.causeway.persistence.jpa.applib.CausewayModulePersistenceJpaApplib;

import org.springframework.context.event.EventListener;

@Configuration
@Import({
        CausewayModuleCoreRuntimeServices.class,
        CausewayModulePersistenceJpaApplib.class,
        CausewayModuleViewerWicketViewer.class
})

@EntityScan(basePackageClasses = {BaseApplication.class})
@ComponentScan
public class BaseApplication {
    //do something useful
    @Inject private PartnerRepository partnerRepository;
    @Inject private UserRepository userRepository;
    @Inject private UserService userService;
    public static final String NAMESPACE="base";

    // add functions here
    @EventListener(ApplicationReadyEvent.class)
    public void seedOnStartup(ApplicationReadyEvent event) {
        if (partnerRepository.count() == 0) {
            var p = new Partner();
            p.setName("Main Shop");
            p.setCustomer(true);
            p.setSupplier(true);
            p.setActive(true);
            partnerRepository.save(p);
        }
        if(userRepository.count() == 0) {
            userService.newUser("admin","admin");

        }
    }
}
