package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.TransferenciaSaldo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TransferenciaSaldo entity.
 *
 * When extending this class, extend TransferenciaSaldoRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface TransferenciaSaldoRepository
    extends
        TransferenciaSaldoRepositoryWithBagRelationships,
        JpaRepository<TransferenciaSaldo, Long>,
        JpaSpecificationExecutor<TransferenciaSaldo> {
    @Query(
        "select transferenciaSaldo from TransferenciaSaldo transferenciaSaldo where transferenciaSaldo.utilizador.login = ?#{principal.username}"
    )
    List<TransferenciaSaldo> findByUtilizadorIsCurrentUser();

    default Optional<TransferenciaSaldo> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<TransferenciaSaldo> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<TransferenciaSaldo> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct transferenciaSaldo from TransferenciaSaldo transferenciaSaldo left join fetch transferenciaSaldo.de left join fetch transferenciaSaldo.para left join fetch transferenciaSaldo.utilizador left join fetch transferenciaSaldo.motivoTransferencia",
        countQuery = "select count(distinct transferenciaSaldo) from TransferenciaSaldo transferenciaSaldo"
    )
    Page<TransferenciaSaldo> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct transferenciaSaldo from TransferenciaSaldo transferenciaSaldo left join fetch transferenciaSaldo.de left join fetch transferenciaSaldo.para left join fetch transferenciaSaldo.utilizador left join fetch transferenciaSaldo.motivoTransferencia"
    )
    List<TransferenciaSaldo> findAllWithToOneRelationships();

    @Query(
        "select transferenciaSaldo from TransferenciaSaldo transferenciaSaldo left join fetch transferenciaSaldo.de left join fetch transferenciaSaldo.para left join fetch transferenciaSaldo.utilizador left join fetch transferenciaSaldo.motivoTransferencia where transferenciaSaldo.id =:id"
    )
    Optional<TransferenciaSaldo> findOneWithToOneRelationships(@Param("id") Long id);
}
