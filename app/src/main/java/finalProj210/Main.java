package finalProj210;
import com.google.common.graph.*;
import java.awt.Color;
import java.util.Scanner;
import java.util.Arrays;

class Main {
  public static void main(String[] args) {
  /* 
    // Mutable graphs can be changed after we build them
    MutableGraph<String> graph =
    GraphBuilder.directed().build();

    //System.out.println(graph instanceof MutableGraph);
    //System.out.println(graph instanceof Graph);
    //System.out.println(graph instanceof ValueGraph);
    //System.out.println(graph instanceof Network);


    // Here we make changes:
    graph.addNode("Joe");
    graph.putEdge("Jordan", "Nick"); // if edge references nodes that don't exist, they get added

    new GraphDisplay(graph);
    //System.out.println(graph);

    // Can add/remove anytime
    graph.putEdge("Jordan", "Joe");

    */
    //System.out.println(graph);

    // Immutable ones can't: you have to add everything before you build

    MutableValueGraph<String,Integer> graph =
    ValueGraphBuilder.directed().build();
    /*MutableNetwork<String, String> graph = 
    NetworkBuilder.directed().allowsParallelEdges(true).build();*/

    Scanner file = ReadFile.read("/Users/sanjanayasna/Desktop/Data_Structs_Practice/FinalProj210/data_simple.csv");
    Integer lineCount = 0;
    file.nextLine();
    while (file.hasNextLine() && lineCount < 1) {
      String line = file.nextLine();
      line = line.replace('"', ' ');
      System.out.println("line is: " + line);
      String[] fields = line.split(",");
      //graph.putEdgeValue(fields[0], fields[2]);
      System.out.println(Arrays.toString(fields));
      for (int i = 3; i >= 1; i--){
        //edge already exists, so we take value of that edge, delete it, and replace set edge with newwer value
        if (!graph.edgeValue(fields[i], fields[i-1]).isEmpty()){
          System.out.println(graph.edgeValue(fields[i], fields[i-1]));
        }
        else graph.putEdgeValue(fields[i], fields[i-1], Integer.valueOf(fields[4]));
      }
      
     // graph.putEdgeValue(fields[3], fields[2], Integer.valueOf(fields[4]));
     // graph.putEdgeValue(fields[2], fields[1], Integer.valueOf(fields[4]));
     // graph.putEdgeValue(fields[1], fields[0], Integer.valueOf(fields[4]));
      lineCount += 1;
    }
    file.close();
    new GraphDisplay(graph);
  //   graph.addNode("Ford");

   // graph.putEdgeValue("Bass", "McConnell", 1);
   // graph.putEdgeValue("Sabin-Reed", "McConnell", 0); 
  //  graph.putEdgeValue("Sabin-Reed", "Burton", 0);    // edge values not necessarily unique

  //  new GraphDisplay(graph);

    // Throws error
    // graph2.removeEdge("Bass", "McConnell");

    /* 
    // Network allows objects on the edges
    MutableNetwork<String,String> network = NetworkBuilder.undirected().build();
    network.addEdge("Northampton","Boston","I-90");
    network.addEdge("Northampton","New York","I-91");
    network.addEdge("New York","Boston","I-95");
   // network.addEdge("New York","Boston","Route_dad_went_to_get_milk");

    System.out.println(network);
    //System.out.println(network.incidentEdges("Northampton"));
    //System.out.println(network.successors("Northampton"));
    //System.out.println(network.incidentNodes("I-95"));
    GraphDisplay d3 = new GraphDisplay(network);
    d3.setNodeColors(Color.GREEN);
    d3.setEdgeColors(Color.BLUE);
    d3.setColor("Northampton",Color.RED);
    for (Object n : d3.getNodeSet()) {
      System.out.println(n+" : "+d3.getLabel(n));
    }
    for (Object e : d3.getEdgeSet()) {
      System.out.println(e+" : "+d3.getLabel(e));
    }
    //System.out.println(network instanceof MutableGraph);
    //System.out.println(network instanceof Graph);
    //System.out.println(network instanceof ValueGraph);
    //System.out.println(network instanceof Network);
  }
  */
}
}