package hobbit.entities.actors;

import edu.monash.fit2024.simulator.userInterface.MessageRenderer;
import hobbit.*;

import java.util.List;

public class TestActor extends HobbitActor {

    public TestActor(MessageRenderer m, MiddleEarth world) {
        super(Team.GOOD, 50, m, world);
    }

    @Override
    /**
     * Stands still and waits for other actors to try its affordances. Introduced so that Attack could be tested
     *
     * @author dsquire
     */
    public void act() {
        say(this.getShortDescription() + " is standing still at " + this.world.getEntityManager().whereIs(this).getShortDescription());
        describeScene();
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
