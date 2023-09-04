package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NivelEnsinoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NivelEnsinoDTO.class);
        NivelEnsinoDTO nivelEnsinoDTO1 = new NivelEnsinoDTO();
        nivelEnsinoDTO1.setId(1L);
        NivelEnsinoDTO nivelEnsinoDTO2 = new NivelEnsinoDTO();
        assertThat(nivelEnsinoDTO1).isNotEqualTo(nivelEnsinoDTO2);
        nivelEnsinoDTO2.setId(nivelEnsinoDTO1.getId());
        assertThat(nivelEnsinoDTO1).isEqualTo(nivelEnsinoDTO2);
        nivelEnsinoDTO2.setId(2L);
        assertThat(nivelEnsinoDTO1).isNotEqualTo(nivelEnsinoDTO2);
        nivelEnsinoDTO1.setId(null);
        assertThat(nivelEnsinoDTO1).isNotEqualTo(nivelEnsinoDTO2);
    }
}
