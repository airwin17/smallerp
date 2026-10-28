package airwin17.account.models;

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

import lombok.Data;

import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Nature;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static airwin17.account.AccountApplication.NAMESPACE;

@DomainObject(nature = Nature.ENTITY)
@Named(NAMESPACE+".AcountMove")
@Entity
@Data
public class AccountMove {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Journal journal;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private State state = State.ACCOUNT_MOVE_DRAFT;

    private String origin;

    @OneToMany(mappedBy = "move", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountMoveLine> lines = new ArrayList<>();

    public void post() {
        BigDecimal debit = lines.stream().map(AccountMoveLine::getDebit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal credit = lines.stream().map(AccountMoveLine::getCredit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (debit.compareTo(credit) != 0) {
            throw new IllegalStateException("Entry does not balance");
        }
        state = State.ACCOUNT_MOVE_POSTED;
    }

}
