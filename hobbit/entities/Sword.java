/**
 * An entity that has the CHOPPER and WEAPON attributes and so can
 * be used to chop down trees, attack others, etc.
 *
 * @author dsquire
 */
package hobbit.entities;

import edu.monash.fit2024.simulator.userInterface.MessageRenderer;
import hobbit.Capability;
import hobbit.HobbitEntity;
import hobbit.actions.Leave;
import hobbit.actions.Take;

public class Sword extends HobbitEntity {

    public Sword(MessageRenderer m) {
        super(m);

        this.shortDescription = "a sword";
        this.longDescription = "A gleaming sword";
        this.hitpoints = 100; // start with a nice powerful, sharp sword

        this.addAffordance(new Take(this, m));
        this.addAffordance(new Leave(this, m));
        this.capabilities.add(Capability.CHOPPER);
        this.capabilities.add(Capability.WEAPON);

        this.setSymbol("�");
    }

    public String getSymbol() {
        return super.getSymbol(); // let's try some UniCode :)
    }

}
