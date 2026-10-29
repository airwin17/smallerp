package main.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.models.Uom;
import main.models.UomType;

@Repository
public interface UomRepository extends JpaRepository<Uom,Long> {
    List<Uom> findByName(String name);
    List<Uom> findAllByTypeAndCategoryName(UomType type, String category);
    Uom findByTypeAndCategoryName(UomType type, String category);
}
