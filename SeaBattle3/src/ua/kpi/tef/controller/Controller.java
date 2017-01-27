package ua.kpi.tef.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.kpi.tef.model.Algorithm;
import ua.kpi.tef.model.Coordinate;
import ua.kpi.tef.model.Field;
import ua.kpi.tef.model.Ship;
import ua.kpi.tef.view.View;

import java.io.IOException;
import java.util.Random;

public class Controller {
    @FXML
    Canvas canvas1;
    @FXML
    Canvas canvas2;
    @FXML
    Label lettersLabel1;
    @FXML
    Label lettersLabel2;
    @FXML
    Label numbersLabel1;
    @FXML
    Label numbersLabel2;
    @FXML
    Button hintCanvas1;
    @FXML
    Button hintCanvas2;
    @FXML
    TextArea messageArea;

    public void setCheckShipSize1(boolean checkShipSize) {
        this.checkShipSize1.setSelected(checkShipSize);
    }

    public void setCheckShipSize2(boolean checkShipSize) {
        this.checkShipSize2.setSelected(checkShipSize);
    }

    public void setCheckShipSize3(boolean checkShipSize) {
        this.checkShipSize3.setSelected(checkShipSize);
    }

    public void setCheckShipSize4(boolean checkShipSize) {
        this.checkShipSize4.setSelected(checkShipSize);
    }

    @FXML
    RadioButton checkShipSize1;
    @FXML
    RadioButton checkShipSize2;
    @FXML
    RadioButton checkShipSize3;
    @FXML
    RadioButton checkShipSize4;

    @FXML
    Label shipSize1Amount;
    @FXML
    Label shipSize2Amount;
    @FXML
    Label shipSize3Amount;
    @FXML
    Label shipSize4Amount;
    @FXML
    Button applyButton;

    public int getShipSize1Amount() {
        return Integer.parseInt(shipSize1Amount.getText());
    }

    public void setShipSize1Amount(int shipSize1Amount) {
        this.shipSize1Amount.setText(shipSize1Amount+"");
    }

    public int getShipSize2Amount() {
        return Integer.parseInt(shipSize2Amount.getText());
    }

    public void setShipSize2Amount(int shipSize2Amount) {
        this.shipSize2Amount.setText(shipSize2Amount+"");
    }

    public int getShipSize3Amount() {
        return Integer.parseInt(shipSize3Amount.getText());
    }

    public void setShipSize3Amount(int shipSize3Amount) {
        this.shipSize3Amount.setText(shipSize3Amount+"");
    }

    public int getShipSize4Amount() {
        return Integer.parseInt(shipSize4Amount.getText());
    }

    public void setShipSize4Amount(int shipSize4Amount) {
        this.shipSize4Amount.setText(shipSize4Amount+"");
    }

    public Button getHintCanvas2() {
        return hintCanvas2;
    }

    public Button getHintCanvas1() {
        return hintCanvas1;
    }


    private Stage mainStage;

    //player mode form
    private Parent fxmlPlayerMode;
    private Parent fxmlWinnerMode;
    private Stage PlayerModeStage;
    private Stage winnerStage;
    int c = 0;

