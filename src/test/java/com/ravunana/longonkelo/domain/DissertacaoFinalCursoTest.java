package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DissertacaoFinalCursoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DissertacaoFinalCurso.class);
        DissertacaoFinalCurso dissertacaoFinalCurso1 = new DissertacaoFinalCurso();
        dissertacaoFinalCurso1.setId(1L);
        DissertacaoFinalCurso dissertacaoFinalCurso2 = new DissertacaoFinalCurso();
        dissertacaoFinalCurso2.setId(dissertacaoFinalCurso1.getId());
        assertThat(dissertacaoFinalCurso1).isEqualTo(dissertacaoFinalCurso2);
        dissertacaoFinalCurso2.setId(2L);
        assertThat(dissertacaoFinalCurso1).isNotEqualTo(dissertacaoFinalCurso2);
        dissertacaoFinalCurso1.setId(null);
        assertThat(dissertacaoFinalCurso1).isNotEqualTo(dissertacaoFinalCurso2);
    }
}
