import java.awt.Point;
import java.io.*;
import java.applet.*;

public class Util {


    private static Point[][] p = ChessBoard.p;
    private AudioClip au;
    static boolean mute = false;
    static boolean win = false;
    public Util(){
        try{
            File f = new File("audio\\beep.au");
            au = Applet.newAudioClip(f.toURL());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void play(){
        if(!mute)
            au.play();
    }

    public static Point NorthWest(Point pp){
        int x = 0;
        int y = 0;
        for(int ii=1;ii<9;ii++)
            for(int jj=1;jj<9;jj++){
                if(p[ii][jj] .equals(pp)){
                    x = ii;
                    y = jj;
                }
            }
        if(x-1>0 && y-1>0)
            return p[x-1] [y-1];
        return null;
    }
    public static Point NorthEast(Point pp){
        int x = 0;
        int y = 0;
        for(int ii=1;ii<9;ii++)
            for(int jj=1;jj<9;jj++){
                if(p[ii][jj] .equals(pp)){
                    x = ii;
                    y = jj;
                }
            }
        if(x+1<9 && y-1>0)
            return p[x+1] [y-1];
        return null;
    }
    public static Point SouthWest(Point pp){
        int x = 0;
        int y = 0;
        for(int ii=1;ii<9;ii++)
            for(int jj=1;jj<9;jj++){
                if(p[ii][jj] .equals(pp)){
                    x = ii;
                    y = jj;
                }
            }
        if(x-1>0 && y+1<9)
            return p[x-1] [y+1];
        return null;
    }
    public static Point SouthEast(Point pp){
        int x = 0;
        int y = 0;
        for(int ii=1;ii<9;ii++)
            for(int jj=1;jj<9;jj++){
                if(p[ii][jj] .equals(pp)){
                    x = ii;
                    y = jj;
                }
            }
        if(x+1<9 && y+1<9)
            return p[x+1] [y+1];
        return null;
    }

    public static boolean eat(Chess monster,CheckerState state){
        int x = 0, y = 0;
        Chess [] red = state.red;
        Chess [] black = state.black;

        if(monster==null)
            return false;
        for(int ii=1;ii<9;ii++)
            for(int jj=1;jj<9;jj++){
                if(p[ii][jj] .equals(monster.getLocation())){
                    x = ii;
                    y = jj;
                }
            }
        if(monster.getColor().equals("red")){
            for(int j=0;j<12;j++){
                if(x-2>0&&y-2>0&&monster.isKing() && black[j].isVisible()&&black[j].getLocation().equals(p[x-1][y-1])&&hasChess(red,black,p[x-2][y-2]).equals("none"))
                    return true;
                if(x-2>0&&y+2<9&&black[j].getLocation().equals(p[x-1][y+1])&&black[j].isVisible()&&hasChess(red,black,p[x-2][y+2]).equals("none"))
                    return true;
                if(x+2<9&&y-2>0&&monster.isKing() && black[j].isVisible()&&black[j].getLocation().equals(p[x+1][y-1])&&hasChess(red,black,p[x+2][y-2]).equals("none"))
                    return true;
                if(x+2<9&&y+2<9&&black[j].getLocation().equals(p[x+1][y+1])&&black[j].isVisible()&&hasChess(red,black,p[x+2][y+2]).equals("none"))
                    return true;
            }
            return false;
        }
        else{
            for(int j=0;j<12;j++){
                if(x-2>0&&y-2>0&&red[j].isVisible()&&red[j].getLocation().equals(p[x-1][y-1])&&hasChess(red,black,p[x-2][y-2]).equals("none"))
                    return true;
                if(x-2>0&&y+2<9&&monster.isKing()&&red[j].getLocation().equals(p[x-1][y+1])&&red[j].isVisible()&&hasChess(red,black,p[x-2][y+2]).equals("none"))
                    return true;
                if(x+2<9&&y-2>0&&red[j].isVisible()&&red[j].getLocation().equals(p[x+1][y-1])&&hasChess(red,black,p[x+2][y-2]).equals("none"))
                    return true;
                if(x+2<9&&y+2<9&&monster.isKing()&&red[j].getLocation().equals(p[x+1][y+1])&&red[j].isVisible()&&hasChess(red,black,p[x+2][y+2]).equals("none"))
                    return true;
            }
            return false;
        }
    }

    public static boolean eat(Chess monster,Chess[] red,Chess[] black){
        int x = 0, y = 0;
        if(monster==null)
            return false;
        for(int ii=1;ii<9;ii++)
            for(int jj=1;jj<9;jj++){
                if(p[ii][jj] .equals(monster.getLocation())){
                    x = ii;
                    y = jj;
                }
            }
        if(monster.getColor().equals("red")){
            for(int j=0;j<12;j++){
                if(x-2>0&&y-2>0&&monster.isKing() && black[j].isVisible()&&black[j].getLocation().equals(p[x-1][y-1])&&hasChess(red,black,p[x-2][y-2]).equals("none"))
                    return true;
                if(x-2>0&&y+2<9&&black[j].getLocation().equals(p[x-1][y+1])&&black[j].isVisible()&&hasChess(red,black,p[x-2][y+2]).equals("none"))
                    return true;
                if(x+2<9&&y-2>0&&monster.isKing() && black[j].isVisible()&&black[j].getLocation().equals(p[x+1][y-1])&&hasChess(red,black,p[x+2][y-2]).equals("none"))
                    return true;
                if(x+2<9&&y+2<9&&black[j].getLocation().equals(p[x+1][y+1])&&black[j].isVisible()&&hasChess(red,black,p[x+2][y+2]).equals("none"))
                    return true;
            }
            return false;
        }
        else{
            for(int j=0;j<12;j++){
                if(x-2>0&&y-2>0&&red[j].isVisible()&&red[j].getLocation().equals(p[x-1][y-1])&&hasChess(red,black,p[x-2][y-2]).equals("none"))
                    return true;
                if(x-2>0&&y+2<9&&monster.isKing()&&red[j].getLocation().equals(p[x-1][y+1])&&red[j].isVisible()&&hasChess(red,black,p[x-2][y+2]).equals("none"))
                    return true;
                if(x+2<9&&y-2>0&&red[j].isVisible()&&red[j].getLocation().equals(p[x+1][y-1])&&hasChess(red,black,p[x+2][y-2]).equals("none"))
                    return true;
                if(x+2<9&&y+2<9&&monster.isKing()&&red[j].getLocation().equals(p[x+1][y+1])&&red[j].isVisible()&&hasChess(red,black,p[x+2][y+2]).equals("none"))
                    return true;
            }
            return false;
        }
    }

    public static boolean eat(Chess chess,CheckerState state,boolean b){
        int x = 0, y = 0;
        Chess [] red = state.red;
        Chess [] black = state.black;
        if(chess.getColor()=="red"){
            for(int i=0;i<12;i++){
                if(red[i].isVisible()){
                    for(int ii=1;ii<9;ii++)
                        for(int jj=1;jj<9;jj++){
                            if(p[ii][jj] .equals(red[i].getLocation())){
                                x = ii;
                                y = jj;
                            }
                        }
                    for(int j=0;j<12;j++){
                        if(x-2>0&&y-2>0&&red[i].isKing() && black[j].isVisible()&&black[j].getLocation().equals(p[x-1][y-1])&&hasChess(red,black,p[x-2][y-2]).equals("none"))
                            return true;
                        if(x-2>0&&y+2<9&&black[j].getLocation().equals(p[x-1][y+1])&&black[j].isVisible()&&hasChess(red,black,p[x-2][y+2]).equals("none"))
                            return true;
                        if(x+2<9&&y-2>0&&red[i].isKing() && black[j].isVisible()&&black[j].getLocation().equals(p[x+1][y-1])&&hasChess(red,black,p[x+2][y-2]).equals("none"))
                            return true;
                        if(x+2<9&&y+2<9&&black[j].getLocation().equals(p[x+1][y+1])&&black[j].isVisible()&&hasChess(red,black,p[x+2][y+2]).equals("none"))
                            return true;
                    }
                }
            }
            return false;
        }
        else{
            for(int i=0;i<12;i++){

                if(black[i].isVisible()){
                    for(int ii=1;ii<9;ii++)
                        for(int jj=1;jj<9;jj++){
                            if(p[ii][jj] .equals(black[i].getLocation())){
                                x = ii;
                                y = jj;
                            }
                        }
                    for(int j=0;j<12;j++){
                        if(y+2<9&&x-2>0&&black[i].isKing() && red[j].isVisible()&&red[j].getLocation().equals(p[x-1][y+1])&&x-2>0&&hasChess(red,black,p[x-2][y+2]).equals("none"))
                            return true;
                        if(x-2>0&&y-2>0&&red[j].getLocation().equals(p[x-1][y-1])&&red[j].isVisible()&&x-2>0&&y-2>0&&hasChess(red,black,p[x-2][y-2]).equals("none"))
                            return true;
                        if(x+2<9&&y+2<9&&black[i].isKing() &&red[j].isVisible()&& red[j].getLocation().equals(p[x+1][y+1])&&y+2<9&&hasChess(red,black,p[x+2][y+2]).equals("none"))
                            return true;
                        if(x+2<9&&y-2>0&&red[j].getLocation().equals(p[x+1][y-1])&&y-2>0&&red[j].isVisible()&&hasChess(red,black,p[x+2][y-2]).equals("none"))
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public static String hasChess(Chess[] red,Chess[] black,Point pp){
        for(int i=0;i<12;i++){
            if(red[i].isVisible()&&red[i].getLocation().equals(pp))
                return "red";
            if(black[i].isVisible()&&black[i].getLocation().equals(pp))
                return "black";
        }
        return "none";
    }
    public static String hasChess(CheckerState st,Point pp){
        Chess[] red = st.red;
        Chess[] black = st.black;
        return hasChess(red,black,pp);
    }
}
