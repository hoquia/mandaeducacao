package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategoriaOcorrenciaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaOcorrencia.class);
        CategoriaOcorrencia categoriaOcorrencia1 = new CategoriaOcorrencia();
        categoriaOcorrencia1.setId(1L);
        CategoriaOcorrencia categoriaOcorrencia2 = new CategoriaOcorrencia();
        categoriaOcorrencia2.setId(categoriaOcorrencia1.getId());
        assertThat(categoriaOcorrencia1).isEqualTo(categoriaOcorrencia2);
        categoriaOcorrencia2.setId(2L);
        assertThat(categoriaOcorrencia1).isNotEqualTo(categoriaOcorrencia2);
        categoriaOcorrencia1.setId(null);
        assertThat(categoriaOcorrencia1).isNotEqualTo(categoriaOcorrencia2);
    }
}
