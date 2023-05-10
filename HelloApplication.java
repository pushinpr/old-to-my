package com.example.lab_2;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

class Plane{
    private Label name;
    private double health;
    private double speed;
    private Line life;
    private ImageView iplane;
    private double x, y;

    private boolean active;
    private Rectangle rectActive;

    public Plane(String n, double h, double s, double _x, double _y ){
        x=_x;
        y=_y;

        name=new Label(n);
        name.setLayoutX(x);
        name.setLayoutY(y);

        health=h;
        speed=s;
        life=new Line(x,y+15, x+health,y+15);
        life.setStrokeWidth(5);
        life.setStroke(Color.LIGHTGREEN);

        iplane=new ImageView(HelloApplication.imgplane);
        iplane.setX(x);
        iplane.setY(y+15+7);

        active= false;

        rectActive= new Rectangle(x-5,y-5,105,105);
        rectActive.setFill(Color.TRANSPARENT);
        rectActive.setStroke(Color.MAGENTA);

        HelloApplication.group.getChildren().addAll(name, life, iplane);

    }
    public void explode(){
        HelloApplication.group.getChildren().removeAll(name, life, iplane);

        if( active )HelloApplication.group.getChildren().remove(rectActive);

    }

    public boolean isActive(){
        return active;
    }
    public boolean flipActivation(){
        if(active){
            HelloApplication.group.getChildren().remove(rectActive);
        }
        else{
            HelloApplication.group.getChildren().add(rectActive);
        }
        active= !active;

        return active;
    }

    public boolean mouseActivate( double mx, double my ){
       if(iplane.boundsInParentProperty().get().contains(mx,my)){
           flipActivation();
           return true;
       }
       return false;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "name=" + name.getText() +
                ", health=" + health +
                ", speed=" + speed +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
    public String getName(){
        return name.getText();
    }
    public void setName(String n){
        name.setText(n);
    }
    public String getHealth(){
        return Double.toString(health) ;
    }
    public void setHealth(String h){

        try {
            health= Double.parseDouble(h);
        }
        catch(Exception e){
            health=0.0;
        }

        life.setStartX(x);
        life.setStartY(y+15);
        life.setEndX(x+health);
        life.setEndY(y+15);

    }

    public String getSpeed(){
        return Double.toString(speed) ;
    }
    public void setSpeed(String s){

        try {
            speed= Double.parseDouble(s);
        }
        catch(Exception e){
            speed=0.0;
        }
    }

   public void setCoordinates(){
       name.setLayoutX(x);
       name.setLayoutY(y);


       life.setStartX(x);
       life.setStartY(y+15);
       life.setEndX(x+health);
       life.setEndY(y+15);


       iplane.setX(x);
       iplane.setY(y+15+7);

       rectActive.setX(x-5);
       rectActive.setY(y-5);

   }

    public String getX(){
        return Double.toString(x);
    }

    public void setX( double _x ){
        x= _x;

        setCoordinates();
    }
    public void setX(String sX){
        try {
            x= Double.parseDouble(sX);
        }
        catch(Exception e){
            x=0.0;
        }

        setX(x);
    }
    public String getY(){
        return Double.toString(y);
    }

    public void setY( double _y ){
        y= _y;

        setCoordinates();
    }

    public void setY(String sY) {
        try {
            y = Double.parseDouble(sY);
        } catch (Exception e) {
            y = 0.0;
        }

        setY(y);
    }

    public void move( double dx, double dy ){
        double speedFactor = speed / 100;
        x = x + dx * speedFactor;
        y = y + dy * speedFactor;
        setCoordinates();
    }
}

public class HelloApplication extends Application {

    public static Group group= new Group();

    public static Image imgplane;

    public static int counter=0;

    public static ArrayList<Plane> herd = new ArrayList<>();

    public static ArrayList<String> getParamsToChange( int index ){
        Plane r= herd.get(index);

        ArrayList<String> arr= new ArrayList<String>();
        arr.add( r.getName() );
        arr.add( r.getHealth() );
        arr.add( r.getSpeed() );
        arr.add( r.getX() );
        arr.add( r.getY() );
        return arr;
    }

    public static ArrayList<String> getNames(){
        ArrayList<String> arr = new ArrayList<>();

        for( Plane r:herd ){
            arr.add(r.toString() );
        }

        return arr;
    }

    public static ArrayList<Object> getObjName(){
        ArrayList<Object> arr = new ArrayList<>();

        for( Plane r:herd ){
            arr.add(r.getName() );
        }

        return arr;
    }

    public static void changePlane(int planeIndex, String sName, String sHealth, String sSpeed, String sX, String sY ){
        Plane r= herd.get(planeIndex);

        r.setName(sName);
        r.setHealth(sHealth);
        r.setSpeed(sSpeed);
        r.setX(sX);
        r.setY(sY);

    }
    public static void createNewPlane(String sName, String sHealth, String sSpeed, String sX, String sY ){

        System.out.printf("sName=%s sHealth=%s sSpeed=%s sX=%s sY=%s \n", sName, sHealth, sSpeed, sX, sY );

        if( sName.equals("") ) sName="Plane";

        double h;
        try {
             h= Double.parseDouble(sHealth);
        }
        catch(Exception e){
            h=0.0;
        }

        double s;
        try {
            s= Double.parseDouble(sSpeed);
        }
        catch(Exception e){
            s=0.0;
        }

        double x;
        try {
            x= Double.parseDouble(sX);
        }
        catch(Exception e){
            x=0.0;
        }


        double y;
        try {
            y= Double.parseDouble(sY);
        }
        catch(Exception e){
            y=0.0;
        }

        HelloApplication.herd.add(new Plane(sName,h,s,x,y) );
    }

    @Override
    public void start(Stage stage) throws IOException {
        imgplane= new Image( HelloApplication.class.getResource("plane.png").toString(),
                100,100,false,false);


        Scene scene = new Scene(group, 1000, 700);

        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if( mouseEvent.getButton().equals(MouseButton.SECONDARY) ){
                    ChoosePlaneToChangeParamsDlg.display(mouseEvent.getX(), mouseEvent.getY());
                }
                else{
                    boolean flg=false;
                    for( Plane r:herd ){
                        if( r.mouseActivate(mouseEvent.getX(), mouseEvent.getY() ) )flg=true;
                    }

                    if( flg==false)
                    PlaneParamsDlg.display(mouseEvent.getX(), mouseEvent.getY() );
                }
                System.out.println("Got control back!");
            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.DELETE) )
                {
                    ArrayList<Plane> tmp = new ArrayList<>();

                    for( Plane r:HelloApplication.herd ){
                        if( r.isActive() ){
                            r.explode();
                        }
                        else{
                            tmp.add(r);
                        }

                        herd= tmp;
                    }
                }

                boolean flg=false;
                double dx=0.0;
                double dy=0.0;

                if (keyEvent.getCode().equals(KeyCode.W) )
                {
                    dy=-10.0; flg= true;
                }
                if (keyEvent.getCode().equals(KeyCode.A) )
                {
                    dx=-10.0; flg= true;
                }
                if (keyEvent.getCode().equals(KeyCode.S) )
                {
                    dy=10.0; flg= true;
                }
                if (keyEvent.getCode().equals(KeyCode.D) )
                {
                    dx=10.0; flg= true;
                }

                if( flg ){
                    for( Plane r:HelloApplication.herd ){
                        if( r.isActive() ){
                            r.move(dx, dy);
                        }

                    }

                }

            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();

    }
}
