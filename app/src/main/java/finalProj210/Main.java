package finalProj210;
import com.google.common.graph.*;
import java.util.Scanner;
import java.awt.Point;
/*Main class, displays initial graph display setup */
class Main {
  /*Main method, calls the other classes based upon user inputs of graph preferences and starting node */
  public static void main(String[] args) throws Exception{
   MutableValueGraph<String,Integer> graph =
    ValueGraphBuilder.directed().build();
    Scanner file = ReadFile.read("/Users/sanjanayasna/Desktop/Data_Structs_Practice/FinalProj210/data_simple.csv");
    Integer lineCount = 0;
    file.nextLine();
    while (file.hasNextLine()) {
      String line = file.nextLine();
      line = line.replace('"', ' ');
      String[] fields = line.split(",");
      for (int i = 3; i >= 1; i--){
        //edge already exists, so we take value of that edge, delete it, and replace set edge with newwer value
        if (!graph.edgeValue(fields[i], fields[i-1]).isEmpty()){
        //  Optional<Integer> prevEdge = graph.edgeValue(fields[i], fields[i-1]);
          Integer prevEdge = graph.edgeValueOrDefault(fields[i], fields[i-1], 0);
          Integer newEdge = prevEdge + Integer.valueOf(fields[4]);
          graph.putEdgeValue(fields[i], fields[i-1], newEdge);
        }
        else graph.putEdgeValue(fields[i], fields[i-1], Integer.valueOf(fields[4]));
      }
      lineCount += 1;
    }
    file.close();
    Scanner questions = new Scanner(System.in);
    System.out.println("Enter a number, 1 or 2, based on preference");
    System.out.println("Do you want the graph to have gross cases (1) or outcome decimal percentage edges (2) ?");
    String ans1 = questions.nextLine();
    System.out.println("Do you want the graph to have default link direction, from outcome down to location (1) or reverted link direction from location up to outcome (2)?");
    String ans2 = questions.nextLine();
    System.out.println("Longest (1) or shortest path (2) from given node?");
    String ans3 = questions.nextLine();
    System.out.println("Chose source node from set of nodes. Be mindful not to include leading/trailing spaces: " + graph.nodes());
    String source = questions.nextLine();
    source = " " + source + " ";
     questions.close();
    if (ans1.equals("1")){
      MutableValueGraph<String, Integer> currGraph = graph;
      if (ans2.equals("2")){
        currGraph= invertedLinksInteger(currGraph);
      }
      GraphDisplay animation = new GraphDisplay(currGraph);
      arrange(animation);
      if (ans3.equals("1")){
        Longest.grossPath(currGraph, source);
      }
      if (ans3.equals("2")){
        Shortest.grossPath(currGraph, source);
      }
    }
    if (ans1.equals("2")){
      MutableValueGraph<String, Double> currGraph = weightAsPercentage(graph);
      if (ans2.equals("2")){
        currGraph= invertedLinksDouble(currGraph);
      }
      GraphDisplay animation = new GraphDisplay(currGraph);
      arrange(animation);
      if (ans3.equals("1")){
        currGraph = invertedWeightAsPercentage(currGraph);
        ShortestbyPercent.grossPath(currGraph, source);
      }
      if (ans3.equals("2")){
        ShortestbyPercent.grossPath(currGraph, source);
      }
    }
    
   
  }

