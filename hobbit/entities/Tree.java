/**
 * Class to represent a tree.  Trees are currently pretty passive.  They can be chopped down
 * to produce wood; see the Chop class.
 *
 * @author ram
 */
package hobbit.entities;

import edu.monash.fit2024.simulator.userInterface.MessageRenderer;
import hobbit.HobbitAffordance;
import hobbit.HobbitEntity;
import hobbit.actions.Chop;

public class Tree extends HobbitEntity {

    public Tree(MessageRenderer m) {
        super(m);
        HobbitAffordance chop = new Chop(this, m);
        this.addAffordance(chop);
        this.setLongDescription("A beautiful spreading oak tree.");
        this.setShortDescription("a tree");
        this.setSymbol("T");
    }

}
