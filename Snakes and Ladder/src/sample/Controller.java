package sample;

import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.io.File;
import java.util.Objects;
import java.util.Random;

public class Controller {

    Mythread T1;
    Player p1;
    Player p2;
    Dice D;
    @FXML
    private Label popup;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView P1;
    @FXML
    private ImageView P2;
    @FXML
    private ImageView Game;
    @FXML
    private ImageView promptImg;
    @FXML
    private ImageView dicePng; //for the dice
    @FXML
    private ImageView entry_arrows;
    @FXML
    private Label P1portal;
    @FXML
    private Label P2portal;
    @FXML
    private ImageView RightPane;
    @FXML
    private ImageView LeftPane;


    @FXML
    public void roller(MouseEvent event) {

        D.roll(event, dicePng, P1, P2, p1, p2, P1portal, P2portal, image1, image2,LeftPane,RightPane,popup);

    }

    public void diceprompt() {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(promptImg);
        translate.setDuration(Duration.millis(1000));
        translate.setCycleCount(Animation.INDEFINITE);
        translate.setByY(12);
        translate.setAutoReverse(true);
        translate.play();
    }

    public void initialize() {

        diceprompt();
        p1 = new Player();
        p2 = new Player();
        D = new Dice();
        T1=new Mythread();

        Glow glow = new Glow();
        Glow glow1=new Glow();
        glow1.setLevel(0.0);
        glow.setLevel(0.9);
        LeftPane.setOpacity(1);
        image1.setOpacity(1);
        RightPane.setOpacity(0.5);
        image2.setOpacity(0.5);
        image2.setEffect(glow1);
        image1.setEffect(glow);
        P1portal.setOpacity(1);
        P2portal.setOpacity(0.5);

        String message = "   Ongoing Game ";
        popup.setText(message);
        Font font = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20);
        popup.setFont(font);

    }

}

class Token extends Thread {

    private ImageView I;

    Token(ImageView I) {
        this.setI(I);
    }

    Token(ImageView I, int x) {
        this.setI(I);
        if(x==1){
            I.setLayoutX(SnakesAndLadder.Xpos[0]);
            I.setLayoutY(SnakesAndLadder.Ypos[0]);
            for (int i = 0; i < (SnakesAndLadder.SnakesAndLadder[0].length) / 2; i++) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runner(I,SnakesAndLadder.SnakesAndLadder[0][2 * i], SnakesAndLadder.SnakesAndLadder[0][2 * i + 1]));
            }
        }
        else {
            I.setLayoutX(SnakesAndLadder.Xpos[0]);
            I.setLayoutY(SnakesAndLadder.Ypos[0]);
            for(int i=0;i<=5;i++){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runner(I,SnakesAndLadder.Xpos[i],SnakesAndLadder.Ypos[i]));
            }

        }
    }
    public void move(int diceNumber,Label popup) {
        //final pos = curr index + diceNumber and then match it with finalXY array and then move
        int currX = (int) I.getLayoutX();
        int currY = (int) I.getLayoutY();
        int index = 0;
        for (int i = 0; i < SnakesAndLadder.Xpos.length; i++) {
            if (SnakesAndLadder.Xpos[i] == currX && SnakesAndLadder.Ypos[i] == currY) {
                index = i;
                break;
            }
        }
        int startIndex = index+1;
        index += diceNumber;
        if(index==99){
            System.out.println("Game Over!!");
            labelThread lt1 = new labelThread(popup);
            lt1.start();
        }
        if(index>99){

            //do nothing
            System.out.println("Try again");

        }

        else if (SnakesAndLadder.SnakesAndLadder[index][0]==0 && SnakesAndLadder.SnakesAndLadder[index][1]==0) {
            //this means no snake or ladder
            for(int i=startIndex;i<=index;i++){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runner(I,SnakesAndLadder.Xpos[i],SnakesAndLadder.Ypos[i]));
            }
        } else {
            //System.out.println("FALSE");
            for (int i = 0; i < (SnakesAndLadder.SnakesAndLadder[index].length) / 2; i++) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runner(I, SnakesAndLadder.SnakesAndLadder[index][2 * i], SnakesAndLadder.SnakesAndLadder[index][2 * i + 1]));
            }

        }


    }
    public ImageView getI() { return I; }
    public void setI(ImageView i) {I = i; }
}

class Dice {

    private static int flag=0;
    static int movenumber=0;


