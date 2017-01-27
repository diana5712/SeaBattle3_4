package ua.kpi.tef.model;


import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diana on 08.01.17.
 */
public class Ship {
    private int idShip;
    private Color color;
    private List<Coordinate> coordinateList;


    public Ship(int idShip, Color color) {
        this.idShip = idShip;
        this.color = color;
        coordinateList = new ArrayList<Coordinate>();
    }

    public int getIdShip() {
        return idShip;
    }

    public Color getColor() {
        return color;
    }

    public List<Coordinate> getCoordinateList() {
        return coordinateList;
    }

    public void setIdShip(int idShip) {
        this.idShip = idShip;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setCoordinateList(List<Coordinate> coordinateList) {
        this.coordinateList = coordinateList;
    }

    @Override
    public String toString() {
        String coords = "";
        for (Coordinate temp:coordinateList) {
            coords += temp.toString() + "\n";
        }
        return "Ship{" +
                "idShip=" + idShip +
                ", color=" + color +
                ", coordinateList:\n" + coords+ '}';
    }

    public boolean belongToShip(Coordinate coordinate){
        for (Coordinate coord:coordinateList) {
            if(coord.isEqual(coordinate))
                return true;
        }
        return false;
    }
}
