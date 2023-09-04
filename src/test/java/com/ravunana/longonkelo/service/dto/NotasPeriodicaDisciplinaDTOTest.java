package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotasPeriodicaDisciplinaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotasPeriodicaDisciplinaDTO.class);
        NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO1 = new NotasPeriodicaDisciplinaDTO();
        notasPeriodicaDisciplinaDTO1.setId(1L);
        NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO2 = new NotasPeriodicaDisciplinaDTO();
        assertThat(notasPeriodicaDisciplinaDTO1).isNotEqualTo(notasPeriodicaDisciplinaDTO2);
        notasPeriodicaDisciplinaDTO2.setId(notasPeriodicaDisciplinaDTO1.getId());
        assertThat(notasPeriodicaDisciplinaDTO1).isEqualTo(notasPeriodicaDisciplinaDTO2);
        notasPeriodicaDisciplinaDTO2.setId(2L);
        assertThat(notasPeriodicaDisciplinaDTO1).isNotEqualTo(notasPeriodicaDisciplinaDTO2);
        notasPeriodicaDisciplinaDTO1.setId(null);
        assertThat(notasPeriodicaDisciplinaDTO1).isNotEqualTo(notasPeriodicaDisciplinaDTO2);
    }
}
