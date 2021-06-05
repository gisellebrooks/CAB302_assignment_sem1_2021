package marketplace.GUI;
import javax.swing.*;
import java.awt.*;

public class FullSizeJPanel extends JPanel {

    public FullSizeJPanel() {
        super();
        setBackground(Color.WHITE);
        setBounds(0, 0, fullWidth, fullHeight);
        setPreferredSize(new Dimension(fullWidth, fullHeight));
        setSize(new Dimension(fullWidth, fullHeight));
    }

    public static int fullWidth = 1181;
    public static int fullHeight = 718;
}
