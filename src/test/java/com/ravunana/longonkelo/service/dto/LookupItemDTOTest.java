package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LookupItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LookupItemDTO.class);
        LookupItemDTO lookupItemDTO1 = new LookupItemDTO();
        lookupItemDTO1.setId(1L);
        LookupItemDTO lookupItemDTO2 = new LookupItemDTO();
        assertThat(lookupItemDTO1).isNotEqualTo(lookupItemDTO2);
        lookupItemDTO2.setId(lookupItemDTO1.getId());
        assertThat(lookupItemDTO1).isEqualTo(lookupItemDTO2);
        lookupItemDTO2.setId(2L);
        assertThat(lookupItemDTO1).isNotEqualTo(lookupItemDTO2);
        lookupItemDTO1.setId(null);
        assertThat(lookupItemDTO1).isNotEqualTo(lookupItemDTO2);
    }
}
