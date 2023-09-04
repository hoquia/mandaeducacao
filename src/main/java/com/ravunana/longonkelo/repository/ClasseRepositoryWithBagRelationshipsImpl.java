package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.Classe;
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
public class ClasseRepositoryWithBagRelationshipsImpl implements ClasseRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Classe> fetchBagRelationships(Optional<Classe> classe) {
        return classe.map(this::fetchNivesEnsinos);
    }

    @Override
    public Page<Classe> fetchBagRelationships(Page<Classe> classes) {
        return new PageImpl<>(fetchBagRelationships(classes.getContent()), classes.getPageable(), classes.getTotalElements());
    }

    @Override
    public List<Classe> fetchBagRelationships(List<Classe> classes) {
        return Optional.of(classes).map(this::fetchNivesEnsinos).orElse(Collections.emptyList());
    }

    Classe fetchNivesEnsinos(Classe result) {
        return entityManager
            .createQuery("select classe from Classe classe left join fetch classe.nivesEnsinos where classe is :classe", Classe.class)
            .setParameter("classe", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Classe> fetchNivesEnsinos(List<Classe> classes) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, classes.size()).forEach(index -> order.put(classes.get(index).getId(), index));
        List<Classe> result = entityManager
            .createQuery(
                "select distinct classe from Classe classe left join fetch classe.nivesEnsinos where classe in :classes",
                Classe.class
            )
            .setParameter("classes", classes)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
