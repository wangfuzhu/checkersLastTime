import javax.swing.*;
import java.awt.event.*;

public class Checkers extends JFrame implements ActionListener{
    ChessBoard cb;
    JPanel top = new JPanel();
    JButton start = new JButton("New");
    JButton bar = new JButton();
    JButton quit = new JButton("Quit");

    public static void main(String[] args){
        new Checkers();
    }

    public Checkers(){
        cb = new ChessBoard();
        this.setLayout(null);
        cb.setLocation(0,35);

        top.setBounds(0,0,480,35);
        top.setLayout(null);
        top.add(start);
        top.add(bar);
        top.add(quit);

        start.setBounds(0,0,80,30);
        start.addActionListener(this);

        bar.setBounds(0,30,480,5);
        bar.setEnabled(false);

        quit.setBounds(410,0,70,30);
        quit.addActionListener(this);

        this.add(top);
        this.add(cb);
        this.setBounds(100,100,490,545);
        this.setTitle("Checkers");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() instanceof JButton){
            JButton jb = (JButton)e.getSource();
            if(jb.equals(start)){
                Util.win = false;
                cb.init();
                cb.setVisible(true);
                cb.repaint();
            }
            else if(jb.equals(quit)){
                int i = JOptionPane.showConfirmDialog(null, "Are you sure to quit?");
                if(i==0)
                    System.exit(0);

            }
        }
    }

}