package hobbit.tests;

import hobbit.MiddleEarth;
import hobbit.Team;
import hobbit.TextInterface;
import hobbit.entities.actors.Goblin;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ProDigy SML on 4/24/2015.
 */
public class TestGoblin {

    @Test
    public void CorrectTeam() {
        MiddleEarth middleEarth = new MiddleEarth();

        TextInterface textInterface = new TextInterface(middleEarth);

        Goblin goblin = new Goblin(textInterface, middleEarth);

        Assert.assertEquals(goblin.getTeam(), Team.EVIL);
    }

}