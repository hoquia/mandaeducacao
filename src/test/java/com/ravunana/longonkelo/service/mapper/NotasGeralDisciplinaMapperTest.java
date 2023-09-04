package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotasGeralDisciplinaMapperTest {

    private NotasGeralDisciplinaMapper notasGeralDisciplinaMapper;

    @BeforeEach
    public void setUp() {
        notasGeralDisciplinaMapper = new NotasGeralDisciplinaMapperImpl();
    }
}
