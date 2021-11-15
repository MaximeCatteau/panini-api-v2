package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.Trade;
import fr.paniniapiv2.db.TradeProposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TradePropositionRepository extends JpaRepository<TradeProposition, Integer> {
    @Query(nativeQuery = true, value = "select * from trade_propositions tp where tp.recipient_id = :playerId")
    List<TradeProposition> getAllTradePropositionsByPlayer(@Param("playerId") Long playerId);

    @Query(nativeQuery = true, value = "select * from trade_propositions tp where tp.trade_id = :tradeId")
    List<TradeProposition> getPropositionsForTrade(@Param("tradeId") int tradeId);
}
