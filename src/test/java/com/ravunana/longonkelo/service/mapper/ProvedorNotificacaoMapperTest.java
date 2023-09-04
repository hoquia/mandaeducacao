package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProvedorNotificacaoMapperTest {

    private ProvedorNotificacaoMapper provedorNotificacaoMapper;

    @BeforeEach
    public void setUp() {
        provedorNotificacaoMapper = new ProvedorNotificacaoMapperImpl();
    }
}
