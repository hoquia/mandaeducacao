package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DisciplinaCurricularDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisciplinaCurricularDTO.class);
        DisciplinaCurricularDTO disciplinaCurricularDTO1 = new DisciplinaCurricularDTO();
        disciplinaCurricularDTO1.setId(1L);
        DisciplinaCurricularDTO disciplinaCurricularDTO2 = new DisciplinaCurricularDTO();
        assertThat(disciplinaCurricularDTO1).isNotEqualTo(disciplinaCurricularDTO2);
        disciplinaCurricularDTO2.setId(disciplinaCurricularDTO1.getId());
        assertThat(disciplinaCurricularDTO1).isEqualTo(disciplinaCurricularDTO2);
        disciplinaCurricularDTO2.setId(2L);
        assertThat(disciplinaCurricularDTO1).isNotEqualTo(disciplinaCurricularDTO2);
        disciplinaCurricularDTO1.setId(null);
        assertThat(disciplinaCurricularDTO1).isNotEqualTo(disciplinaCurricularDTO2);
    }
}
