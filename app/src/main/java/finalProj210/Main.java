package finalProj210;
import com.google.common.graph.*;
import java.awt.Color;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Optional;

class Main {
  public static void main(String[] args) throws Exception{
    MutableValueGraph<String,Integer> graph =
    ValueGraphBuilder.directed().build();
    Scanner file = ReadFile.read("/Users/sanjanayasna/Desktop/Data_Structs_Practice/FinalProj210/data_simple.csv");
    Integer lineCount = 0;
    file.nextLine();
    while (file.hasNextLine() && lineCount <122) {
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
    new GraphDisplay(graph);
    Shortest.grossPath(graph, " Yes (Timely) ");

}
}