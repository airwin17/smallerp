package main;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.apache.causeway.core.runtimeservices.CausewayModuleCoreRuntimeServices;
import org.apache.causeway.persistence.jpa.applib.CausewayModulePersistenceJpaApplib;
import org.apache.causeway.viewer.wicket.viewer.CausewayModuleViewerWicketViewer;


@Configuration
@Import({
        CausewayModuleCoreRuntimeServices.class,
        CausewayModulePersistenceJpaApplib.class,
        CausewayModuleViewerWicketViewer.class,
        BaseApplication.class,
})
@EnableJpaRepositories
@EntityScan(basePackageClasses = {ProductApplication.class})
@ComponentScan
public class ProductApplication {

    public static final String NAMESPACE = "product";


}
