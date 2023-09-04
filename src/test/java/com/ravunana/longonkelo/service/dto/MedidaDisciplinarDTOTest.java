package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MedidaDisciplinarDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedidaDisciplinarDTO.class);
        MedidaDisciplinarDTO medidaDisciplinarDTO1 = new MedidaDisciplinarDTO();
        medidaDisciplinarDTO1.setId(1L);
        MedidaDisciplinarDTO medidaDisciplinarDTO2 = new MedidaDisciplinarDTO();
        assertThat(medidaDisciplinarDTO1).isNotEqualTo(medidaDisciplinarDTO2);
        medidaDisciplinarDTO2.setId(medidaDisciplinarDTO1.getId());
        assertThat(medidaDisciplinarDTO1).isEqualTo(medidaDisciplinarDTO2);
        medidaDisciplinarDTO2.setId(2L);
        assertThat(medidaDisciplinarDTO1).isNotEqualTo(medidaDisciplinarDTO2);
        medidaDisciplinarDTO1.setId(null);
        assertThat(medidaDisciplinarDTO1).isNotEqualTo(medidaDisciplinarDTO2);
    }
}
