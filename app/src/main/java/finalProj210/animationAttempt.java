package finalProj210;
import com.google.common.graph.*;
import java.awt.Point;
import java.awt.Color;

public class animationAttempt {
    public static void colorizeShit(GraphDisplay d){
        d.setColor(" Yes (Timely) ", Color.PINK);
        Object edge = d.getEdgeBetween(" Yes (Timely) ", " Priority 2 - 72 Hours ");
        d.setColor(edge, Color.MAGENTA);
        
    }
}
