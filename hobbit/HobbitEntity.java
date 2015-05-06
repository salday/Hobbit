/**
 * Class that represents inanimate objects in the hobbit world.
 *
 * @author ram
 */
package hobbit;

import edu.monash.fit2024.simulator.matter.Entity;
import edu.monash.fit2024.simulator.userInterface.MessageRenderer;

import java.util.HashSet;

public class HobbitEntity extends Entity implements HobbitEntityInterface {
    private String symbol;
    protected HashSet<Capability> capabilities;
    protected int hitpoints = 0; // Not all non-actor entities will make use of this

    protected HobbitEntity(MessageRenderer m) {
        super(m);
        capabilities = new HashSet<Capability>();
    }

    /* 
     * @see hobbit.HobbitEntityInterface#getSymbol()
     */
    @Override
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String s) {
        symbol = s;
    }


    @Override
    public boolean hasCapability(Capability c) {
        return capabilities.contains(c);
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int p) {
        hitpoints = p;
    }

    public void takeDamage(int damage) {
        this.hitpoints -= damage;
    }
}
