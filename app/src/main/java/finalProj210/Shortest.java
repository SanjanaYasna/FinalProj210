package finalProj210;
import java.util.*;
import java.awt.Color;
import com.google.common.graph.*;

/*takes in value graph with integer edge values, to use Dijakstra algorithm and output resulting SHORTEST path(s), and also animate*/
public class Shortest {
    //keeps track of visited node
    public static HashSet<String> visited = new HashSet<String>();
    //keeps track of the node that was visited to get to certain node, for use in contruct path function
    public static HashMap<String, String> via = new HashMap<String, String>(); 
    //keeps track of distances that beat records for shortest yet
    public static HashMap<String, Integer> distance = new HashMap<String, Integer>();
    //queue that keeps track of hte minimum computed total distance (multiple if they share a minimum value)
    public static Queue<String> minOverall = new LinkedList<String>();
    //set of possible minimum points, as we only care about final distination location as opposed to reason for child intervention case
    public static HashSet<String> possibleMinimums = new HashSet<String>();
    //my proudest revelation, that I can do this: priority  queue that orders hashmaps by value pairs, to pick out the minimum among the distances computed
    public static PriorityQueue< HashMap<String, Integer> > minDistance = new PriorityQueue<>((firstVal, secVal) -> {
        String keyInA = firstVal.keySet().iterator().next(); 
        String keyInB = secVal.keySet().iterator().next(); 
        return firstVal.get(keyInA) - secVal.get(keyInB); 
       }
    );
    //unvisited node hashset
    public static HashSet<String> unvisited = new HashSet<String>();
    /*Function to calculate gross path from startNode to each of the other nodes of graph (lowest distance recorded in distance hashmap),
     * and then print out hte output(s) for shortest path along with the colors. Contains animatations as well for djakstra in action and
     * the final outputed path in green.
     * A nood or edge is highlighted in yellow for a split second if it is currently being considered as a potential path to a node but it doesn't beat the record for
     * the smallest distance to said node it is pointing to. It contains green if a new path was found that was more efficient.
     * The resulting shortest path/paths are outputted in green, and their overall path and associated weights are printed in console
     */
    public static void grossPath(MutableValueGraph<String,Integer> graph, String startNode) throws Exception{
        //Initialize set of possible minimums
        possibleMinimums.add(" 5-Beaumont ");
        possibleMinimums.add(" 10-El Paso ");
        possibleMinimums.add(" 9-Midland ");
        possibleMinimums.add(" 7-Austin ");
        possibleMinimums.add(" 11-Edinburg ");
        possibleMinimums.add(" 2-Abilene ");
        possibleMinimums.add(" 4-Tyler ");
        possibleMinimums.add(" 3-Arlington ");
        possibleMinimums.add(" AH ");
        possibleMinimums.add(" 8-San Antonio ");
        possibleMinimums.add(" 1-Lubbock ");
        possibleMinimums.add(" 6-Houston ");

        GraphDisplay animation = new GraphDisplay(graph);
        Main.arrange(animation);
        animation.setColor(startNode, Color.YELLOW);
        Thread.sleep(5000);
        animation.setColor(startNode, new Color(192, 192, 255));
        //give dummy value to minOverall:
        minOverall.add(" 6-Houston ");
       if (!graph.nodes().contains(startNode)){
        throw new IllegalArgumentException();
       }
       unvisited.add(startNode);
       for (String s : graph.nodes()){
        distance.put(s, 99999);
       }
       via.put(startNode, null);
       minDistance.add(new HashMap<>(){ {
        put(startNode, 0);
       } });
        distance.put(startNode, 0);
        while (!unvisited.isEmpty()){
            HashMap<String, Integer> minDistanceNode = new HashMap<String, Integer>(minDistance.peek());
            String s = minDistanceNode.keySet().toString();
            s = s.substring(1, s.length() - 1); //to remove brackets from tostring
            animation.setColor(s, Color.GREEN);
            try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            animation.setColor(s,new Color(192, 192, 255));
            unvisited.remove(s);
            //minDistance.remove();
            visited.add(s);
            propogate(graph, s, animation);
       }

    System.out.println("From the node " + startNode + ", the destination(s) with the shortest path overall with regard to weights is " + 
    minOverall.toString());
    System.out.println("In order to get path of said function, the lookupPaths() function was used and the paths are printed below");
    for (String s: minOverall){
        System.out.println(lookupPath(graph, s, animation));
    }
}

    /*helper function for the major processing in the while loop in grossPath(), checks successors, updates distances, etc */
    public static void propogate(MutableValueGraph<String,Integer> graph, String node, GraphDisplay animation){
        int currNodeWeight = distance.get(node);
        minDistance.remove();
        for (String successor : graph.successors(node)){
            int totalFromNode = graph.edgeValueOrDefault(node, successor, 0) + currNodeWeight;
            if (totalFromNode < distance.get(successor)){
                distance.put(successor, totalFromNode);
                via.put(successor, node);
                Object edge = animation.getEdgeBetween(node, successor);
                animation.setColor(edge,Color.GREEN);
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                animation.setColor(edge,Color.BLACK);
            unvisited.add(successor);
            minDistance.add(new HashMap<>(){ {
                    put(successor, distance.get(successor));
                }
            });
            if (distance.get(successor) < distance.get(minOverall.peek()) && 
            possibleMinimums.contains(successor) && minOverall.peek() != successor){
                minOverall.remove();
                minOverall.add(successor);
            }

            if (distance.get(successor) == distance.get(minOverall.peek()) && 
            possibleMinimums.contains(successor)  && minOverall.peek() != successor){
                minOverall.add(successor);
            }
        
        }
        else{
            Object edge = animation.getEdgeBetween(node, successor);
            animation.setColor(edge,Color.YELLOW);
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                animation.setColor(edge,Color.BLACK);
        }
        }  
        animation.setColor(node, new Color(192, 192, 255)); 
    }

    /*Outputs string of overall path to a destination (from what was computed as shortest path from via hashtable), 
    and animates said path in green */
    public static String lookupPath(MutableValueGraph<String,Integer> graph,String destination, GraphDisplay animation){
        LinkedList<String> path = new LinkedList<String>();
        String currNode = destination;
        animation.setColor(currNode, Color.GREEN);
        while (via.get(currNode) != null){
            path.add(currNode);
            currNode = via.get(currNode);
        }
        path.add(currNode);
        String res = "";
        for (int i = 0; i <3; i++){
            res += path.get(i) + "(" + graph.edgeValueOrDefault(path.get(i+1), path.get(i), 0) + ") ->";
            Object edge = animation.getEdgeBetween(path.get(i+1), path.get(i));
            animation.setColor(path.get(i), Color.GREEN);
            animation.setColor(edge,Color.GREEN);
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
        animation.setColor(path.get(3), Color.GREEN);
        res += path.get(3);
        return res;
    }
}
