package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.EstadoDissertacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EstadoDissertacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoDissertacaoRepository extends JpaRepository<EstadoDissertacao, Long>, JpaSpecificationExecutor<EstadoDissertacao> {}
