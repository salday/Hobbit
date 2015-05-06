package hobbit.entities;

import edu.monash.fit2024.simulator.userInterface.MessageRenderer;
import hobbit.HobbitActor;
import hobbit.HobbitEntity;
import hobbit.actions.Leave;
import hobbit.entities.actors.Pony;

import java.util.ArrayList;

/**
 * Created by ProDigy SML on 5/3/2015.
 */
public class Saddlebag extends HobbitEntity {

    private ArrayList<HobbitEntity> listOfItems = new ArrayList<HobbitEntity>();
    private HobbitActor owner;

    public Saddlebag(MessageRenderer m) {
        super(m);
    }

    public void takeBag(HobbitActor hobbitActor) {

        if (hobbitActor.getPony().getClass() == Pony.class) {
            this.owner = hobbitActor.getPony();
            hobbitActor.getPony().setItemCarried(this);
            this.addAffordance(new Leave(this, this.messageRenderer));
        } else {
            if (listOfItems.size() == 0) {
                this.owner = hobbitActor;
                hobbitActor.setItemCarried(this);
                this.addAffordance(new Leave(this, this.messageRenderer));
            } else {
                this.owner = null;
            }
        }
    }

    //Should have a leave affordance here
    public void leaveBag() {
        if (this.owner != null) {
            this.owner = null;
        }
    }

    public boolean hasSpace() {
        return listOfItems.size() < 10;
    }

    public boolean isEmpty() {
        return listOfItems.size() == 0;
    }

    public void storeItem(HobbitEntity item) {
        this.listOfItems.add(item);
    }

    public void getDescription() {
        if (this.isEmpty()) System.out.println("Saddle Bag Holds No Items!");
        else {
            System.out.println("The Saddle Bag Holds: ");
            for (HobbitEntity i : this.listOfItems) {
                System.out.println(i.getShortDescription());
            }
        }

    }

}