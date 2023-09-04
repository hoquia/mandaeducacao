package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.DisciplinaCurricular;
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
public class DisciplinaCurricularRepositoryWithBagRelationshipsImpl implements DisciplinaCurricularRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<DisciplinaCurricular> fetchBagRelationships(Optional<DisciplinaCurricular> disciplinaCurricular) {
        return disciplinaCurricular.map(this::fetchPlanosCurriculars);
    }

    @Override
    public Page<DisciplinaCurricular> fetchBagRelationships(Page<DisciplinaCurricular> disciplinaCurriculars) {
        return new PageImpl<>(
            fetchBagRelationships(disciplinaCurriculars.getContent()),
            disciplinaCurriculars.getPageable(),
            disciplinaCurriculars.getTotalElements()
        );
    }

    @Override
    public List<DisciplinaCurricular> fetchBagRelationships(List<DisciplinaCurricular> disciplinaCurriculars) {
        return Optional.of(disciplinaCurriculars).map(this::fetchPlanosCurriculars).orElse(Collections.emptyList());
    }

    DisciplinaCurricular fetchPlanosCurriculars(DisciplinaCurricular result) {
        return entityManager
            .createQuery(
                "select disciplinaCurricular from DisciplinaCurricular disciplinaCurricular left join fetch disciplinaCurricular.planosCurriculars where disciplinaCurricular is :disciplinaCurricular",
                DisciplinaCurricular.class
            )
            .setParameter("disciplinaCurricular", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<DisciplinaCurricular> fetchPlanosCurriculars(List<DisciplinaCurricular> disciplinaCurriculars) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, disciplinaCurriculars.size()).forEach(index -> order.put(disciplinaCurriculars.get(index).getId(), index));
        List<DisciplinaCurricular> result = entityManager
            .createQuery(
                "select distinct disciplinaCurricular from DisciplinaCurricular disciplinaCurricular left join fetch disciplinaCurricular.planosCurriculars where disciplinaCurricular in :disciplinaCurriculars",
                DisciplinaCurricular.class
            )
            .setParameter("disciplinaCurriculars", disciplinaCurriculars)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
