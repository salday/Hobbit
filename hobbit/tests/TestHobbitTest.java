package hobbit.tests;

import edu.monash.fit2024.gridworld.Grid.CompassBearing;
import edu.monash.fit2024.simulator.matter.EntityManager;
import edu.monash.fit2024.simulator.space.LocationMaker;
import hobbit.*;
import hobbit.entities.Axe;
import hobbit.entities.Sword;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class TestHobbitTest {

    @Test
    public void testHobbitLocation() {

        HobbitLocation test = new HobbitLocation();
        //test get and set methods for symbol
        test.setSymbol('7');
        assertEquals(test.getSymbol(), '7');

        //test default, get and set for empty symbol
        assertEquals(test.getEmptySymbol(), '.');
        test.setEmptySymbol('\'');
        assertEquals(test.getEmptySymbol(), '\'');

        //testSet long Description
        assertEquals(test.getLongDescription(), null);
        test.setLongDescription("I am a great Person, Greater then you all");
        assertEquals(test.getLongDescription(), "I am a great Person, Greater then you all");

        //test short Description
        assertEquals(test.getShortDescription(), null);
        test.setShortDescription("");
        assertEquals(test.getShortDescription(), "");

        //test HobbitLocationMaker and make inside and getMaker from hobbitlocation
        LocationMaker test1 = test.getMaker();
        //expected output is a HobbitLocation
        //If test fails, a type cast exception will be thrown. I am currently unsure how to catch and throw a more usful error so I will leave it
        test = (HobbitLocation) test1.make();


    }

    @Test
    public void testTeam() {
        // No test methods, only contains eNum
    }

    @Test
    public void testMiddleEarth() {
        //must still test initializeWorld
        //must test manually the displayGrid Class
        MiddleEarth test = new MiddleEarth();

        //test height
        //should get grid's height, which should be the maps height, which according to the map in the specification is 10
        assertEquals(test.height(), 10);

        //test width
        //should get grid's width, which should be the maps width, which according to the map in the specification is 10
        assertEquals(test.width(), 10);
        
		/*test initialize world, getGrid, and getEntertityManager, and getEntertitymanger
        //Results: 	river should be at (3-7, 8) on map
		 * 			Mirkwood should be at (9-10, 1-10) on map
		 * 			Shire should be at (4-6, 5-7) excluding (5,6) on map
		 * 			Bag End should be at (5,6) on map
		 * 			Should be ring on map
		 * 			should be treasure on map
		 * 			bilbo should be on map
		 * 			should be a sword on map
		 * 			should be armor on map
		 * 			should have a goblin on map
		 * 			should have a pony on map
		 * 			should have two saddle bags on map
		 * 
		*/

        TextInterface iTest = new TextInterface(test);
        test.initializeWorld(iTest);
        HobbitGrid testGrid = test.getGrid();
        HobbitLocation currentLocation;
        EntityManager em = test.getEntityManager();
        List<HobbitEntityInterface> l;
        int ring = 0;
        int armor = 0;
        int goblin = 0;
        int pony = 0;
        int sword = 0;
        int treasure = 0;
        int bilbo = 0;
        int saddlebag = 0;
        int axe = 0;
        //Test that the ring, armor, goblin, pony, sword, treasure, Bilbo and two saddle bags are on map is on map
        //Assuming bilbo starts off at bags end
        //Assuming a sword looks like t
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                currentLocation = testGrid.getLocationByCoordinates(i, j);
                l = em.contents(currentLocation);
                if (l != null) {
                    for (HobbitEntityInterface item : l) {
                        String thingo = item.getSymbol();
                        if ("o".equals(thingo)) {
                            ring++;
                        } else if ("c".equals(thingo)) {
                            armor++;
                        } else if ("g".equals(thingo)) {
                            goblin++;
                        } else if ("p".equals(thingo)) {
                            pony++;
                        } else if ("Æ".equals(thingo)) {
                            axe++;
                        } else if ("t".equals(thingo)) {
                            sword++;
                        } else if ("×".equals(thingo)) {
                            treasure++;
                        } else if ("u".equals(thingo)) {
                            saddlebag++;
                        } else if ("@".equals(thingo)) {
                            if (i == 5 && j == 6) bilbo++;
                        }

//						switch(thingo){
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
        assertEquals(pony, 1);
        assertEquals(sword, 1);
        assertEquals(treasure, 1);
        assertEquals(bilbo, 1);
        assertEquals(saddlebag, 2);
        assertEquals(axe, 2);


        //Test that the Mirkwood is on map
        for (int i = 8; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                currentLocation = testGrid.getLocationByCoordinates(i, j);
                assertEquals(currentLocation.getSymbol(), 'F');
            }
        }

        //Test that the Shire is on map
        for (int i = 3; i < 6; i++) {
            for (int j = 4; j < 7; j++) {
                if (!(i == 4 && j == 5)) {
                    currentLocation = testGrid.getLocationByCoordinates(i, j);
                    assertEquals(currentLocation.getSymbol(), 'S');
                }
            }
        }
        //Test River is on map
        int j = 7;
        for (int i = 2; i <= 6; i++) {

            currentLocation = testGrid.getLocationByCoordinates(i, j);
            assertEquals(currentLocation.getSymbol(), 'R');

        }

        //Test Bags End is on map
        currentLocation = testGrid.getLocationByCoordinates(4, 5);
        assertEquals(currentLocation.getSymbol(), 'b');


        //Test canMove
        //biblo should be able to move in all directions
        currentLocation = testGrid.getLocationByCoordinates(5, 6);
        HobbitEntityInterface act = (HobbitEntityInterface) em.contents(currentLocation).get(1);
        for (CompassBearing d : CompassBearing.values()) {
            test.canMove((HobbitActor) act, d);
        }

        //Test moveEntity
        //biblo should be able to move in all directions
        currentLocation = testGrid.getLocationByCoordinates(5, 6);
        act = (HobbitEntityInterface) em.contents(currentLocation).get(1);
        CompassBearing d = CompassBearing.NORTH;
        test.moveEntity((HobbitActor) act, d);
        currentLocation = testGrid.getLocationByCoordinates(5, 5);
        act = (HobbitEntityInterface) em.contents(currentLocation).get(1);
        assertEquals(act.getSymbol(), "@");

        //Test find
        assertEquals(currentLocation, test.find(act));

    }

    @Test
    public void testHobbitGrid() {
        //Only can test constructor, must manually test render
        HobbitGrid test = new HobbitGrid(HobbitLocation.getMaker());
    }

    @Test
    public void testHobbitEntity() {
        MiddleEarth testMiddleEarth = new MiddleEarth();
        TextInterface ui = new TextInterface(testMiddleEarth);
        HobbitEntity test = new Axe(ui);

        //test get and set methods for symbol
        test.setSymbol("7");
        assertEquals(test.getSymbol(), "7");

        //test hasCapability
        //should start without and Capability
        assertEquals(test.hasCapability(Capability.CHOPPER), false);
        assertEquals(test.hasCapability(Capability.WEAPON), false);

        //test  getHitpoints
        //assumption: default hit points should be zero
        //more testing if gets correct value
        assertEquals(test.getHitpoints(), 0);

        //test takeDamage
        //assumption: negative damage is reasonable
        test.takeDamage(10);
        assertEquals(test.getHitpoints(), -10);
    }

    //Test
    //usefulForTestingTypesOfHobitActors
    //test actor should be team must be expected team,  h expected hitpoints, 
    public void testHobbitActor(HobbitActor test, Team team, int h, TextInterface ui, MiddleEarth testMiddleEarth, boolean isHumanControlled) {
        //must do setScheduler
        /*
		MiddleEarth testMiddleEarth = new MiddleEarth();
		TextInterface ui = new TextInterface(testMiddleEarth);
		HobbitActor test = new HobbitActor(Team.GOOD, 100, ui, testMiddleEarth);
		*/
        //test getTeam and setTeam
        assertEquals(test.getTeam(), team);
        int ord = team.ordinal() + 1;
        Team next;
        if (ord == 3) next = Team.values()[0];
        else next = Team.values()[ord];

        test.setTeam(next);
        assertEquals(test.getTeam(), next);

        //test getHitpoints, and take damage, isDead
        assertEquals(test.getHitpoints(), h);
        test.takeDamage(h / 2);
        assertEquals(test.getHitpoints(), h / 2);
        test.takeDamage(test.getHitpoints() - 1);
        assertEquals(test.isDead(), false);
        test.takeDamage(1);
        assertEquals(test.isDead(), true);

        Sword testSword = new Sword(ui);
        //getItemCarried and setItemCarried
        assertEquals(test.getItemCarried(), null);
        test.setItemCarried(testSword);
        assertEquals(test.getItemCarried(), testSword);

        //test get and set methods for symbol
        test.setSymbol("7");
        assertEquals(test.getSymbol(), "7");

        //test get and set for humanControlled
        assertEquals(test.isHumanControlled(), isHumanControlled);

    }

    @Test
    public void testApplication() {
        //nothing to test, rely on System Tests
    }
}