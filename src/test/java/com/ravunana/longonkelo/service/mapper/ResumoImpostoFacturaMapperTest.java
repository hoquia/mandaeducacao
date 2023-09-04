package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResumoImpostoFacturaMapperTest {

    private ResumoImpostoFacturaMapper resumoImpostoFacturaMapper;

    @BeforeEach
    public void setUp() {
        resumoImpostoFacturaMapper = new ResumoImpostoFacturaMapperImpl();
    }
}
