package hobbit.tests;

import hobbit.MiddleEarth;
import hobbit.TextInterface;
import hobbit.entities.Axe;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by ProDigy SML on 4/23/2015.
 */
public class TestAxe {

    @Test
    public void TestGetSymbol() {

        MiddleEarth world = new MiddleEarth();
        TextInterface ui = new TextInterface(world);

        Axe axe = new Axe(ui);

        assertEquals(axe.getSymbol(), "Æ");
    }

    @Test
    public void TestSetSymbol() {
        MiddleEarth world = new MiddleEarth();
        TextInterface ui = new TextInterface(world);

        Axe axe = new Axe(ui);
        axe.setSymbol("s");


        assertEquals(axe.getSymbol(), "s");
    }

}