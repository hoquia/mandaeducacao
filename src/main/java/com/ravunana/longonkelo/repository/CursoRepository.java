package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.Curso;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Curso entity.
 */
@Repository
public interface CursoRepository extends JpaRepository<Curso, Long>, JpaSpecificationExecutor<Curso> {
    default Optional<Curso> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Curso> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Curso> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct curso from Curso curso left join fetch curso.areaFormacao",
        countQuery = "select count(distinct curso) from Curso curso"
    )
    Page<Curso> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct curso from Curso curso left join fetch curso.areaFormacao")
    List<Curso> findAllWithToOneRelationships();

    @Query("select curso from Curso curso left join fetch curso.areaFormacao where curso.id =:id")
    Optional<Curso> findOneWithToOneRelationships(@Param("id") Long id);
}
