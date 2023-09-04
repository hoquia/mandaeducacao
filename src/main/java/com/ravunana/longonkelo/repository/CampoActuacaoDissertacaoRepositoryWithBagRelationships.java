package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.CampoActuacaoDissertacao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CampoActuacaoDissertacaoRepositoryWithBagRelationships {
    Optional<CampoActuacaoDissertacao> fetchBagRelationships(Optional<CampoActuacaoDissertacao> campoActuacaoDissertacao);

    List<CampoActuacaoDissertacao> fetchBagRelationships(List<CampoActuacaoDissertacao> campoActuacaoDissertacaos);

    Page<CampoActuacaoDissertacao> fetchBagRelationships(Page<CampoActuacaoDissertacao> campoActuacaoDissertacaos);
}
