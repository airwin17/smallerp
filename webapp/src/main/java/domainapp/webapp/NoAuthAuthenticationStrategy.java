package domainapp.webapp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.causeway.applib.services.iactnlayer.InteractionContext;
import org.apache.causeway.applib.services.user.UserMemento;
import org.apache.causeway.viewer.restfulobjects.viewer.webmodule.auth.AuthenticationStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoAuthAuthenticationStrategy implements AuthenticationStrategy {

    @Override
    public InteractionContext lookupValid(HttpServletRequest request, HttpServletResponse response) {
        return InteractionContext.ofUserWithSystemDefaults(
                UserMemento.ofNameAndRoleNames("dev", "causeway-ext-secman-user")
        );
    }

    @Override
    public void bind(HttpServletRequest request, HttpServletResponse response, InteractionContext auth) {
        // no-op
    }

    @Override
    public void invalidate(HttpServletRequest request, HttpServletResponse response) {
        // no-op
    }
}