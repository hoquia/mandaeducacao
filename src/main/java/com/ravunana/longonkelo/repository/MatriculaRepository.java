package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.Matricula;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Matricula entity.
 *
 * When extending this class, extend MatriculaRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface MatriculaRepository
    extends MatriculaRepositoryWithBagRelationships, JpaRepository<Matricula, Long>, JpaSpecificationExecutor<Matricula> {
    @Query("select matricula from Matricula matricula where matricula.utilizador.login = ?#{principal.username}")
    List<Matricula> findByUtilizadorIsCurrentUser();

    default Optional<Matricula> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Matricula> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Matricula> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct matricula from Matricula matricula left join fetch matricula.utilizador left join fetch matricula.turma left join fetch matricula.responsavelFinanceiro left join fetch matricula.discente",
        countQuery = "select count(distinct matricula) from Matricula matricula"
    )
    Page<Matricula> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct matricula from Matricula matricula left join fetch matricula.utilizador left join fetch matricula.turma left join fetch matricula.responsavelFinanceiro left join fetch matricula.discente"
    )
    List<Matricula> findAllWithToOneRelationships();

    @Query(
        "select matricula from Matricula matricula left join fetch matricula.utilizador left join fetch matricula.turma left join fetch matricula.responsavelFinanceiro left join fetch matricula.discente where matricula.id =:id"
    )
    Optional<Matricula> findOneWithToOneRelationships(@Param("id") Long id);
}
