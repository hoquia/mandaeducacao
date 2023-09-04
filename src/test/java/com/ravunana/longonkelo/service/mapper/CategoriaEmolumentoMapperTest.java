package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoriaEmolumentoMapperTest {

    private CategoriaEmolumentoMapper categoriaEmolumentoMapper;

    @BeforeEach
    public void setUp() {
        categoriaEmolumentoMapper = new CategoriaEmolumentoMapperImpl();
    }
}
