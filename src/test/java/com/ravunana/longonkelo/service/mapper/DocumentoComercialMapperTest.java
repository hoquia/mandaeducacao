package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DocumentoComercialMapperTest {

    private DocumentoComercialMapper documentoComercialMapper;

    @BeforeEach
    public void setUp() {
        documentoComercialMapper = new DocumentoComercialMapperImpl();
    }
}
