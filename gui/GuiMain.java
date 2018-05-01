
package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GuiMain extends Application {
	
	public static StepView selected;
    
    // To add click-drag functionality, declare variables
    // that will hold mouseX, mouseY, nodeX, nodeY.
    private double mouseX = 0;
    private double mouseY = 0;
    private double nodeX = 0;
    private double nodeY = 0;
    
    /*
    double pants_node_spacing = 5;
    double pants_node_width = 100;
    String text_highlight = "white"; */
    
    BorderPane borderpane;
    Drawer drawer; // Holds pants.
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Yggdrasil");
        stage.getIcons().add(new Image("file:yggdrasil.png"));
        
        borderpane = new BorderPane();
        drawer = new Drawer();
        drawer.pane.prefWidthProperty().bind(stage.widthProperty());
        drawer.pane.prefHeightProperty().bind(stage.heightProperty());
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        //root.getChildren().add();
        
        Group root = new Group();
        
        // Make menu bar
        MenuBar menubar = makeMenuBar();
        menubar.prefWidthProperty().bind(stage.widthProperty());
        // Make some pants and add it to drawer
  
        borderpane.setTop(menubar);
        
        // Add event handlesers
        drawer.pane.setOnMousePressed(mouseClick());
        drawer.pane.setOnMouseDragged(mouseDrag());
        
        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add("ygg.css");
        root.getChildren().add(drawer.pane);
        root.getChildren().add(borderpane);
        
        
        stage.setScene(scene);
        stage.show();
    }
    
    private MenuBar makeMenuBar(){
        MenuBar menubar = new MenuBar();
        // File Menu /////////////
        Menu file_menu = new Menu("File");
        
        MenuItem save = new MenuItem("Save");
        save.setAccelerator(KeyCombination.keyCombination("Ctrl + S"));
        save.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Save File
            }
        });
        
        MenuItem save_as = new MenuItem("Save As");
        save_as.setAccelerator(KeyCombination.keyCombination("Shift + Ctrl + S"));
        save_as.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Save File As...
            }
        });
        
        MenuItem open = new MenuItem("Open");
        open.setAccelerator(KeyCombination.keyCombination("Ctrl + O"));
        open.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Open File ... 
            }
        });
       
        MenuItem self_destr = new MenuItem("Self-Destruct");
        self_destr.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Self Destruct func
            }
        }); 
        
        file_menu.getItems().addAll(save, save_as, open, self_destr);
        
        // Tree Menu ////////////
        Menu tree_menu = new Menu("Tree");
        
        MenuItem verify = new MenuItem("Verify");
        //verify.setAccelerator(KeyCombination.keyCombination("Shift + Ctrl + V"));
        verify.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Verify
            }
        });
        
        tree_menu.getItems().addAll(verify);
        
        // Help Menu /////////////
        Menu help_menu = new Menu("Help");
        
        MenuItem instructions = new MenuItem("Instructions");
        instructions.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent t){
                    // New Window / open textfile with instructions written
                }
        });
              
        MenuItem menu_shortcuts = new MenuItem("Shortcuts");
        menu_shortcuts.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // New Window / open textfile with program shortcuts.
            }
        });
        
        MenuItem bugs_fixes = new MenuItem("Bugs / Fixes");
        bugs_fixes.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Link to web documentation or anothher text file
            }
        });
        
        help_menu.getItems().addAll(instructions, menu_shortcuts, bugs_fixes);
        menubar.getMenus().addAll(file_menu, tree_menu, help_menu);
        return menubar;
    }
    
    private EventHandler<MouseEvent> mouseClick() {
        EventHandler<MouseEvent> mouseClickHandler = new EventHandler<MouseEvent>() {
            // Get the current mouse coordinates, relative to the scene
            public void handle(MouseEvent event){
                if (event.getButton() == MouseButton.PRIMARY){
                   mouseX = event.getSceneX();
                   mouseY = event.getSceneY();
                   
                   // Get node coordinates
                   nodeX = drawer.pane.getLayoutX();
                   nodeY = drawer.pane.getLayoutY();
                }
            } 
        };
        return mouseClickHandler;
    }
    private EventHandler<MouseEvent> mouseDrag(){
        EventHandler<MouseEvent> mouseDragHandler = new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event){
                if (event.getButton() == MouseButton.PRIMARY){
                    // Find the difference in coordinates.
                    double difX = event.getSceneX() - mouseX;
                    double difY = event.getSceneY() - mouseY;
                    // Update pants coordinates.
                    nodeX += difX;
                    nodeY += difY;
                    drawer.pane.setLayoutX(nodeX);
                    drawer.pane.setLayoutY(nodeY);
                    // Update mouse coordinates.
                    mouseX = event.getSceneX();
                    mouseY = event.getSceneY();
                }
            }
        };
        return mouseDragHandler;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
   
    
}
