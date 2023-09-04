package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.NaturezaTrabalho;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NaturezaTrabalho entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NaturezaTrabalhoRepository extends JpaRepository<NaturezaTrabalho, Long>, JpaSpecificationExecutor<NaturezaTrabalho> {}
