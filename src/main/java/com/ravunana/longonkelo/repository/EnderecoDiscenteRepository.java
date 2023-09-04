package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.EnderecoDiscente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EnderecoDiscente entity.
 */
@Repository
public interface EnderecoDiscenteRepository extends JpaRepository<EnderecoDiscente, Long>, JpaSpecificationExecutor<EnderecoDiscente> {
    default Optional<EnderecoDiscente> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<EnderecoDiscente> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<EnderecoDiscente> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct enderecoDiscente from EnderecoDiscente enderecoDiscente left join fetch enderecoDiscente.pais left join fetch enderecoDiscente.provincia left join fetch enderecoDiscente.municipio left join fetch enderecoDiscente.discente",
        countQuery = "select count(distinct enderecoDiscente) from EnderecoDiscente enderecoDiscente"
    )
    Page<EnderecoDiscente> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct enderecoDiscente from EnderecoDiscente enderecoDiscente left join fetch enderecoDiscente.pais left join fetch enderecoDiscente.provincia left join fetch enderecoDiscente.municipio left join fetch enderecoDiscente.discente"
    )
    List<EnderecoDiscente> findAllWithToOneRelationships();

    @Query(
        "select enderecoDiscente from EnderecoDiscente enderecoDiscente left join fetch enderecoDiscente.pais left join fetch enderecoDiscente.provincia left join fetch enderecoDiscente.municipio left join fetch enderecoDiscente.discente where enderecoDiscente.id =:id"
    )
    Optional<EnderecoDiscente> findOneWithToOneRelationships(@Param("id") Long id);
}
