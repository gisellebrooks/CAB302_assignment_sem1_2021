package marketplace.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * <h1>A logo for the top </h1>
 * * a login screen to allow users and admins to log in to the app
 * *
 * * @author Ali
 */
public class LogoPanel extends JPanel {
    public LogoPanel() {
        try {
            setBackground(Color.WHITE);
            BufferedImage myPicture = ImageIO.read(new File("MarketPlace2/src/images/Logo.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            add(picLabel);
        } catch (IOException ex) {
          ex.printStackTrace();  
        }
    }
}
