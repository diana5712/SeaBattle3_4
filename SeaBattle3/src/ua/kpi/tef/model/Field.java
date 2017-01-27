package ua.kpi.tef.model;


import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diana on 08.01.17.
 */
public class Field {
    private String mode;
    private Canvas canvas;
    private List<Ship> shipsList;
    private List<Ship> killedShipsList;
    private List<Coordinate> disableSquaresList;
    private int killed;

    public int getKilled() {
        return killed;
    }

    public void setKilled(int killed) {
        this.killed = killed;
    }

    public Field(String mode, Canvas canvas) {
        this.mode = mode;
        this.canvas = canvas;
        shipsList = new ArrayList<>();
        killedShipsList = new ArrayList<>();
        disableSquaresList = new ArrayList<>();
    }

    public String getMode() {
        return mode;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public List<Ship> getShipsList() {
        return shipsList;
    }

    public List<Ship> getKilledShipsList() {
        return killedShipsList;
    }

    public List<Coordinate> getDisableSquaresList() {
        return disableSquaresList;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setShipsList(List<Ship> shipsList) {
        this.shipsList = shipsList;
    }

    public void setKilledShipsList(List<Ship> killedShipsList) {
        this.killedShipsList = killedShipsList;
    }

    public void setDisableSquaresList(List<Coordinate> disableSquaresList) {
        this.disableSquaresList = disableSquaresList;
    }

    public boolean belongDisableSquaresList(Coordinate coord){
        for (Coordinate c:getDisableSquaresList()) {
            if(c.isEqual(coord))
                return true;
        }
        return false;
    }

    public boolean belongKilledShipList(Coordinate coord){
        for (Ship _ship:getKilledShipsList()) {
            for (Coordinate _coord:_ship.getCoordinateList()) {
                if(_coord.isEqual(coord))
                    return true;
            }
        }
        return false;
    }

    public boolean belongShipList(Coordinate coord){
        for (Ship _ship:getShipsList()) {
            for (Coordinate _coord:_ship.getCoordinateList()) {
                if(_coord.isEqual(coord))
                    return true;
            }
        }
        return false;
    }
}
