package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ImpostoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImpostoDTO.class);
        ImpostoDTO impostoDTO1 = new ImpostoDTO();
        impostoDTO1.setId(1L);
        ImpostoDTO impostoDTO2 = new ImpostoDTO();
        assertThat(impostoDTO1).isNotEqualTo(impostoDTO2);
        impostoDTO2.setId(impostoDTO1.getId());
        assertThat(impostoDTO1).isEqualTo(impostoDTO2);
        impostoDTO2.setId(2L);
        assertThat(impostoDTO1).isNotEqualTo(impostoDTO2);
        impostoDTO1.setId(null);
        assertThat(impostoDTO1).isNotEqualTo(impostoDTO2);
    }
}
