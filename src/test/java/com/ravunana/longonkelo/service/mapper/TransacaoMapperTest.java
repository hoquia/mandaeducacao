package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransacaoMapperTest {

    private TransacaoMapper transacaoMapper;

    @BeforeEach
    public void setUp() {
        transacaoMapper = new TransacaoMapperImpl();
    }
}
