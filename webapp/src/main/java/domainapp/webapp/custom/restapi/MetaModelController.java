package domainapp.webapp.custom.restapi;

import jakarta.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/webapp")
public class MetaModelController {
    private final ApplicationContext applicationContext;

    public MetaModelController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    @GetMapping("/modules")
    public String[] getActiveApplicationModules() {
        // Option A: Search for your internal custom domain module configuration beans
        // Replace "com.yourcompany.modules" with the root package of your business modules
        return new String[]{
                "base", "product", "g"};
    }
}
