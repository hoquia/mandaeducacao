package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.DocumentoComercial;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DocumentoComercial entity.
 */
@Repository
public interface DocumentoComercialRepository
    extends JpaRepository<DocumentoComercial, Long>, JpaSpecificationExecutor<DocumentoComercial> {
    default Optional<DocumentoComercial> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DocumentoComercial> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DocumentoComercial> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct documentoComercial from DocumentoComercial documentoComercial left join fetch documentoComercial.transformaEm",
        countQuery = "select count(distinct documentoComercial) from DocumentoComercial documentoComercial"
    )
    Page<DocumentoComercial> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct documentoComercial from DocumentoComercial documentoComercial left join fetch documentoComercial.transformaEm")
    List<DocumentoComercial> findAllWithToOneRelationships();

    @Query(
        "select documentoComercial from DocumentoComercial documentoComercial left join fetch documentoComercial.transformaEm where documentoComercial.id =:id"
    )
    Optional<DocumentoComercial> findOneWithToOneRelationships(@Param("id") Long id);
}
