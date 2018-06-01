import javax.swing.*;
import javax.swing.plaf.synth.SynthSeparatorUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChessBoard extends JPanel implements MouseListener{


    private static final long serialVersionUID = -8427438142176684142L;
    private static final Toolkit toolkit = Toolkit.getDefaultToolkit();
    private static final Image RedImage = toolkit.getImage("images/red.png");
    private static final Image RedImage1 = toolkit.getImage("images/red1.png");
    private static final Image BlackImage = toolkit.getImage("images/black.png");
    private static final Image RedKingImage = toolkit.getImage("images/redKing.png");
    private static final Image RedKingImage1 = toolkit.getImage("images/redKing.png");
    private static final Image BlackKingImage = toolkit.getImage("images/blackKing.png");
    private static final Image BlackImage1 = toolkit.getImage("images/black1.png");
    private static final Image BlackKingImage1 = toolkit.getImage("images/blackKing1.png");
    private static final Image image = toolkit.getImage("images/bg.jpg");

    private Chess selectedChess;
    private Chess monster;
    private Util util = new Util();

    public static Chess black[] = new Chess[12];
    public static Chess red[] = new Chess[12];
    public static Point[][] p = new Point[9][9];
    public static boolean turn = true;

    public ChessBoard(){
        try {
            init();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        this.setSize(496,516);
        this.addMouseListener(this);
    }

    public void paint(Graphics g){
        g.drawImage(image,0, 0, 480, 480,null);
        for(int i=0;i<12;i++){
            if(black[i].isVisible()){
                Point p = black[i].getLocation();

                if(black[i].isKing()){
                    if(black[i].isSelected())
                        g.drawImage(BlackKingImage1,p.x, p.y, 60,60,null);
                    else
                        g.drawImage(BlackKingImage,p.x, p.y, 60,60,null);
                }
                else
                if(black[i].isSelected())
                    g.drawImage(BlackImage1,p.x, p.y, 60,60,null);
                else
                    g.drawImage(BlackImage,p.x, p.y, 60,60,null);
            }
            if(red[i].isVisible()){
                Point p = red[i].getLocation();

                if(red[i].isKing()){
                    if(red[i].isSelected())
                        g.drawImage(RedKingImage1,p.x, p.y, 60,60,null);
                    else
                        g.drawImage(RedKingImage,p.x, p.y, 60,60,null);
                }
                else
                if(red[i].isSelected())
                    g.drawImage(RedImage1,p.x, p.y, 60,60,null);
                else
                    g.drawImage(RedImage,p.x, p.y, 60,60,null);
            }
        }

    }

    public void init(){

        for(int j=1;j<=8;j++){
            for(int i=1;i<=8;i++){
                p[j] [i]=new Point();
                p[j] [i].x=60*(j-1) ;
                p[j] [i].y=60*(i-1);
            }
        }

        for(int i=0;i<12;i++){
            red[i] = new Chess("red",i);
            black[i] = new Chess("black",i);
        }
        for(int i=0;i<4;i++){
            red[i].setLocation(p[2*i+2][1]);
        }
        for(int i=4;i<8;i++){
            red[i].setLocation(p[2*(i-4)+1][2]);
        }
        for(int i=8;i<12;i++){
            red[i].setLocation(p[2*(i-8)+2][3]);
        }
        for(int i=0;i<4;i++){
            black[i].setLocation(p[2*i+1][6]);
        }
        for(int i=4;i<8;i++){
            black[i].setLocation(p[2*(i-4)+2][7]);
        }
        for(int i=8;i<12;i++){
            black[i].setLocation(p[2*(i-8)+1][8]);
        }
        turn = true;
    }

    public void mousePressed(MouseEvent e) {
        checkOver();
        if(Util.win)
            return;
        int x=e.getX();
        int y=e.getY();
        Point selectedPoint = getPoint(x,y);
        for(int i=0;i<12;i++)
            red[i].setSelected(false);
        for(int i=0;i<12;i++)
            black[i].setSelected(false);
        for(int i=0;i<12;i++){
            if(black[i].isVisible() && black[i].getLocation().equals(selectedPoint)){
                selectedChess = black[i];
                black[i].setSelected(true);
                repaint();
            }
            if(red[i].isVisible() && red[i].getLocation().equals(selectedPoint)){
                selectedChess = red[i];
                red[i].setSelected(true);
                repaint();
            }
        }
        if(selectedChess!=null){
            Point formerPoint = selectedChess.getLocation();
            if(ifCanGo(selectedChess,formerPoint, selectedPoint)){
                selectedChess.setLocation(selectedPoint);
                for(int i=1;i<9;i++){
                    if(selectedPoint.equals(p[i][1])){
                        selectedChess.setKing(true);
                    }
                }
                repaint();
                util.play();
                if(monster!=null && monster.equals(selectedChess) && Util.eat(monster,red,black)){
                    monster = null;
                    System.out.println("������ų�");
                    return;
                }
                if(selectedChess.getColor().equals("black")){
                    CheckerState state = new CheckerState(red,black,true);
                    selectedChess = null;
                    turn = false;
                }else if(selectedChess.getColor().equals("red")) {
                    CheckerState state = new CheckerState(black,red,true);
                    selectedChess = null;
                    turn = true;
                }
            }

        }

    }
    public void mouseClicked(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}

    private boolean ifCanGo(Chess chess,Point former, Point now){
        System.out.println("former");
        System.out.println(former.getX());
        System.out.println(former.getY());

        System.out.println("now");
        System.out.println(now.getX());
        System.out.println(now.getY());

        int fx = 0, fy = 0, nx = 0, ny = 0;
        monster = null;
        for(int i=1;i<9;i++)
            for(int j=1;j<9;j++){
                if(p[i][j] .equals(former)){
                    fx = i;
                    fy = j;
                }
                if(p[i][j] .equals(now)){
                    nx = i;
                    ny = j;
                }
            }

        if(chess.isKing() && Math.abs(fx - nx) == 1 && Math.abs(fy - ny) == 1){
            if(Util.eat(chess,new CheckerState(red,black),true)){
                JOptionPane.showMessageDialog(this, "����Ե��Է�������");
                return false;
            }
            else if(Util.eat(chess,new CheckerState(black,red),true)){
                JOptionPane.showMessageDialog(this, "����Ե��Է�������");
                return false;
            }
            return true;
        }
        if(turn)
        {
            if(chess.getColor()=="black"){
                if(Math.abs(fx - nx) == 1 && fy - ny == 1){
                    if(Util.eat(chess,new CheckerState(red,black),true)){
                        JOptionPane.showMessageDialog(this, "����Ե��Է�������");
                        return false;
                    }
                    return true;
                }
                else if(Math.abs(fx - nx) == 2 && Math.abs(fy - ny) == 2){
                    for(int i=0;i<12;i++){
                        if(fy - ny ==2){
                            if(nx-fx==2 &&red[i].isVisible()&&fx<8 && red[i].getLocation().equals(p[fx+1][fy-1])){
                                red[i].setVisible(false);
                                monster = chess;
                                return true;
                            }
                            if(nx-fx==-2 &&red[i].isVisible()&&red[i].getLocation().equals(p[fx-1][fy-1])){
                                red[i].setVisible(false);
                                monster = chess;
                                return true;
                            }
                        }
                        else if(chess.isKing()){
                            if(nx-fx==2 &&fx<8 &&red[i].isVisible()&& red[i].getLocation().equals(p[fx+1][fy-1])){
                                red[i].setVisible(false);
                                monster = chess;
                                return true;
                            }
                            if(nx-fx==-2 &&red[i].isVisible()&&red[i].getLocation().equals(p[fx-1][fy-1])){
                                red[i].setVisible(false);
                                monster = chess;
                                return true;
                            }
                            if(nx-fx==2 &&fx<8 &&red[i].isVisible()&& fy<8 && red[i].getLocation().equals(p[fx+1][fy+1])){
                                red[i].setVisible(false);
                                monster = chess;
                                return true;
                            }
                            if(nx-fx==-2 &&fy<8 && red[i].isVisible()&& red[i].getLocation().equals(p[fx-1][fy+1])){
                                red[i].setVisible(false);
                                monster = chess;
                                return true;
                            }
                        }
                    }
                }
            }
        }else
        {
            if(chess.getColor()=="red") {
                if(Math.abs(nx - fx) == 1 && ny - fy == 1){
                    if(Util.eat(chess,new CheckerState(red,black),true)){
                        JOptionPane.showMessageDialog(this, "����Ե��Է�������");
                        return false;
                    }
                    return true;
                }
                else if(Math.abs(nx - fx) == 2 && Math.abs(ny - fy) == 2){
                    for(int i=0;i<12;i++){
                        if(fy - ny == -2){
                            if(nx-fx == 2 &&black[i].isVisible()&&fx<8 && black[i].getLocation().equals(p[fx+1][fy+1])){
                                black[i].setVisible(false);
                                monster = chess;
                                return true;
                            }
                            if(nx-fx == -2 &&black[i].isVisible()&&black[i].getLocation().equals(p[fx-1][fy+1])){
                                black[i].setVisible(false);
                                monster = chess;
                                return true;
                            }
                        }
                        else if(chess.isKing()){
                            if(nx-fx==2 &&fx<8 &&black[i].isVisible()&& black[i].getLocation().equals(p[fx+1][fy-1])){
                                black[i].setVisible(false);
                                monster = chess;
                                return true;
                            }
                            if(nx-fx==-2 &&black[i].isVisible()&&black[i].getLocation().equals(p[fx-1][fy-1])){
                                black[i].setVisible(false);
                                monster = chess;
                                return true;
                            }
                            if(nx-fx==2 &&fx<8 &&black[i].isVisible()&& fy<8 && black[i].getLocation().equals(p[fx+1][fy+1])){
                                black[i].setVisible(false);
                                monster = chess;
                                return true;
                            }
                            if(nx-fx==-2 &&fy<8 && black[i].isVisible()&& black[i].getLocation().equals(p[fx-1][fy+1])){
                                black[i].setVisible(false);
                                monster = chess;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    private Point getPoint(int x,int y){
        int i=1,j=1;
        while(x-i*60>=5 && i<8) i++;
        while(y-j*60>=5 && j<8) j++;
        return p[i][j];
    }

    private void checkOver(){
        for(int i=0;i<12;i++){
            if(red[i].isVisible())
                break;
            if(i==11){
                JOptionPane.showMessageDialog(null, "Black win!");
                return ;
            }
        }
        for(int i=0;i<12;i++){
            if(black[i].isVisible())
                break;
            if(i==11){
                JOptionPane.showMessageDialog(null, "Red Win!");
                return ;
            }
        }
    }
}
