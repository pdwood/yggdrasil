
package yggdrasil_gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 *
 * @author Mario
 */
public class YggdrasilGuiJavaFX extends Application {
    
    // To add click-drag functionality, declare variables
    // that will hold mouseX, mouseY, nodeX, nodeY.
    private double mouseX = 0;
    private double mouseY = 0;
    private double nodeX = 0;
    private double nodeY = 0;
    
    double pants_node_spacing = 2;
    double pants_node_width = 100;
    
    BorderPane borderpane;
    VBox pants;
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Yggdrasil");
        
        borderpane = new BorderPane();
        
        pants = new VBox();
        //pants.setPrefSize(100, 80);
        pants.setAlignment(Pos.CENTER);
        
        // Initialize text entry and textbox properties;
        TextField enter_st = new TextField("Enter Statement");
        enter_st.setMaxWidth(pants_node_width);
        
        // Buttons and button properties;
        double button_width = (pants_node_width / 3) - pants_node_spacing;
        Button close_br = new Button("X");
        close_br.setPrefWidth(button_width);
        Button open_br = new Button("O");
        open_br.setPrefWidth(button_width);
        Button more_br = new Button("+");
        more_br.setPrefWidth(button_width);
            // Button Tooltips (Hover Over)
        Tooltip close_br_tt = new Tooltip("Close Branch");
        Tooltip open_br_tt = new Tooltip("Open Branch");
        Tooltip more_br_tt = new Tooltip("New Branch");
        

        HBox hb_buttons = new HBox();
        hb_buttons.getChildren().addAll(close_br, open_br, more_br);
        hb_buttons.setSpacing(pants_node_spacing);
        hb_buttons.setAlignment(Pos.CENTER);

        pants.getChildren().addAll(enter_st, hb_buttons);
        String vb_border = "-fx-border-color: grey;\n" +
                           "-fx-border-insets: 5;\n" +
                           "-fx-border-width: 1;\n" +
                           "-fx-border-style: solid;\n"; 
 
        pants.setStyle(vb_border);
        pants.setPadding(new Insets(pants_node_spacing, pants_node_spacing, 
                                    pants_node_spacing, pants_node_spacing));
        pants.setSpacing(pants_node_spacing);
        pants.setLayoutX(120);
        pants.setLayoutY(50);
        
        
        /*
        vbox.getChildren().addAll(new Button("Add Statement"), new Button("New Branch"), new Button("Terminate"));
        m_Pants.getChildren().add(vbox); */
        
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        //root.getChildren().add();
        
        Group root = new Group();
        
        SubScene subscene = new SubScene(root, 300, 300);
        
        MenuBar menubar = new MenuBar();
        menubar.prefWidthProperty().bind(stage.widthProperty());
        
        borderpane.setTop(menubar);
        
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
        
        
        
        Scene scene = new Scene(root, 400, 400);
        root.getChildren().add(borderpane);
        root.getChildren().add(pants);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
   
    
}
