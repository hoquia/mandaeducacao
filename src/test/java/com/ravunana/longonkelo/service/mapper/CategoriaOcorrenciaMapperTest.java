package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoriaOcorrenciaMapperTest {

    private CategoriaOcorrenciaMapper categoriaOcorrenciaMapper;

    @BeforeEach
    public void setUp() {
        categoriaOcorrenciaMapper = new CategoriaOcorrenciaMapperImpl();
    }
}
