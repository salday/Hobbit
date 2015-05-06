/**
 * Class that represents Affordances in the hobbit world.
 * <p/>
 * This class implements such methods in HobbitActionInterface as can reasonably
 * be written at this stage, to minimize the amount of work that needs to be done to add new Affordances.
 *
 * @author ram
 */
package hobbit;

import edu.monash.fit2024.simulator.matter.Actor;
import edu.monash.fit2024.simulator.matter.Affordance;
import edu.monash.fit2024.simulator.userInterface.MessageRenderer;

public abstract class HobbitAffordance extends Affordance implements HobbitActionInterface {

    public HobbitAffordance(HobbitEntityInterface theTarget, MessageRenderer m) {
        super(theTarget, m);
    }

    @Override
    /**
     * Commands default to not being Move commands.
     *
     * Move commands, obviously, need to override this.
     *
     * @author ram
     * @return false
     */
    public boolean isMoveCommand() {
        return false;
    }


    @Override
    public int getDuration() {
        return 1;
    }


    /**
     * Accessor for the target of this affordance.
     *
     * Checks that the target conforms to the HobbitEntityInterface and
     * downcast and returns it if so; otherwise returns null.
     *
     * @return the current target or null if the current target isn't a HobbitEntityInterface
     */
    protected HobbitEntityInterface getTarget() {
        if (target instanceof HobbitEntityInterface)
            return (HobbitEntityInterface) target;
        return null;
    }

    @Override
    /**
     * Wrapper for the act() method that downcasts its parameter to minimize
     * dangerous downcasting in subclasses.
     *
     * @author ram
     */
    public void execute(Actor<?> actor) {
        if (actor instanceof HobbitActor)
            act((HobbitActor) actor);

    }

    /**
     * Compares two HobbitActionInterfaces.
     *
     * This is used for alphabetizing command lists for pretty output.
     *
     * @author ram
     * @param the HobbitActionInterface to compare to
     * @return 0 if equal, negative if less than, positive if greater than.
     */
    public int compareTo(HobbitActionInterface other) {
        return this.getDescription().compareTo(other.getDescription());
    }
}
