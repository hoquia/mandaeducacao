package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LongonkeloHistoricoMapperTest {

    private LongonkeloHistoricoMapper longonkeloHistoricoMapper;

    @BeforeEach
    public void setUp() {
        longonkeloHistoricoMapper = new LongonkeloHistoricoMapperImpl();
    }
}
