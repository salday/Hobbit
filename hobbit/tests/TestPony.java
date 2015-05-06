package hobbit.tests;

import hobbit.MiddleEarth;
import hobbit.Team;
import hobbit.TextInterface;
import hobbit.entities.actors.Pony;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ProDigy SML on 4/28/2015.
 */
public class TestPony {

    @Test
    public void CorrectTeam() {
        MiddleEarth middleEarth = new MiddleEarth();

        TextInterface textInterface = new TextInterface(middleEarth);

        Pony pony = new Pony(textInterface, middleEarth);

        assertEquals(pony.getTeam(), Team.NEUTRAL);
    }
}