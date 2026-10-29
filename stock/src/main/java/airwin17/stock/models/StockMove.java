package airwin17.stock.models;

import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.Data;

import main.models.Product;

import main.models.Uom;

import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Title;

import java.math.BigDecimal;
import java.time.LocalDate;

import static airwin17.stock.StockApplication.NAMESPACE;

@Entity
@Data
@Named(NAMESPACE+".StockMove")
@DomainObject(nature = Nature.ENTITY)
public class StockMove {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)          // optional: direct receive has no picking
    private StockPicking picking;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Product product;

    @Column(nullable = false)
    private BigDecimal quantity;

    @ManyToOne(optional = false)
    private Uom uom;

    @ManyToOne(optional = false)
    private StockLocation source;

    @ManyToOne(optional = false)
    private StockLocation destination;

    @Enumerated(EnumType.STRING)
    private State state = State.DRAFT;

    private LocalDate doneOn;

    @Title
    public String title() {
        return quantity + " " + product.getName();
    }

    public void complete(LocalDate on) {
        state = State.DONE;
        doneOn = on;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id != null && id.equals(((StockMove) o).id);
    }
}
