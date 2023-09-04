package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransferenciaSaldoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferenciaSaldo.class);
        TransferenciaSaldo transferenciaSaldo1 = new TransferenciaSaldo();
        transferenciaSaldo1.setId(1L);
        TransferenciaSaldo transferenciaSaldo2 = new TransferenciaSaldo();
        transferenciaSaldo2.setId(transferenciaSaldo1.getId());
        assertThat(transferenciaSaldo1).isEqualTo(transferenciaSaldo2);
        transferenciaSaldo2.setId(2L);
        assertThat(transferenciaSaldo1).isNotEqualTo(transferenciaSaldo2);
        transferenciaSaldo1.setId(null);
        assertThat(transferenciaSaldo1).isNotEqualTo(transferenciaSaldo2);
    }
}
