package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.Conta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Conta entity.
 */
@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>, JpaSpecificationExecutor<Conta> {
    default Optional<Conta> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Conta> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Conta> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct conta from Conta conta left join fetch conta.moeda",
        countQuery = "select count(distinct conta) from Conta conta"
    )
    Page<Conta> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct conta from Conta conta left join fetch conta.moeda")
    List<Conta> findAllWithToOneRelationships();

    @Query("select conta from Conta conta left join fetch conta.moeda where conta.id =:id")
    Optional<Conta> findOneWithToOneRelationships(@Param("id") Long id);
}
