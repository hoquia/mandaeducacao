package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormacaoDocenteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormacaoDocenteDTO.class);
        FormacaoDocenteDTO formacaoDocenteDTO1 = new FormacaoDocenteDTO();
        formacaoDocenteDTO1.setId(1L);
        FormacaoDocenteDTO formacaoDocenteDTO2 = new FormacaoDocenteDTO();
        assertThat(formacaoDocenteDTO1).isNotEqualTo(formacaoDocenteDTO2);
        formacaoDocenteDTO2.setId(formacaoDocenteDTO1.getId());
        assertThat(formacaoDocenteDTO1).isEqualTo(formacaoDocenteDTO2);
        formacaoDocenteDTO2.setId(2L);
        assertThat(formacaoDocenteDTO1).isNotEqualTo(formacaoDocenteDTO2);
        formacaoDocenteDTO1.setId(null);
        assertThat(formacaoDocenteDTO1).isNotEqualTo(formacaoDocenteDTO2);
    }
}
