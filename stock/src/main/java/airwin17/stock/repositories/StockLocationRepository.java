package airwin17.stock.repositories;

import airwin17.stock.models.StockLocation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockLocationRepository extends JpaRepository<StockLocation, Long> {
}
