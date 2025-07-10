import javax.swing.border.*;
import java.awt.*;

public class RoundedBorder extends LineBorder {
    private final int arc;
    public RoundedBorder(int arc) { super(Color.BLACK, 0, true); this.arc = arc; }

    @Override public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
        g.drawRoundRect(x, y, w - 1, h - 1, arc, arc);
    }
}