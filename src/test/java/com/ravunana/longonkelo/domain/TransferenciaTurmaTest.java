package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransferenciaTurmaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferenciaTurma.class);
        TransferenciaTurma transferenciaTurma1 = new TransferenciaTurma();
        transferenciaTurma1.setId(1L);
        TransferenciaTurma transferenciaTurma2 = new TransferenciaTurma();
        transferenciaTurma2.setId(transferenciaTurma1.getId());
        assertThat(transferenciaTurma1).isEqualTo(transferenciaTurma2);
        transferenciaTurma2.setId(2L);
        assertThat(transferenciaTurma1).isNotEqualTo(transferenciaTurma2);
        transferenciaTurma1.setId(null);
        assertThat(transferenciaTurma1).isNotEqualTo(transferenciaTurma2);
    }
}
