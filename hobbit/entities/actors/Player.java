/**
 * A very minimal Actor that the user can control.  Its tick() method
 * prompts the user to select a command.
 *
 * @author ram
 */
package hobbit.entities.actors;


import edu.monash.fit2024.simulator.space.Location;
import edu.monash.fit2024.simulator.userInterface.MessageRenderer;
import hobbit.*;

import java.util.List;

public class Player extends HobbitActor {


    public Player(Team team, int hitpoints, MessageRenderer m, MiddleEarth world) {
        super(team, hitpoints, m, world);
        humanControlled = true; // this feels like a hack. Surely this should be dynamic
    }

    /**
     * Allow the user to select an action to perform, and then add that action to
     * the event queue.
     *
     * @param l the Location of the player (not actually used)
     * @author ram
     */
    public void tick(Location l) {
        describeScene();
        scheduler.schedule(TextInterface.getUserDecision(this), this, 1);
    }

    @Override
    /**
     * Does nothing -- the user selects what this Player does.
     */
    public void act() {

    }

    public void describeScene() {
        HobbitLocation location = this.world.getEntityManager().whereIs(this);
        say(this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription());
        HobbitEntityInterface itemCarried = this.getItemCarried();
        if (itemCarried != null) {
            say(this.getShortDescription()
                    + " is holding " + itemCarried.getShortDescription() + " [" + itemCarried.getHitpoints() + "]");
        }
        List<HobbitEntityInterface> contents = this.world.getEntityManager().contents(location);
        if (contents.size() > 1) { // if it is equal to one, the only thing here is this TestActor, so there is nothing to report
            say(this.getShortDescription() + " can see:");
            for (HobbitEntityInterface entity : contents) {
                if (entity != this) { // don't include self in scene description
                    say("\t " + entity.getSymbol() + " - " + entity.getLongDescription() + " [" + entity.getHitpoints() + "]");
                }
            }
        }
    }
}
