/**
 * Command to attack entities.
 * <p/>
 * This affordance is attached to all attackable entities
 *
 * @author David.Squire@monash.edu (dsquire)
 */
package hobbit.actions;

import edu.monash.fit2024.simulator.userInterface.MessageRenderer;
import hobbit.*;

public class Attack extends HobbitAffordance implements HobbitActionInterface {

    public Attack(HobbitEntityInterface theTarget, MessageRenderer m) {
        super(theTarget, m);
    }

    @Override
    public int getDuration() {
        return 1;
    }

    @Override
    public String getDescription() {
        return "attack " + this.target.getShortDescription();
    }


    @Override
    /**
     * Determine whether a particular actor can attack the target.
     *
     * @author dsquire
     * @param a the actor being queried
     * @return true: any actor can always try an attack, it just won't do much good unless the actor has a suitable weapon
     */
    public boolean canDo(HobbitActor a) {

        return true;
    }

    @Override
    /**
     * Perform the Attack command on an entity.
     *
     * This damages the entity attacked, tires the attacker, and blunts any weapon used for the attack
     *
     * @author dsquire
     *  - adapted from the equivalent class in the old Eiffel version
     * @param the actor who is attacking
     */
    public void act(HobbitActor a) {
        HobbitEntityInterface target = this.getTarget();
        boolean targetIsActor = target instanceof HobbitActor;
        HobbitActor targetActor = null;
        if (targetIsActor) {
            targetActor = (HobbitActor) target;
        }
        if (a.isHumanControlled() // a human-controlled player can attack anyone
                || (targetIsActor && (a.getTeam() != targetActor.getTeam())) // others will only attack actors on different teams
                ) {
            a.say(a.getShortDescription() + " is attacking " + target.getShortDescription() + "!");
            HobbitEntityInterface itemCarried = a.getItemCarried();
            if (itemCarried != null) {
                if (itemCarried.hasCapability(Capability.WEAPON)) {
                    target.takeDamage(itemCarried.getHitpoints() + 1); // blunt weapon won't do much, but it will still do some damage
                    itemCarried.takeDamage(1); // weapon gets blunt
                    a.takeDamage(1); // actor uses energy
                } else {
                    if (targetIsActor) {
                        targetActor.say("\t" + targetActor.getShortDescription()
                                + " is amused by " + a.getShortDescription()
                                + "'s attempted attack with "
                                + itemCarried.getShortDescription());
                    }
                } // if is carrying a weapon
            } // if carrying something
            else { // attack with bare hands
                target.takeDamage((a.getHitpoints() / 20) + 1); // a bare-handed attack doesn't do much damage.
                a.takeDamage(2); // actor uses energy. It's twice as tiring as using a weapon
            } // if carrying something
            if (a.isDead()) {
                a.setLongDescription(a.getLongDescription() + ", that died of exhaustion while attacking someone");
            }
            if (this.getTarget().getHitpoints() <= 0) {  // can't use isDead(), as we don't know that the target is an actor
                target.setLongDescription(target.getLongDescription() + ", that was killed in a fight");
            }
        } // not game player and different teams
        if (targetIsActor && (a.getTeam() == targetActor.getTeam())) {
            a.say("\t" + a.getShortDescription() + " says: Silly me! We're on the same team, " + target.getShortDescription() + ". No harm done");
        }
    }
}
