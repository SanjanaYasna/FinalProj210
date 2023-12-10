package finalProj210;
import java.util.*;
import java.io.*;
import org.checkerframework.common.value.qual.BoolVal;

import com.google.common.graph.*;

public class Shortest {
    public static HashSet<String> visited = new HashSet<String>();
    public static HashMap<String, String> via = new HashMap<String, String>(); //path thing, not very necessary
    public static HashMap<String, Integer> distance = new HashMap<String, Integer>();
       //my proudest revelation, that I can do this: priority  queue that orders hashmaps by value pairs, ascending
    public static PriorityQueue< HashMap<String, Integer> > minDistance = new PriorityQueue<>((firstVal, secVal) -> {
        String keyInA = firstVal.keySet().iterator().next(); 
        String keyInB = secVal.keySet().iterator().next(); 
        return firstVal.get(keyInA) - secVal.get(keyInB); 
       }
    );
    public static HashSet<String> unvisited = new HashSet<String>();
    public static void grossPath(MutableValueGraph<String,Integer> graph, String startNode) throws Exception{
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
       }
    });
       distance.put(startNode, 0);
       while (!unvisited.isEmpty()){
        HashMap<String, Integer> minDistanceNode = new HashMap<String, Integer>(minDistance.peek());
        String s = minDistanceNode.keySet().toString();
        s = s.substring(1, s.length() - 1); //to remove brackets from tostring
        unvisited.remove(s);
       //minDistance.remove();
        visited.add(s);
        propogate(graph, s);
       }

       System.out.println(via.toString());

    }
    public static  void propogate(MutableValueGraph<String,Integer> graph, String node){
        int currNodeWeight = distance.get(node);
        minDistance.remove();


        for (String successor : graph.successors(node)){
            int totalFromNode = graph.edgeValueOrDefault(node, successor, 0) + currNodeWeight;
            if (totalFromNode < distance.get(successor)){
                distance.put(successor, totalFromNode);
                via.put(successor, node);
                /*minDistance.add(new HashMap<>(){ {
                    put(successor, totalFromNode);
                }
            });}*/
            unvisited.add(successor);
            minDistance.add(new HashMap<>(){ {
                    put(successor, distance.get(successor));
                }
            });}
        }
    }
}
