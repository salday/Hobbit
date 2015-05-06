package hobbit.tests;

import edu.monash.fit2024.simulator.matter.EntityManager;
import hobbit.*;
import hobbit.entities.actors.Goblin;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestActorsTest {

    @Test
    public void testGoblin() {
        MiddleEarth testMiddleEarth = new MiddleEarth();
        TextInterface ui = new TextInterface(testMiddleEarth);
        Goblin test = new Goblin(ui, testMiddleEarth);
        testMiddleEarth.initializeWorld(ui);

        //Test Hobbit Actor Methods
        TestHobbitTest superTestMethods = new TestHobbitTest();
        //Assume Goblin should have 50 hitpoints
        superTestMethods.testHobbitActor(test, Team.EVIL, 50, ui, testMiddleEarth, false);

        //test act
        //Assumption Goblin always starts at (7,7)
        //moves in any direction
        HobbitGrid g = testMiddleEarth.getGrid();
        HobbitLocation currentLocation;
        EntityManager em = testMiddleEarth.getEntityManager();
        int goblinSomewhere = 0;
        boolean goblinInSameSpot = false;
        List<HobbitEntityInterface> l;
        test.act();
        for (int i = 6; i <= 8; i++) {
            for (int j = 6; j <= 8; j++) {

                currentLocation = g.getLocationByCoordinates(i, j);
                l = em.contents(currentLocation);
                if (l != null) {
                    for (HobbitEntityInterface item : l) {

                        if (item.getSymbol() == "g") {
                            if (!(i == 7 && j == 7)) goblinSomewhere++;
                            else goblinInSameSpot = true;
                        }
                    }
                }
            }
        }
        assertEquals(goblinSomewhere, 1);
        assertEquals(goblinInSameSpot, false);

    }

}