    void roll(MouseEvent event, ImageView dicePng, ImageView one, ImageView two, Player p1, Player p2, Label pp1, Label pp2, ImageView image1, ImageView image2,ImageView leftpane,ImageView rightpane,Label popup){

        //b.setDisable(true);
        if(flag==0){

            Glow glow = new Glow();
            Glow glow1=new Glow();

            //setting level of the glow effect
            glow.setLevel(0.9);
            glow1.setLevel(0);

            leftpane.setOpacity(0.5);
            image1.setOpacity(0.5);
            pp1.setOpacity(0.5);
            rightpane.setOpacity(1);
            image2.setOpacity(1);
            pp2.setOpacity(1);

            //Applying bloom effect to text
            image2.setEffect(glow);
            image1.setEffect(glow1);

        }
        else{

            Glow glow = new Glow();
            Glow glow1=new Glow();

            //setting level of the glow effect
            glow1.setLevel(0.0);

            //setting level of the glow effect
            glow.setLevel(0.9);


            leftpane.setOpacity(1);
            image1.setOpacity(1);
            pp1.setOpacity(1);
            rightpane.setOpacity(0.5);
            image2.setOpacity(0.5);
            pp2.setOpacity(0.5);
            //Applying bloom effect to text
            image2.setEffect(glow1);
            image1.setEffect(glow);

        }

        Thread t=new Thread(){

            public void run(){
                try{
                    movenumber++;

                    int i=0;
                    int diceface = 0; //the no. we get finally on the top


                    while(i<15){

                        diceface=generateRandomNumber();
                        Image myimage=new Image(Objects.requireNonNull(getClass().getResourceAsStream("img" + diceface + ".png")));
                        dicePng.setImage(myimage);
                        i++;
                        Thread.sleep(50);
                    }


                    //dicedisplay(bt, p, diceface);
                    if(flag==0){
                        //pp1.setText("Player 1");
                        dicedisplay(one, p1, diceface,popup);

                        flag=1;
                    }else{
                        //pp2.setText("Player 2");
                        dicedisplay(two,p2, diceface,popup);
                        flag=0;
                    }

                    //b.setDisable(false);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }


    public int generateRandomNumber() {
        Random randomNum = new Random();
        int num = 1 + randomNum.nextInt(6);
        //System.out.println("Random number: " + num);

        return num;
    }


    public void dicedisplay(ImageView I, Player p, int diceface,Label popup) {
        //int diceNumber = 6;

        if (!p.getPlayeropen()) {
            if (diceface == 1) {
                p.setT(new Token(I, 1));
                //System.out.println("Opening player");
                p.setPlayeropen(true);
            }
            if (diceface == 6) {
                //Player1 reopens
                p.setT(new Token(I, 6));
                //System.out.println("Opening player");
                p.setPlayeropen(true);
            } else {
                //System.out.println("Try next time!!");
            }
        } else {
            Mythread T1 = new Mythread(I, diceface,popup);
            T1.start();
        }
    }
    public int getFlag(){
        return flag;
    }

}
class SnakesAndLadder{

    static double Xpos[] = {
            47,119,184,257,322,388,454,521,581,650,
            650,581,521,454,388,322,257,184,119,47,
            47,119,184,257,322,388,454,521,581,650,
            650,581,521,454,388,322,257,184,119,47,
            47,119,184,257,322,388,454,521,581,650,
            650,581,521,454,388,322,257,184,119,47,
            47,119,184,257,322,388,454,521,581,650,
            650,581,521,454,388,322,257,184,119,47,
            47,119,184,257,322,388,454,521,581,650,
            650,581,521,454,388,322,257,184,119,47,
    };
    static double Ypos[] = {
            618,618,618,618,618,618,618,618,618,618,
            557,557,557,557,557,557,557,557,557,557,
            482,482,482,482,482,482,482,482,482,482,
            420,420,420,420,420,420,420,420,420,420,
            346,346,346,346,346,346,346,346,346,346,
            282,282,282,282,282,282,282,282,282,282,
            217,217,217,217,217,217,217,217,217,217,
            146,146,146,146,146,146,146,146,146,146,
            87,87,87,87,87,87,87,87,87,87,
            14,14,14,14,14,14,14,14,14,14,
    };
    static double[][] SnakesAndLadder =
            {
                    /* 0-10 */		{47,618,76,560,103,527,130,486,154,451,184,420},{0,0},{0,0},{257,618,299,609,343,587,391,568,454,557},{0,0},{0,0},{0,0},{0,0},{581,618,587,587,601,533,615,486,629,445,650,420},{0,0},
                    /* 11-20 */		{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{257,557,288,546,343,574,381,615,454,618},{0,0},{0,0},{0,0},
                    /* 21-30 */		{47,482,50,445,61,423,76,382,95,355,119,346},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{521,482,498,471,472,430,449,389,429,355,395,314,369,273,347,232,322,204,300,163,274,129,260,96,257,87},{0,0},{0,0},
                    /* 31-40 */		{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},
                    /* 41-50*/		{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},
                    /* 51-60 */		{650,282,629,286,601,266,567,245,533,232,506,216,483,204,454,217},{0,0},{0,0},{454,282,471,266,517,273,505,327,483,341,464,368,437,396,454,420},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},
                    /* 61-70*/		{0,0},{119,217,154,210,183,225,154,266,164,300,198,323,188,355,169,382,148,409,134,437,154,464,182,486,188,519,163,533,119,557},{0,0},{257,217,205,216,169,225,129,245,103,266,68,286,47,282},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},
                    /* 71-80*/      {0,0},{581,146,587,122,621,63,635,30,650,14},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{47,146,50,122,68,88,84,63,96,40,119,14},
                    /* 81-90 */		{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{454,87,429,87,403,104,403,145,377,169,347,184,329,210,343,232,347,273,343,300,329,323,295,327,278,341,274,368,295,396,322,420},{0,0},{0,0},{0,0},
                    /* 91-100 */	{0,0},{0,0},{521,14,545,14,579,22,586,70,561,87,526,111,498,128,521,146},{0,0},{322,14,363,13,329,22,329,63,359,96,393,128,388,146},{0,0},{0,0},{184,14,188,30,197,63,169,88,137,102,110,129,119,146},{0,0},{0,0},

            };
    public int getIndex(int X,int Y){
        int index=0;
        for (int i = 0; i < Xpos.length; i++) {
            if (Xpos[i] == X && Ypos[i] == Y) {
                index = i;
                break;
            }
        }
        return index;

    }
    public void climbLadder(int X,int Y,ImageView I){
        int index = getIndex(X,Y);
        for (int i = 0; i < (SnakesAndLadder[index].length) / 2; i++) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(new Runner(I, SnakesAndLadder[index][2 * i], SnakesAndLadder[index][2 * i + 1]));
        }
    }
    public void descendSnake(int x,int y,ImageView I){
        int index = getIndex(x,y);
        for (int i = 0; i < (SnakesAndLadder[index].length) / 2; i++) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(new Runner(I, SnakesAndLadder[index][2 * i], SnakesAndLadder[index][2 * i + 1]));
        }
    }
}
class labelThread extends Thread{
    private Label messageLabel;
    labelThread(Label messageLabel){
        this.setMessageLabel(messageLabel);
    }
    public void run(){
        Platform.runLater(new labelRunner(this.getMessageLabel()));
    }
    public Label getMessageLabel() { return messageLabel; }
    public void setMessageLabel(Label messageLabel) { this.messageLabel = messageLabel; }
}
class labelRunner implements Runnable{
    private Label messageLabel;
    labelRunner(Label messageLabel){
        this.setMessageLabel(messageLabel);
    }

    @Override
    public void run() {
        String message;
        if(Dice.movenumber%2==1){
            message = "  Congratulations \n        Player 1";

        }else{
            message = "  Congratulations \n        Player 2";
        }
        this.getMessageLabel().setText(message);
        Font font = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20);
        this.getMessageLabel().setFont(font);

    }
    public Label getMessageLabel() { return messageLabel; }
    public void setMessageLabel(Label messageLabel) { this.messageLabel = messageLabel; }
}
class Mythread extends Thread {
    private ImageView I;
    private int diceNumber;
    private Label popup;

