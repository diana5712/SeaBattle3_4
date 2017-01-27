package ua.kpi.tef.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by diana on 08.01.17.
 */
public class View {
    public static final String HUMAN_MODE = "With human";
    public static final String COMPUTER_MODE = "With computer";
    public static final String RESPUBLICA_MODE = "Respublica";
    public static final String ALPHABET_MODE = "Alphabet";
    public static final String RESPUBLICA = "    R       E       S       P       U       B       L        I       C       A";
    public static final String ALPHABET = "    A       B       C      D       E       F       G       H       I       J";
    public static final String ONE_DECK_SHIP = "One deck ships left:\t";
    public static final String TWO_DECK_SHIP = "Two deck ships left:\t";
    public static final String THREE_DECK_SHIP = "Three deck ships left:\t";
    public static final String FOUR_DECK_SHIP = "Four deck ships left:\t";
    public static final String ENTER = "\n";
    public static final String NOTHING = "";

    public static final void drawField(GraphicsContext context){
        context.setFill(Color.BLACK); // устанавливаем цвет
        context.setLineWidth(0.5);
        for(int i = 0; i <= 300 ; i = i + 30 ) {
            context.strokeLine(i, 0, i, 300);
            context.strokeLine(0, i, 300, i);
        }
    }
}
