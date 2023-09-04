package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.SerieDocumento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SerieDocumento entity.
 */
@Repository
public interface SerieDocumentoRepository extends JpaRepository<SerieDocumento, Long>, JpaSpecificationExecutor<SerieDocumento> {
    default Optional<SerieDocumento> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SerieDocumento> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SerieDocumento> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct serieDocumento from SerieDocumento serieDocumento left join fetch serieDocumento.tipoDocumento",
        countQuery = "select count(distinct serieDocumento) from SerieDocumento serieDocumento"
    )
    Page<SerieDocumento> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct serieDocumento from SerieDocumento serieDocumento left join fetch serieDocumento.tipoDocumento")
    List<SerieDocumento> findAllWithToOneRelationships();

    @Query(
        "select serieDocumento from SerieDocumento serieDocumento left join fetch serieDocumento.tipoDocumento where serieDocumento.id =:id"
    )
    Optional<SerieDocumento> findOneWithToOneRelationships(@Param("id") Long id);
}
