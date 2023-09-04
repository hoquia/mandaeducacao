package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LongonkeloHistoricoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LongonkeloHistorico.class);
        LongonkeloHistorico longonkeloHistorico1 = new LongonkeloHistorico();
        longonkeloHistorico1.setId(1L);
        LongonkeloHistorico longonkeloHistorico2 = new LongonkeloHistorico();
        longonkeloHistorico2.setId(longonkeloHistorico1.getId());
        assertThat(longonkeloHistorico1).isEqualTo(longonkeloHistorico2);
        longonkeloHistorico2.setId(2L);
        assertThat(longonkeloHistorico1).isNotEqualTo(longonkeloHistorico2);
        longonkeloHistorico1.setId(null);
        assertThat(longonkeloHistorico1).isNotEqualTo(longonkeloHistorico2);
    }
}
