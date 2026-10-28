package base.base;

import org.apache.causeway.core.runtimeservices.CausewayModuleCoreRuntimeServices;

import org.apache.causeway.viewer.wicket.viewer.CausewayModuleViewerWicketViewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.apache.causeway.persistence.jpa.applib.CausewayModulePersistenceJpaApplib;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import({
        CausewayModuleCoreRuntimeServices.class,
        CausewayModulePersistenceJpaApplib.class,
        CausewayModuleViewerWicketViewer.class
})
@EnableJpaRepositories
@EntityScan(basePackageClasses = {PurchaseApplication.class})
@ComponentScan
public class PurchaseApplication {



}
