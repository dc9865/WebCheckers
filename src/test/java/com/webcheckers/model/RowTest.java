package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RowTest {

    private int index;
    private Row CuT;

    @BeforeEach
    public void setUp() {
        this.index = 1;
        CuT = new Row(index);
    }

    @Test
    public void testGetIndex() {
        int test = CuT.getIndex();
        assertEquals(index, test);
    }

    @Test
    public void testIterator() {
        assertNotNull(CuT.iterator());
    }
}
