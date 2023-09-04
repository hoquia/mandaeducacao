package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlanoMultaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoMultaDTO.class);
        PlanoMultaDTO planoMultaDTO1 = new PlanoMultaDTO();
        planoMultaDTO1.setId(1L);
        PlanoMultaDTO planoMultaDTO2 = new PlanoMultaDTO();
        assertThat(planoMultaDTO1).isNotEqualTo(planoMultaDTO2);
        planoMultaDTO2.setId(planoMultaDTO1.getId());
        assertThat(planoMultaDTO1).isEqualTo(planoMultaDTO2);
        planoMultaDTO2.setId(2L);
        assertThat(planoMultaDTO1).isNotEqualTo(planoMultaDTO2);
        planoMultaDTO1.setId(null);
        assertThat(planoMultaDTO1).isNotEqualTo(planoMultaDTO2);
    }
}
