package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LicaoMapperTest {

    private LicaoMapper licaoMapper;

    @BeforeEach
    public void setUp() {
        licaoMapper = new LicaoMapperImpl();
    }
}
