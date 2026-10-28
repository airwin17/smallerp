package airwin17.account.models;

import base.models.Partner;
import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.Data;

import java.math.BigDecimal;

import static airwin17.account.AccountApplication.NAMESPACE;

@Data
@Entity
@Named(NAMESPACE+".AccountMoveLine")
public class AccountMoveLine {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AccountMove move;

    @ManyToOne(optional = false)
    private Account account;

    @ManyToOne
    private Partner partner;

    private String label;

    private BigDecimal debit = BigDecimal.ZERO;
    private BigDecimal credit = BigDecimal.ZERO;

    @ManyToOne
    private AccountMoveLine reconciledWith;
}
