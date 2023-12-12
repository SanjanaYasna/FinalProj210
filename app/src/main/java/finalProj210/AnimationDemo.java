package finalProj210;
import com.google.common.graph.*;
import java.awt.Point;
import java.awt.Color;

/** Demo of Guava graph with GraphDisplay color animation */
public class AnimationDemo {
  /** Runs the demo */
  public static void main(String[] args) {
    ImmutableGraph<String> graph =
      GraphBuilder.undirected()
        .<String>immutable()
        .putEdge("A","B")
        .putEdge("B","C")
        .putEdge("C","D")
        .putEdge("D","E")
        .putEdge("E","A")
        .addNode("F")
        .build();

    GraphDisplay d = new GraphDisplay(graph);
    d.labelOffset = new Point(0,3);

    String[] labels = {"C", "E", "F", "A", "D", "B"};
    while (true) {
      for (String lbl: labels) {
        try {
          Thread.sleep(500);
        } catch (Exception e) {
        }
        d.setColor(lbl,Color.BLUE);
      }
      for (Object edge: d.getEdgeSet()) {
        try {
          Thread.sleep(500);
        } catch (Exception e) {
        }
        d.setColor(edge,Color.GREEN);
      }
      for (String lbl: labels) {
        try {
          Thread.sleep(500);
        } catch (Exception e) {
        }
        d.setColor(lbl,Color.RED);
      }
      for (Object edge: d.getEdgeSet()) {
        try {
          Thread.sleep(500);
        } catch (Exception e) {
        }
        d.setColor(edge,Color.MAGENTA);
      }
    }
  }
}