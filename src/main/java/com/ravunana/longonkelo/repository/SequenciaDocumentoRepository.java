package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.SequenciaDocumento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SequenciaDocumento entity.
 */
@Repository
public interface SequenciaDocumentoRepository
    extends JpaRepository<SequenciaDocumento, Long>, JpaSpecificationExecutor<SequenciaDocumento> {
    default Optional<SequenciaDocumento> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SequenciaDocumento> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SequenciaDocumento> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct sequenciaDocumento from SequenciaDocumento sequenciaDocumento left join fetch sequenciaDocumento.serie",
        countQuery = "select count(distinct sequenciaDocumento) from SequenciaDocumento sequenciaDocumento"
    )
    Page<SequenciaDocumento> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct sequenciaDocumento from SequenciaDocumento sequenciaDocumento left join fetch sequenciaDocumento.serie")
    List<SequenciaDocumento> findAllWithToOneRelationships();

    @Query(
        "select sequenciaDocumento from SequenciaDocumento sequenciaDocumento left join fetch sequenciaDocumento.serie where sequenciaDocumento.id =:id"
    )
    Optional<SequenciaDocumento> findOneWithToOneRelationships(@Param("id") Long id);
}
