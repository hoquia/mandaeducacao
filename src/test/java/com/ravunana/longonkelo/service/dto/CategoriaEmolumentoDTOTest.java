package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategoriaEmolumentoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaEmolumentoDTO.class);
        CategoriaEmolumentoDTO categoriaEmolumentoDTO1 = new CategoriaEmolumentoDTO();
        categoriaEmolumentoDTO1.setId(1L);
        CategoriaEmolumentoDTO categoriaEmolumentoDTO2 = new CategoriaEmolumentoDTO();
        assertThat(categoriaEmolumentoDTO1).isNotEqualTo(categoriaEmolumentoDTO2);
        categoriaEmolumentoDTO2.setId(categoriaEmolumentoDTO1.getId());
        assertThat(categoriaEmolumentoDTO1).isEqualTo(categoriaEmolumentoDTO2);
        categoriaEmolumentoDTO2.setId(2L);
        assertThat(categoriaEmolumentoDTO1).isNotEqualTo(categoriaEmolumentoDTO2);
        categoriaEmolumentoDTO1.setId(null);
        assertThat(categoriaEmolumentoDTO1).isNotEqualTo(categoriaEmolumentoDTO2);
    }
}
