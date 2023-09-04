package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategoriaOcorrenciaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaOcorrenciaDTO.class);
        CategoriaOcorrenciaDTO categoriaOcorrenciaDTO1 = new CategoriaOcorrenciaDTO();
        categoriaOcorrenciaDTO1.setId(1L);
        CategoriaOcorrenciaDTO categoriaOcorrenciaDTO2 = new CategoriaOcorrenciaDTO();
        assertThat(categoriaOcorrenciaDTO1).isNotEqualTo(categoriaOcorrenciaDTO2);
        categoriaOcorrenciaDTO2.setId(categoriaOcorrenciaDTO1.getId());
        assertThat(categoriaOcorrenciaDTO1).isEqualTo(categoriaOcorrenciaDTO2);
        categoriaOcorrenciaDTO2.setId(2L);
        assertThat(categoriaOcorrenciaDTO1).isNotEqualTo(categoriaOcorrenciaDTO2);
        categoriaOcorrenciaDTO1.setId(null);
        assertThat(categoriaOcorrenciaDTO1).isNotEqualTo(categoriaOcorrenciaDTO2);
    }
}
