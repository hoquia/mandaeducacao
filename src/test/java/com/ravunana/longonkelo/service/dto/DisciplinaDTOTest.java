package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DisciplinaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisciplinaDTO.class);
        DisciplinaDTO disciplinaDTO1 = new DisciplinaDTO();
        disciplinaDTO1.setId(1L);
        DisciplinaDTO disciplinaDTO2 = new DisciplinaDTO();
        assertThat(disciplinaDTO1).isNotEqualTo(disciplinaDTO2);
        disciplinaDTO2.setId(disciplinaDTO1.getId());
        assertThat(disciplinaDTO1).isEqualTo(disciplinaDTO2);
        disciplinaDTO2.setId(2L);
        assertThat(disciplinaDTO1).isNotEqualTo(disciplinaDTO2);
        disciplinaDTO1.setId(null);
        assertThat(disciplinaDTO1).isNotEqualTo(disciplinaDTO2);
    }
}
