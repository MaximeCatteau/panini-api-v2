package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM trades WHERE trade_status not in ('TRADE_FINISHED', 'TRADE_CANCELLED')")
    List<Trade> getAllTrades();
}
