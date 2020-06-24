package com.digitalAcademy.customer.util;


import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MaxNum{
    public boolean max(int a, int b) {
        return a > b ? true : false;
    }
}

class TestMaxNum{
    private MaxNum maxNum = new MaxNum();

    @Test
    void testGetMax(){
        assertTrue(maxNum.max(5,2));
        assertFalse(maxNum.max(1,5));
    }
}