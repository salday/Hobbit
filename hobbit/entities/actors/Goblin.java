/**
 * A simple bad guy.
 * <p/>
 * Goblins select a direction and move in it until they walk into something, then they turn around.  They
 * notify the user when they turn around, or if they get stuck in a position with no exits.
 *
 * @author Vinu + SAL
 */
package hobbit.entities.actors;

/**
 * TO-DO!!!
 *
 * Make length of the side of the square within 1-4. 
 * 
 * KNOWN ISSUE: Goblin cant go back home properly
 */

import edu.monash.fit2024.gridworld.Grid;
import edu.monash.fit2024.simulator.space.Direction;
import edu.monash.fit2024.simulator.space.Location;
import edu.monash.fit2024.simulator.userInterface.MessageRenderer;
import hobbit.HobbitActor;
import hobbit.MiddleEarth;
import hobbit.Team;
import hobbit.actions.Move;

import java.util.ArrayList;

public class Goblin extends HobbitActor {

    private Grid.CompassBearing myDirection;
    private Location homeBase;
    private boolean atHome;
    private Integer turns = 0;
    private Integer currentTurn = 0;
    private int squareLength=0;
    private int currentPositionSquare=0;
    private ArrayList<Grid.CompassBearing> pathHome;
    private Grid.CompassBearing oldDirection;
    private boolean initialFlag = true;

    public Goblin(MessageRenderer m, MiddleEarth world) {
        super(Team.EVIL, 50, m, world);
        myDirection = Grid.CompassBearing.getRandomBearing();

        this.setAtHome(true);

        this.act();
    }

    @Override

    /**
     * Tries to walk forward, turns by 45 degrees until either an exit is found or it is established that
     * no exit exists.
     *
     * @author ram
     * @param a this
     */
    public void act() {
        // Did I hit something?  If so, bear right.
        if (initialFlag){
            initialFlag = false;
            this.homeBase = this.world.getEntityManager().whereIs(this);
            if (homeBase == null){
                this.homeBase = this.world.getGrid().getLocationByCoordinates(7, 7);
            }
        }
        if (this.getAtHome()) {
            if (turns == 0) {
                this.turns = (int) ((Math.random() * 4) + 1);
            }

            if (!this.turns.equals(this.currentTurn)) {
                this.currentTurn++;
            } else {
                this.turns = (int) ((Math.random() * 4) + 1);
                this.currentTurn = 0;
                
                if (currentPositionSquare == 0 && squareLength == 0){
                    squareLength = (int) ((Math.random() * 3) + 1);

                    currentPositionSquare = squareLength;

                    oldDirection = myDirection;

                    while (!MiddleEarth.getEntitymanager().seesExit(this, myDirection)) {
                        myDirection = myDirection.turn(45);
                        if (myDirection == oldDirection) {
                            // I've turned completely around and can't find an exit -- I'm stuck!
                            messageRenderer.render(this.getShortDescription() + " is stuck!  Help!");
                            return;
                        }
                    }
                }
                
                this.action();
            }
        } else {
            this.action();
        }
    }

    public void action() {
        
        if (currentPositionSquare == 0) {
            myDirection.turn(270);
            oldDirection.turn(270);
            
            currentPositionSquare = squareLength;
        }
        
        if (MiddleEarth.getEntitymanager().seesExit(this, myDirection)) {

            Move myMove = new Move(myDirection, messageRenderer, world);

            //Attempt to initialise the scheduler...

            this.scheduler.schedule(myMove, this, 1);

            currentPositionSquare--;
        }else {
            this.goHome();
        }
    }

    public Location getHomeBase() {
        return this.homeBase;
    }

    public void setAtHome(boolean atHome) {
        this.atHome = atHome;
    }

    public boolean getAtHome() {
        return this.atHome;
    }

    public void goHome(){
        this.pathHome = new ArrayList<Grid.CompassBearing>();
        
        ArrayList<Grid.CompassBearing> currentPath;
        
        Location currentLocation = world.getEntityManager().whereIs(this);
        
        for (Grid.CompassBearing direction: Grid.CompassBearing.values()){
            
            System.out.println(homeBase);

            System.out.println(homeBase.getNeighbour(direction));
            
            System.out.println(currentLocation);
            
            System.out.println(direction);
            
            currentPath = makePath(homeBase.getNeighbour(direction), currentLocation, direction);
            
            if (currentPath != null){
                pathHome.add(direction);
                pathHome = currentPath;
            }
        }
        
        Move myMove = new Move(pathHome.get(0), messageRenderer, world);
        
        this.scheduler.schedule(myMove, this, 1);
    }
    
    private ArrayList<Grid.CompassBearing> makePath(Location start, Location end, Grid.CompassBearing direction){
        ArrayList<Grid.CompassBearing> path = new ArrayList<Grid.CompassBearing>();
        
        int counter = 0;
        
        for (Grid.CompassBearing q: Grid.CompassBearing.values()){
            if (direction == q) break;
            counter++;
        }
        
        if (start == end){
            path.add(direction);
        }else {
            if (start.getNeighbour(direction) == null){
                path = null;
            }else {
                path = makePath(start.getNeighbour(direction), end, direction);
            }
            
            if (counter % 2 == 1){
                ArrayList<Grid.CompassBearing> leftPath;
                ArrayList<Grid.CompassBearing> rightPath;
                
                if (start.getNeighbour(direction.turn(135)) == null){
                    leftPath = null;
                }else {
                    leftPath = makePath(start.getNeighbour(direction.turn(135)), end, direction.turn(135));
                }

                if (start.getNeighbour(direction.turn(225)) == null){
                    rightPath = null;
                }else {
                    rightPath = makePath(start.getNeighbour(direction.turn(225)), end, direction.turn(225));
                }
                
                if (rightPath == null){
                    path = leftPath;
                }else {
                    path = rightPath;
                }
            }
            
        }
        return path;
    }
    
}