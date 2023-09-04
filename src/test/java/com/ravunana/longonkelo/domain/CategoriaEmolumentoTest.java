package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategoriaEmolumentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaEmolumento.class);
        CategoriaEmolumento categoriaEmolumento1 = new CategoriaEmolumento();
        categoriaEmolumento1.setId(1L);
        CategoriaEmolumento categoriaEmolumento2 = new CategoriaEmolumento();
        categoriaEmolumento2.setId(categoriaEmolumento1.getId());
        assertThat(categoriaEmolumento1).isEqualTo(categoriaEmolumento2);
        categoriaEmolumento2.setId(2L);
        assertThat(categoriaEmolumento1).isNotEqualTo(categoriaEmolumento2);
        categoriaEmolumento1.setId(null);
        assertThat(categoriaEmolumento1).isNotEqualTo(categoriaEmolumento2);
    }
}
