package domainapp.webapp.custom.restapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.inject.Inject;

import org.apache.causeway.applib.id.LogicalType;
import org.apache.causeway.applib.services.metamodel.MetaModelService;

import org.apache.causeway.applib.services.registry.ServiceRegistry;

import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/layout")
public class MetaModelController {
    private final MetaModelService metaModelService;
    private final ServiceRegistry serviceRegistry;

    public MetaModelController(MetaModelService metaModelService,ServiceRegistry serviceRegistry) {
        this.metaModelService = metaModelService;
        this.serviceRegistry = serviceRegistry;
    }
    @GetMapping("/services/{domaineServiceName}")
    public ResponseEntity<ObjectNode> getServices(@PathVariable String domaineServiceName) throws JsonProcessingException {
        ObjectNode objectNode = new ObjectNode(JsonNodeFactory.instance);
        Annotation[] annotations = getClassByName(domaineServiceName).getAnnotations();
        ObjectMapper mapper = new ObjectMapper();

        for (Annotation annotation : annotations) {
            Map<String, Object> annotationValues = new LinkedHashMap<>();
            for (Method method : annotation.annotationType().getDeclaredMethods()) {
                try {
                    // Enforce access if necessary and invoke the attribute method
                    method.setAccessible(true);
                    Object value = method.invoke(annotation);

                    // Skip default base methods like toString or annotationType
                    annotationValues.put(method.getName(), value);
                } catch (Exception e) {
                    annotationValues.put(method.getName(), "Error reading value");
                }
            }
            objectNode.set(annotation.annotationType().getSimpleName(), mapper.valueToTree(annotationValues));
        }


        return ResponseEntity.ok(objectNode);
    }

    @GetMapping("/services/{domaineServiceName}/actions/{actionName}/invoke")
    public ResponseEntity<ObjectNode> getClasses(@PathVariable String domaineServiceName) {

        return ResponseEntity.ok().build();
    }
    private Class<?> getClassByName(String domaineServiceName) throws IllegalArgumentException{
        return (metaModelService.lookupLogicalTypeByName(domaineServiceName)
                .map(LogicalType::correspondingClass)
                .orElseThrow(() -> new IllegalArgumentException("No domain service found for name: " + domaineServiceName)));

    }
}
