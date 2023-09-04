package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InstituicaoEnsinoMapperTest {

    private InstituicaoEnsinoMapper instituicaoEnsinoMapper;

    @BeforeEach
    public void setUp() {
        instituicaoEnsinoMapper = new InstituicaoEnsinoMapperImpl();
    }
}
