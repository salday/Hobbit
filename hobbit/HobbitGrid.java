/**
 * Grid of HobbitLocations.
 *
 * @author ram
 */
package hobbit;

import edu.monash.fit2024.gridworld.Grid;
import edu.monash.fit2024.simulator.matter.EntityManager;
import edu.monash.fit2024.simulator.space.LocationMaker;

import java.util.List;

public class HobbitGrid extends Grid<HobbitLocation> {

    private static int locationWidth = 8;

    public HobbitGrid(LocationMaker<HobbitLocation> factory) {
        super(10, 10, factory);
    }

    /**
     * Display the grid to System.out
     *
     * @param em
     * @author ram
     */
    public void render(EntityManager<HobbitEntityInterface, HobbitLocation> em) {
        String buffer = "";

        for (List<HobbitLocation> row : locations) {
            for (HobbitLocation loc : row) {
                StringBuffer emptyBuffer = new StringBuffer();
                char es = loc.getEmptySymbol();
                for (int i = 0; i < locationWidth - 3; i++) {
                    emptyBuffer.append(es);
                }
                StringBuffer buf = new StringBuffer("|" + loc.getSymbol() + ":");
                List<HobbitEntityInterface> contents = em.contents(loc);
                if (contents == null || contents.isEmpty())
                    buf.append(emptyBuffer);
                else {
                    for (HobbitEntityInterface e : contents) {
                        buf.append(e.getSymbol());
                    }
                }
                buf.append(emptyBuffer);
                buf.setLength(locationWidth);
                buf.append("| ");

                buffer += buf;
            }
            buffer += "\n";

        }

        System.out.println(buffer);
    }
}
