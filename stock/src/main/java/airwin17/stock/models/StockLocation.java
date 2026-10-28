package airwin17.stock.models;

import jakarta.inject.Named;
import jakarta.persistence.Entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.Data;

import lombok.ToString;

import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Title;

import java.util.List;

import static airwin17.stock.StockApplication.NAMESPACE;

@Entity
@Data
@DomainObject(nature = Nature.ENTITY)
@Named(NAMESPACE+".StockLocation")
public class StockLocation {


    @Id @GeneratedValue
    private Long id;

    @Title
    @ToString.Include
    private String name;

    @Enumerated(EnumType.STRING)
    private Usage usage;

    @ManyToOne(fetch = FetchType.LAZY)
    private StockLocation parent;

    public String validateParent(StockLocation proposed) {
        if (proposed == null || id == null) return null;
        if (id.equals(proposed.getId())) return "A location cannot be its own parent";
        for (StockLocation c = proposed; c != null; c = c.getParent()) {
            if (id.equals(c.getId())) return "Parent would create a cycle";
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id != null && id.equals(((StockLocation) o).id);
    }
}
