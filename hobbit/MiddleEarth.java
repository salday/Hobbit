/**
 * Class representing the Hobbit world.
 *
 * @author ram
 */
package hobbit;

import edu.monash.fit2024.simulator.matter.EntityManager;
import edu.monash.fit2024.simulator.space.Direction;
import edu.monash.fit2024.simulator.space.Location;
import edu.monash.fit2024.simulator.space.World;
import edu.monash.fit2024.simulator.userInterface.MessageRenderer;
import hobbit.actions.Leave;
import hobbit.actions.Take;
import hobbit.entities.Axe;
import hobbit.entities.Saddlebag;
import hobbit.entities.Sword;
import hobbit.entities.Tree;
import hobbit.entities.actors.Goblin;
import hobbit.entities.actors.Player;
import hobbit.entities.actors.Pony;
import hobbit.entities.actors.TestActor;

public class MiddleEarth extends World {
    private HobbitGrid myGrid;

    private static final EntityManager<HobbitEntityInterface, HobbitLocation> entityManager = new EntityManager<HobbitEntityInterface, HobbitLocation>();

    public MiddleEarth() {
        HobbitLocation.HobbitLocationMaker factory = HobbitLocation.getMaker();
        myGrid = new HobbitGrid(factory);
        space = myGrid;

    }

    /** Accessor for the height of the grid.
     *
     * @author ram
     * @return the height of the grid
     */
    public int height() {
        return space.getHeight();
    }

    /**
     * Accessor for the width of the grid
     *
     * @author ram
     * @return the width of the grid
     */
    public int width() {
        return space.getWidth();
    }

