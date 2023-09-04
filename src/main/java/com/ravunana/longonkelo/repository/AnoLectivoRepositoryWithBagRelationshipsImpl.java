package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.AnoLectivo;
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
public class AnoLectivoRepositoryWithBagRelationshipsImpl implements AnoLectivoRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<AnoLectivo> fetchBagRelationships(Optional<AnoLectivo> anoLectivo) {
        return anoLectivo.map(this::fetchNivesEnsinos);
    }

    @Override
    public Page<AnoLectivo> fetchBagRelationships(Page<AnoLectivo> anoLectivos) {
        return new PageImpl<>(fetchBagRelationships(anoLectivos.getContent()), anoLectivos.getPageable(), anoLectivos.getTotalElements());
    }

    @Override
    public List<AnoLectivo> fetchBagRelationships(List<AnoLectivo> anoLectivos) {
        return Optional.of(anoLectivos).map(this::fetchNivesEnsinos).orElse(Collections.emptyList());
    }

    AnoLectivo fetchNivesEnsinos(AnoLectivo result) {
        return entityManager
            .createQuery(
                "select anoLectivo from AnoLectivo anoLectivo left join fetch anoLectivo.nivesEnsinos where anoLectivo is :anoLectivo",
                AnoLectivo.class
            )
            .setParameter("anoLectivo", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<AnoLectivo> fetchNivesEnsinos(List<AnoLectivo> anoLectivos) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, anoLectivos.size()).forEach(index -> order.put(anoLectivos.get(index).getId(), index));
        List<AnoLectivo> result = entityManager
            .createQuery(
                "select distinct anoLectivo from AnoLectivo anoLectivo left join fetch anoLectivo.nivesEnsinos where anoLectivo in :anoLectivos",
                AnoLectivo.class
            )
            .setParameter("anoLectivos", anoLectivos)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
