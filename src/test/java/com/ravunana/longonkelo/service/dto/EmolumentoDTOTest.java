package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmolumentoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmolumentoDTO.class);
        EmolumentoDTO emolumentoDTO1 = new EmolumentoDTO();
        emolumentoDTO1.setId(1L);
        EmolumentoDTO emolumentoDTO2 = new EmolumentoDTO();
        assertThat(emolumentoDTO1).isNotEqualTo(emolumentoDTO2);
        emolumentoDTO2.setId(emolumentoDTO1.getId());
        assertThat(emolumentoDTO1).isEqualTo(emolumentoDTO2);
        emolumentoDTO2.setId(2L);
        assertThat(emolumentoDTO1).isNotEqualTo(emolumentoDTO2);
        emolumentoDTO1.setId(null);
        assertThat(emolumentoDTO1).isNotEqualTo(emolumentoDTO2);
    }
}
