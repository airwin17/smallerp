package base.managers;

import base.Services.UserService;
import base.models.User;
import base.repositories.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.PromptStyle;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.SemanticsOf;

import java.util.List;

import static base.BaseApplication.NAMESPACE;

@DomainObject(nature = Nature.VIEW_MODEL)
@Named(NAMESPACE+".UserManager")
public class UserManager {
    @Inject private UserRepository userRepository;
    @Inject private UserService userService;
    @Property
    private String search;
    @Action(semantics = SemanticsOf.SAFE)
    public List<User> getAllUsers(@Parameter(optionality = Optionality.OPTIONAL) String search) {
        if(search != null)
            return List.of((User) userService.loadUserByUsername(search));
        return userRepository.findAll();
    }
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public UserManager createNewUser(String username, String password) {
        userService.newUser(username, password);
        return this;
    }
}
