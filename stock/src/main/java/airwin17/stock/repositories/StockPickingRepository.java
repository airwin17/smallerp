package airwin17.stock.repositories;

import airwin17.stock.models.StockPicking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPickingRepository extends JpaRepository<StockPicking, Long> {
}
