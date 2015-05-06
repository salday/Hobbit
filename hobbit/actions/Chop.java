/**
 * Command to chop down trees.
 * <p/>
 * This affordance is attached to trees, and is only available to actors that are
 * carrying a CHOPPER.
 *
 * @author ram
 */
package hobbit.actions;

import edu.monash.fit2024.simulator.userInterface.MessageRenderer;
import hobbit.*;

public class Chop extends HobbitAffordance implements HobbitActionInterface {

    public Chop(HobbitEntityInterface theTarget, MessageRenderer m) {
        super(theTarget, m);
    }

    @Override
    public int getDuration() {
        return 1;
    }

    @Override
    public String getDescription() {
        return "chop " + this.target.getShortDescription();
    }


    @Override
    /**
     * Determine whether a particular actor can chop the target.
     *
     * @author ram
     * @param a the actor being queried
     * @return true if the actor is carrying something with the CHOPPER capability.
     */
    public boolean canDo(HobbitActor a) {
        if (a.getItemCarried() == null)
            return false;
        return a.getItemCarried().hasCapability(Capability.CHOPPER);
    }

    @Override
    /**
     * Perform the Chop command on a tree.
     *
     * This replaces the tree with a pile of wood.
     *
     * @author ram
     * @param the actor who is chopping
     */
    public void act(HobbitActor a) {
        // tree becomes a pile of wood
        // remove chop affordance and change the way it appears
        target.removeAffordance(this);
        target.setLongDescription("A pile of wood that was once " + target.getShortDescription());
        target.setShortDescription("a pile of wood");
        getTarget().setSymbol("w");
    }
}
