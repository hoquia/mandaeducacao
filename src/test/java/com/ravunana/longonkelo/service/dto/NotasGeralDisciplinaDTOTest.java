package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotasGeralDisciplinaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotasGeralDisciplinaDTO.class);
        NotasGeralDisciplinaDTO notasGeralDisciplinaDTO1 = new NotasGeralDisciplinaDTO();
        notasGeralDisciplinaDTO1.setId(1L);
        NotasGeralDisciplinaDTO notasGeralDisciplinaDTO2 = new NotasGeralDisciplinaDTO();
        assertThat(notasGeralDisciplinaDTO1).isNotEqualTo(notasGeralDisciplinaDTO2);
        notasGeralDisciplinaDTO2.setId(notasGeralDisciplinaDTO1.getId());
        assertThat(notasGeralDisciplinaDTO1).isEqualTo(notasGeralDisciplinaDTO2);
        notasGeralDisciplinaDTO2.setId(2L);
        assertThat(notasGeralDisciplinaDTO1).isNotEqualTo(notasGeralDisciplinaDTO2);
        notasGeralDisciplinaDTO1.setId(null);
        assertThat(notasGeralDisciplinaDTO1).isNotEqualTo(notasGeralDisciplinaDTO2);
    }
}
