package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DocenteMapperTest {

    private DocenteMapper docenteMapper;

    @BeforeEach
    public void setUp() {
        docenteMapper = new DocenteMapperImpl();
    }
}
