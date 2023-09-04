package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnderecoDiscenteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnderecoDiscenteDTO.class);
        EnderecoDiscenteDTO enderecoDiscenteDTO1 = new EnderecoDiscenteDTO();
        enderecoDiscenteDTO1.setId(1L);
        EnderecoDiscenteDTO enderecoDiscenteDTO2 = new EnderecoDiscenteDTO();
        assertThat(enderecoDiscenteDTO1).isNotEqualTo(enderecoDiscenteDTO2);
        enderecoDiscenteDTO2.setId(enderecoDiscenteDTO1.getId());
        assertThat(enderecoDiscenteDTO1).isEqualTo(enderecoDiscenteDTO2);
        enderecoDiscenteDTO2.setId(2L);
        assertThat(enderecoDiscenteDTO1).isNotEqualTo(enderecoDiscenteDTO2);
        enderecoDiscenteDTO1.setId(null);
        assertThat(enderecoDiscenteDTO1).isNotEqualTo(enderecoDiscenteDTO2);
    }
}
