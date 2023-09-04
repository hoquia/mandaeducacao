package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransferenciaTurmaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferenciaTurmaDTO.class);
        TransferenciaTurmaDTO transferenciaTurmaDTO1 = new TransferenciaTurmaDTO();
        transferenciaTurmaDTO1.setId(1L);
        TransferenciaTurmaDTO transferenciaTurmaDTO2 = new TransferenciaTurmaDTO();
        assertThat(transferenciaTurmaDTO1).isNotEqualTo(transferenciaTurmaDTO2);
        transferenciaTurmaDTO2.setId(transferenciaTurmaDTO1.getId());
        assertThat(transferenciaTurmaDTO1).isEqualTo(transferenciaTurmaDTO2);
        transferenciaTurmaDTO2.setId(2L);
        assertThat(transferenciaTurmaDTO1).isNotEqualTo(transferenciaTurmaDTO2);
        transferenciaTurmaDTO1.setId(null);
        assertThat(transferenciaTurmaDTO1).isNotEqualTo(transferenciaTurmaDTO2);
    }
}
