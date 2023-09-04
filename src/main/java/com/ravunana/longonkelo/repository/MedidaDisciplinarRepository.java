package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.MedidaDisciplinar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MedidaDisciplinar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedidaDisciplinarRepository extends JpaRepository<MedidaDisciplinar, Long>, JpaSpecificationExecutor<MedidaDisciplinar> {}
