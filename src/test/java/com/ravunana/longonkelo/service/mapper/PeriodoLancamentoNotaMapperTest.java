package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PeriodoLancamentoNotaMapperTest {

    private PeriodoLancamentoNotaMapper periodoLancamentoNotaMapper;

    @BeforeEach
    public void setUp() {
        periodoLancamentoNotaMapper = new PeriodoLancamentoNotaMapperImpl();
    }
}
