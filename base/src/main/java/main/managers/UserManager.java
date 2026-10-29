package main.managers;

import java.util.List;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.SemanticsOf;

import main.Services.UserService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import main.models.User;
import main.repositories.UserRepository;

import static main.BaseApplication.NAMESPACE;

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
