package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlanoDescontoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoDescontoDTO.class);
        PlanoDescontoDTO planoDescontoDTO1 = new PlanoDescontoDTO();
        planoDescontoDTO1.setId(1L);
        PlanoDescontoDTO planoDescontoDTO2 = new PlanoDescontoDTO();
        assertThat(planoDescontoDTO1).isNotEqualTo(planoDescontoDTO2);
        planoDescontoDTO2.setId(planoDescontoDTO1.getId());
        assertThat(planoDescontoDTO1).isEqualTo(planoDescontoDTO2);
        planoDescontoDTO2.setId(2L);
        assertThat(planoDescontoDTO1).isNotEqualTo(planoDescontoDTO2);
        planoDescontoDTO1.setId(null);
        assertThat(planoDescontoDTO1).isNotEqualTo(planoDescontoDTO2);
    }
}
