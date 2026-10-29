package main.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.models.ProductTemplate;

@Repository
public interface ProductTemplateRepository extends JpaRepository<ProductTemplate,Long> {

    List<ProductTemplateRepository> findByNameContainingIgnoreCase(String name);
}