  /*Displays graph with nodes in nice positions */
  public static void arrange(GraphDisplay customize){
    customize.setLoc(" Yes (Timely) ", new Point(1100, 50));
    customize.setLoc(" No (Not Timely) ", new Point(600, 50));
    customize.setLoc(" Priority 2 - 72 Hours ", new Point(500, 200));
    customize.setLoc(" Priority 1 - 24 Hours ", new Point(1300, 200));
    customize.setLoc(" General Residential Operation ", new Point(200, 350));
    customize.setLoc(" AH ", new Point(350, 400));
    customize.setLoc(" Exemption Request ", new Point(500, 400));
    customize.setLoc(" Residential Treatment Center ", new Point(700, 400));
    customize.setLoc(" Illegal Operation ", new Point(1000, 400));
    customize.setLoc(" Independent Foster Family/Group Home ", new Point(1300, 400));
    customize.setLoc(" Child Placing Agency/Branches ", new Point(1500, 350));
    customize.setLoc(" 3-Arlington ", new Point(100, 1000));
    customize.setLoc(" 7-Austin ", new Point(250, 1000));
    customize.setLoc(" 1-Lubbock ", new Point(450, 1000));
    customize.setLoc(" 11-Edinburg ", new Point(650, 1000));
    customize.setLoc(" 4-Tyler ", new Point(800, 1000));
    customize.setLoc(" 5-Beaumont ", new Point(950, 1000));
    customize.setLoc(" 10-El Paso ", new Point(1100, 1000));
    customize.setLoc(" 6-Houston ", new Point(1250, 1000));
    customize.setLoc(" 2-Abilene ", new Point(1400, 1000));
    customize.setLoc(" 8-San Antonio ", new Point(1550, 1000));
    customize.setLoc(" 9-Midland ", new Point(1700, 1000));
  }

  /*takes graph and converts integer edges to percentage outcomes (each edge value / total sum of edges of all successors of a node) */
  public static MutableValueGraph<String,Double> weightAsPercentage(MutableValueGraph<String,Integer> graph){
    MutableValueGraph<String,Double> newGraph = ValueGraphBuilder.directed().build();
    for (String s: graph.nodes()){
      newGraph.addNode(s);
      if (!graph.successors(s).isEmpty()){
        int i = 0;
        //sum of weights from all successors
        for (String successor : graph.successors(s)){
          i += graph.edgeValueOrDefault(s, successor, 0);
          newGraph.addNode(s);
        }
        //sets new percentage weights
         for (String successor : graph.successors(s)){
          Double newLink = (double) Math.floor((double)graph.edgeValueOrDefault(s, successor, 0)* 100/i)/ 100.000;
          newGraph.putEdgeValue(s, successor, newLink);
         // graph.putEdgeValue(s, successor, () );
        }
      }
    }
    return newGraph;
  }

  /*take double values of eges and subtracts the decimals from 1, to return graph with inverted edge values */
  public static MutableValueGraph<String, Double> invertedWeightAsPercentage(MutableValueGraph<String,Double> graph){
    MutableValueGraph<String,Double> newGraph=ValueGraphBuilder.directed().build();
    for (EndpointPair<String> link : graph.edges()){
      Double originalPercent = graph.edgeValueOrDefault(link.source(), link.target(),0.0);
      //am actual pain
      Double newPercent = (double) Math.floor(1 * 100.00 -originalPercent * 100.00) / 100.00;
      newGraph.putEdgeValue(link.source(), link.target() ,newPercent);
    }
    System.out.println(newGraph.edges());
    return newGraph;
  }

  /*Takes graph of edge value integer input, and outputs the links in an inverted fashion */
  public static MutableValueGraph<String,Integer> invertedLinksInteger(MutableValueGraph<String,Integer> graph){
    MutableValueGraph<String,Integer> newGraph=
    ValueGraphBuilder.directed().build();;
    for (EndpointPair<String> link : graph.edges()){
      Integer edgeVal = graph.edgeValueOrDefault(link.source(), link.target(), 0);
      //newGraph.removeEdge(link.source(), link.target());
      newGraph.putEdgeValue(link.target(), link.source(), edgeVal);
    }
    return newGraph;
  }

  /*Takes graph of edge value double input, and outputs the links in an inverted fashion */
  public static MutableValueGraph<String,Double> invertedLinksDouble(MutableValueGraph<String,Double> graph){
    MutableValueGraph<String,Double> newGraph=
    ValueGraphBuilder.directed().build();;
    for (EndpointPair<String> link : graph.edges()){
      Double edgeVal = graph.edgeValueOrDefault(link.source(), link.target(), 0.0);
      //newGraph.removeEdge(link.source(), link.target());
      newGraph.putEdgeValue(link.target(), link.source(), edgeVal);
    }
    return newGraph;
  }
}