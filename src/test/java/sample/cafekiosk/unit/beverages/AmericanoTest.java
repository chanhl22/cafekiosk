package sample.cafekiosk.unit.beverages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AmericanoTest {

    @Test
    void getName() {
        Americano americano = new Americano();

        assertEquals(americano.getName(), "아메리카노");
    }

}