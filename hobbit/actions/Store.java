package hobbit.actions;

import edu.monash.fit2024.simulator.userInterface.MessageRenderer;
import hobbit.HobbitActor;
import hobbit.HobbitAffordance;
import hobbit.HobbitEntity;
import hobbit.HobbitEntityInterface;
import hobbit.entities.Saddlebag;
import hobbit.entities.actors.Pony;

/**
 * Created by ProDigy SML on 5/3/2015.
 */
public class Store extends HobbitAffordance {

    public Store(HobbitEntityInterface theTarget, MessageRenderer m) {
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
        if (a.getPony() != null) {
            if ((a.getPony().getClass() == Pony.class) && a.getPony().getItemCarried().getClass() == Saddlebag.class) {
                if (((Saddlebag) (a.getPony().getItemCarried())).hasSpace()) {
                    return true;
                }
            }
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
        ((Saddlebag) a.getPony().getItemCarried()).storeItem((HobbitEntity) this.target);
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
        return "store " + target.getShortDescription();
    }
}