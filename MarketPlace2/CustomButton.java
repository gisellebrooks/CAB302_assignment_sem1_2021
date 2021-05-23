import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class CustomButton extends JButton {

    public CustomButton(String text) {
        super(text);
        Border thickBorder = new LineBorder(Color.BLACK, 12);
        setBorder(thickBorder);
        setOpaque(true);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setContentAreaFilled(true);
        setBorderPainted(false);
        setFocusPainted(false);
    }

}