    /**
     * Set up the world, setting descriptions for locations and placing items and actors
     * on the grid.
     *
     * @author ram
     * @param iface a MessageRenderer to be passed onto newly-created entities
     */
    public void initializeWorld(MessageRenderer iface) {
        HobbitLocation loc;
        // Set default location string
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                loc = myGrid.getLocationByCoordinates(col, row);
                loc.setLongDescription("Middle Earth (" + col + ", " + row + ")");
                loc.setShortDescription("Middle Earth (" + col + ", " + row + ")");
                loc.setSymbol('.');
            }
        }


        // The Shire
        for (int row = 4; row < 7; row++) {
            for (int col = 3; col < 6; col++) {
                loc = myGrid.getLocationByCoordinates(col, row);
                loc.setLongDescription("The Shire (" + col + ", " + row + ")");
                loc.setShortDescription("The Shire (" + col + ", " + row + ")");
                loc.setSymbol('S');
            }
        }

        //Bag End
        loc = myGrid.getLocationByCoordinates(4, 5);
        loc.setLongDescription("Bag End");
        loc.setShortDescription("Bag End");
        loc.setSymbol('b');
        // let's put an object here - just for a test
        HobbitEntity chainMail = new HobbitEntity(iface);
        chainMail.setShortDescription("chain mail");
        chainMail.setLongDescription("a small chain-mail suit");
        chainMail.setSymbol("c");
        chainMail.setHitpoints(5);
        // add a Take affordance to the chain mail, so that an actor can take it
        entityManager.setLocation(chainMail, loc);
        chainMail.addAffordance(new Take(chainMail, iface));
        chainMail.addAffordance(new Leave(chainMail, iface));
        // Bilbo
        Player bilbo = new Player(Team.GOOD, 100, iface, this);
        bilbo.setShortDescription("Bilbo");
        entityManager.setLocation(bilbo, loc);
        bilbo.resetMoveCommands(loc);

        // The River Sherbourne
        for (int col = 2; col < 7; col++) {
            loc = myGrid.getLocationByCoordinates(col, 7);
            loc.setShortDescription("The River Sherboune (" + col + ", " + 7 + ")");
            loc.setLongDescription("The River Sherboune (" + col + ", " + 7 + ")");
            loc.setSymbol('R');
            loc.setEmptySymbol('~'); // to represent water
        }

        // Mirkwood Forest
        for (int row = 0; row < 10; row++) {
            for (int col = 8; col < 10; col++) {
                loc = myGrid.getLocationByCoordinates(col, row);
                loc.setLongDescription("Mirkwood Forest (" + col + ", " + row + ")");
                loc.setShortDescription("Mirkwood Forest (" + col + ", " + row + ")");
                loc.setSymbol('F');

                // forests have trees
                for (int i = 0; i < 4; i++)
                    entityManager.setLocation(new Tree(iface), loc);
            }
        }
        
		/*
		 * Scatter some other entities and actors around
		 */
        // a ring
        loc = myGrid.getLocationByCoordinates(3, 1);
        HobbitEntity ring = new HobbitEntity(iface);
        ring.setShortDescription("a ring");
        ring.setLongDescription("a dully shining ring");
        ring.setSymbol("o");
        ring.setHitpoints(500);
        // add a Take affordance to the ring, so that an actor can take it
        entityManager.setLocation(ring, loc);
        ring.addAffordance(new Take(ring, iface));
        ring.addAffordance(new Leave(ring, iface));
        // a troll treasure
        loc = myGrid.getLocationByCoordinates(1, 5);
        HobbitEntity treasure = new HobbitEntity(iface);
        treasure.setShortDescription("a treasure");
        treasure.setLongDescription("a troll treasure");
        treasure.setSymbol("�");
        treasure.setHitpoints(100);
        // add a Take affordance to the treasure, so that an actor can take it
        entityManager.setLocation(treasure, loc);
        treasure.addAffordance(new Take(treasure, iface));
        treasure.addAffordance(new Leave(treasure, iface));
        // "And my axe!"
        Axe axe = new Axe(iface);
        loc = myGrid.getLocationByCoordinates(5, 5);
        entityManager.setLocation(axe, loc);

        // A sword
        Sword sword = new Sword(iface);
        loc = myGrid.getLocationByCoordinates(3, 4);
        entityManager.setLocation(sword, loc);

        // A bad guy
        Goblin goblin = new Goblin(iface, this);
        goblin.setShortDescription("Glorp the goblin");
        goblin.setSymbol("g");
        loc = myGrid.getLocationByCoordinates(7, 7);
        entityManager.setLocation(goblin, loc);

        // A target actor for testing actions
        TestActor testActor = new TestActor(iface, this);
        testActor.setShortDescription("Dorko the nobody");
        testActor.setSymbol("d");
        loc = myGrid.getLocationByCoordinates(3, 3);
        entityManager.setLocation(testActor, loc);


        //Initialise pony
        Pony pony = new Pony(iface, this);
        loc = myGrid.getLocationByCoordinates(2, 9);
        entityManager.setLocation(pony, loc);
        pony.setLongDescription("IM a crazy pony");
        pony.setShortDescription("pony");
        pony.setSymbol("P");

        //Initialise Saddlebags
        Saddlebag saddlebag = new Saddlebag(iface);
        loc = myGrid.getLocationByCoordinates(1, 9);
        saddlebag.setLongDescription("This is a black leather Saddle Bag! ");
        saddlebag.setShortDescription("Saddle Bag!");
        saddlebag.setSymbol("U");
        entityManager.setLocation(saddlebag, loc);
        saddlebag.addAffordance(new Take(saddlebag, iface));
        saddlebag.addAffordance(new Leave(saddlebag, iface));
    }

    /**
     * Display the grid.
     *
     * This is a wrapper for Grid.render().
     *
     * @author ram
     */
    public void displayGrid() {
        myGrid.render(entityManager);
    }


    /**
     * Determine whether a given actor can move in a given direction.
     *
     * @author ram
     * @param a the actor
     * @param whichDirection the direction
     * @return true if the actor can see an exit in that direction, false otherwise.
     */
    public boolean canMove(HobbitActor a, Direction whichDirection) {
        HobbitLocation where = (HobbitLocation) entityManager.whereIs(a); // requires a cast for no reason I can discern
        return where.hasExit(whichDirection);
    }


    /**
     * Accessor for the grid.
     *
     * @author ram
     * @return the grid
     */


    public HobbitGrid getGrid() {
        return myGrid;
    }

    /**
     * Move an actor in a direction.
     *
     * @author ram
     * @param a the actor to move
     * @param whichDirection the direction in which to move the actor
     */
    public void moveEntity(HobbitActor a, Direction whichDirection) {
        Location loc = entityManager.whereIs(a).getNeighbour(whichDirection);
        // Base class unavoidably stores superclass references, so do a checked downcast here
        if (loc instanceof HobbitLocation)
            entityManager.setLocation(a, (HobbitLocation) entityManager.whereIs(a).getNeighbour(whichDirection));
    }

    /**
     * Wrapper for entityManager.whereIs().
     *
     * @author ram
     * @param e the entity to find
     * @return the Location of that entity, or null if it's not in the grid
     */
    public Location find(HobbitEntityInterface e) {
        return entityManager.whereIs(e);
    }

    @SuppressWarnings("unchecked")
    /**
     * This is only here for compliance with the abstract base class's interface and is not supposed to be
     * called.
     */
    public EntityManager<HobbitEntityInterface, HobbitLocation> getEntityManager() {
        return MiddleEarth.getEntitymanager();
    }

    public static EntityManager<HobbitEntityInterface, HobbitLocation> getEntitymanager() {
        return entityManager;
    }
}
