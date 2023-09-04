package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.PlanoDesconto;
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
public class PlanoDescontoRepositoryWithBagRelationshipsImpl implements PlanoDescontoRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PlanoDesconto> fetchBagRelationships(Optional<PlanoDesconto> planoDesconto) {
        return planoDesconto.map(this::fetchCategoriasEmolumentos);
    }

    @Override
    public Page<PlanoDesconto> fetchBagRelationships(Page<PlanoDesconto> planoDescontos) {
        return new PageImpl<>(
            fetchBagRelationships(planoDescontos.getContent()),
            planoDescontos.getPageable(),
            planoDescontos.getTotalElements()
        );
    }

    @Override
    public List<PlanoDesconto> fetchBagRelationships(List<PlanoDesconto> planoDescontos) {
        return Optional.of(planoDescontos).map(this::fetchCategoriasEmolumentos).orElse(Collections.emptyList());
    }

    PlanoDesconto fetchCategoriasEmolumentos(PlanoDesconto result) {
        return entityManager
            .createQuery(
                "select planoDesconto from PlanoDesconto planoDesconto left join fetch planoDesconto.categoriasEmolumentos where planoDesconto is :planoDesconto",
                PlanoDesconto.class
            )
            .setParameter("planoDesconto", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<PlanoDesconto> fetchCategoriasEmolumentos(List<PlanoDesconto> planoDescontos) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, planoDescontos.size()).forEach(index -> order.put(planoDescontos.get(index).getId(), index));
        List<PlanoDesconto> result = entityManager
            .createQuery(
                "select distinct planoDesconto from PlanoDesconto planoDesconto left join fetch planoDesconto.categoriasEmolumentos where planoDesconto in :planoDescontos",
                PlanoDesconto.class
            )
            .setParameter("planoDescontos", planoDescontos)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
