package airwin17.stock.models;

import base.models.Partner;
import base.models.Product;
import base.models.Uom;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import jakarta.persistence.Transient;

import lombok.Data;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.repository.RepositoryService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static airwin17.stock.StockApplication.NAMESPACE;

@Entity
@Data
@Named(NAMESPACE+".StockPicking")
public class StockPicking {


    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private State state = State.DRAFT;

    @ManyToOne
    private Partner partner;

    @ManyToOne(optional = false)
    private StockLocation source;

    @ManyToOne(optional = false)
    private StockLocation destination;

    private String origin;          // sale order name, copied; not a sale dependency
    private LocalDate scheduledOn;

    @OneToMany(mappedBy = "picking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockMove> moves = new ArrayList<>();

    @Inject @Transient
    private RepositoryService repositoryService;

    public String title() {
        return (type == Type.IN ? "IN/" : "OUT/") + id;
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public StockMove addMove(Product product, BigDecimal quantity, Uom uom) {
        StockMove move = new StockMove();
        move.setPicking(this);
        move.setProduct(product);
        move.setQuantity(quantity);
        move.setUom(uom);
        move.setSource(source);
        move.setDestination(destination);
        moves.add(move);
        return repositoryService.persistAndFlush(move);
    }

    @Action
    public StockPicking confirm() {
        if (moves.isEmpty()) throw new IllegalStateException("Picking has no moves");
        LocalDate today = LocalDate.now();
        moves.forEach(m -> m.complete(today));
        state = State.DONE;
        return this;
    }

    public String disableConfirm() {
        return state == State.DONE ? "Already done" : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id != null && id.equals(((StockPicking) o).id);
    }

    @Override
    public int hashCode() { return getClass().hashCode(); }
}
