package hobbit.entities.actors;

import edu.monash.fit2024.gridworld.Grid;
import edu.monash.fit2024.simulator.matter.EntityManager;
import edu.monash.fit2024.simulator.space.Location;
import edu.monash.fit2024.simulator.userInterface.MessageRenderer;
import hobbit.*;
import hobbit.actions.Move;
import hobbit.entities.Saddlebag;

import java.util.List;

/**
 * Created by ProDigy SML on 4/27/2015.
 * <p/>
 * NOTE: coordinates are set within the constructor,
 */
public class Pony extends HobbitActor {

    private Grid.CompassBearing myDirection;
    private Location currentLocation;
    private HobbitActor owner;

    //Constructor 2 => Has an owner
    public Pony(MessageRenderer m, MiddleEarth world) {
        super(Team.NEUTRAL, 40, m, world);

        this.owner = null;

        this.myDirection = Grid.CompassBearing.getRandomBearing();
    }

    //Move until I find my owner
    public void act() {
        if (this.owner == null) {
            this.actNoOwner();
        } else {

            if (this.owner.getClass().isInstance(Player.class)) {
                System.out.println(this.getItemCarried().getShortDescription());
                if (this.getItemCarried().getClass().isInstance(Saddlebag.class)) {
                    ((Saddlebag) (this.getItemCarried())).getDescription();
                }
            }

            this.actWithOwner();
        }

    }

    private void actWithOwner() {

        EntityManager<HobbitEntityInterface, HobbitLocation> entityManager = this.world.getEntityManager();

        boolean breakFlag = false;

        for (Grid.CompassBearing d : Grid.CompassBearing.values()) {

            List<HobbitEntityInterface> hobbitEntities = entityManager.contents((HobbitLocation) entityManager.whereIs(this).getNeighbour(d));

            if (hobbitEntities != null) {
                for (HobbitEntityInterface i : hobbitEntities) {
                    if (i == this.owner) {
                        this.myDirection = d;
                        breakFlag = true;

                        Move myMove = new Move(myDirection, messageRenderer, world);
                        this.scheduler.schedule(myMove, this, 1);

                        break;
                    }
                }

                if (breakFlag) {
                    break;
                }
            }
        }

    }

    private void actNoOwner() {

        Grid.CompassBearing oldDirection = myDirection;

        int turns = (int) ((Math.random() * 8));

        turns = turns * 45;

        myDirection = myDirection.turn(turns);

        while (!MiddleEarth.getEntitymanager().seesExit(this, myDirection)) {

            turns = (int) ((Math.random() * 8));

            turns = turns * 45;

            myDirection = myDirection.turn(turns);
        }

        if (myDirection != oldDirection)    // we turned
            System.out.println(this.getShortDescription() + " decides to go " + myDirection + " next.");

        // I can see an exit.
        Move myMove = new Move(myDirection, messageRenderer, world);

        //Attempt to initialise the scheduler...

        this.scheduler.schedule(myMove, this, 1);

        //start searching for neighbours

        EntityManager<HobbitEntityInterface, HobbitLocation> entityManager = this.world.getEntityManager();

        boolean breakFlag = false;

        System.out.println("Looking for owner");

        for (Grid.CompassBearing d : Grid.CompassBearing.values()) {

            List<HobbitEntityInterface> hobbitEntities = entityManager.contents((HobbitLocation) entityManager.whereIs(this).getNeighbour(d));

            if (hobbitEntities != null) {

                System.out.println("Checking Direction");

                for (HobbitEntityInterface i : hobbitEntities) {
                    if (i.getClass().getSuperclass() == HobbitActor.class) {
                        this.setOwner((HobbitActor) i);
                        this.setTeam(this.owner.getTeam());

                        Location location = world.getEntityManager().whereIs(owner);
                        world.getEntityManager().setLocation(this, (HobbitLocation) location);

                        breakFlag = true;
                        System.out.println("I found my owner!!! :D");
                        break;
                    }
                }

                if (breakFlag) {
                    break;
                }
            }
        }
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Location getCurrentLocation() {
        return this.currentLocation;
    }

    public void setOwner(HobbitActor owner) {
        this.owner = owner;
        this.owner.setPony(this);
    }

    public HobbitActor getOwner() {
        return this.owner;
    }
}