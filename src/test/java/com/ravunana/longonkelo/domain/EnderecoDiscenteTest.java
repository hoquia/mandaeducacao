package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnderecoDiscenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnderecoDiscente.class);
        EnderecoDiscente enderecoDiscente1 = new EnderecoDiscente();
        enderecoDiscente1.setId(1L);
        EnderecoDiscente enderecoDiscente2 = new EnderecoDiscente();
        enderecoDiscente2.setId(enderecoDiscente1.getId());
        assertThat(enderecoDiscente1).isEqualTo(enderecoDiscente2);
        enderecoDiscente2.setId(2L);
        assertThat(enderecoDiscente1).isNotEqualTo(enderecoDiscente2);
        enderecoDiscente1.setId(null);
        assertThat(enderecoDiscente1).isNotEqualTo(enderecoDiscente2);
    }
}
