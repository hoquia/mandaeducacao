package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoDisciplinaCurricularMapperTest {

    private EstadoDisciplinaCurricularMapper estadoDisciplinaCurricularMapper;

    @BeforeEach
    public void setUp() {
        estadoDisciplinaCurricularMapper = new EstadoDisciplinaCurricularMapperImpl();
    }
}
