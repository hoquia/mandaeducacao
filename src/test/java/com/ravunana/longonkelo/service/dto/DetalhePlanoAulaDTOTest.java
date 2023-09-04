package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetalhePlanoAulaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalhePlanoAulaDTO.class);
        DetalhePlanoAulaDTO detalhePlanoAulaDTO1 = new DetalhePlanoAulaDTO();
        detalhePlanoAulaDTO1.setId(1L);
        DetalhePlanoAulaDTO detalhePlanoAulaDTO2 = new DetalhePlanoAulaDTO();
        assertThat(detalhePlanoAulaDTO1).isNotEqualTo(detalhePlanoAulaDTO2);
        detalhePlanoAulaDTO2.setId(detalhePlanoAulaDTO1.getId());
        assertThat(detalhePlanoAulaDTO1).isEqualTo(detalhePlanoAulaDTO2);
        detalhePlanoAulaDTO2.setId(2L);
        assertThat(detalhePlanoAulaDTO1).isNotEqualTo(detalhePlanoAulaDTO2);
        detalhePlanoAulaDTO1.setId(null);
        assertThat(detalhePlanoAulaDTO1).isNotEqualTo(detalhePlanoAulaDTO2);
    }
}
