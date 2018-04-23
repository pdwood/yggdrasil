
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
import javafx.scene.control.ContextMenu;
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
    
    double pants_node_spacing = 5;
    double pants_node_width = 100;
    
    String text_highlight = "white";
    
    BorderPane borderpane;
    VBox pants;
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Yggdrasil");
        
        borderpane = new BorderPane();
       
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        //root.getChildren().add();
        
        Group root = new Group();
        
        SubScene subscene = new SubScene(root, 300, 300);
        
        // Make menu bar
        MenuBar menubar = makeMenuBar();
        menubar.prefWidthProperty().bind(stage.widthProperty());
        // Make some pants
        pants = makePants();
        
        borderpane.setTop(menubar);
        
        // Add event handlesers
        pants.setOnMousePressed(mouseClick());
        pants.setOnMouseDragged(mouseDrag());
        
        Scene scene = new Scene(root, 600, 500);
        root.getChildren().add(pants);
        root.getChildren().add(borderpane);
        
        stage.setScene(scene);
        stage.show();
    }
    
    private VBox makePants(){
        VBox pants = new VBox();
        //pants.setPrefSize(100, 80);
        pants.setAlignment(Pos.CENTER);
        
        // Initialize text entry and textbox properties;
        TextField enter_st = new TextField("Enter Statement");
        enter_st.setMaxWidth(pants_node_width);
        enter_st.setStyle("-fx-control-inner-background: " + text_highlight);
        
        enter_st.setContextMenu(makeTextFieldMenus());
        
        // Buttons and button properties;
        double button_width = (pants_node_width / 3) - pants_node_spacing;
        Button close_br = new Button("X");
        close_br.setPrefWidth(button_width);
        Button open_br = new Button("O");
        open_br.setPrefWidth(button_width);
        Button more_br = new Button("+");
        more_br.setPrefWidth(button_width);
            // Button Tooltips (Hover Over)
        Tooltip cb_tt = new Tooltip("Close Branch");
        Tooltip ob_tt = new Tooltip("Open Branch");
        Tooltip mb_tt = new Tooltip("New Branch");
        Tooltip.install(close_br, cb_tt);
        Tooltip.install(open_br, ob_tt);
        Tooltip.install(more_br, mb_tt);

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
        // Initial X and Y.
        pants.setLayoutX(120);
        pants.setLayoutY(50);
        
        return pants;
    }
    
    private ContextMenu makeTextFieldMenus(){
        ContextMenu rcl_menu = new ContextMenu();
        
        MenuItem premise = new MenuItem("Premise");
        premise.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Specify that statement is a premise, action, etc
            }
        });
        
        // Make Intro and Elim submenus
        Menu intro = new Menu("Intro");
        MenuItem and_i = new MenuItem("∧");
        and_i.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Specify And Intro
            }
        });
        MenuItem or_i = new MenuItem("∨");
        or_i.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Specify Or Intro
            }
        });
        MenuItem neg_i = new MenuItem("¬");
        neg_i.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Specify Negation Intro
            }
        });
        MenuItem cont_i = new MenuItem("⊥");
        cont_i.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Specify Contradiction Intro
            }
        });
        MenuItem cond_i = new MenuItem("→");
        cond_i.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Specify Conditional Intro
            }
        });
        MenuItem bic_i = new MenuItem("↔");
        bic_i.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Specify Biconditional Intro
            }
        });
        MenuItem eq_i = new MenuItem("=");
        eq_i.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Specify = Intro
            }
        });
        MenuItem unq_i = new MenuItem("∀");
        unq_i.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Specify Universal Quantifier Intro
            }
        });
        MenuItem exq_i = new MenuItem("∃");
        exq_i.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Specify Existential Quantifier Intro
            }
        });
        intro.getItems().addAll(and_i, or_i, neg_i, cont_i, cond_i, bic_i, eq_i, unq_i, exq_i);
        
        Menu elim = new Menu("Elim");
        MenuItem and_e = new MenuItem("∧");
        and_e.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Specify And Elim
            }
        });
        MenuItem or_e = new MenuItem("∨");
        or_e.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Specify Or Elim
            }
        });
        MenuItem neg_e = new MenuItem("¬");
        neg_e.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Specify Negation Elim
            }
        });
        MenuItem cont_e = new MenuItem("⊥");
        cont_e.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Specify Contradiction Elim
            }
        });
        MenuItem cond_e = new MenuItem("→");
        cond_e.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Specify Conditional Elim
            }
        });
        MenuItem bic_e = new MenuItem("↔");
        bic_e.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Specify Biconditional Elim
            }
        });
        MenuItem eq_e = new MenuItem("=");
        eq_e.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Specify = Elim
            }
        });
        MenuItem unq_e = new MenuItem("∀");
        unq_e.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Specify Universal Quantifier Elim
            }
        });
        MenuItem exq_e = new MenuItem("∃");
        exq_e.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                // Specify Existential Quantifier Elim
            }
        });
        elim.getItems().addAll(and_e, or_e, neg_e, cont_e, cond_e, bic_e, eq_e, unq_e, exq_e);
        
        rcl_menu.getItems().addAll(premise, intro, elim);
        return rcl_menu;
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
                   nodeX = pants.getLayoutX();
                   nodeY = pants.getLayoutY();
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
                    pants.setLayoutX(nodeX);
                    pants.setLayoutY(nodeY);
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
