package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.Matricula;
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
public class MatriculaRepositoryWithBagRelationshipsImpl implements MatriculaRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Matricula> fetchBagRelationships(Optional<Matricula> matricula) {
        return matricula.map(this::fetchCategoriasMatriculas);
    }

    @Override
    public Page<Matricula> fetchBagRelationships(Page<Matricula> matriculas) {
        return new PageImpl<>(fetchBagRelationships(matriculas.getContent()), matriculas.getPageable(), matriculas.getTotalElements());
    }

    @Override
    public List<Matricula> fetchBagRelationships(List<Matricula> matriculas) {
        return Optional.of(matriculas).map(this::fetchCategoriasMatriculas).orElse(Collections.emptyList());
    }

    Matricula fetchCategoriasMatriculas(Matricula result) {
        return entityManager
            .createQuery(
                "select matricula from Matricula matricula left join fetch matricula.categoriasMatriculas where matricula is :matricula",
                Matricula.class
            )
            .setParameter("matricula", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Matricula> fetchCategoriasMatriculas(List<Matricula> matriculas) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, matriculas.size()).forEach(index -> order.put(matriculas.get(index).getId(), index));
        List<Matricula> result = entityManager
            .createQuery(
                "select distinct matricula from Matricula matricula left join fetch matricula.categoriasMatriculas where matricula in :matriculas",
                Matricula.class
            )
            .setParameter("matriculas", matriculas)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
