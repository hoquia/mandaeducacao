package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.CampoActuacaoDissertacao;
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
public class CampoActuacaoDissertacaoRepositoryWithBagRelationshipsImpl implements CampoActuacaoDissertacaoRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<CampoActuacaoDissertacao> fetchBagRelationships(Optional<CampoActuacaoDissertacao> campoActuacaoDissertacao) {
        return campoActuacaoDissertacao.map(this::fetchCursos);
    }

    @Override
    public Page<CampoActuacaoDissertacao> fetchBagRelationships(Page<CampoActuacaoDissertacao> campoActuacaoDissertacaos) {
        return new PageImpl<>(
            fetchBagRelationships(campoActuacaoDissertacaos.getContent()),
            campoActuacaoDissertacaos.getPageable(),
            campoActuacaoDissertacaos.getTotalElements()
        );
    }

    @Override
    public List<CampoActuacaoDissertacao> fetchBagRelationships(List<CampoActuacaoDissertacao> campoActuacaoDissertacaos) {
        return Optional.of(campoActuacaoDissertacaos).map(this::fetchCursos).orElse(Collections.emptyList());
    }

    CampoActuacaoDissertacao fetchCursos(CampoActuacaoDissertacao result) {
        return entityManager
            .createQuery(
                "select campoActuacaoDissertacao from CampoActuacaoDissertacao campoActuacaoDissertacao left join fetch campoActuacaoDissertacao.cursos where campoActuacaoDissertacao is :campoActuacaoDissertacao",
                CampoActuacaoDissertacao.class
            )
            .setParameter("campoActuacaoDissertacao", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<CampoActuacaoDissertacao> fetchCursos(List<CampoActuacaoDissertacao> campoActuacaoDissertacaos) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream
            .range(0, campoActuacaoDissertacaos.size())
            .forEach(index -> order.put(campoActuacaoDissertacaos.get(index).getId(), index));
        List<CampoActuacaoDissertacao> result = entityManager
            .createQuery(
                "select distinct campoActuacaoDissertacao from CampoActuacaoDissertacao campoActuacaoDissertacao left join fetch campoActuacaoDissertacao.cursos where campoActuacaoDissertacao in :campoActuacaoDissertacaos",
                CampoActuacaoDissertacao.class
            )
            .setParameter("campoActuacaoDissertacaos", campoActuacaoDissertacaos)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
