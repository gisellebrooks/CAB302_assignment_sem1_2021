package marketplace.GUI;

import marketplace.Util.Fonts;

import javax.swing.*;
import java.awt.*;

public class CustomPinkButton extends JButton {

    public CustomPinkButton(String text) {
        super(text);
        Fonts fonts;
        fonts = new Fonts();
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2,2,2,2, new Color(0,0,0)),
                BorderFactory.createLineBorder(new Color(255, 211, 211), 4)
        ));
        setOpaque(true);
        setBackground(new Color(255, 211, 211));
        setForeground(Color.BLACK);
        setContentAreaFilled(true);
        setFocusPainted(false);
        setFont(fonts.small);
    }

}
