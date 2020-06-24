package com.digitalAcademy.customer.util;

import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Data
class Person{
    private String firstname;
    private String lastname;

    public Person(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}

public class TestCalculator {

    private final Calculator calculator= new Calculator();
    private final Person person = new Person("John","Doe");

    @Test
    void groupedAssertions(){
        assertAll("person",
                ()->assertEquals("John",person.getFirstname()),
                ()->assertEquals("Doe",person.getLastname()));
    }

    @Test
    void testAddMethod(){
        assertEquals(2,calculator.add(1,1));
        assertEquals(6,calculator.add(1,5));
        assertTrue('a' < 'b', "Assert message can be lazily evaluated.");
    }

    @Test
    void testMultiplyMethod(){
        assertEquals(6,calculator.multiply(3,2));
        assertEquals(15,calculator.multiply(5,3));
        assertEquals(7,calculator.multiply(7,1));
    }

    @Test
    void testDivideMethod(){
        assertEquals(2,calculator.divide(6,3));
        assertEquals(10,calculator.divide(50,5));
    }
}
