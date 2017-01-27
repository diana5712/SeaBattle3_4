package ua.kpi.tef.model;

/**
 * Created by diana on 08.01.17.
 */
public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){return x;}

    public int getY() {return y;}

    public void setX(int x){this.x = x;}

    public void setY(int y){this.y = y;}

    @Override
    public String toString(){
        return "x:"+this.x+", y:"+this.y;
    }
    public boolean isEqual(Coordinate coord){
        if((this.x == coord.getX()) && (this.y == coord.getY()))
            return true;
        return false;
    }
}
