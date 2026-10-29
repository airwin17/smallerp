package main;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;

import org.apache.causeway.core.runtimeservices.CausewayModuleCoreRuntimeServices;
import org.apache.causeway.persistence.jpa.applib.CausewayModulePersistenceJpaApplib;
import org.apache.causeway.viewer.wicket.viewer.CausewayModuleViewerWicketViewer;

import main.Services.UserService;
import jakarta.inject.Inject;
import main.models.Partner;
import main.repositories.PartnerRepository;
import main.repositories.UserRepository;

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
