package base.models;

import base.managers.ProductTemplateManager;
import base.repositories.ProductTemplateRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import lombok.Data;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.services.repository.RepositoryService;

import java.util.List;

@Entity
@Data
@DomainObject(
        autoCompleteMethod = "findByNameContainingIgnoreCase",
        autoCompleteRepository = ProductTemplateRepository.class
)
@Named("product.productTemplate")
public class ProductTemplate {
    @Inject private RepositoryService repositoryService;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "template")
    private List<Product> variants;
    @Action
    public ProductTemplateManager back(){
        return new ProductTemplateManager();
    }
}
