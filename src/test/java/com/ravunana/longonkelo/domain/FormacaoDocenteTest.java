package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormacaoDocenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormacaoDocente.class);
        FormacaoDocente formacaoDocente1 = new FormacaoDocente();
        formacaoDocente1.setId(1L);
        FormacaoDocente formacaoDocente2 = new FormacaoDocente();
        formacaoDocente2.setId(formacaoDocente1.getId());
        assertThat(formacaoDocente1).isEqualTo(formacaoDocente2);
        formacaoDocente2.setId(2L);
        assertThat(formacaoDocente1).isNotEqualTo(formacaoDocente2);
        formacaoDocente1.setId(null);
        assertThat(formacaoDocente1).isNotEqualTo(formacaoDocente2);
    }
}
