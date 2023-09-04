package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.MeioPagamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MeioPagamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeioPagamentoRepository extends JpaRepository<MeioPagamento, Long>, JpaSpecificationExecutor<MeioPagamento> {}
