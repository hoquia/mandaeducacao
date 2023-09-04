package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.Licao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Licao entity.
 */
@Repository
public interface LicaoRepository extends JpaRepository<Licao, Long>, JpaSpecificationExecutor<Licao> {
    @Query("select licao from Licao licao where licao.utilizador.login = ?#{principal.username}")
    List<Licao> findByUtilizadorIsCurrentUser();

    default Optional<Licao> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Licao> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Licao> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct licao from Licao licao left join fetch licao.utilizador left join fetch licao.planoAula",
        countQuery = "select count(distinct licao) from Licao licao"
    )
    Page<Licao> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct licao from Licao licao left join fetch licao.utilizador left join fetch licao.planoAula")
    List<Licao> findAllWithToOneRelationships();

    @Query("select licao from Licao licao left join fetch licao.utilizador left join fetch licao.planoAula where licao.id =:id")
    Optional<Licao> findOneWithToOneRelationships(@Param("id") Long id);
}
