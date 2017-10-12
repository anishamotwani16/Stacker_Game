import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Stacker extends JFrame implements KeyListener {

    JFrame jf = new JFrame("STACKER");
    static int level = 1;
    long time = 200;
    int last = 0;
    int col = 10;
    int row = 20;
    JButton b[][];
    int length[] = {5, 5};
    int layer = 1;
    int deltax[] = {0, -1};
    boolean press = false;
    boolean forward = true;
    boolean start = true;
    int count=1;

    public Stacker()        //constructor
    {
        jf.setDefaultCloseOperation(EXIT_ON_CLOSE);
        b = new JButton[row][col];
        jf.setLayout(new GridLayout(row, col));
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < col; y++) {
                b[x][y] = new JButton(" ");    //text on the button
                b[x][y].setBackground(Color.white);
                jf.add(b[x][y]);
                b[x][y].setEnabled(false);
            }   //end inner for
        }
        jf.addKeyListener(this);
        jf.pack();
        jf.setVisible(true);
        go();
    }

    public void go() {
        int tmp;
        do {
            if (forward == true) {
                forward();
            } else {
                back();
            }
            if (deltax[1] == 10 - length[1]) {
                forward = false;
            } else if (deltax[1] == 0) {
                forward = true;
            }
            draw();
            if(level==1&&count==1)
                JOptionPane.showMessageDialog(null, "Press Space Bar to stop the moving row!");
            count=0;
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        } while (press == false);
        if (layer < 8) {
            time = 150 - (layer * layer * 2 - layer);
        } else {
            time = time - 2;
        }
        press = false;
        tmp = check();
        length[0] = length[1];
        length[1] = tmp;
        if (layer == 20 && length[1] > 0) {
            JOptionPane.showMessageDialog(null, "Congratulations! You beat level " + level + "!");
            level++;
            jf.setVisible(false);
            jf.dispose();
            new Stacker();
        }
        if (length[1] <= 0) {
            JOptionPane.showMessageDialog(null, "Game over! You reached Level-" + level + " Line-" + layer + "!");
            System.exit(0);
        }
        layer++;
        last = deltax[1];
        start = false;
        go();
    }

    public int check() {
        if (start == true) {
            return length[1];
        } else if (last < deltax[1]) {
            if (deltax[1] + length[1] - 1 <= last + length[0] - 1) {
                return length[1];
            } else {
                return length[1] - ((deltax[1] + length[1]) - (last + length[0]));
            }
        } else if (last > deltax[1])
        {
            return length[1] - (last - deltax[1]);
        } else {
            return length[1];
        }
    }

    public void forward() {
        deltax[0] = Math.abs(deltax[1]);
        deltax[1]++;
    }

    public void back() {
        deltax[0] = deltax[1];
        deltax[1]--;
    }

    public void draw() {
        int x;
        for (x = 0; x < length[1]; x++) {
            b[20 - layer][x + deltax[0]].setBackground(Color.white);
        }
        int ch;
        ch = level % 5;
        for (x = 0; x < length[1]; x++) {
            switch (ch) {
            case 0:
                b[20 - layer][x + deltax[1]].setBackground(Color.red);
                break;
            case 1:
                b[20 - layer][x + deltax[1]].setBackground(Color.blue);
                break;
            case 2:
                b[20 - layer][x + deltax[1]].setBackground(Color.green);
                break;
            case 3:
                b[20 - layer][x + deltax[1]].setBackground(Color.cyan);
                break;
            case 4:
                b[20 - layer][x + deltax[1]].setBackground(Color.yellow);
                break;

            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == e.VK_SPACE)
        {
            press = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    public static void main(String[] args) {
        Stacker s = new Stacker();
    }}
