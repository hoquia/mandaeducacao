package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlanoCurricularDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoCurricularDTO.class);
        PlanoCurricularDTO planoCurricularDTO1 = new PlanoCurricularDTO();
        planoCurricularDTO1.setId(1L);
        PlanoCurricularDTO planoCurricularDTO2 = new PlanoCurricularDTO();
        assertThat(planoCurricularDTO1).isNotEqualTo(planoCurricularDTO2);
        planoCurricularDTO2.setId(planoCurricularDTO1.getId());
        assertThat(planoCurricularDTO1).isEqualTo(planoCurricularDTO2);
        planoCurricularDTO2.setId(2L);
        assertThat(planoCurricularDTO1).isNotEqualTo(planoCurricularDTO2);
        planoCurricularDTO1.setId(null);
        assertThat(planoCurricularDTO1).isNotEqualTo(planoCurricularDTO2);
    }
}
