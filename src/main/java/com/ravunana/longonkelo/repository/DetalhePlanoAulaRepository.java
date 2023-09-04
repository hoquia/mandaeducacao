package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.DetalhePlanoAula;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DetalhePlanoAula entity.
 */
@Repository
public interface DetalhePlanoAulaRepository extends JpaRepository<DetalhePlanoAula, Long>, JpaSpecificationExecutor<DetalhePlanoAula> {
    default Optional<DetalhePlanoAula> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DetalhePlanoAula> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DetalhePlanoAula> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct detalhePlanoAula from DetalhePlanoAula detalhePlanoAula left join fetch detalhePlanoAula.planoAula",
        countQuery = "select count(distinct detalhePlanoAula) from DetalhePlanoAula detalhePlanoAula"
    )
    Page<DetalhePlanoAula> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct detalhePlanoAula from DetalhePlanoAula detalhePlanoAula left join fetch detalhePlanoAula.planoAula")
    List<DetalhePlanoAula> findAllWithToOneRelationships();

    @Query(
        "select detalhePlanoAula from DetalhePlanoAula detalhePlanoAula left join fetch detalhePlanoAula.planoAula where detalhePlanoAula.id =:id"
    )
    Optional<DetalhePlanoAula> findOneWithToOneRelationships(@Param("id") Long id);
}
