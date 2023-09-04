package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DisciplinaCurricularMapperTest {

    private DisciplinaCurricularMapper disciplinaCurricularMapper;

    @BeforeEach
    public void setUp() {
        disciplinaCurricularMapper = new DisciplinaCurricularMapperImpl();
    }
}
