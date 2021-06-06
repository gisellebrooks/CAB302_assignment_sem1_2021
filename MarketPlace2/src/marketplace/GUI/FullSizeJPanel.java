package marketplace.GUI;
import javax.swing.*;
import java.awt.*;

/**
 * <h1>A full size screen </h1>
 * * adds a screen of full size. All pages are built on this component
 * *
 * * @author Ali
 */
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
