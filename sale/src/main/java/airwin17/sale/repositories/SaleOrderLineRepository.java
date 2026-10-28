package airwin17.sale.repositories;

import airwin17.sale.models.SaleOrderLine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleOrderLineRepository extends JpaRepository<SaleOrderLine, Long> {
    List<SaleOrderLine> findAll();
}
