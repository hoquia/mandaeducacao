package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrecoEmolumentoMapperTest {

    private PrecoEmolumentoMapper precoEmolumentoMapper;

    @BeforeEach
    public void setUp() {
        precoEmolumentoMapper = new PrecoEmolumentoMapperImpl();
    }
}
