package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.FormacaoDocente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FormacaoDocente entity.
 */
@Repository
public interface FormacaoDocenteRepository extends JpaRepository<FormacaoDocente, Long>, JpaSpecificationExecutor<FormacaoDocente> {
    default Optional<FormacaoDocente> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<FormacaoDocente> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<FormacaoDocente> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct formacaoDocente from FormacaoDocente formacaoDocente left join fetch formacaoDocente.grauAcademico left join fetch formacaoDocente.docente",
        countQuery = "select count(distinct formacaoDocente) from FormacaoDocente formacaoDocente"
    )
    Page<FormacaoDocente> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct formacaoDocente from FormacaoDocente formacaoDocente left join fetch formacaoDocente.grauAcademico left join fetch formacaoDocente.docente"
    )
    List<FormacaoDocente> findAllWithToOneRelationships();

    @Query(
        "select formacaoDocente from FormacaoDocente formacaoDocente left join fetch formacaoDocente.grauAcademico left join fetch formacaoDocente.docente where formacaoDocente.id =:id"
    )
    Optional<FormacaoDocente> findOneWithToOneRelationships(@Param("id") Long id);
}
