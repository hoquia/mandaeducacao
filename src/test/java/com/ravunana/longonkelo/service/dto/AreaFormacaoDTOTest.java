package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AreaFormacaoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaFormacaoDTO.class);
        AreaFormacaoDTO areaFormacaoDTO1 = new AreaFormacaoDTO();
        areaFormacaoDTO1.setId(1L);
        AreaFormacaoDTO areaFormacaoDTO2 = new AreaFormacaoDTO();
        assertThat(areaFormacaoDTO1).isNotEqualTo(areaFormacaoDTO2);
        areaFormacaoDTO2.setId(areaFormacaoDTO1.getId());
        assertThat(areaFormacaoDTO1).isEqualTo(areaFormacaoDTO2);
        areaFormacaoDTO2.setId(2L);
        assertThat(areaFormacaoDTO1).isNotEqualTo(areaFormacaoDTO2);
        areaFormacaoDTO1.setId(null);
        assertThat(areaFormacaoDTO1).isNotEqualTo(areaFormacaoDTO2);
    }
}
