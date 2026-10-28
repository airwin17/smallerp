package base.repositories;

import base.models.ProductTemplate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTemplateRepository extends JpaRepository<ProductTemplate,Long> {

    List<ProductTemplateRepository> findByNameContainingIgnoreCase(String name);
}
