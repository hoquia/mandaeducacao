package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FormacaoDocenteMapperTest {

    private FormacaoDocenteMapper formacaoDocenteMapper;

    @BeforeEach
    public void setUp() {
        formacaoDocenteMapper = new FormacaoDocenteMapperImpl();
    }
}
