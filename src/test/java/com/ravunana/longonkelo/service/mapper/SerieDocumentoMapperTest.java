package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SerieDocumentoMapperTest {

    private SerieDocumentoMapper serieDocumentoMapper;

    @BeforeEach
    public void setUp() {
        serieDocumentoMapper = new SerieDocumentoMapperImpl();
    }
}
