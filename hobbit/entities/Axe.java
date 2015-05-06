/**
 * A very minimal entity that has the CHOPPER attribute and so can
 * be used to chop down trees.
 *
 * @author ram
 */
package hobbit.entities;

import edu.monash.fit2024.simulator.userInterface.MessageRenderer;
import hobbit.Capability;
import hobbit.HobbitEntity;
import hobbit.actions.Leave;
import hobbit.actions.Take;

public class Axe extends HobbitEntity {

    public Axe(MessageRenderer m) {
        super(m);

        this.shortDescription = "an axe";
        this.longDescription = "A shiny axe.";
        this.hitpoints = 100; // start with a nice powerful, sharp axe

        this.addAffordance(new Take(this, m));
        this.addAffordance(new Leave(this, m));
        this.capabilities.add(Capability.CHOPPER);

        this.setSymbol("�");
    }

    public String getSymbol() {
        return super.getSymbol();
    }

}