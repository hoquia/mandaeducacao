package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.PeriodoLancamentoNota;
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
public class PeriodoLancamentoNotaRepositoryWithBagRelationshipsImpl implements PeriodoLancamentoNotaRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PeriodoLancamentoNota> fetchBagRelationships(Optional<PeriodoLancamentoNota> periodoLancamentoNota) {
        return periodoLancamentoNota.map(this::fetchClasses);
    }

    @Override
    public Page<PeriodoLancamentoNota> fetchBagRelationships(Page<PeriodoLancamentoNota> periodoLancamentoNotas) {
        return new PageImpl<>(
            fetchBagRelationships(periodoLancamentoNotas.getContent()),
            periodoLancamentoNotas.getPageable(),
            periodoLancamentoNotas.getTotalElements()
        );
    }

    @Override
    public List<PeriodoLancamentoNota> fetchBagRelationships(List<PeriodoLancamentoNota> periodoLancamentoNotas) {
        return Optional.of(periodoLancamentoNotas).map(this::fetchClasses).orElse(Collections.emptyList());
    }

    PeriodoLancamentoNota fetchClasses(PeriodoLancamentoNota result) {
        return entityManager
            .createQuery(
                "select periodoLancamentoNota from PeriodoLancamentoNota periodoLancamentoNota left join fetch periodoLancamentoNota.classes where periodoLancamentoNota is :periodoLancamentoNota",
                PeriodoLancamentoNota.class
            )
            .setParameter("periodoLancamentoNota", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<PeriodoLancamentoNota> fetchClasses(List<PeriodoLancamentoNota> periodoLancamentoNotas) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, periodoLancamentoNotas.size()).forEach(index -> order.put(periodoLancamentoNotas.get(index).getId(), index));
        List<PeriodoLancamentoNota> result = entityManager
            .createQuery(
                "select distinct periodoLancamentoNota from PeriodoLancamentoNota periodoLancamentoNota left join fetch periodoLancamentoNota.classes where periodoLancamentoNota in :periodoLancamentoNotas",
                PeriodoLancamentoNota.class
            )
            .setParameter("periodoLancamentoNotas", periodoLancamentoNotas)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
