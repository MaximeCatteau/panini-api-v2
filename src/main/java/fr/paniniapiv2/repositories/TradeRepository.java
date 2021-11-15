package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM trades WHERE trade_status not in ('TRADE_FINISHED', 'TRADE_CANCELLED')")
    List<Trade> getAllTradesStepOne();

    @Query(nativeQuery = true, value = "SELECT count(*) FROM trade_propositions WHERE trade_id = :tradeId")
    Integer getPropositionCountForTrade(@Param(value = "tradeId") int tradeId);

    @Query(nativeQuery = true, value = "SELECT * FROM trades WHERE trade_status not in ('TRADE_FINISHED', 'TRADE_CANCELLED') AND transmitter_id = :playerId")
    List<Trade> getAllTradesStepOneForPlayer(@Param("playerId") Long playerId);

    @Query(nativeQuery = true, value = "select * from trades t where t.trade_status = 'TRADE_PROPOSED' and t.transmitter_id = :playerId")
    List<Trade> getAllTradesProposedForPlayer(@Param("playerId") Long playerId);
}
