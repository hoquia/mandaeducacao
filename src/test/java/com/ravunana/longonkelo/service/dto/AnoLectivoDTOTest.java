package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnoLectivoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnoLectivoDTO.class);
        AnoLectivoDTO anoLectivoDTO1 = new AnoLectivoDTO();
        anoLectivoDTO1.setId(1L);
        AnoLectivoDTO anoLectivoDTO2 = new AnoLectivoDTO();
        assertThat(anoLectivoDTO1).isNotEqualTo(anoLectivoDTO2);
        anoLectivoDTO2.setId(anoLectivoDTO1.getId());
        assertThat(anoLectivoDTO1).isEqualTo(anoLectivoDTO2);
        anoLectivoDTO2.setId(2L);
        assertThat(anoLectivoDTO1).isNotEqualTo(anoLectivoDTO2);
        anoLectivoDTO1.setId(null);
        assertThat(anoLectivoDTO1).isNotEqualTo(anoLectivoDTO2);
    }
}
