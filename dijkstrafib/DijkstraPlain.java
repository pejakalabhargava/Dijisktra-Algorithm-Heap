
package dijkstrafib;
import java.lang.*;
import java.util.*;

/**
 *
 * @author amitskatti
 */
public final class DijkstraPlain {

    public static <Integer> Map<Integer, Double> shortestPaths(DirectedGraph<Integer> graph, int source) {


	ArrayPriorityQueue pq = new ArrayPriorityQueue();

	Map<Integer, Double> result = new HashMap<Integer, Double>();

	for (Integer node : graph)
            pq.enqueue((java.lang.Integer)node, Double.POSITIVE_INFINITY);
	
	pq.decreaseKey(source, 0.0);

        while (!pq.isEmpty()) {
            PNode curr = pq.dequeueMin();
            result.put((Integer)curr.vertex, curr.dist);
            for (Map.Entry<Integer, Double> arc : graph.edgesFrom((Integer)curr.vertex).entrySet()) {
                if (result.containsKey(arc.getKey())) continue;

                double pathCost = curr.dist + arc.getValue();
                
				PNode dest = pq.ValueAt((java.lang.Integer)arc.getKey());
                               
				if (pathCost < dest.dist)
                    pq.decreaseKey(dest.vertex, pathCost);
            }
        }
        return result;
    }

    
}
