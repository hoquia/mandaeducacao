package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LicaoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LicaoDTO.class);
        LicaoDTO licaoDTO1 = new LicaoDTO();
        licaoDTO1.setId(1L);
        LicaoDTO licaoDTO2 = new LicaoDTO();
        assertThat(licaoDTO1).isNotEqualTo(licaoDTO2);
        licaoDTO2.setId(licaoDTO1.getId());
        assertThat(licaoDTO1).isEqualTo(licaoDTO2);
        licaoDTO2.setId(2L);
        assertThat(licaoDTO1).isNotEqualTo(licaoDTO2);
        licaoDTO1.setId(null);
        assertThat(licaoDTO1).isNotEqualTo(licaoDTO2);
    }
}
