package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlanoCurricularMapperTest {

    private PlanoCurricularMapper planoCurricularMapper;

    @BeforeEach
    public void setUp() {
        planoCurricularMapper = new PlanoCurricularMapperImpl();
    }
}
