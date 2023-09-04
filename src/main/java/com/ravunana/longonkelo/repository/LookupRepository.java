package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.Lookup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Lookup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LookupRepository extends JpaRepository<Lookup, Long>, JpaSpecificationExecutor<Lookup> {}
