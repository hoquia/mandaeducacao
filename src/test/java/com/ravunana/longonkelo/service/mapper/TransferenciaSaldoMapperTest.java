package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransferenciaSaldoMapperTest {

    private TransferenciaSaldoMapper transferenciaSaldoMapper;

    @BeforeEach
    public void setUp() {
        transferenciaSaldoMapper = new TransferenciaSaldoMapperImpl();
    }
}
