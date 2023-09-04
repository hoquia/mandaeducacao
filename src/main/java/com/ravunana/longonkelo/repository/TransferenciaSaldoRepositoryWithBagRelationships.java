package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.TransferenciaSaldo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface TransferenciaSaldoRepositoryWithBagRelationships {
    Optional<TransferenciaSaldo> fetchBagRelationships(Optional<TransferenciaSaldo> transferenciaSaldo);

    List<TransferenciaSaldo> fetchBagRelationships(List<TransferenciaSaldo> transferenciaSaldos);

    Page<TransferenciaSaldo> fetchBagRelationships(Page<TransferenciaSaldo> transferenciaSaldos);
}
