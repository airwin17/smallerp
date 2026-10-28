package airwin17.account.models;

import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;

import lombok.Data;

import org.apache.causeway.applib.annotation.Title;

import static airwin17.account.AccountApplication.NAMESPACE;

@Entity
@Named(NAMESPACE+".Journal")
@Data
public class Journal {
    public enum Type { SALE, CASH, GENERAL }

    @Id @GeneratedValue
    private Long id;

    @Title
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    private Account defaultAccount;
}
