package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.TransferenciaSaldo;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class TransferenciaSaldoRepositoryWithBagRelationshipsImpl implements TransferenciaSaldoRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<TransferenciaSaldo> fetchBagRelationships(Optional<TransferenciaSaldo> transferenciaSaldo) {
        return transferenciaSaldo.map(this::fetchTransacoes);
    }

    @Override
    public Page<TransferenciaSaldo> fetchBagRelationships(Page<TransferenciaSaldo> transferenciaSaldos) {
        return new PageImpl<>(
            fetchBagRelationships(transferenciaSaldos.getContent()),
            transferenciaSaldos.getPageable(),
            transferenciaSaldos.getTotalElements()
        );
    }

    @Override
    public List<TransferenciaSaldo> fetchBagRelationships(List<TransferenciaSaldo> transferenciaSaldos) {
        return Optional.of(transferenciaSaldos).map(this::fetchTransacoes).orElse(Collections.emptyList());
    }

    TransferenciaSaldo fetchTransacoes(TransferenciaSaldo result) {
        return entityManager
            .createQuery(
                "select transferenciaSaldo from TransferenciaSaldo transferenciaSaldo left join fetch transferenciaSaldo.transacoes where transferenciaSaldo is :transferenciaSaldo",
                TransferenciaSaldo.class
            )
            .setParameter("transferenciaSaldo", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<TransferenciaSaldo> fetchTransacoes(List<TransferenciaSaldo> transferenciaSaldos) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, transferenciaSaldos.size()).forEach(index -> order.put(transferenciaSaldos.get(index).getId(), index));
        List<TransferenciaSaldo> result = entityManager
            .createQuery(
                "select distinct transferenciaSaldo from TransferenciaSaldo transferenciaSaldo left join fetch transferenciaSaldo.transacoes where transferenciaSaldo in :transferenciaSaldos",
                TransferenciaSaldo.class
            )
            .setParameter("transferenciaSaldos", transferenciaSaldos)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
