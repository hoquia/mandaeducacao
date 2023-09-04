package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.ResponsavelTurma;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ResponsavelTurma entity.
 */
@Repository
public interface ResponsavelTurmaRepository extends JpaRepository<ResponsavelTurma, Long>, JpaSpecificationExecutor<ResponsavelTurma> {
    @Query(
        "select responsavelTurma from ResponsavelTurma responsavelTurma where responsavelTurma.utilizador.login = ?#{principal.username}"
    )
    List<ResponsavelTurma> findByUtilizadorIsCurrentUser();

    default Optional<ResponsavelTurma> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ResponsavelTurma> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ResponsavelTurma> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct responsavelTurma from ResponsavelTurma responsavelTurma left join fetch responsavelTurma.utilizador left join fetch responsavelTurma.turma",
        countQuery = "select count(distinct responsavelTurma) from ResponsavelTurma responsavelTurma"
    )
    Page<ResponsavelTurma> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct responsavelTurma from ResponsavelTurma responsavelTurma left join fetch responsavelTurma.utilizador left join fetch responsavelTurma.turma"
    )
    List<ResponsavelTurma> findAllWithToOneRelationships();

    @Query(
        "select responsavelTurma from ResponsavelTurma responsavelTurma left join fetch responsavelTurma.utilizador left join fetch responsavelTurma.turma where responsavelTurma.id =:id"
    )
    Optional<ResponsavelTurma> findOneWithToOneRelationships(@Param("id") Long id);
}
