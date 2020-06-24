package com.digitalAcademy.customer.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EquationQuadratic {
    public String calEquationQuadratic(double a, double b, double c) {
        double D = b * b - 4 * a * c;
        if (D > 0) {
            double x1, x2;
            x1 = (-b - Math.sqrt(D)) / (2 * a);
            x2 = (-b + Math.sqrt(D)) / (2 * a);
            return ("x1 = " + x1 + ", x2 = " + x2);
        } else if (D == 0) {
            double x;
            x = -b / (2 * a);
            return ("x = " + x);
        } else {
            return ("Equation has no roots");
        }
    }
}

class TestEquationQuadratic{
    private EquationQuadratic quadratic = new EquationQuadratic();

    @Test
    void testGetCalQuadratic(){
        assertEquals("x1 = -4.7912878474779195, x2 = -0.20871215252208009",quadratic.calEquationQuadratic(1,5,1));
        assertEquals("x = -1.0",quadratic.calEquationQuadratic(1,2,1));
        assertEquals("Equation has no roots",quadratic.calEquationQuadratic(1,2,3));
    }
}


