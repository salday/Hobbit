/**
 * Class that models locations in Middle-Earth.
 *
 * @author ram
 */

package hobbit;

import edu.monash.fit2024.simulator.space.Direction;
import edu.monash.fit2024.simulator.space.Location;
import edu.monash.fit2024.simulator.space.LocationMaker;

import java.util.HashMap;

public class HobbitLocation extends Location {
    private char symbol;
    private char emptySymbol = '.'; // symbol displayed to represent empty space
    private String longDescription;
    private String shortDescription;


    /**
     * Factory class used by Grids to instantiate HobbitLocations
     *
     * @author ram
     *
     */
    public static class HobbitLocationMaker implements LocationMaker<HobbitLocation> {

        @Override
        /**
         * Factory method.
         *
         * @author ram
         * @return a new HobbitLocation
         */
        public HobbitLocation make() {
            return new HobbitLocation();
        }

    }

    /**
     * A factory factory.
     *
     * @author ram
     * @return an object with a make() method that can create HobbitLocations
     */
    public static HobbitLocationMaker getMaker() {
        return new HobbitLocationMaker();
    }


    /**
     * Accessor for this location's symbol.
     *
     * @author ram
     * @return a char representing this location
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Mutator for this location's symbol
     *
     * @author ram
     * @param c the character to set the symbol to
     */
    public void setSymbol(char c) {
        symbol = c;
    }

    /**
     * Accessor for this location's empty symbol.
     *
     * @author dsquire
     * @return a char representing empty space at this location
     */
    public char getEmptySymbol() {
        return emptySymbol;
    }

    /**
     * Mutator for this location's empty symbol
     *
     * @author dsquire
     * @param c the character to set the empty symbol to
     */
    public void setEmptySymbol(char c) {
        emptySymbol = c;
    }

    public void setLongDescription(String s) {
        longDescription = s;
    }

    public void setShortDescription(String s) {
        shortDescription = s;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public HobbitLocation() {
        neighbours = new HashMap<Direction, Location>();
    }

}
