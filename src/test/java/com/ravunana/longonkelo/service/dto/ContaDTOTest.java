package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContaDTO.class);
        ContaDTO contaDTO1 = new ContaDTO();
        contaDTO1.setId(1L);
        ContaDTO contaDTO2 = new ContaDTO();
        assertThat(contaDTO1).isNotEqualTo(contaDTO2);
        contaDTO2.setId(contaDTO1.getId());
        assertThat(contaDTO1).isEqualTo(contaDTO2);
        contaDTO2.setId(2L);
        assertThat(contaDTO1).isNotEqualTo(contaDTO2);
        contaDTO1.setId(null);
        assertThat(contaDTO1).isNotEqualTo(contaDTO2);
    }
}
