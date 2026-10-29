package main.models;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.Title;

import lombok.Data;

import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import main.managers.UomCategoryManager;
import main.repositories.UomCategoryRepository;

@Entity
@Named("product.UomCategories")
@DomainObject(
        autoCompleteRepository = UomCategoryRepository.class,
        autoCompleteMethod="findByName"
)

@Data
public class UomCategory {

    @Id @GeneratedValue
    private Long id;


    @Title
    @Property(editing = Editing.ENABLED)
    @Column(nullable = false)
    private String name;
    public  UomCategory(String name){
        this.name = name;
    }
    public  UomCategory(){

    }
    @Action
    public UomCategoryManager back(){
        return new UomCategoryManager();
    }


}
