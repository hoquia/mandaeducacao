package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.PlanoMulta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlanoMulta entity.
 */
@Repository
public interface PlanoMultaRepository extends JpaRepository<PlanoMulta, Long>, JpaSpecificationExecutor<PlanoMulta> {
    @Query("select planoMulta from PlanoMulta planoMulta where planoMulta.utilizador.login = ?#{principal.username}")
    List<PlanoMulta> findByUtilizadorIsCurrentUser();

    default Optional<PlanoMulta> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PlanoMulta> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PlanoMulta> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct planoMulta from PlanoMulta planoMulta left join fetch planoMulta.utilizador",
        countQuery = "select count(distinct planoMulta) from PlanoMulta planoMulta"
    )
    Page<PlanoMulta> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct planoMulta from PlanoMulta planoMulta left join fetch planoMulta.utilizador")
    List<PlanoMulta> findAllWithToOneRelationships();

    @Query("select planoMulta from PlanoMulta planoMulta left join fetch planoMulta.utilizador where planoMulta.id =:id")
    Optional<PlanoMulta> findOneWithToOneRelationships(@Param("id") Long id);
}
