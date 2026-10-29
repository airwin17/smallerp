package airwin17.stock.repositories;

import airwin17.stock.models.State;
import airwin17.stock.models.StockMove;

import airwin17.stock.models.Usage;


import main.models.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMoveRepository extends JpaRepository<StockMove, Long> {
    List<StockMove> findByDestinationUsageAndProductAndState(Usage destination, Product product, State state);
    List<StockMove> findBySourceUsageAndProductAndState(Usage source, Product product, State state);

}
