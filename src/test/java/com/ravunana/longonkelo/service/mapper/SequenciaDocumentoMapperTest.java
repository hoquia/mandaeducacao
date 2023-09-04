package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SequenciaDocumentoMapperTest {

    private SequenciaDocumentoMapper sequenciaDocumentoMapper;

    @BeforeEach
    public void setUp() {
        sequenciaDocumentoMapper = new SequenciaDocumentoMapperImpl();
    }
}
