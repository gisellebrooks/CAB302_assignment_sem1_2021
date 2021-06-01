import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

//source: https://stackoverflow.com/questions/33398028/resize-jpanel-in-jframe/33399110#33399110
public class test {

    private JFrame frame = new JFrame();
    private JPanel panel1 = new JPanel() {
        private static final long serialVersionUID = 1L;

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 50);
        }

        @Override
        public Border getBorder() {
            return new LineBorder(Color.BLACK, 1);
        }
    };
    private JPanel panel2 = new JPanel() {
        private static final long serialVersionUID = 1L;

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 50);
        }

        @Override
        public Border getBorder() {
            return new LineBorder(Color.RED, 1);
        }
    };
    private JPanel panel3 = new JPanel() {
        private static final long serialVersionUID = 1L;

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 50);
        }

        @Override
        public Border getBorder() {
            return new LineBorder(Color.BLUE, 1);
        }
    };
    private JPanel panel4 = new JPanel() {
        private static final long serialVersionUID = 1L;

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 50);
        }

        @Override
        public Border getBorder() {
            return new LineBorder(Color.ORANGE, 1);
        }
    };
    private JPanel panel5 = new JPanel() {
        private static final long serialVersionUID = 1L;

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        @Override
        public Border getBorder() {
            return new LineBorder(Color.GREEN, 1);
        }
    };
    private GridLayout gridLayout = new GridLayout(2, 2);
    private BorderLayout borderLayout = new BorderLayout();

    public test() {
        frame.setLayout(gridLayout);
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        Timer t = new Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (frame.getContentPane().getLayout() == borderLayout) {
                    frame.getContentPane().removeAll();
                    frame.setLayout(gridLayout);
                    frame.add(panel1);
                    frame.add(panel2);
                    frame.add(panel3);
                    frame.add(panel4);
                    frame.pack();
                    //frame.revalidate();
                    //frame.repaint();
                } else if (frame.getContentPane().getLayout() == gridLayout) {
                    frame.getContentPane().removeAll();
                    frame.setLayout(borderLayout);
                    frame.add(panel5);
                    frame.pack();
                    //frame.revalidate();
                    //frame.repaint();
                }
            }
        });
        t.setInitialDelay(2500);
        t.setRepeats(true);
        t.start();
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new test();
            }
        });
    }
}