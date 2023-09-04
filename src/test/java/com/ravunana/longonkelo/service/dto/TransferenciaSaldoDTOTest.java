package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransferenciaSaldoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferenciaSaldoDTO.class);
        TransferenciaSaldoDTO transferenciaSaldoDTO1 = new TransferenciaSaldoDTO();
        transferenciaSaldoDTO1.setId(1L);
        TransferenciaSaldoDTO transferenciaSaldoDTO2 = new TransferenciaSaldoDTO();
        assertThat(transferenciaSaldoDTO1).isNotEqualTo(transferenciaSaldoDTO2);
        transferenciaSaldoDTO2.setId(transferenciaSaldoDTO1.getId());
        assertThat(transferenciaSaldoDTO1).isEqualTo(transferenciaSaldoDTO2);
        transferenciaSaldoDTO2.setId(2L);
        assertThat(transferenciaSaldoDTO1).isNotEqualTo(transferenciaSaldoDTO2);
        transferenciaSaldoDTO1.setId(null);
        assertThat(transferenciaSaldoDTO1).isNotEqualTo(transferenciaSaldoDTO2);
    }
}
