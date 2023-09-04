package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular;
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
public class EstadoDisciplinaCurricularRepositoryWithBagRelationshipsImpl
    implements EstadoDisciplinaCurricularRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<EstadoDisciplinaCurricular> fetchBagRelationships(Optional<EstadoDisciplinaCurricular> estadoDisciplinaCurricular) {
        return estadoDisciplinaCurricular.map(this::fetchDisciplinasCurriculars);
    }

    @Override
    public Page<EstadoDisciplinaCurricular> fetchBagRelationships(Page<EstadoDisciplinaCurricular> estadoDisciplinaCurriculars) {
        return new PageImpl<>(
            fetchBagRelationships(estadoDisciplinaCurriculars.getContent()),
            estadoDisciplinaCurriculars.getPageable(),
            estadoDisciplinaCurriculars.getTotalElements()
        );
    }

    @Override
    public List<EstadoDisciplinaCurricular> fetchBagRelationships(List<EstadoDisciplinaCurricular> estadoDisciplinaCurriculars) {
        return Optional.of(estadoDisciplinaCurriculars).map(this::fetchDisciplinasCurriculars).orElse(Collections.emptyList());
    }

    EstadoDisciplinaCurricular fetchDisciplinasCurriculars(EstadoDisciplinaCurricular result) {
        return entityManager
            .createQuery(
                "select estadoDisciplinaCurricular from EstadoDisciplinaCurricular estadoDisciplinaCurricular left join fetch estadoDisciplinaCurricular.disciplinasCurriculars where estadoDisciplinaCurricular is :estadoDisciplinaCurricular",
                EstadoDisciplinaCurricular.class
            )
            .setParameter("estadoDisciplinaCurricular", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<EstadoDisciplinaCurricular> fetchDisciplinasCurriculars(List<EstadoDisciplinaCurricular> estadoDisciplinaCurriculars) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream
            .range(0, estadoDisciplinaCurriculars.size())
            .forEach(index -> order.put(estadoDisciplinaCurriculars.get(index).getId(), index));
        List<EstadoDisciplinaCurricular> result = entityManager
            .createQuery(
                "select distinct estadoDisciplinaCurricular from EstadoDisciplinaCurricular estadoDisciplinaCurricular left join fetch estadoDisciplinaCurricular.disciplinasCurriculars where estadoDisciplinaCurricular in :estadoDisciplinaCurriculars",
                EstadoDisciplinaCurricular.class
            )
            .setParameter("estadoDisciplinaCurriculars", estadoDisciplinaCurriculars)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