    public Mythread(){}
    public Mythread(ImageView I, int diceNumber,Label popup) {
        this.setI(I);
        this.setDiceNumber(diceNumber);
        this.setPopup(popup);
    }

    public void run() {
        Token P = new Token(I);
        P.move(this.getDiceNumber(),this.getPopup());
    }
    public int getDiceNumber() { return diceNumber; }
    public void setDiceNumber(int diceNumber) { this.diceNumber = diceNumber; }
    public ImageView getI() { return I; }
    public void setI(ImageView i) { I = i; }
    public Label getPopup() { return popup; }
    public void setPopup(Label popup) { this.popup = popup; }
}

class Player {

    private boolean playeropen;
    private Token t;

    Player(boolean p, Token t) {
        this.playeropen = p;
        this.t = t;
    }
    Player() { this.playeropen = false; }
    public boolean getPlayeropen() {
        return this.playeropen;
    }
    public void setPlayeropen(boolean p) {
        this.playeropen = p;
    }
    public Token getT() {
        return t;
    }
    public void setT(Token t) {
        this.t = t;
    }
}

class Runner implements Runnable {
    private ImageView I;
    private double X;
    private double Y;

    public Runner(ImageView I, double X, double Y) {
        this.setI(I);
        this.setX(X);
        this.setY(Y);
    }

    @Override
    public void run() {
        I.setLayoutX(this.getX());
        I.setLayoutY(this.getY());
    }
    public double getY() {return Y;}
    public void setY(double y) {Y = y;}
    public double getX() {return X; }
    public void setX(double x) {X = x; }
    public ImageView getI() {return I; }
    public void setI(ImageView i) {I = i;}
}