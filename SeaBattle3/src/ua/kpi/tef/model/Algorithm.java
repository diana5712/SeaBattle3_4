package ua.kpi.tef.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import ua.kpi.tef.controller.Controller;
import ua.kpi.tef.controller.PlayerModeController;
import ua.kpi.tef.view.View;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by diana on 08.01.17.
 */
public class Algorithm {
    private Field field1;
    private Field field2;
    public static String selectedShipSize;
    Controller parent;
    static public EventHandler filter;
    public static boolean missed;

    private static int for2SizeShip = 0;
    private static int for3SizeShip = 0;
    private static int for4SizeShip = 0;
    private String mode;
    public boolean flag = false;
    private List<Coordinate> listOfMissed = new ArrayList<>();

    public Algorithm(Canvas canvas1, Canvas canvas2,Controller contr ){
        field1 = new Field(PlayerModeController.fieldMode,canvas1);
        field2 = new Field(PlayerModeController.fieldMode,canvas2);
        selectedShipSize = "4 - палубний";
        parent = contr;
    }

    public Field getField1(){return field1;}
    public Field getField2(){return field2;}

    //взагалі відповідає за вигляд, можливо перенести в View
    public static void selectPlayerMode(String playerMode, Button button1, Button button2){
        button1.setVisible(true);
    }

    public static void selectFieldMode(String fieldMode, Label lettersLabel1, Label lettersLabel2){
        if(fieldMode.equals(View.RESPUBLICA_MODE)){
            lettersLabel1.setText(View.RESPUBLICA);
            lettersLabel2.setText(View.RESPUBLICA);
        }
        else if(fieldMode.equals(View.ALPHABET_MODE)){
            lettersLabel1.setText(View.ALPHABET);
            lettersLabel2.setText(View.ALPHABET);
        }
    }

    //по горизонтали и вертикали
    public boolean isNearShip(Ship ship,Coordinate coordinate){
        for (Coordinate coord:ship.getCoordinateList()) {
            if((((coord.getX()+30==coordinate.getX()) || (coord.getX()-30==coordinate.getX()))&& (coord.getY()==coordinate.getY()))
                ||(((coord.getY()+30==coordinate.getY()) || (coord.getY()-30==coordinate.getY())) && (coord.getX()==coordinate.getX()))
                    )
                return true;
        }
        return false;
    }

    public void recoverCanvas(Canvas scene, Field field, Button applyBut) {
        GraphicsContext context = scene.getGraphicsContext2D();

        if(applyBut.isVisible()){
            context.setFill(Color.GREY);
            for(int i = 0; i < field.getDisableSquaresList().size(); i++){
                context.fillOval(field.getDisableSquaresList().get(i).getX()+15-4,
                        field.getDisableSquaresList().get(i).getY()+15-4, 8, 8);
            }

            context.setFill(Color.GREEN);
            for (int i = 0; i < field.getShipsList().size(); i++) {
                for (int j = 0; j < field.getShipsList().get(i).getCoordinateList().size(); j++) {
                    context.fillRect(field.getShipsList().get(i).getCoordinateList().get(j).getX(),
                            field.getShipsList().get(i).getCoordinateList().get(j).getY(), 30, 30);
                }
            }
        }
        else{
            context.setFill(Color.GREEN);
            for (int i = 0; i < field.getShipsList().size(); i++) {
                for (int j = 0; j < field.getShipsList().get(i).getCoordinateList().size(); j++) {
                    context.fillRect(field.getShipsList().get(i).getCoordinateList().get(j).getX(),
                            field.getShipsList().get(i).getCoordinateList().get(j).getY(), 30, 30);
                }
            }
            if(field.getKilledShipsList().size() != 0){
                context.setFill(Color.RED);
                for (int i = 0; i < field.getKilledShipsList().size(); i++) {
                    for (int j = 0; j < field.getKilledShipsList().get(i).getCoordinateList().size(); j++) {
                        context.fillRect(field.getKilledShipsList().get(i).getCoordinateList().get(j).getX(),
                                field.getKilledShipsList().get(i).getCoordinateList().get(j).getY(), 30, 30);
                    }
                }
            }
        }
    }

