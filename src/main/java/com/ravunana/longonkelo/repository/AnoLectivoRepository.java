package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.AnoLectivo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AnoLectivo entity.
 *
 * When extending this class, extend AnoLectivoRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface AnoLectivoRepository extends AnoLectivoRepositoryWithBagRelationships, JpaRepository<AnoLectivo, Long> {
    @Query("select anoLectivo from AnoLectivo anoLectivo where anoLectivo.utilizador.login = ?#{principal.username}")
    List<AnoLectivo> findByUtilizadorIsCurrentUser();

    default Optional<AnoLectivo> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<AnoLectivo> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<AnoLectivo> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct anoLectivo from AnoLectivo anoLectivo left join fetch anoLectivo.directorGeral left join fetch anoLectivo.subDirectorPdagogico left join fetch anoLectivo.subDirectorAdministrativo left join fetch anoLectivo.responsavelSecretariaGeral left join fetch anoLectivo.responsavelSecretariaPedagogico left join fetch anoLectivo.utilizador",
        countQuery = "select count(distinct anoLectivo) from AnoLectivo anoLectivo"
    )
    Page<AnoLectivo> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct anoLectivo from AnoLectivo anoLectivo left join fetch anoLectivo.directorGeral left join fetch anoLectivo.subDirectorPdagogico left join fetch anoLectivo.subDirectorAdministrativo left join fetch anoLectivo.responsavelSecretariaGeral left join fetch anoLectivo.responsavelSecretariaPedagogico left join fetch anoLectivo.utilizador"
    )
    List<AnoLectivo> findAllWithToOneRelationships();

    @Query(
        "select anoLectivo from AnoLectivo anoLectivo left join fetch anoLectivo.directorGeral left join fetch anoLectivo.subDirectorPdagogico left join fetch anoLectivo.subDirectorAdministrativo left join fetch anoLectivo.responsavelSecretariaGeral left join fetch anoLectivo.responsavelSecretariaPedagogico left join fetch anoLectivo.utilizador where anoLectivo.id =:id"
    )
    Optional<AnoLectivo> findOneWithToOneRelationships(@Param("id") Long id);
}