    ToggleGroup buttonGroup = new ToggleGroup();
    Algorithm algorithm = new Algorithm(canvas1,canvas2,this);
    WinnerController win_contr;
    int counterViewBut1 = 2;
    int counterViewBut2 = 2;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private void initLoader() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("../fxml/PlayerMode.fxml"));
            Parent fxmlPlayerMode = (Parent) fxmlLoader.load();
            PlayerModeController contr = fxmlLoader.getController();
            contr.setLabels(lettersLabel1,lettersLabel2,hintCanvas1,hintCanvas2);
            PlayerModeStage = new Stage();
            PlayerModeStage.setTitle("Choose mode");
            PlayerModeStage.setResizable(false);
            PlayerModeStage.initOwner(mainStage);
            PlayerModeStage.initModality(Modality.APPLICATION_MODAL);
            PlayerModeStage.setScene(new Scene(fxmlPlayerMode, PlayerModeStage.getWidth(), PlayerModeStage.getHeight()));
            PlayerModeStage.hide();

            FXMLLoader fxmlLoader2 = new FXMLLoader(
                    getClass().getResource("../fxml/Winner.fxml"));
            Parent fxmlWinnerMode = (Parent) fxmlLoader2.load();
            win_contr= fxmlLoader2.getController();
            winnerStage = new Stage();
            winnerStage.setTitle("Congratulations!");
            winnerStage.setResizable(false);
            winnerStage.initOwner(mainStage);
            winnerStage.initModality(Modality.APPLICATION_MODAL);
            winnerStage.setScene(new Scene(fxmlWinnerMode, winnerStage.getWidth(), winnerStage.getHeight()));
            winnerStage.hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(){
        initLoader();
        Algorithm.selectFieldMode(PlayerModeController.fieldMode,lettersLabel1,lettersLabel2);
        View.drawField(canvas1.getGraphicsContext2D());
        View.drawField(canvas2.getGraphicsContext2D());

        checkShipSize1.setToggleGroup(buttonGroup);
        checkShipSize2.setToggleGroup(buttonGroup);
        checkShipSize3.setToggleGroup(buttonGroup);
        checkShipSize4.setToggleGroup(buttonGroup);

        hintCanvas1.setVisible(false);
        hintCanvas2.setVisible(false);
        applyButton.setVisible(false);
    }

    @FXML
    public void startGame(){
        checkShipSize4.setSelected(true);
        hintCanvas1.setVisible(true);
        hintCanvas2.setVisible(false);
        applyButton.setVisible(true);
        hideShips(canvas1);
        hideShips(canvas2);
        setShipsAmount();
        if(c != 0){
            canvas1.removeEventFilter(MouseEvent.MOUSE_CLICKED, Algorithm.filter);
            canvas2.removeEventFilter(MouseEvent.MOUSE_CLICKED, Algorithm.filter);
            algorithm.flag = false;
        }
        c++;
        algorithm  = new Algorithm(canvas1, canvas2, this);
        PlayerModeStage.show();
        messageArea.setText(messageArea.getText() + "\nPlayer 1: set your ships, please!");

        algorithm.mouseClick(canvas1,algorithm.getField1(), applyButton, messageArea);
    }

    private void setShipsAmount(){
        shipSize1Amount.setText("4");
        shipSize2Amount.setText("3");
        shipSize3Amount.setText("2");
        shipSize4Amount.setText("1");
    }

    public void hideShips(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 300, 300);
        View.drawField(canvas.getGraphicsContext2D());
    }

    @FXML
    public void viewShips1(){
        if(counterViewBut1 % 2 == 0) {
            hideShips(canvas1);
            algorithm.recoverCanvas(canvas1, algorithm.getField1(), applyButton);
            if(!applyButton.isVisible()) {
                canvas2.setDisable(true);
            }
            //System.out.println(counterViewBut1);
            counterViewBut1++;
        }
        else{
            hideShips(canvas1);
            if(!applyButton.isVisible()){
                canvas2.setDisable(false);
                algorithm.returnToGame(canvas1, algorithm.getField1());
            }
            //System.out.println(counterViewBut1);
            counterViewBut1++;
        }

    }


    @FXML
    public void viewShips2(){
        if(counterViewBut2 % 2 == 0) {
            hideShips(canvas2);
            algorithm.recoverCanvas(canvas2, algorithm.getField2(), applyButton);
            if(!applyButton.isVisible()) {
                canvas1.setDisable(true);
            }
            //System.out.println(counterViewBut2);
            counterViewBut2++;
        }
        else{
            hideShips(canvas2);
            if(!applyButton.isVisible()) {
                canvas1.setDisable(false);
                algorithm.returnToGame(canvas2, algorithm.getField2());
            }
            //System.out.println(counterViewBut2);
            counterViewBut2++;
        }
    }

    @FXML
    public void applyShipLocation(){
        if(algorithm.getField1().getShipsList().size() <= 10 && algorithm.getField2().getShipsList().size()==0) {
            if(locateShips(algorithm.getField1())){
                hideShips(canvas1);
                getHintCanvas1().setVisible(false);
                getHintCanvas2().setVisible(true);
                if(PlayerModeController.playerMode.equals(View.COMPUTER_MODE)) {
                    applyButton.setVisible(false);
                    getHintCanvas2().setVisible(false);
                    setRandomShips();
                    setCheckShipSize4(false);
                }
                else{
                    setShipsAmount();
                    messageArea.setText(messageArea.getText()+"\nPlayer 2: set your ships, please!");
                    algorithm.mouseClick(algorithm.getField2().getCanvas(), algorithm.getField2(), applyButton, messageArea);
                }
            }
        }
        else if (algorithm.getField2().getShipsList().size() != 0) {
            if(locateShips(algorithm.getField2())) {
                hideShips(canvas2);
                setCheckShipSize4(false);
                applyButton.setVisible(false);
            }
        }
        if(!applyButton.isVisible()){
            algorithm.getField1().getDisableSquaresList().removeAll(algorithm.getField1().getDisableSquaresList());
            algorithm.getField2().getDisableSquaresList().removeAll(algorithm.getField2().getDisableSquaresList());
            startShootGame();
        }
    }

    public boolean locateShips(Field field){
        if(shipSize1Amount.getText().equals("0") &&
                shipSize2Amount.getText().equals("0") &&
                shipSize3Amount.getText().equals("0") &&
                shipSize4Amount.getText().equals("0")) {
            messageArea.setText(messageArea.getText() + "\nShips located!");
            return true;
        } else
            messageArea.setText(messageArea.getText()+"\nLess than 10 ships!");
        return false;
    }

    public void startShootGame(){
        algorithm.mouseClick(algorithm.getField2().getCanvas(), algorithm.getField2(), applyButton, messageArea);
        getHintCanvas1().setVisible(true);
        getHintCanvas2().setVisible(false);
    }

    public void setRandomShips(){
        GraphicsContext context = algorithm.getField2().getCanvas().getGraphicsContext2D();
        Random random = new Random();
        Field field = algorithm.getField2();
        Ship ship;
        switch (random.nextInt(2)){
            case 1:
                //4
                ship = new Ship(1, Color.AQUA);
                ship.getCoordinateList().add(new Coordinate(0,0));
                ship.getCoordinateList().add(new Coordinate(0,30));
                ship.getCoordinateList().add(new Coordinate(0,60));
                ship.getCoordinateList().add(new Coordinate(0,90));
                setAutoShip(field,ship);
                //3.1
                ship = new Ship(1, Color.AQUA);
                ship.getCoordinateList().add(new Coordinate(60,0));
                ship.getCoordinateList().add(new Coordinate(60,30));
                ship.getCoordinateList().add(new Coordinate(60,60));
                setAutoShip(field,ship);
                //3.2
                ship = new Ship(1, Color.AQUA);
                ship.getCoordinateList().add(new Coordinate(60,120));
                ship.getCoordinateList().add(new Coordinate(60,150));
                ship.getCoordinateList().add(new Coordinate(60,180));
                setAutoShip(field,ship);
                //2.1
                ship = new Ship(1, Color.AQUA);
                ship.getCoordinateList().add(new Coordinate(0,150));
                ship.getCoordinateList().add(new Coordinate(0,180));
                setAutoShip(field,ship);
                //2.2
                ship = new Ship(1, Color.AQUA);
                ship.getCoordinateList().add(new Coordinate(0,240));
                ship.getCoordinateList().add(new Coordinate(0,270));
                setAutoShip(field,ship);
                //2.3
                ship = new Ship(1, Color.AQUA);
                ship.getCoordinateList().add(new Coordinate(60,240));
                ship.getCoordinateList().add(new Coordinate(60,270));
                setAutoShip(field,ship);
                for(int i = 0; i <4;i++){
                    Coordinate coord = new Coordinate((random.nextInt(6)+4)*30,random.nextInt(10)*30);
                    if(field.belongDisableSquaresList(coord))
                        i--;
                    else {
                        ship = new Ship(1, Color.AQUA);
                        ship.getCoordinateList().add(coord);
                        setAutoShip(field, ship);
                    }
                }
                break;
            case 2:
                //4
                ship = new Ship(1, Color.AQUA);
                ship.getCoordinateList().add(new Coordinate(270,0));
                ship.getCoordinateList().add(new Coordinate(240,0));
                ship.getCoordinateList().add(new Coordinate(210,0));
                ship.getCoordinateList().add(new Coordinate(180,0));
                setAutoShip(field,ship);
                //3.1
                ship = new Ship(1, Color.AQUA);
                ship.getCoordinateList().add(new Coordinate(210,270));
                ship.getCoordinateList().add(new Coordinate(240,270));
                ship.getCoordinateList().add(new Coordinate(270,270));
                setAutoShip(field,ship);
                //3.2
                ship = new Ship(1, Color.AQUA);
                ship.getCoordinateList().add(new Coordinate(0,270));
                ship.getCoordinateList().add(new Coordinate(30,270));
                ship.getCoordinateList().add(new Coordinate(60,270));
                setAutoShip(field,ship);
                //2.1
                ship = new Ship(1, Color.AQUA);
                ship.getCoordinateList().add(new Coordinate(0,0));
                ship.getCoordinateList().add(new Coordinate(30,0));
                setAutoShip(field,ship);
                //2.2
                ship = new Ship(1, Color.AQUA);
                ship.getCoordinateList().add(new Coordinate(90,0));
                ship.getCoordinateList().add(new Coordinate(120,0));
                setAutoShip(field,ship);
                //2.3
                ship = new Ship(1, Color.AQUA);
                ship.getCoordinateList().add(new Coordinate(120,270));
                ship.getCoordinateList().add(new Coordinate(150,270));
                setAutoShip(field,ship);
                for(int i = 0; i <4;i++){
                    Coordinate coord = new Coordinate(random.nextInt(10)*30,(random.nextInt(7)+2)*30);
                    if(field.belongDisableSquaresList(coord))
                        i--;
                    else {
                        ship = new Ship(1, Color.AQUA);
                        ship.getCoordinateList().add(coord);
                        setAutoShip(field, ship);
                        algorithm.checkRestrict(ship,field,context, false);
                    }
                }
                break;
            default:
                //4
                ship = new Ship(1, Color.AQUA);
                ship.getCoordinateList().add(new Coordinate(0,0));
                ship.getCoordinateList().add(new Coordinate(0,30));
                ship.getCoordinateList().add(new Coordinate(0,60));
                ship.getCoordinateList().add(new Coordinate(0,90));
                setAutoShip(field,ship);
                //3.1
                ship = new Ship(1, Color.AQUA);
                ship.getCoordinateList().add(new Coordinate(0,150));
                ship.getCoordinateList().add(new Coordinate(0,180));
                ship.getCoordinateList().add(new Coordinate(0,210));
                setAutoShip(field,ship);
                //3.2
                ship = new Ship(1, Color.AQUA);
                ship.getCoordinateList().add(new Coordinate(60,0));
                ship.getCoordinateList().add(new Coordinate(90,0));
                ship.getCoordinateList().add(new Coordinate(120,0));
                setAutoShip(field,ship);
                //2.1
                ship = new Ship(1, Color.AQUA);
                ship.getCoordinateList().add(new Coordinate(0,270));
                ship.getCoordinateList().add(new Coordinate(30,270));
                setAutoShip(field,ship);
                //2.2
                ship = new Ship(1, Color.AQUA);
                ship.getCoordinateList().add(new Coordinate(180,0));
                ship.getCoordinateList().add(new Coordinate(210,0));
                setAutoShip(field,ship);
                //2.3
                ship = new Ship(1, Color.AQUA);
                ship.getCoordinateList().add(new Coordinate(270,0));
                ship.getCoordinateList().add(new Coordinate(270,30));
                setAutoShip(field,ship);
                for(int i = 0; i <4;i++){
                    Coordinate coord = new Coordinate((random.nextInt(7)+3)*30,(random.nextInt(7)+3)*30);
                    if(field.belongDisableSquaresList(coord))
                        i--;
                    else {
                        ship = new Ship(1, Color.AQUA);
                        ship.getCoordinateList().add(coord);
                        setAutoShip(field, ship);
                        algorithm.checkRestrict(ship,field,context, false);
                    }
                }
                break;
        }
    }

    public void computerShot(){
        boolean flag = true;
        Random rand = new Random();
        Coordinate coor = new Coordinate(rand.nextInt(10) * 30, rand.nextInt(10) * 30);
        while (algorithm.getField1().belongDisableSquaresList(coor))
            coor = new Coordinate(rand.nextInt(10) * 30, rand.nextInt(10) * 30);
        po:
        while(flag) {
            System.out.println("inside flag = true");
            po1:
            if (algorithm.getField1().belongShipList(coor)) {
                System.out.println("Попали "+coor.getX()+" "+coor.getY());
                po3:
                for (int i = 0; i < algorithm.getField1().getShipsList().size(); i++) {
                    System.out.println("проходимся по всіх кораблях "+i);
                    Ship _ship = algorithm.getField1().getShipsList().get(i);
                    po2:
                    for (int j = 0; j < _ship.getCoordinateList().size(); j++) {
                        System.out.println("проходимось по всых координатах корабля "+ j);
                        Coordinate _coord = _ship.getCoordinateList().get(j);
                        po5:
                        if (_coord.getX() == coor.getX() && _coord.getY() == coor.getY()) {//якщо на місці координати є корабель
                            System.out.println("найшли координату");

                            canvas1.getGraphicsContext2D().setFill(Color.RED);
                            //synchronized (canvas1){
                                //try {
                                //    Thread.sleep(2000);
                                //} catch (InterruptedException e) {
                                //}
                           // }
                            canvas1.getGraphicsContext2D().fillRect(coor.getX(), coor.getY(), 30, 30);
                            po4:
                            System.out.println("Перевірка вбили чи ранили");
                            for (int k = 0; k < algorithm.getField1().getKilledShipsList().size(); k++) {
                                System.out.println(i+"вбитий корабель");
                                Ship killedShip = algorithm.getField1().getKilledShipsList().get(k);
                                if (algorithm.isNearShip(killedShip, coor)) {
                                    System.out.println("Поруч є вбитий корабель, значить ранили, тому адд до нього коорд");
                                    killedShip.getCoordinateList().add(coor);
                                    algorithm.getField1().getDisableSquaresList().add(coor);


                                    //якзо поруч нема кораблыв, значить вбитий ы рестррікт поставати, інакше
                                    int p=0;
                                    for(p =0;p<4;p++){
                                        coor = getNearCoordinate(coor);
                                        if(algorithm.getField1().belongDisableSquaresList(coor)
                                                || ((coor.getX()<0 || coor.getX()>=300)&& (coor.getY()<0 || coor.getY()>=300)))
                                            p++;
                                        else {
                                            System.out.println("Координата поруч: "+coor.getX()+" "+coor.getY());
                                            break;
                                        }
                                    }
                                    if(p==4){//значить не знайшли поруч координату
                                        coor = new Coordinate(rand.nextInt(10)*30,rand.nextInt(10)*30);
                                        while (algorithm.getField1().belongDisableSquaresList(coor))
                                            coor = new Coordinate(rand.nextInt(10)*30,rand.nextInt(10)*30);
                                        System.out.println("Координата поруч: "+coor.getX()+" "+coor.getY());
                                        algorithm.checkRestrict(killedShip,algorithm.getField1(),canvas1.getGraphicsContext2D(),true);
                                    }

//                                    coor = getNearCoordinate(coor);
//                                    while (algorithm.getField1().belongDisableSquaresList(coor))
//                                        coor = getNearCoordinate(coor);
//                                    System.out.println("Координата поруч: "+coor.getX()+" "+coor.getY());
                                    break po5;
                                }
//                                    System.out.println("wondered or deaded");
//                                    if (algorithm.isNearShipNotBelongKilledArray(killedShip, coor, algorithm.getField1())) {
//                                        System.out.println("wonded");
//                                        flag = true;
//                                        getNearCoordinate(coor);
//                                        while (algorithm.getField1().belongDisableSquaresList(coor))
//                                            getNearCoordinate(coor);
//                                        //coor set new value как- то перейти на po1
//                                        break;
//                                    }
//                                    algorithm.checkRestrict(killedShip, algorithm.getField1(), canvas1.getGraphicsContext2D(), true);
//                                    System.out.println("deaded");
//                                    algorithm.getField1().setKilled(algorithm.getField1().getKilled() + 1);
//                                    flag = true;
//                                    coor = new Coordinate(rand.nextInt(10),rand.nextInt(10));
//                                    while (algorithm.getField1().belongDisableSquaresList(coor))
//                                        coor = new Coordinate(rand.nextInt(10),rand.nextInt(10));
//                                    break;
//                                }
//                            }
//                            if(flag==false) {
//                                Ship ship = new Ship(new Random().nextInt(), Color.AQUA);
//                                ship.getCoordinateList().add(coor);
//                                algorithm.getField1().getKilledShipsList().add(ship);
//                                System.out.println("created");
//                                if (algorithm.isNearShipNotBelongKilledArray(ship, coor, algorithm.getField1())) {
//                                    System.out.println("wonded");
//                                    flag = true;
//                                    //coor set new value как- то перейти на po1
//                                    getNearCoordinate(coor);
//                                    while (algorithm.getField1().belongDisableSquaresList(coor))
//                                        getNearCoordinate(coor);
//                                    break;
//                                }else {
//                                    algorithm.checkRestrict(ship, algorithm.getField1(), canvas1.getGraphicsContext2D(), true);
//                                    System.out.println("deaded");
//                                    algorithm.getField1().setKilled(algorithm.getField1().getKilled() + 1);
//                                    flag = true;
//                                    coor = new Coordinate(rand.nextInt(10),rand.nextInt(10));
//                                    while (algorithm.getField1().belongDisableSquaresList(coor))
//                                        coor = new Coordinate(rand.nextInt(10),rand.nextInt(10));//coor set new value как- то перейти на po
//                                    break;
//                                }
                            }
                            System.out.println("якщо ми тут, значить поруч вбитих нема");
                            Ship ship = new Ship(new Random().nextInt(), Color.AQUA);
                            ship.getCoordinateList().add(coor);
                            algorithm.getField1().getKilledShipsList().add(ship);
                            algorithm.getField1().getDisableSquaresList().add(coor);
                            System.out.println("перша палуба вбитого");
                            //якзо поруч нема кораблыв, значить вбитий ы рестррікт поставати, інакше
                            int p=0;
                            for(p =0;p<4;p++){
                                coor = getNearCoordinate(coor);
                                if(algorithm.getField1().belongDisableSquaresList(coor)
                                        || ((coor.getX()<0 || coor.getX()>300)&& (coor.getY()<0 || coor.getY()>300)))
                                    p++;
                                else {
                                    System.out.println("Координата поруч: "+coor.getX()+" "+coor.getY());
                                    break;
                                }
                            }

                            if(p==4){//значить не знайшли поруч координату
                                coor = new Coordinate(rand.nextInt(10)*30,rand.nextInt(10)*30);
                                while (algorithm.getField1().belongDisableSquaresList(coor))
                                    coor = new Coordinate(rand.nextInt(10)*30,rand.nextInt(10)*30);
                                System.out.println("Координата поруч: "+coor.getX()+" "+coor.getY());
                                algorithm.checkRestrict(ship,algorithm.getField1(),canvas1.getGraphicsContext2D(),true);
                            }

                            flag = true;
                            break po3;
                        }
                        System.out.println(" не найшли координату");
                    }
                }
            } else {
                    System.out.println("промазали "+coor.getX()+" "+coor.getY());
                    flag = false;
                    algorithm.getField1().getDisableSquaresList().add(coor);

                    canvas1.getGraphicsContext2D().setFill(Color.GREY);
                    canvas1.getGraphicsContext2D().fillOval(coor.getX() + 15 - 4, coor.getY() + 15 - 4, 8, 8);
            }
    }
    algorithm.mouseClick(algorithm.getField2().getCanvas(), algorithm.getField2(), applyButton, messageArea);
    System.out.println("ставим 2");
    }

    public void setAutoShip(Field field,Ship ship){
        field.getShipsList().add(ship);
        for (Coordinate coor:ship.getCoordinateList()) {
            field.getDisableSquaresList().add(coor);
        }
    }

    public void setWinner(String winner){
        hintCanvas1.setVisible(false);
        hintCanvas2.setVisible(false);
        win_contr.setWinnerLabel(winner);
        winnerStage.show();
    }

    public Coordinate getNearCoordinate(Coordinate coordinate){
        Random random = new Random();
        switch (random.nextInt(4)){
            case 1:
                return new Coordinate(coordinate.getX(),coordinate.getY()+30);
            case 2:
                return new Coordinate(coordinate.getX(),coordinate.getY()-30);
            case 3:
                return new Coordinate(coordinate.getX()+30,coordinate.getY());
            default:
                return new Coordinate(coordinate.getX()-30,coordinate.getY());
        }
    }
}
