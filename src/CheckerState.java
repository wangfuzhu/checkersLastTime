import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;

public class CheckerState{
    static Point p[][] = ChessBoard.p;
    Chess red[];
    Chess black[] ;
    static Chess OriRed[];
    static Chess OriBlack[];
    Chess redKiller;
    Chess blackKiller;
    public static int[] w = new int[]{8,6,-6,8,-8,-4,4};
    int depth;
    int value;

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
    public CheckerState(Chess red[], Chess[] black, boolean b){

        Chess red0[] = new Chess[12];
        Chess black0[] = new Chess[12];
        OriRed = red;
        OriBlack = black;

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

    public int getValue(){
        //w = new int[]{0,6,-6,9,-9,-3,3};
        int redNumber = this.getRedNumber();
        int blackNumber = this.getBlackNumber();
        int redKingNumber = this.getRedKingNumber();
        int blackKingNumber = this.getBlackKingNumber();
        int redBeingKilledNumber = this.getRedBeingKilledNumber();
        int blackBeingKilledNumber = this.getBlackBeingKilledNumber();

        if(redNumber+redKingNumber==0)
            return -99999;
        if(blackNumber+blackKingNumber==0)
            return 99999;
		/*if(redNumber+redKingNumber > blackNumber+blackKingNumber){
			w[1]--;
			w[3]--;
		}
		else if(redNumber+redKingNumber < blackNumber+blackKingNumber){
			w[1]++;
			w[3]++;
		}*/
        return w[0] + w[1] * redNumber + w[2] * blackNumber + w[3] * redKingNumber+
                w[4] * blackKingNumber + w[5] * redBeingKilledNumber + w[6] * blackBeingKilledNumber;
    }

    public ArrayList nextStatesOfRed(){
        ArrayList list = new ArrayList();
        for(int i=0;i<12;i++){
            if(!red[i].isVisible())
                continue;
            list.addAll(nextEatingStates(red[i],this,true));
        }
        if(list.size()==0)
            for(int i=0;i<12;i++){
                if(!red[i].isVisible())
                    continue;
                list.addAll(nextStates(red[i],this));
            }
        return list;
    }

    public ArrayList nextStatesOfBlack(){
        ArrayList list = new ArrayList();
        for(int i=0;i<12;i++){
            if(!black[i].isVisible())
                continue;
            list.addAll(nextEatingStates(black[i],this,true));
        }
        if(list.size()==0)
            for(int i=0;i<12;i++){
                if(!black[i].isVisible())
                    continue;
                list.addAll(nextStates(black[i],this));
            }
        return list;
    }

    private ArrayList nextEatingStates(Chess chess, CheckerState st,boolean first){
        ArrayList list = new ArrayList();
        Point pp = chess.getLocation();
        int x = pp.x/60 +1;
        int y = pp.y/60 +1;
        Chess red[] = st.red;
        Chess black[] = st.black;

        if(chess.getColor().equals("red")){
            if(Util.eat(chess,st,true)){
                if(chess.isVisible()){
                    for(int j=0;j<12;j++){
                        if(x-2>0&&y-2>0&&chess.isKing() && black[j].isVisible()&&black[j].getLocation().equals(p[x-1][y-1])&&hasChess(p[x-2][y-2]).equals("none"))
                        {
                            CheckerState state = new CheckerState(red ,black);
                            state.getRed(p[x][y]).setLocation(p[x-2][y-2]);
                            state.redKiller = state.getRed(p[x-2][y-2]);
                            state.getBlack(p[x-1][y-1]).setVisible(false);
                            list.add(state);
                        }
                        if(y+2<9&&x-2>0&&black[j].getLocation().equals(p[x-1][y+1])&&black[j].isVisible()&&hasChess(p[x-2][y+2]).equals("none"))
                        {
                            CheckerState state = new CheckerState(red ,black);
                            state.getRed(p[x][y]).setLocation(p[x-2][y+2]);
                            state.redKiller = state.getRed(p[x-2][y+2]);
                            state.getBlack(p[x-1][y+1]).setVisible(false);
                            list.add(state);
                        }
                        if(x+2<9&&y-2>0&&chess.isKing() && black[j].isVisible()&&black[j].getLocation().equals(p[x+1][y-1])&&hasChess(p[x+2][y-2]).equals("none"))
                        {
                            CheckerState state = new CheckerState(red ,black);
                            state.getRed(p[x][y]).setLocation(p[x+2][y-2]);
                            state.redKiller = state.getRed(p[x+2][y-2]);
                            state.getBlack(p[x+1][y-1]).setVisible(false);
                            list.add(state);
                        }
                        if(x+2<9&&y+2<9&&black[j].getLocation().equals(p[x+1][y+1])&&black[j].isVisible()&&hasChess(p[x+2][y+2]).equals("none"))
                        {
                            CheckerState state = new CheckerState(red ,black);
                            state.getRed(p[x][y]).setLocation(p[x+2][y+2]);
                            state.redKiller = state.getRed(p[x+2][y+2]);
                            state.getBlack(p[x+1][y+1]).setVisible(false);
                            list.add(state);
                        }
                    }
                }
            }
            ArrayList temp = new ArrayList();
            for(int i=0;i<list.size();i++){
                temp.add(null);
            }
            Collections.copy(temp, list);
            ArrayList nextLevelList = new ArrayList();
            for(Iterator it = temp.iterator();it.hasNext();){
                CheckerState s = (CheckerState)it.next();
                if(Util.eat(s.redKiller, s)){
                    nextLevelList.addAll(nextEatingStates(s.redKiller,s,false));
                }
            }
            if(nextLevelList.size()>0)
                return nextLevelList;
            if(list.size()>0)
                return list;
        }
        else{ //black
            if(Util.eat(chess,st,true)){
                if(chess.isVisible()){
                    for(int j=0;j<12;j++){
                        if(x-2>0&&y-2>0 && red[j].isVisible()&&red[j].getLocation().equals(p[x-1][y-1])&&hasChess(p[x-2][y-2]).equals("none"))
                        {
                            CheckerState state = new CheckerState(red ,black);
                            state.getBlack(p[x][y]).setLocation(p[x-2][y-2]);
                            state.blackKiller = state.getBlack(p[x-2][y-2]);
                            state.getRed(p[x-1][y-1]).setVisible(false);
                            list.add(state);
                        }
                        if(y+2<9&&x-2>0&&chess.isKing()&&red[j].getLocation().equals(p[x-1][y+1])&&red[j].isVisible()&&hasChess(p[x-2][y+2]).equals("none"))
                        {
                            CheckerState state = new CheckerState(red ,black);
                            state.getBlack(p[x][y]).setLocation(p[x-2][y+2]);
                            state.blackKiller = state.getBlack(p[x-2][y+2]);
                            state.getRed(p[x-1][y+1]).setVisible(false);
                            list.add(state);
                        }
                        if(x+2<9&&y-2>0 && red[j].isVisible()&&red[j].getLocation().equals(p[x+1][y-1])&&hasChess(p[x+2][y-2]).equals("none"))
                        {
                            CheckerState state = new CheckerState(red ,black);
                            state.getBlack(p[x][y]).setLocation(p[x+2][y-2]);
                            state.blackKiller = state.getBlack(p[x+2][y-2]);
                            state.getRed(p[x+1][y-1]).setVisible(false);
                            list.add(state);
                        }
                        if(x+2<9&&y+2<9&&chess.isKing()&&red[j].getLocation().equals(p[x+1][y+1])&&red[j].isVisible()&&hasChess(p[x+2][y+2]).equals("none"))
                        {
                            CheckerState state = new CheckerState(red ,black);
                            state.getBlack(p[x][y]).setLocation(p[x+2][y+2]);
                            state.blackKiller = state.getBlack(p[x+2][y+2]);
                            state.getRed(p[x+1][y+1]).setVisible(false);
                            list.add(state);
                        }
                    }
                }
            }

            ArrayList temp = new ArrayList();
            for(int i=0;i<list.size();i++){
                temp.add(null);
            }
            Collections.copy(temp, list);
            ArrayList nextLevelList = new ArrayList();
            for(Iterator it = temp.iterator();it.hasNext();){
                CheckerState s = (CheckerState)it.next();
                if(Util.eat(s.blackKiller, s)){
                    nextLevelList.addAll(nextEatingStates(s.blackKiller,s,false));
                }
            }

            if(nextLevelList.size()>0)
                return nextLevelList;
            if(list.size()>0)
                return list;
        }

        return list;
    }

    private ArrayList nextStates(Chess chess,CheckerState st){
        ArrayList list = new ArrayList();
        Point pp = chess.getLocation();

        Point southEast = Util.SouthEast(pp);
        Point southWest = Util.SouthWest(pp);
        Point northWest = Util.NorthWest(pp);
        Point northEast = Util.NorthEast(pp);

        Chess red[] = st.red;
        Chess black[] = st.black;

        if(chess.getColor().equals("red")){

            if(southEast!=null && hasChess(southEast).equals("none")){
                CheckerState state = new CheckerState(red ,black);
                state.getRed(pp).setLocation(southEast);
                list.add(state);
            }
            if(southWest!=null && hasChess(southWest).equals("none")){
                CheckerState state = new CheckerState(red ,black);
                state.getRed(pp).setLocation(southWest);
                list.add(state);
            }
            if(northEast!=null && hasChess(northEast).equals("none") && chess.isKing()){
                CheckerState state = new CheckerState(red ,black);
                state.getRed(pp).setLocation(northEast);
                list.add(state);
            }
            if(northWest!=null && hasChess(northWest).equals("none") && chess.isKing()){
                CheckerState state = new CheckerState(red ,black);
                state.getRed(pp).setLocation(northWest);

                list.add(state);
            }
            if(southEast!=null){
                Point ssEast = Util.SouthEast(southEast);
                if(ssEast!=null && hasChess(southEast).equals("black") && hasChess(ssEast).equals("none")){
                    CheckerState state = new CheckerState(red,black);
                    state.getRed(pp).setLocation(ssEast);
                    state.getBlack(southEast).setVisible(false);
                    list.add(state);
                }
            }
            if(southWest!=null){
                Point ssWest = Util.SouthWest(southWest);
                if(ssWest!=null && hasChess(southWest).equals("black") && hasChess(ssWest).equals("none")){
                    CheckerState state = new CheckerState(red,black);
                    state.getRed(pp).setLocation(ssWest);
                    state.getBlack(southWest).setVisible(false);
                    list.add(state);
                }
            }
            if(northWest!=null){
                Point nnWest = Util.NorthWest(northWest);
                if(chess.isKing() && nnWest!=null && hasChess(northWest).equals("black") && hasChess(nnWest).equals("none")){
                    CheckerState state = new CheckerState(red,black);
                    state.getRed(pp).setLocation(nnWest);
                    state.getBlack(northWest).setVisible(false);
                    list.add(state);
                }
            }
            if(northEast!=null){
                Point nnEast =Util.NorthEast(northEast);
                if(chess.isKing() && nnEast!=null && hasChess(northEast).equals("black") && hasChess(nnEast).equals("none")){
                    CheckerState state = new CheckerState(red,black);
                    state.getRed(pp).setLocation(nnEast);
                    state.getBlack(northEast).setVisible(false);
                    list.add(state);
                }
            }
            return list;
        }
        else{ //black

            if(southEast!=null && chess.isKing() && hasChess(southEast).equals("none")){
                CheckerState state = new CheckerState(red ,black);
                state.getBlack(pp).setLocation(southEast);
                list.add(state);
            }
            if(southWest!=null && chess.isKing() && hasChess(southWest).equals("none")){
                CheckerState state = new CheckerState(red ,black);
                state.getBlack(pp).setLocation(southWest);
                list.add(state);
            }
            if(northEast!=null && hasChess(northEast).equals("none")){
                CheckerState state = new CheckerState(red ,black);
                state.getBlack(pp).setLocation(northEast);
                list.add(state);
            }
            if(northWest!=null && hasChess(northWest).equals("none")){
                CheckerState state = new CheckerState(red ,black);
                state.getBlack(pp).setLocation(northWest);
                list.add(state);
            }
            if(southEast!=null){
                Point ssEast = Util.SouthEast(southEast);
                if(chess.isKing() && ssEast!=null && hasChess(southEast).equals("red") && hasChess(ssEast).equals("none")){
                    CheckerState state = new CheckerState(red,black);
                    state.getBlack(pp).setLocation(ssEast);
                    state.getRed(southEast).setVisible(false);
                    list.add(state);
                }
            }
            if(southWest!=null){
                Point ssWest = Util.SouthWest(southWest);
                if(chess.isKing() && ssWest!=null && hasChess(southWest).equals("red") && hasChess(ssWest).equals("none")){
                    CheckerState state = new CheckerState(red,black);
                    state.getBlack(pp).setLocation(ssWest);
                    state.getRed(southWest).setVisible(false);
                    list.add(state);
                }
            }
            if(northWest!=null){
                Point nnWest = Util.NorthWest(northWest);
                if(nnWest!=null && hasChess(northWest).equals("red") && hasChess(nnWest).equals("none")){
                    CheckerState state = new CheckerState(red,black);
                    state.getBlack(pp).setLocation(nnWest);
                    state.getRed(northWest).setVisible(false);
                    list.add(state);
                }
            }
            if(northEast!=null){
                Point nnEast = Util.NorthEast(northEast);
                if(nnEast!=null && hasChess(northEast).equals("red") && hasChess(nnEast).equals("none")){
                    CheckerState state = new CheckerState(red,black);
                    state.getBlack(pp).setLocation(nnEast);
                    state.getRed(northEast).setVisible(false);
                    list.add(state);
                }
            }
            return list;
        }
    }
    private String hasChess(Point pp){
        if(pp==null)
            return null;

        for(int i=0;i<12;i++){
            if(red[i].isVisible()&&red[i].getLocation().equals(pp))
                return "red";
            if(black[i].isVisible()&&black[i].getLocation().equals(pp))
                return "black";
        }
        return "none";
    }

    public Chess getBlack(Point p){
        for(int i=0;i<12;i++)
            if(black[i].isVisible() && black[i].getLocation().equals(p))
                return black[i];
        return null;
    }
    public Chess getRed(Point p){
        for(int i=0;i<12;i++)
            if(red[i].isVisible() && red[i].getLocation().equals(p))
                return red[i];
        return null;
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
    public int getRedNumber(){
        int count = 0;
        for(int i=0;i<12;i++){
            if(red[i].isVisible() && !red[i].isKing())
                count++;
        }
        return count;
    }
    public int getBlackNumber(){
        int count = 0;
        for(int i=0;i<12;i++){
            if(black[i].isVisible() && !black[i].isKing())
                count++;
        }
        return count;
    }
    public int getRedKingNumber(){
        int count = 0;
        for(int i=0;i<12;i++){
            if(red[i].isVisible() && red[i].isKing())
                count++;
        }
        return count;
    }
    public int getBlackKingNumber(){
        int count = 0;
        for(int i=0;i<12;i++){
            if(black[i].isVisible() && black[i].isKing())
                count++;
        }
        return count;
    }
    public int getRedBeingKilledNumber(){
        for(int i=0;i<12;i++){
            red[i].counted = false;
        }

        int x = 0, y = 0, count = 0;
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
                    if(x-2>0&&y+2<9&&black[i].isKing() && red[j].isVisible()&&red[j].getLocation().equals(p[x-1][y+1])&&hasChess(p[x-2][y+2]).equals("none") && !red[j].counted)
                    {
                        count++;
                        red[j].counted = true;
                    }
                    if(x-2>0&&y-2>0&&red[j].getLocation().equals(p[x-1][y-1])&&red[j].isVisible()&&hasChess(p[x-2][y-2]).equals("none")&& !red[j].counted)
                    {
                        count++;
                        red[j].counted = true;
                    }
                    if(x+2<9&&y+2<9&&black[i].isKing() &&red[j].isVisible()&& red[j].getLocation().equals(p[x+1][y+1])&&y+2<9&&hasChess(p[x+2][y+2]).equals("none")&& !red[j].counted)
                    {
                        count++;
                        red[j].counted = true;
                    }
                    if(x+2<9&&y-2>0&&red[j].getLocation().equals(p[x+1][y-1])&&red[j].isVisible()&&hasChess(p[x+2][y-2]).equals("none")&& !red[j].counted)
                    {
                        count++;
                        red[j].counted = true;
                    }
                }
            }
        }
        return count;
    }

    public int getBlackBeingKilledNumber(){
        for(int i=0;i<12;i++){
            black[i].counted = false;
        }
        int x = 0, y = 0, count = 0;
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
                    if(x-2>0&&y-2>0&&red[i].isKing() && black[j].isVisible()&&black[j].getLocation().equals(p[x-1][y-1])&&hasChess(p[x-2][y-2]).equals("none")&&!black[j].counted)
                    {
                        count++;
                        black[j].counted = true;
                    }
                    if(x-2>0&&y+2<9&&black[j].getLocation().equals(p[x-1][y+1])&&black[j].isVisible()&&hasChess(p[x-2][y+2]).equals("none")&&!black[j].counted)
                    {
                        count++;
                        black[j].counted = true;
                    }
                    if(x+2<9&&y-2>0&&red[i].isKing() && black[j].isVisible()&&black[j].getLocation().equals(p[x+1][y-1])&&hasChess(p[x+2][y-2]).equals("none")&&!black[j].counted)
                    {
                        count++;
                        black[j].counted = true;
                    }
                    if(x+2<9&&y+2<9&&black[j].getLocation().equals(p[x+1][y+1])&&black[j].isVisible()&&hasChess(p[x+2][y+2]).equals("none")&&!black[j].counted)
                    {
                        count++;
                        black[j].counted = true;
                    }
                }
            }
        }
        return count;
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
    public CheckerState copy(){
        CheckerState st = new CheckerState(red,black);
        return st;

    }
}
