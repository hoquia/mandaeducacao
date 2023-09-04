package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.AnexoDiscente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AnexoDiscente entity.
 */
@Repository
public interface AnexoDiscenteRepository extends JpaRepository<AnexoDiscente, Long> {
    default Optional<AnexoDiscente> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AnexoDiscente> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AnexoDiscente> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct anexoDiscente from AnexoDiscente anexoDiscente left join fetch anexoDiscente.discente",
        countQuery = "select count(distinct anexoDiscente) from AnexoDiscente anexoDiscente"
    )
    Page<AnexoDiscente> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct anexoDiscente from AnexoDiscente anexoDiscente left join fetch anexoDiscente.discente")
    List<AnexoDiscente> findAllWithToOneRelationships();

    @Query("select anexoDiscente from AnexoDiscente anexoDiscente left join fetch anexoDiscente.discente where anexoDiscente.id =:id")
    Optional<AnexoDiscente> findOneWithToOneRelationships(@Param("id") Long id);
}
