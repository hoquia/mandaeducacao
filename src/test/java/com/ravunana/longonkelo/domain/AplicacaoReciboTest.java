package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AplicacaoReciboTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AplicacaoRecibo.class);
        AplicacaoRecibo aplicacaoRecibo1 = new AplicacaoRecibo();
        aplicacaoRecibo1.setId(1L);
        AplicacaoRecibo aplicacaoRecibo2 = new AplicacaoRecibo();
        aplicacaoRecibo2.setId(aplicacaoRecibo1.getId());
        assertThat(aplicacaoRecibo1).isEqualTo(aplicacaoRecibo2);
        aplicacaoRecibo2.setId(2L);
        assertThat(aplicacaoRecibo1).isNotEqualTo(aplicacaoRecibo2);
        aplicacaoRecibo1.setId(null);
        assertThat(aplicacaoRecibo1).isNotEqualTo(aplicacaoRecibo2);
    }
}
