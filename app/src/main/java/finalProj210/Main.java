package finalProj210;
import com.google.common.graph.*;
import java.awt.Color;
import java.util.Scanner;
import java.awt.Point;
import java.util.Arrays;
import java.util.Optional;

class Main {
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
    //Idea #1: do weights by percentage, subtract htem from 1, and maybe find say the most common reason for, say, an untimely intervention 
    //(the most commonvalues would have the smallest decimal, since you subtract htem from 1)
    file.close();
   /*  GraphDisplay customize = new GraphDisplay(graph);
    System.out.println(graph.successors( " No (Not Timely) "));
    arrange(customize);
    Shortest.grossPath(graph, " No (Not Timely) "); */
    GraphDisplay customize = new GraphDisplay(invertedWeightAsPercentage(graph));
    arrange(customize);
   //System.out.println(graph.nodes().toString());
  }

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

  public static MutableValueGraph<String, Double> invertedWeightAsPercentage(MutableValueGraph<String,Integer> graph){
    MutableValueGraph<String,Double> newGraph = weightAsPercentage(graph);
    System.out.println(newGraph.edges());
    for (EndpointPair<String> link : newGraph.edges()){
      Double originalPercent = newGraph.edgeValueOrDefault(link.source(), link.target(),0.0);
      Double newPercent = (double) Math.floor(1 * 100.00 -originalPercent * 100.00) / 100.00;
      newGraph.putEdgeValue(link.source(), link.target() ,newPercent);
    }
    System.out.println(newGraph.edges());
    return newGraph;
  }
}