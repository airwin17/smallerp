package base.repositories;

import base.models.UomCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UomCategoryRepository extends JpaRepository<UomCategory, Long> {
    List<UomCategory> findByName(String name);
}
