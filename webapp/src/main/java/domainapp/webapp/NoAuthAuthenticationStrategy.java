package domainapp.webapp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.causeway.applib.services.iactnlayer.InteractionContext;
import org.apache.causeway.applib.services.user.UserMemento;
import org.apache.causeway.viewer.restfulobjects.viewer.webmodule.auth.AuthenticationStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("noAuthAuthenticationStrategy")
public class NoAuthAuthenticationStrategy implements AuthenticationStrategy {

    @Override
    public InteractionContext lookupValid(
            final HttpServletRequest request,
            final HttpServletResponse response) {

        return InteractionContext.ofUserWithSystemDefaults(
                UserMemento.ofName("dev")
                        .withRoleAdded("admin_role") // Use standard app roles if any
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