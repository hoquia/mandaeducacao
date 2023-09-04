package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HistoricoSaudeMapperTest {

    private HistoricoSaudeMapper historicoSaudeMapper;

    @BeforeEach
    public void setUp() {
        historicoSaudeMapper = new HistoricoSaudeMapperImpl();
    }
}
