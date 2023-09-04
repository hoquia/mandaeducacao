package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnoLectivoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnoLectivo.class);
        AnoLectivo anoLectivo1 = new AnoLectivo();
        anoLectivo1.setId(1L);
        AnoLectivo anoLectivo2 = new AnoLectivo();
        anoLectivo2.setId(anoLectivo1.getId());
        assertThat(anoLectivo1).isEqualTo(anoLectivo2);
        anoLectivo2.setId(2L);
        assertThat(anoLectivo1).isNotEqualTo(anoLectivo2);
        anoLectivo1.setId(null);
        assertThat(anoLectivo1).isNotEqualTo(anoLectivo2);
    }
}
