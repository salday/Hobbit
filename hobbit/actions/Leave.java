package hobbit.actions;

import edu.monash.fit2024.simulator.userInterface.MessageRenderer;
import hobbit.HobbitAction;
import hobbit.HobbitActor;
import hobbit.HobbitAffordance;
import hobbit.HobbitEntityInterface;


public class Leave extends HobbitAffordance {
    private HobbitEntityInterface itemCarried;


    public Leave(HobbitEntityInterface theTarget, MessageRenderer m) {
        super(theTarget, m);
    }

    @Override
    /**
     * This always returns true -- we assume anything that's got a Leave affordance can be dropped
     * by any HobbitActor.
     *
     * @author ram
     * @param a the HobbitActor we are querying
     * @return true
     */
    public boolean canDo(HobbitActor a) {
        if (a.getItemCarried() != null) {
            itemCarried = a.getItemCarried();
            return true;
        } else {
            return false;
        }
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
        HobbitAction.getEntitymanager().setLocation(a.getItemCarried(), HobbitAction.getEntitymanager().whereIs(a));
        a.setItemCarried(null);
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
        return "Leave " + itemCarried.getShortDescription();
    }

}