package airwin17.account.models;

import airwin17.account.AccountApplication;
import base.models.Partner;
import jakarta.inject.Named;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import lombok.Data;

import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Nature;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static airwin17.account.AccountApplication.NAMESPACE;

@Data
@Entity
@DomainObject(nature = Nature.ENTITY)
@Named(NAMESPACE +".Invoice")
public class Invoice {
    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type = Type.INVOICE_OUT;

    @Enumerated(EnumType.STRING)
    private State state = State.INVOICE_DRAFT;

    @ManyToOne(optional = false)
    private Partner partner;

    @ManyToOne(optional = false)
    private Journal journal;

    private LocalDate date;
    private String origin;                 // order name, copied; sale is not a dependency

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceLine> lines = new ArrayList<>();

    @OneToOne
    private AccountMove move;

    public BigDecimal getTotal() {
        return lines.stream().map(InvoiceLine::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
