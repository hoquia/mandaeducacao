package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.TransferenciaTurma;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TransferenciaTurma entity.
 */
@Repository
public interface TransferenciaTurmaRepository
    extends JpaRepository<TransferenciaTurma, Long>, JpaSpecificationExecutor<TransferenciaTurma> {
    @Query(
        "select transferenciaTurma from TransferenciaTurma transferenciaTurma where transferenciaTurma.utilizador.login = ?#{principal.username}"
    )
    List<TransferenciaTurma> findByUtilizadorIsCurrentUser();

    default Optional<TransferenciaTurma> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TransferenciaTurma> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TransferenciaTurma> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct transferenciaTurma from TransferenciaTurma transferenciaTurma left join fetch transferenciaTurma.de left join fetch transferenciaTurma.para left join fetch transferenciaTurma.utilizador left join fetch transferenciaTurma.motivoTransferencia left join fetch transferenciaTurma.matricula",
        countQuery = "select count(distinct transferenciaTurma) from TransferenciaTurma transferenciaTurma"
    )
    Page<TransferenciaTurma> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct transferenciaTurma from TransferenciaTurma transferenciaTurma left join fetch transferenciaTurma.de left join fetch transferenciaTurma.para left join fetch transferenciaTurma.utilizador left join fetch transferenciaTurma.motivoTransferencia left join fetch transferenciaTurma.matricula"
    )
    List<TransferenciaTurma> findAllWithToOneRelationships();

    @Query(
        "select transferenciaTurma from TransferenciaTurma transferenciaTurma left join fetch transferenciaTurma.de left join fetch transferenciaTurma.para left join fetch transferenciaTurma.utilizador left join fetch transferenciaTurma.motivoTransferencia left join fetch transferenciaTurma.matricula where transferenciaTurma.id =:id"
    )
    Optional<TransferenciaTurma> findOneWithToOneRelationships(@Param("id") Long id);
}
