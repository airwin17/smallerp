package main.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.models.UomCategory;

@Repository
public interface UomCategoryRepository extends JpaRepository<UomCategory, Long> {
    List<UomCategory> findByName(String name);
}
