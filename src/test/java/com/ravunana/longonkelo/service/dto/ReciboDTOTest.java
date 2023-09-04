package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReciboDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReciboDTO.class);
        ReciboDTO reciboDTO1 = new ReciboDTO();
        reciboDTO1.setId(1L);
        ReciboDTO reciboDTO2 = new ReciboDTO();
        assertThat(reciboDTO1).isNotEqualTo(reciboDTO2);
        reciboDTO2.setId(reciboDTO1.getId());
        assertThat(reciboDTO1).isEqualTo(reciboDTO2);
        reciboDTO2.setId(2L);
        assertThat(reciboDTO1).isNotEqualTo(reciboDTO2);
        reciboDTO1.setId(null);
        assertThat(reciboDTO1).isNotEqualTo(reciboDTO2);
    }
}
