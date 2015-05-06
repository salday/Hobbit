/**
 * Base class for all intransitive commands in the hobbit world.
 *
 * @author ram
 */
package hobbit;

import edu.monash.fit2024.simulator.matter.Action;
import edu.monash.fit2024.simulator.matter.Actor;
import edu.monash.fit2024.simulator.matter.EntityManager;
import edu.monash.fit2024.simulator.userInterface.MessageRenderer;

public abstract class HobbitAction extends Action implements HobbitActionInterface {

    private static final EntityManager<HobbitEntityInterface, HobbitLocation> entityManager = MiddleEarth.getEntitymanager();

    public HobbitAction(MessageRenderer m) {
        super(m);
    }

    /**
     * By default, commands are not move commands.  This method needs to be overridden in any
     * command that is a move command (i.e. can potentially change the actor's location).
     *
     * @return false
     */
    public boolean isMoveCommand() {
        return false;
    }

    public void execute(Actor<?> a) {
        if (a instanceof HobbitActor)
            act((HobbitActor) a);
    }

    public abstract void act(HobbitActor a);

    public int compareTo(HobbitActionInterface other) {
        return this.getDescription().compareTo(other.getDescription());
    }

    public static EntityManager<HobbitEntityInterface, HobbitLocation> getEntitymanager() {
        return entityManager;
    }


}
