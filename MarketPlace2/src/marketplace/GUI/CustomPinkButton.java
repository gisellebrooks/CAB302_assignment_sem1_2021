package marketplace.GUI;

import marketplace.Util.Fonts;

import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class CustomPinkButton extends JButton {

    public CustomPinkButton(String text) {
        super(text);
        Fonts fonts;
        fonts = new Fonts();
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2,2,2,2, new Color(0,0,0)),
                BorderFactory.createLineBorder(Color.WHITE, 4)
        ));
        setOpaque(true);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setContentAreaFilled(true);
        setFocusPainted(false);
        setFont(fonts.small);
    }

}
