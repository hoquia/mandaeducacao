package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ItemFacturaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemFacturaDTO.class);
        ItemFacturaDTO itemFacturaDTO1 = new ItemFacturaDTO();
        itemFacturaDTO1.setId(1L);
        ItemFacturaDTO itemFacturaDTO2 = new ItemFacturaDTO();
        assertThat(itemFacturaDTO1).isNotEqualTo(itemFacturaDTO2);
        itemFacturaDTO2.setId(itemFacturaDTO1.getId());
        assertThat(itemFacturaDTO1).isEqualTo(itemFacturaDTO2);
        itemFacturaDTO2.setId(2L);
        assertThat(itemFacturaDTO1).isNotEqualTo(itemFacturaDTO2);
        itemFacturaDTO1.setId(null);
        assertThat(itemFacturaDTO1).isNotEqualTo(itemFacturaDTO2);
    }
}
