package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NaturezaTrabalhoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NaturezaTrabalhoDTO.class);
        NaturezaTrabalhoDTO naturezaTrabalhoDTO1 = new NaturezaTrabalhoDTO();
        naturezaTrabalhoDTO1.setId(1L);
        NaturezaTrabalhoDTO naturezaTrabalhoDTO2 = new NaturezaTrabalhoDTO();
        assertThat(naturezaTrabalhoDTO1).isNotEqualTo(naturezaTrabalhoDTO2);
        naturezaTrabalhoDTO2.setId(naturezaTrabalhoDTO1.getId());
        assertThat(naturezaTrabalhoDTO1).isEqualTo(naturezaTrabalhoDTO2);
        naturezaTrabalhoDTO2.setId(2L);
        assertThat(naturezaTrabalhoDTO1).isNotEqualTo(naturezaTrabalhoDTO2);
        naturezaTrabalhoDTO1.setId(null);
        assertThat(naturezaTrabalhoDTO1).isNotEqualTo(naturezaTrabalhoDTO2);
    }
}
