package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LookupItemMapperTest {

    private LookupItemMapper lookupItemMapper;

    @BeforeEach
    public void setUp() {
        lookupItemMapper = new LookupItemMapperImpl();
    }
}
