package airwin17.sale;

import base.BaseApplication;

import base.ProductApplication;

import org.apache.causeway.core.runtimeservices.CausewayModuleCoreRuntimeServices;

import org.apache.causeway.viewer.wicket.viewer.CausewayModuleViewerWicketViewer;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
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
@EntityScan(basePackageClasses = {SaleApplication.class})
@ComponentScan
public class SaleApplication {

    public static final String NAMESPACE = "sale";

}
