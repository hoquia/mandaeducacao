package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnexoDiscenteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnexoDiscenteDTO.class);
        AnexoDiscenteDTO anexoDiscenteDTO1 = new AnexoDiscenteDTO();
        anexoDiscenteDTO1.setId(1L);
        AnexoDiscenteDTO anexoDiscenteDTO2 = new AnexoDiscenteDTO();
        assertThat(anexoDiscenteDTO1).isNotEqualTo(anexoDiscenteDTO2);
        anexoDiscenteDTO2.setId(anexoDiscenteDTO1.getId());
        assertThat(anexoDiscenteDTO1).isEqualTo(anexoDiscenteDTO2);
        anexoDiscenteDTO2.setId(2L);
        assertThat(anexoDiscenteDTO1).isNotEqualTo(anexoDiscenteDTO2);
        anexoDiscenteDTO1.setId(null);
        assertThat(anexoDiscenteDTO1).isNotEqualTo(anexoDiscenteDTO2);
    }
}
