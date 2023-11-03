package lab06;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.concurrent.*;

/**
 * @author David
 */
public class Example1 extends Application {

    Label clock1, clock2, clock3, clock4, clock5;
    TextArea messages;
    Button stopButton, longtaskButton;

    AnimationTimer timer1;
    Thread timer2;
    java.util.Timer timer3;
    java.util.TimerTask task;
    javafx.concurrent.Service<Long> timer5;

    Timeline timer4;

    public void startClocks(){
        clock1.setText(getTime());
        clock2.setText(getTime());
        clock3.setText(getTime());
        clock4.setText(getTime());

        /* 1.2 Implement a JavaFX AnimationTimer to update clock1 */

        /* 1.3 Implement a normal thread that runs a loop to update clock2 */

        /* 1.4 Implement a TimerTask and Timer to update clock3 */ 
        
        /* 1.5 Implement a JavaFX Timeline to update clock4 */ 

        /* 1.6A Create instance of JavaFX Service here */  
 
    
    }

    /* 1.6B Define the JavaFX Service here that counts to 1000000000 without 
     * freezing the GUI */  


    public void doCount(){
        /* 1.6C add code here that reset and start the JavaFX Service for 1.5 */
    }
    
    public void stopTimers(){
        /*1.7  add code here that will stop all the tmer clocks */
    }

  
    
    // You should not need to change any code below this point 

    /* main method that runs GUI as a program and launches GUI on the JavaFX  
     * Application Thread */

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        /* initialise GUI components here */
        initComponents(stage);
        startClocks();
    }

    /* helper method that sets time, formats it, and returns as String */
    public String getTime() {
        LocalDateTime now = LocalDateTime.now();
        return //now.format(DateTimeFormatter.ISO_DATE) + " " +
                now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getExactTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("HH:mm:ss:SSS"));
    }

    public void longtask() {
        messages.appendText("\nThe long task has begun");
        try {  
            Thread.sleep(2000); } /* sleep for 2000ms = 2s */
        catch (InterruptedException e) { /* ignore exceptions */ } 
            
        messages.appendText("\nThe long task is over");
    }
    
    /* sets up all the GUI components */
    private void initComponents(Stage stage) {
        GridPane root = new GridPane();
        Scene scene = new Scene(root, 600, 400);

        GridPane topgrid = new GridPane();
        GridPane lowergrid = new GridPane();
        root.add(topgrid, 0, 0);
        root.add(lowergrid, 0, 1);

        topgrid.add(new Label("Clock 1 - updated by AnimationTimer"), 0,0);
        topgrid.add(new Label("Clock 2 - updated from another Thread"), 0,1);
        topgrid.add(new Label("Clock 3 - updated by util.Timer"), 0,2);
        topgrid.add(new Label("Clock 4 - updated by Timeline"), 0,3);
        topgrid.add(new Label(""), 0,5);

        clock1 = new Label(); clock1.setTextFill(Color.RED);
        topgrid.add(clock1, 1,0);
        clock2 = new Label(); clock2.setTextFill(Color.BLUE);
        topgrid.add(clock2, 1,1);
        clock3 = new Label(); clock3.setTextFill(Color.GREEN);
        topgrid.add(clock3, 1,2);
        clock4 = new Label(); clock4.setTextFill(Color.DARKGREEN);
        topgrid.add(clock4, 1,3);

        messages = new TextArea();
        messages.setPrefWidth(400);
        lowergrid.add(messages, 0,0);

        stopButton = new Button("Stop");
        stopButton.setOnAction( e -> stopTimers() );
        topgrid.add(stopButton, 0,6);

        longtaskButton = new Button("Do a long task");
        longtaskButton.setOnAction(  e -> longtask() );

        Button countButton = new Button("Count");
        countButton.setOnAction( e -> doCount()  );    

        topgrid.add(longtaskButton, 1,6);
        topgrid.add(countButton, 2,6);

        /* add "scene" to "stage" and make visible*/
        stage.setTitle("CM3113 Lab 06 - Example 1");
        stage.setScene(scene);
        stage.show();
    }

}