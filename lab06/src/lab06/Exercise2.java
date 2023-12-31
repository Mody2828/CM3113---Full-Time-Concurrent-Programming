// File: MainAppEx2SwingTimer.java
package lab06;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Exercise2 extends Application {

    /* references to GUI components that will be event sources here */
    private Button button1, button2, button3;
    private MenuItem menuItemA, menuItemB;
    private TextField textfield1;
    private TextArea textarea1;
    private Timeline timer;


    /* **** declare references to application specific objects here **** */
    
    private void handleMenu1(){
        textarea1.appendText("You selected item1a\n");
    }
    
    private void handleMenu2(){
        textarea1.appendText("You selected item1b\n");
    }
    
    private void handleButton1(){
        textarea1.appendText("You hit button 1, it plays the Timeline timer\n");
        textfield1.setText("You hit button 1\n");
        timer.play();
    }
    
    private void handleButton2(){
        textarea1.appendText("You hit button 2, it pauses the Timeline timer\n");
        textfield1.setText("You hit button 2\n");
        timer.pause();
    }
        
    private void handleButton3(){
        textarea1.setText("You hit button 3 and it overwrites by using setText instead of appendText\n");
        textfield1.setText("You hit button 3\n");
    }
    
    private void handleTextField1(){
        String text = textfield1.getText();
        textarea1.appendText("You typed " + text + "\n");
    }

    private long counter;

    public void setupTimer(){
    /* construct a swing timer to generate actionEvents every 1000 milliseconds
     * Because the timer is not a visible GUI component we don't need to add 
     * the timer to the visible contents of the frame. But we do need to 
     * ensure that timer events are handled */
        counter = 0;
        timer = new Timeline(
            new KeyFrame(Duration.seconds(1.0), 
                         e -> {textarea1.appendText("\n" + //
                                 "The Timeline timer has generated event " + counter) ; counter++;}
            )
        );
            
        // the timer object is a thread so we need to start it!
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
        
    }
    
    /* main method that runs GUI as a program */
    // You should not need to change any code below this point
    /* launch GUI here */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        /* initialise GUI components here */
        initComponents(stage);
        setupTimer();
    }

    public void initComponents(Stage stage) {
        GridPane root = new GridPane();
        GridPane panel = new GridPane();
        Scene scene = new Scene(root, 600, 400);
        root.add(panel, 0, 1);

        GridPane left = new GridPane();
        GridPane right = new GridPane();
        panel.add(left, 0,0);
        panel.add(right, 1,0);
        
        

        /* add "scene" to "stage" and make visible*/
        stage.setTitle("CM3113 Lab 06 - Exercise 2");
        stage.setScene(scene);
        stage.show();

        // set up menu bar for this frame
        MenuBar menubar = new MenuBar();
        Menu menu1 = new Menu("Menu1");
        menuItemA = new MenuItem("Item A");
        menuItemB = new MenuItem("Item B");
        menu1.getItems().add(menuItemA);
        menu1.getItems().add(menuItemB);
        menubar.getMenus().add(menu1);

        root.add(menubar, 0, 0);  // add menu bar to this frame
        // register ActionListener for each menu item
        
        menuItemA.setOnAction(
            /* **** do whatever item1b requires **** */
            e -> {
                handleMenu1();
                textarea1.appendText("You selected item1b\n");
            }
            );
        
        menuItemB.setOnAction(
            /* **** do whatever item1b requires **** */
            e -> {
                handleMenu2();
                textarea1.appendText("You selected item1b\n");
            }
            );

        // create a Panel to group together button1, button2, button3
        button1 = new Button("B1");
        right.add(button1,1,0);
        button2 = new Button("B2");
        right.add(button2,1,1);
        button3 = new Button("B3");
        right.add(button3,1,2);

        // register listeners for action events generated by each button
        button1.setOnAction(
            /* **** do whatever button 1 requires **** */
            e -> handleButton1());
        button2.setOnAction(
            /* **** do whatever button 2 requires **** */
            e -> handleButton2());
        button3.setOnAction(
            /* **** do whatever button 3 requires **** */
            e -> handleButton3());

        // create a Text Field and locate it at the south side of the frame
        textfield1 = new TextField("");
        /* (initial text, column width) */
        root.add(textfield1, 0, 2);
        // make sure user can edit this field i.e. this field is for input
        textfield1.setEditable(true);
        // register frame as listener for action events generated by this field
        textfield1.setOnAction(
            // user has finished editing textfield1 get text and use it
            e -> handleTextField1());

        // create a Text Field and locate it at the south side of the frame
        textarea1 = new TextArea(""); /*(initial text,rows,cols) */
        // make sure user cannot edit this field i.e. if for output only
        textarea1.setEditable(false);
        /* provide automatic scrolling capability for the text area by adding
         * it into a JScrollPane add to the contents of this frame */
        panel.add(new ScrollPane(textarea1), 7,0);
        
        // construct any application specific objects here... 
    }
}
