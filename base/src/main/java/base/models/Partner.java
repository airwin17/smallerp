package base.models;

import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.Data;

import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.persistence.jpa.applib.integration.CausewayEntityListener;

@Entity
@Data
@Named("base.Partner")
@DomainObject
@EntityListeners(CausewayEntityListener.class)
public class Partner {
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(named = "partner Name")
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean isInstitution;
    private String address;
    private String phoneNumber;
    private String email;
    private boolean customer;
    private boolean supplier;
    private boolean active;
    @ManyToOne
    private Partner parent;
    public Partner()
    {
    }
    public Partner(String name) {
        this.name = name;
    }
}
