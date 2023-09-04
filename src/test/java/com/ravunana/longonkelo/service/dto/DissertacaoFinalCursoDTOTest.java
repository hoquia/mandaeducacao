package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DissertacaoFinalCursoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DissertacaoFinalCursoDTO.class);
        DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO1 = new DissertacaoFinalCursoDTO();
        dissertacaoFinalCursoDTO1.setId(1L);
        DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO2 = new DissertacaoFinalCursoDTO();
        assertThat(dissertacaoFinalCursoDTO1).isNotEqualTo(dissertacaoFinalCursoDTO2);
        dissertacaoFinalCursoDTO2.setId(dissertacaoFinalCursoDTO1.getId());
        assertThat(dissertacaoFinalCursoDTO1).isEqualTo(dissertacaoFinalCursoDTO2);
        dissertacaoFinalCursoDTO2.setId(2L);
        assertThat(dissertacaoFinalCursoDTO1).isNotEqualTo(dissertacaoFinalCursoDTO2);
        dissertacaoFinalCursoDTO1.setId(null);
        assertThat(dissertacaoFinalCursoDTO1).isNotEqualTo(dissertacaoFinalCursoDTO2);
    }
}
