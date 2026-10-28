package base.repositories;

import base.models.Uom;


import base.models.UomCategory;
import base.models.UomType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UomRepository extends JpaRepository<Uom,Long> {
    List<Uom> findByName(String name);
    List<Uom> findAllByTypeAndCategoryName(UomType type, String category);
    Uom findByTypeAndCategoryName(UomType type,String category);
}
