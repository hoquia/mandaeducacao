package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstadoDisciplinaCurricularDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoDisciplinaCurricularDTO.class);
        EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO1 = new EstadoDisciplinaCurricularDTO();
        estadoDisciplinaCurricularDTO1.setId(1L);
        EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO2 = new EstadoDisciplinaCurricularDTO();
        assertThat(estadoDisciplinaCurricularDTO1).isNotEqualTo(estadoDisciplinaCurricularDTO2);
        estadoDisciplinaCurricularDTO2.setId(estadoDisciplinaCurricularDTO1.getId());
        assertThat(estadoDisciplinaCurricularDTO1).isEqualTo(estadoDisciplinaCurricularDTO2);
        estadoDisciplinaCurricularDTO2.setId(2L);
        assertThat(estadoDisciplinaCurricularDTO1).isNotEqualTo(estadoDisciplinaCurricularDTO2);
        estadoDisciplinaCurricularDTO1.setId(null);
        assertThat(estadoDisciplinaCurricularDTO1).isNotEqualTo(estadoDisciplinaCurricularDTO2);
    }
}
