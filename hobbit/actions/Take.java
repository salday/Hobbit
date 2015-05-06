/**
 * Action that lets a HobbitActor pick up an object.
 *
 * @author ram
 */
package hobbit.actions;

import edu.monash.fit2024.simulator.userInterface.MessageRenderer;
import hobbit.HobbitAction;
import hobbit.HobbitActor;
import hobbit.HobbitAffordance;
import hobbit.HobbitEntityInterface;
import hobbit.entities.Saddlebag;

public class Take extends HobbitAffordance {

    public Take(HobbitEntityInterface theTarget, MessageRenderer m) {
        super(theTarget, m);
    }

    @Override
    /**
     * This always returns true -- we assume anything that's got a Take affordance can be picked up
     * by any HobbitActor.
     *
     * @author ram
     * @param a the HobbitActor we are querying
     * @return true
     */
    public boolean canDo(HobbitActor a) {
        if (a.getItemCarried() == null) {
            return true;
        }
        return false;
    }

    @Override
    /**
     * Perform the take action.
     *
     * Sets a's carried item to be the target of this Affordance.
     *
     * @author ram
     * @param a the HobbitActor that is taking the target
     */
    public void act(HobbitActor a) {
        if (target instanceof Saddlebag) {
            ((Saddlebag) target).takeBag(a);
            HobbitAction.getEntitymanager().remove(target);
        } else if (target instanceof HobbitEntityInterface) {
            a.setItemCarried((HobbitEntityInterface) target);
            HobbitAction.getEntitymanager().remove(target);
        }
    }


    @Override
    /**
     * A String describing what this action will do, suitable for display in a user interface
     *
     * @author ram
     * @return String comprising "take " and the short description of the target of this Affordance
     */
    public String getDescription() {
        // TODO Auto-generated method stub
        return "take " + target.getShortDescription();
    }

}
