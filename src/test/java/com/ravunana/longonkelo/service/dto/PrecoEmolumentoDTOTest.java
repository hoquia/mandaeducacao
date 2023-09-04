package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrecoEmolumentoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrecoEmolumentoDTO.class);
        PrecoEmolumentoDTO precoEmolumentoDTO1 = new PrecoEmolumentoDTO();
        precoEmolumentoDTO1.setId(1L);
        PrecoEmolumentoDTO precoEmolumentoDTO2 = new PrecoEmolumentoDTO();
        assertThat(precoEmolumentoDTO1).isNotEqualTo(precoEmolumentoDTO2);
        precoEmolumentoDTO2.setId(precoEmolumentoDTO1.getId());
        assertThat(precoEmolumentoDTO1).isEqualTo(precoEmolumentoDTO2);
        precoEmolumentoDTO2.setId(2L);
        assertThat(precoEmolumentoDTO1).isNotEqualTo(precoEmolumentoDTO2);
        precoEmolumentoDTO1.setId(null);
        assertThat(precoEmolumentoDTO1).isNotEqualTo(precoEmolumentoDTO2);
    }
}
