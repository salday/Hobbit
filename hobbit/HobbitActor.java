/**
 * Class that represents an Actor (i.e. something that can perform actions) in the hobbit world.
 *
 * @author ram
 * @modified 20130414 dsquire
 * - changed constructor so that affordances that all HobbitActors must have can be added
 * - changed team to be an enum rather than a string
 */
package hobbit;

import edu.monash.fit2024.gridworld.Grid.CompassBearing;
import edu.monash.fit2024.simulator.matter.Actor;
import edu.monash.fit2024.simulator.space.Location;
import edu.monash.fit2024.simulator.time.Scheduler;
import edu.monash.fit2024.simulator.userInterface.MessageRenderer;
import hobbit.actions.Attack;
import hobbit.actions.Move;
import hobbit.actions.Store;
import hobbit.entities.Saddlebag;
import hobbit.entities.actors.Pony;

import java.util.HashSet;

public abstract class HobbitActor extends Actor<HobbitAction> implements HobbitEntityInterface {
    private Team team;
    private int hitpoints;
    private HobbitEntityInterface itemCarried;
    protected MiddleEarth world;
    private String symbol;
    protected boolean humanControlled = false;
    private HashSet<Capability> capabilities;
    protected static Scheduler scheduler;
    private Pony pony = null;

    // Constructor

    public HobbitActor(Team team, int hitpoints, MessageRenderer m, MiddleEarth world) {
        super(m);
        actions = new HashSet<HobbitAction>();
        this.team = team;
        this.hitpoints = hitpoints;
        this.world = world;
        this.symbol = "@";

        // Add affordances that all HobbitActors must have
        HobbitAffordance attack = new Attack(this, m);
        this.addAffordance(attack);
    }

    //Accessors
    public static void setScheduler(Scheduler s) {
        scheduler = s;
    }

    public Team getTeam() {
        return team;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public HobbitEntityInterface getItemCarried() {
        return itemCarried;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void takeDamage(int damage) {
        this.hitpoints -= damage;
    }

    public void setItemCarried(HobbitEntityInterface target) {
        this.itemCarried = target;
        if (this.getPony() != null && this.getPony().getItemCarried().getClass() == Saddlebag.class) {
            this.addAffordance(new Store(target, messageRenderer));
        }
    }


    /**
     * Returns true if this actor is dead, false otherwise.
     *
     * @return true if and only if this actor is dead
     * @author ram
     */
    public boolean isDead() {
        return hitpoints <= 0;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String s) {
        symbol = s;
    }

    public boolean isHumanControlled() {
        return humanControlled;
    }

    /**
     * Returns true if this actor has the given capability, false otherwise.
     * <p/>
     * Wrapper for HashSet<Capability>.contains().
     *
     * @param 'the' Capability to search for
     * @author ram
     */
    public boolean hasCapability(Capability c) {
        return capabilities.contains(c);
    }

    /**
     * Polls the current location to find potential exits, and replaces all the instances of
     * Move in this actor's command set with moves to the new exits.
     *
     * @param loc the actor's location
     * @author ram
     */
    public void resetMoveCommands(Location loc) {
        HashSet<HobbitAction> newActions = new HashSet<HobbitAction>();

        // Copy all the existing non-movement options
        for (HobbitAction a : actions) {
            if (!a.isMoveCommand())
                newActions.add(a);
        }

        // add new movement possibilities
        for (CompassBearing d : CompassBearing.values()) {
            if (loc.getNeighbour(d) != null)
                newActions.add(new Move(d, messageRenderer, world));
        }

        // replace old action list with new
        this.actions = newActions;        // TODO: This assumes that the only actions are the Move actions. This will clobber any others. Needs to be fixed.
    }


    public abstract void act();

    /**
     * Allow the actor to act.
     *
     * @param 'the' actor's current location
     * @author ram
     */
    public void tick(Location l) {
        act();
    }

    public void setPony(Pony pony) {
        this.pony = pony;
    }

    public Pony getPony() {
        return this.pony;
    }
}
