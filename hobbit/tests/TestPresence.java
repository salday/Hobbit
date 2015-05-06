package hobbit.tests;

import edu.monash.fit2024.simulator.matter.EntityManager;
import hobbit.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by ProDigy SML on 4/24/2015.
 */
public class TestPresence {

    @Test
    public void CheckForPresence() {

        MiddleEarth middleEarth = new MiddleEarth();

        TextInterface textInterface = new TextInterface(middleEarth);

        middleEarth.initializeWorld(textInterface);

        HobbitGrid testGrid = middleEarth.getGrid();

        HobbitLocation currentLocation;

        EntityManager em = middleEarth.getEntityManager();
        List<HobbitEntityInterface> listOfHobbitEntities;

        int ring = 0;
        int armor = 0;
        int goblin = 0;
        int pony = 0;
        int sword = 0;
        int treasure = 0;
        int bilbo = 0;
        int saddlebag = 0;
        int axe = 0;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                currentLocation = testGrid.getLocationByCoordinates(i, j);
                listOfHobbitEntities = em.contents(currentLocation);
                if (listOfHobbitEntities != null) {
                    for (HobbitEntityInterface item : listOfHobbitEntities) {
                        String currentItem = item.getSymbol();

                        //being used as current java version does not do switch case with strings properly

                        if ("o".equals(currentItem)) {
                            ring++;
                        } else if ("c".equals(currentItem)) {
                            armor++;
                        } else if ("g".equals(currentItem)) {
                            goblin++;
                        } else if ("p".equals(currentItem)) {
                            pony++;
                        } else if ("Æ".equals(currentItem)) {
                            axe++;
                        } else if ("t".equals(currentItem)) {
                            sword++;
                        } else if ("×".equals(currentItem)) {
                            treasure++;
                        } else if ("u".equals(currentItem)) {
                            saddlebag++;
                        } else if ("@".equals(currentItem)) {
                            if (i == 5 && j == 6) bilbo++;
                        }

//						switch(currentItem){
//							case "o": ring++; break;
//							case "c": armor++; break;
//							case "g": goblin++; break;
//							case "p": pony++; break;
//							case "Æ": axe++; break;
//							case "t": sword++; break;
//							case "×": treasure++; break;
//							case "u": saddlebag++; break;
//							case "@": 
//								if (i ==5 && j == 6){bilbo++;} break;
//						}
                    }
                }
            }
        }
        assertEquals(ring, 1);
        assertEquals(armor, 1);
        assertEquals(goblin, 1);

        //sword symbol is incorrect and hence it crashes, as it does not count

        assertEquals(sword, 1);
        assertEquals(treasure, 1);
        assertEquals(bilbo, 1);
        assertEquals(axe, 1);

        assertEquals(pony, 1);
        assertEquals(saddlebag, 2);
    }
}