package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MeioPagamentoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeioPagamentoDTO.class);
        MeioPagamentoDTO meioPagamentoDTO1 = new MeioPagamentoDTO();
        meioPagamentoDTO1.setId(1L);
        MeioPagamentoDTO meioPagamentoDTO2 = new MeioPagamentoDTO();
        assertThat(meioPagamentoDTO1).isNotEqualTo(meioPagamentoDTO2);
        meioPagamentoDTO2.setId(meioPagamentoDTO1.getId());
        assertThat(meioPagamentoDTO1).isEqualTo(meioPagamentoDTO2);
        meioPagamentoDTO2.setId(2L);
        assertThat(meioPagamentoDTO1).isNotEqualTo(meioPagamentoDTO2);
        meioPagamentoDTO1.setId(null);
        assertThat(meioPagamentoDTO1).isNotEqualTo(meioPagamentoDTO2);
    }
}