    public void returnToGame(Canvas scene, Field field){
        GraphicsContext context = scene.getGraphicsContext2D();

        context.setFill(Color.GREY);
        /*if(listOfMissed.size() != 0){
            for(int i = 0; i < listOfMissed.size(); i++){
                context.fillOval(listOfMissed.get(i).getX()+15-4,
                        listOfMissed.get(i).getY()+15-4, 8, 8);
            }
        }*/

        for(int i = 0; i < field.getDisableSquaresList().size(); i++){
            context.fillOval(field.getDisableSquaresList().get(i).getX()+15-4,
                    field.getDisableSquaresList().get(i).getY()+15-4, 8, 8);
        }
        context.setFill(Color.RED);
        for (int i = 0; i < field.getKilledShipsList().size(); i++) {
            for (int j = 0; j < field.getKilledShipsList().get(i).getCoordinateList().size(); j++) {
                context.fillRect(field.getKilledShipsList().get(i).getCoordinateList().get(j).getX(),
                        field.getKilledShipsList().get(i).getCoordinateList().get(j).getY(), 30, 30);
            }
        }
    }

    public void mouseClick(Canvas scene,Field field, Button applyBut, TextArea textArea){
        if(applyBut.isVisible() == true){
            filter =new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
//                System.out.println("LOOK HERE-----------------------------------");
//                System.out.println(event.getX() + ", " + event.getY());
                        int x = (int) (event.getX() / 30) * 30;
                        int y = (int) (event.getY() / 30) * 30;
                        Coordinate coordinate = new Coordinate(x, y);//создана координата клика мышки
                        System.out.println("Координата принадлежит "+field.belongDisableSquaresList(coordinate));
                        if(!field.belongDisableSquaresList(coordinate)) {
                            if (selectedShipSize.equals("1 - палубний")) {
                                Ship ship = new Ship(new Random().nextInt(), Color.AQUA);
                                ship.getCoordinateList().add(coordinate);
                                field.getShipsList().add(ship);
                                GraphicsContext context = scene.getGraphicsContext2D();
                                context.setFill(Color.GREEN);
                                context.fillRect(x, y, 30, 30); // рисуем прямоугльник 30x30px с левым верним углом в точке (x; y)
                                checkRestrict(ship, field,context,true);
                                System.out.println("1 - палубний додано");
                                parent.setShipSize1Amount(parent.getShipSize1Amount()-1);
                                if( parent.getShipSize1Amount()==0){
                                    selectedShipSize = "4 - палубний";
                                    parent.setCheckShipSize4(true);
                                    deleteFilter(scene);
                                }
                            }else if(selectedShipSize.equals("2 - палубний")) {
                                System.out.println("Я хоча б зайшла...");
                                if(for2SizeShip == 0){
                                    Ship ship = new Ship(new Random().nextInt(), Color.AQUA);
                                    ship.getCoordinateList().add(coordinate);
                                    field.getShipsList().add(ship);
                                    field.getDisableSquaresList().add(coordinate);
                                    GraphicsContext context = scene.getGraphicsContext2D();
                                    context.setFill(Color.GREEN);
                                    context.fillRect(x, y, 30, 30); // рисуем прямоугльник 30x30px с левым верним углом в точке (x; y)
                                    for2SizeShip++;
                                    System.out.println("for2SizeShip " +for2SizeShip);
                                }else{
                                    if(isNearShip(field.getShipsList().get(field.getShipsList().size()-1),coordinate)) {
                                        field.getShipsList().get(field.getShipsList().size() - 1).getCoordinateList().add(coordinate);

                                        GraphicsContext context = scene.getGraphicsContext2D();
                                        context.setFill(Color.GREEN);
                                        context.fillRect(x, y, 30, 30); // рисуем прямоугльник 30x30px с левым верним углом в точке (x; y)

                                        for2SizeShip=0;
                                        System.out.println("for2SizeShip " + for2SizeShip);
                                        System.out.println("2 - палубний додано");
                                        parent.setShipSize2Amount(parent.getShipSize2Amount() - 1);
                                        checkRestrict(field.getShipsList().get(field.getShipsList().size() - 1), field, context,true);
                                    }else
                                        System.out.println("ERROR");
                                    if( parent.getShipSize2Amount()==0){
                                        selectedShipSize = "1 - палубний";
                                        parent.setCheckShipSize1(true);
                                    }
                                }
                            }else if(selectedShipSize.equals("3 - палубний")) {
                                System.out.println("Я хоча б зайшла(2)...");
                                if(for3SizeShip == 0){
                                    Ship ship = new Ship(new Random().nextInt(), Color.AQUA);
                                    ship.getCoordinateList().add(coordinate);
                                    field.getShipsList().add(ship);
                                    field.getDisableSquaresList().add(coordinate);
                                    GraphicsContext context = scene.getGraphicsContext2D();
                                    context.setFill(Color.GREEN);
                                    context.fillRect(x, y, 30, 30); // рисуем прямоугльник 30x30px с левым верним углом в точке (x; y)
                                    for3SizeShip++;
                                    System.out.println("for3SizeShip " +for3SizeShip);
                                }else{
                                    if(for3SizeShip  == 1) {
                                        if (isNearShip(field.getShipsList().get(field.getShipsList().size() - 1), coordinate)) {
                                            field.getShipsList().get(field.getShipsList().size() - 1).getCoordinateList().add(coordinate);
                                            field.getDisableSquaresList().add(coordinate);
                                            GraphicsContext context = scene.getGraphicsContext2D();
                                            context.setFill(Color.GREEN);
                                            context.fillRect(x, y, 30, 30); // рисуем прямоугльник 30x30px с левым верним углом в точке (x; y)
                                            for3SizeShip++;
                                            System.out.println("for3SizeShip " + for3SizeShip);
                                        } else
                                            System.out.println("ERROR");
                                    }else if (for3SizeShip  == 2){
                                        if (isNearShip(field.getShipsList().get(field.getShipsList().size() - 1), coordinate)) {
                                            field.getShipsList().get(field.getShipsList().size() - 1).getCoordinateList().add(coordinate);

                                            GraphicsContext context = scene.getGraphicsContext2D();
                                            context.setFill(Color.GREEN);
                                            context.fillRect(x, y, 30, 30); // рисуем прямоугльник 30x30px с левым верним углом в точке (x; y)

                                            for3SizeShip = 0;
                                            System.out.println("for3SizeShip " + for3SizeShip);
                                            System.out.println("3 - палубний додано");
                                            parent.setShipSize3Amount(parent.getShipSize3Amount() - 1);
                                            checkRestrict(field.getShipsList().get(field.getShipsList().size() - 1), field, context,true);
                                        } else
                                            System.out.println("ERROR");

                                        if (parent.getShipSize3Amount() == 0) {
                                            selectedShipSize = "2 - палубний";
                                            parent.setCheckShipSize2(true);
                                        }
                                    }
                                }
                            }else if(selectedShipSize.equals("4 - палубний")) {
                                System.out.println("Я хоча б зайшла(3)...");
                                if(for4SizeShip == 0){
                                    Ship ship = new Ship(new Random().nextInt(), Color.AQUA);
                                    ship.getCoordinateList().add(coordinate);
                                    field.getShipsList().add(ship);
                                    field.getDisableSquaresList().add(coordinate);
                                    GraphicsContext context = scene.getGraphicsContext2D();
                                    context.setFill(Color.GREEN);
                                    context.fillRect(x, y, 30, 30); // рисуем прямоугльник 30x30px с левым верним углом в точке (x; y)
                                    for4SizeShip++;
                                    System.out.println("for4SizeShip " +for4SizeShip);
                                }else{
                                    if(for4SizeShip  == 1 || for4SizeShip == 2) {
                                        if (isNearShip(field.getShipsList().get(field.getShipsList().size() - 1), coordinate)) {
                                            field.getShipsList().get(field.getShipsList().size() - 1).getCoordinateList().add(coordinate);
                                            field.getDisableSquaresList().add(coordinate);

                                            GraphicsContext context = scene.getGraphicsContext2D();
                                            context.setFill(Color.GREEN);
                                            context.fillRect(x, y, 30, 30); // рисуем прямоугльник 30x30px с левым верним углом в точке (x; y)

                                            for4SizeShip++;
                                            System.out.println("for4SizeShip " + for4SizeShip);
                                        } else
                                            System.out.println("ERROR");

                                    }else if (for4SizeShip  == 3){
                                        if (isNearShip(field.getShipsList().get(field.getShipsList().size() - 1), coordinate)) {
                                            field.getShipsList().get(field.getShipsList().size() - 1).getCoordinateList().add(coordinate);

                                            GraphicsContext context = scene.getGraphicsContext2D();
                                            context.setFill(Color.GREEN);
                                            context.fillRect(x, y, 30, 30); // рисуем прямоугльник 30x30px с левым верним углом в точке (x; y)

                                            for4SizeShip = 0;
                                            System.out.println("for4SizeShip " + for4SizeShip);
                                            System.out.println("4 - палубний додано");
                                            parent.setShipSize4Amount(parent.getShipSize4Amount() - 1);
                                            checkRestrict(field.getShipsList().get(field.getShipsList().size() - 1), field, context,true);
                                        } else
                                            System.out.println("ERROR");

                                        System.out.println("LOOOOOOOOOOOOOOOOOOOOOOOOOOOOOK "+ parent.getShipSize4Amount());
                                        if (parent.getShipSize4Amount() == 0) {
                                            selectedShipSize = "3 - палубний";
                                            parent.setCheckShipSize3(true);
                                            System.out.println("selectedShipSize "+selectedShipSize);
//                                            deleteFilter(scene);
                                        }
                                    }
                                }
                            }
                        }
                        System.out.println("all coord");
                        for (Ship temp : field.getShipsList()) {
                            System.out.println(temp.toString());
                        }
                    }
                }
            };
        }
        else{
            filter = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        GraphicsContext context = scene.getGraphicsContext2D();
                        int x = (int) (event.getX() / 30) * 30;
                        int y = (int) (event.getY() / 30) * 30;
                        Coordinate coordinate = new Coordinate(x, y);//создана координата клика мышки
                        System.out.println("coord "+ coordinate.getX()+" "+coordinate.getY());
                        if(!field.belongKilledShipList(coordinate) && !field.belongDisableSquaresList(coordinate)) {
                            po:
                            for (int i = 0; i < field.getShipsList().size(); i++) {
                                Ship _ship = field.getShipsList().get(i);
                                for (int j = 0; j < _ship.getCoordinateList().size(); j++) {
                                    Coordinate _coord = _ship.getCoordinateList().get(j);
                                    if (_coord.getX() == coordinate.getX() && _coord.getY() == coordinate.getY()) {//якщо на місці координати є корабель
                                        context.setFill(Color.RED);
                                        context.fillRect(x, y, 30, 30);
                                        for (int k = 0; k < field.getKilledShipsList().size(); k++) {
                                            Ship killedShip = field.getKilledShipsList().get(k);
                                            if (isNearShip(killedShip, coordinate)) {
                                                killedShip.getCoordinateList().add(coordinate);
                                                System.out.println("wondered or deaded");
                                                flag = false;
                                                if (isNearShipNotBelongKilledArray(killedShip, coordinate,field)) {
                                                    System.out.println("wonded");
                                                    break po;
                                                }
                                                checkRestrict(killedShip,field,context,true);
                                                System.out.println("deaded");
                                                field.setKilled(field.getKilled() + 1);
                                                break po;
                                            }
                                        }
                                        Ship ship = new Ship(new Random().nextInt(), Color.AQUA);
                                        ship.getCoordinateList().add(coordinate);
                                        field.getKilledShipsList().add(ship);
                                        System.out.println("created");
                                        flag = false;
                                        if (isNearShipNotBelongKilledArray(ship, coordinate,field)) {
                                            System.out.println("wonded");
                                            break po;
                                        }
                                        checkRestrict(ship,field,context,true);
                                        System.out.println("deaded");
                                        field.setKilled(field.getKilled() + 1);
                                        break po;
                                    } else {
                                        context.setFill(Color.GRAY);
                                        context.fillOval(x + 15 - 4, y + 15 - 4, 8, 8);
                                        if(!PlayerModeController.playerMode.equals(View.COMPUTER_MODE)) {
                                            field.getDisableSquaresList().add(new Coordinate(x, y));
                                        }
                                        flag = true;
                                    }
                                }
                            }
                            if(field1.getKilled() == 10 || field2.getKilled() == 10){
                                deleteFilter(scene);
                                flag = false;
                                textArea.setText(textArea.getText() + "\nGAME OVER!");
                                if(field1.getKilled() == 10){
                                    textArea.setText(textArea.getText() + "\nPlayer 2 won!");
                                    parent.setWinner("Player 2");
                                }
                                else if(field2.getKilled() == 10) {
                                    textArea.setText(textArea.getText() + "\nPlayer 1 won!");
                                    parent.setWinner("Player 1");
                                }
                            }
                        }else{
                            System.out.println("Точка уже есть");
                        }

                        if(PlayerModeController.playerMode.equals(View.COMPUTER_MODE)){
                            if(field1.getKilled() == 10 || field2.getKilled() == 10){
                                deleteFilter(scene);
                                flag = false;
                                textArea.setText(textArea.getText() + "\nGAME OVER!");
                                if(field1.getKilled() == 10){
                                    textArea.setText(textArea.getText() + "\nPlayer 2 won!");
                                    parent.setWinner("Player 2");
                                }
                                else if(field2.getKilled() == 10) {
                                    textArea.setText(textArea.getText() + "\nPlayer 1 won!");
                                    parent.setWinner("Player 1");
                                }
                            }
                        }
                        System.out.println("GOOD");
                        if(flag){
                            flag = false;
                            deleteFilter(scene);
                            if(field2.getCanvas().equals(scene)) {
                                parent.getHintCanvas1().setVisible(false);
                                parent.getHintCanvas2().setVisible(true);
                                if(PlayerModeController.playerMode.equals(View.COMPUTER_MODE)){
                                    parent.getHintCanvas1().setVisible(true);
                                    parent.getHintCanvas2().setVisible(false);
                                    parent.computerShot();
                                    System.out.println("метод компа");
                                }else {
                                    mouseClick(field1.getCanvas(), field1, applyBut, textArea);
                                    System.out.println("ставим 1");
                                    textArea.setText(textArea.getText() + "\nPlayer1's move");
                                }
                            }
                            else {
                                parent.getHintCanvas1().setVisible(true);
                                parent.getHintCanvas2().setVisible(false);
                                mouseClick(field2.getCanvas(), field2, applyBut, textArea);
                                System.out.println("ставим 2");
                                textArea.setText(textArea.getText() + "\nPlayer2's move");
                            }
                        }
                    }
                }
            };
        }
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, filter);
    }

    public void deleteFilter(Canvas scene){
        scene.removeEventFilter(MouseEvent.MOUSE_CLICKED, filter);
    }

    public void checkRestrict(Ship ship,Field field,GraphicsContext context,boolean useContext){
        for (Coordinate coord:ship.getCoordinateList()) {
            field.getDisableSquaresList().add(new Coordinate(coord.getX(),coord.getY()));

            setRestrict(new Coordinate(coord.getX()+30,coord.getY()+30),ship,field, context,useContext);
            setRestrict(new Coordinate(coord.getX()+30,coord.getY()),ship,field, context,useContext);
            setRestrict(new Coordinate(coord.getX()+30,coord.getY()-30),ship,field, context,useContext);
            setRestrict(new Coordinate(coord.getX(),coord.getY()-30),ship,field, context,useContext);
            setRestrict(new Coordinate(coord.getX()-30,coord.getY()-30),ship,field, context,useContext);
            setRestrict(new Coordinate(coord.getX()-30,coord.getY()),ship,field, context,useContext);
            setRestrict(new Coordinate(coord.getX()-30,coord.getY()+30),ship,field, context,useContext);
            setRestrict(new Coordinate(coord.getX(),coord.getY()+30),ship,field, context,useContext);
        }
    }

    private void setRestrict(Coordinate temp, Ship ship,Field field,GraphicsContext context,boolean useContext){
        if(!ship.belongToShip(temp)){
            field.getDisableSquaresList().add(temp);
            if(useContext==true) {
                context.setFill(Color.GREY);
                context.fillOval(temp.getX() + 15 - 4, temp.getY() + 15 - 4, 8, 8);
            }
        }else{
            System.out.println(temp.getX()+" "+temp.getY());
        }
    }

    public boolean isNearShipNotBelongKilledArray(Ship killedShip,Coordinate coordinate,Field field){
        for (Ship _ship: field.getShipsList()) {
            for (Coordinate coord : _ship.getCoordinateList()) {
                if (((((coord.getX() + 30 == coordinate.getX()) || (coord.getX() - 30 == coordinate.getX())) && (coord.getY() == coordinate.getY()))
                        || (((coord.getY() + 30 == coordinate.getY()) || (coord.getY() - 30 == coordinate.getY())) && (coord.getX() == coordinate.getX()))
                ) && !killedShip.belongToShip(coord))
                    return true;
            }
        }
        return false;
    }

}
