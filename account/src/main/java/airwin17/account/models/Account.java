package airwin17.account.models;

import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;

import lombok.Data;
import lombok.ToString;

import org.apache.causeway.applib.annotation.Title;

import static airwin17.account.AccountApplication.NAMESPACE;

@Entity
@Named(NAMESPACE+".Account")
@Data
@ToString(onlyExplicitlyIncluded = true)
public class Account {

    public enum Type { VIEW, RECEIVABLE, PAYABLE, INCOME, EXPENSE, CASH, TAX }

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Title
    @ToString.Include
    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    private boolean reconcile;          // receivable and payable only

    @ManyToOne(fetch = FetchType.LAZY)
    private Account parent;             // VIEW groups; no children collection

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id != null && id.equals(((Account) o).id);
    }

    @Override
    public int hashCode() { return getClass().hashCode(); }
}
