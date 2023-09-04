package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResumoAcademicoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResumoAcademicoDTO.class);
        ResumoAcademicoDTO resumoAcademicoDTO1 = new ResumoAcademicoDTO();
        resumoAcademicoDTO1.setId(1L);
        ResumoAcademicoDTO resumoAcademicoDTO2 = new ResumoAcademicoDTO();
        assertThat(resumoAcademicoDTO1).isNotEqualTo(resumoAcademicoDTO2);
        resumoAcademicoDTO2.setId(resumoAcademicoDTO1.getId());
        assertThat(resumoAcademicoDTO1).isEqualTo(resumoAcademicoDTO2);
        resumoAcademicoDTO2.setId(2L);
        assertThat(resumoAcademicoDTO1).isNotEqualTo(resumoAcademicoDTO2);
        resumoAcademicoDTO1.setId(null);
        assertThat(resumoAcademicoDTO1).isNotEqualTo(resumoAcademicoDTO2);
    }
}
