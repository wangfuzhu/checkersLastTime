
public class CheckerState{
    Chess red[];
    Chess black[] ;

    public CheckerState(Chess red[], Chess[] black){
        Chess red0[] = new Chess[12];
        Chess black0[] = new Chess[12];
        for(int i=0;i<12;i++){
            red0[i] = new Chess();
            red0[i].id = red[i].id;
            red0[i].setColor("red");
            red0[i].setLocation(red[i].getLocation());
            red0[i].setVisible(red[i].isVisible());
            red0[i].setKing(red[i].isKing());

            black0[i] = new Chess();
            black0[i].id = black[i].id;
            black0[i].setColor("black");
            black0[i].setLocation(black[i].getLocation());
            black0[i].setVisible(black[i].isVisible());
            black0[i].setKing(black[i].isKing());

        }
        this.red = red0;
        this.black = black0;
    }


    public String toString(){
        String s = "red: ";
        for(int i=0;i<12;i++){
            s+= i+":"+red[i].toString()+" ";
        }
        s += ";  black: ";
        for(int i=0;i<12;i++){
            s+= i+":"+black[i].toString()+" ";
        }
        return s;
    }

    public boolean equals(Object o){
        if(!(o instanceof CheckerState))
            return false;
        CheckerState s = (CheckerState)o;
        Chess redO[] = s.red;
        Chess blackO[] = s.black;
        for(int i=0;i<12;i++){
            if(!(red[i].getLocation().equals(redO[i].getLocation()) &&
                    red[i].isVisible()==redO[i].isVisible() && red[i].isKing()==redO[i].isKing()))
                return false;
            if(!(black[i].getLocation().equals(blackO[i].getLocation()) &&
                    black[i].isVisible()==blackO[i].isVisible() && black[i].isKing()==blackO[i].isKing()))
                return false;
        }
        return true;
    }

}
