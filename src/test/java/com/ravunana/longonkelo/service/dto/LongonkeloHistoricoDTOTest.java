package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LongonkeloHistoricoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LongonkeloHistoricoDTO.class);
        LongonkeloHistoricoDTO longonkeloHistoricoDTO1 = new LongonkeloHistoricoDTO();
        longonkeloHistoricoDTO1.setId(1L);
        LongonkeloHistoricoDTO longonkeloHistoricoDTO2 = new LongonkeloHistoricoDTO();
        assertThat(longonkeloHistoricoDTO1).isNotEqualTo(longonkeloHistoricoDTO2);
        longonkeloHistoricoDTO2.setId(longonkeloHistoricoDTO1.getId());
        assertThat(longonkeloHistoricoDTO1).isEqualTo(longonkeloHistoricoDTO2);
        longonkeloHistoricoDTO2.setId(2L);
        assertThat(longonkeloHistoricoDTO1).isNotEqualTo(longonkeloHistoricoDTO2);
        longonkeloHistoricoDTO1.setId(null);
        assertThat(longonkeloHistoricoDTO1).isNotEqualTo(longonkeloHistoricoDTO2);
    }
}
