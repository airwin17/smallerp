package domainapp.webapp;

import airwin17.stock.StockApplication;
import base.ProductApplication;

import airwin17.sale.SaleApplication;

import org.apache.causeway.extensions.fullcalendar.wkt.ui.viewer.CausewayModuleExtFullCalendarWicketUi;
import org.apache.causeway.extensions.layoutloaders.github.CausewayModuleExtLayoutLoadersGithub;


import org.apache.causeway.security.bypass.CausewayModuleSecurityBypass;
import org.apache.causeway.security.bypass.authentication.AuthenticatorBypass;
import org.apache.causeway.security.bypass.authorization.AuthorizorBypass;

import org.apache.causeway.security.spring.CausewayModuleSecuritySpring;
import org.apache.causeway.viewer.restfulobjects.jaxrsresteasy.CausewayModuleViewerRestfulObjectsJaxrsResteasy;

import org.apache.causeway.viewer.restfulobjects.viewer.CausewayModuleViewerRestfulObjectsViewer;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import org.apache.causeway.applib.CausewayModuleApplibMixins;
import org.apache.causeway.core.config.presets.CausewayPresets;
import org.apache.causeway.core.metamodel.inspect.CausewayModuleCoreMetamodelMixins;
import org.apache.causeway.core.runtimeservices.CausewayModuleCoreRuntimeServices;
import org.apache.causeway.extensions.flyway.impl.CausewayModuleExtFlywayImpl;
import org.apache.causeway.extensions.pdfjs.wkt.ui.CausewayModuleExtPdfjsWicketUi;
import org.apache.causeway.extensions.tabular.excel.CausewayModuleExtTabularExcel;
import org.apache.causeway.persistence.jpa.eclipselink.CausewayModulePersistenceJpaEclipselink;
import org.apache.causeway.testing.fixtures.applib.CausewayModuleTestingFixturesApplib;
import org.apache.causeway.testing.h2console.ui.CausewayModuleTestingH2ConsoleUi;
import org.apache.causeway.valuetypes.asciidoc.metamodel.CausewayModuleValAsciidocMetaModel;
import org.apache.causeway.valuetypes.asciidoc.ui.wkt.CausewayModuleValAsciidocUiWkt;
import org.apache.causeway.viewer.wicket.applib.CausewayModuleViewerWicketApplibMixins;
import org.apache.causeway.viewer.wicket.viewer.CausewayModuleViewerWicketViewer;

import domainapp.webapp.application.ApplicationModule;
import domainapp.webapp.custom.CustomModule;

@Configuration
@Import({
        CausewayModuleApplibMixins.class,
        CausewayModuleCoreMetamodelMixins.class,
        CausewayModuleViewerWicketApplibMixins.class,

        //CausewayModuleApplibChangeAndExecutionLoggers.class,

        CausewayModuleCoreRuntimeServices.class,
        CausewayModulePersistenceJpaEclipselink.class,
        CausewayModuleViewerWicketViewer.class,
        CausewayModuleViewerWicketViewer.class,

        CausewayModuleTestingFixturesApplib.class,
        CausewayModuleTestingH2ConsoleUi.class,
        CausewayModuleViewerRestfulObjectsJaxrsResteasy.class,
        CausewayModuleViewerRestfulObjectsViewer.class,

        CausewayModuleExtFlywayImpl.class,

        //CausewayModuleExtSecmanPersistenceJpa.class,
        //CausewayModuleExtSessionLogPersistenceJpa.class,
        //CausewayModuleExtAuditTrailPersistenceJpa.class,
        //CausewayModuleExtCommandLogPersistenceJpa.class,
        //CausewayModuleExtExecutionLogPersistenceJpa.class,
        //CausewayModuleExtExecutionOutboxPersistenceJpa.class,

        CausewayModuleExtLayoutLoadersGithub.class,
        CausewayModuleExtTabularExcel.class,
        CausewayModuleExtFullCalendarWicketUi.class,
        CausewayModuleExtPdfjsWicketUi.class,

        CausewayModuleValAsciidocMetaModel.class, // for pretty rendering of DTO objects such as CommandDto, InteractionDto
        CausewayModuleValAsciidocUiWkt.class,
        AuthorizorBypass.class,
        AuthenticatorBypass.class,
        CausewayModuleSecuritySpring.class,

        ApplicationModule.class,
        ProductApplication.class,
        //SaleApplication.class,
        //StockApplication.class,

})
@PropertySources({
        @PropertySource(CausewayPresets.DebugDiscovery),
})
public class AppManifest {


}
