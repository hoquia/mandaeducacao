package com.ravunana.longonkelo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ItemFacturaMapperTest {

    private ItemFacturaMapper itemFacturaMapper;

    @BeforeEach
    public void setUp() {
        itemFacturaMapper = new ItemFacturaMapperImpl();
    }
}
