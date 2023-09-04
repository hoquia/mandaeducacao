package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.PeriodoLancamentoNota;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PeriodoLancamentoNotaRepositoryWithBagRelationships {
    Optional<PeriodoLancamentoNota> fetchBagRelationships(Optional<PeriodoLancamentoNota> periodoLancamentoNota);

    List<PeriodoLancamentoNota> fetchBagRelationships(List<PeriodoLancamentoNota> periodoLancamentoNotas);

    Page<PeriodoLancamentoNota> fetchBagRelationships(Page<PeriodoLancamentoNota> periodoLancamentoNotas);
}
