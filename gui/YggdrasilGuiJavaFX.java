
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
import javafx.scene.layout.BorderPane;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuButton;
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

class Pants extends VBox{
    Pants(){
        pants_node_width = 130;
        pants_node_spacing = 5;
        button_width = (pants_node_width / 3) - pants_node_spacing;
        text_highlight = "white"; selected = false;
        statements = new VBox();
        statements.setSpacing(1);
        visual = makePants();
    }
    
    private MenuButton makeTextFieldMenus(){
        MenuButton rcl_menu = new MenuButton();
        rcl_menu.setMinWidth(1);
        rcl_menu.setMaxWidth(23);
        
        MenuItem premise = new MenuItem("Premise");
        premise.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t){
                // Specify that statement is a premise, action, etc
            }
        });
        MenuItem contr = new MenuItem("Contradiction");
        contr.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t){
                // ''
            }
        });
        
        // Make Intro and Elim submenus
        Menu decomp = new Menu("Decomp. Rules");
        MenuItem and_dc = new MenuItem("∧");
        and_dc.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t){
                // Select statement and enter "and_dc select" state
                select();
            }
        });
        MenuItem or_dc = new MenuItem("-");
        or_dc.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t){
                // ''
            }
        });
        MenuItem cond_dc = new MenuItem("-");
        cond_dc.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t){
                // ''
            }
        });
        MenuItem neg_and_dc = new MenuItem("~&");
        neg_and_dc.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t){
                // ''
            }
        });
        MenuItem neg_or_dc = new MenuItem("~|");
        neg_or_dc.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t){
                // ''
            }
        });
        MenuItem neg_cond_dc = new MenuItem("~-");
        neg_cond_dc.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t){
                // ''
            }
        });
        MenuItem bic_dc = new MenuItem("--");
        bic_dc.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t){
                //
            }
        });
        MenuItem neg_bic_dc = new MenuItem("");
        neg_bic_dc.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t){
                //
            }
        });
        MenuItem dneg_dc = new MenuItem("");
        dneg_dc.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t){
                //
            }
        });
        
        decomp.getItems().addAll(and_dc, or_dc, cond_dc, neg_and_dc, neg_or_dc, neg_cond_dc, bic_dc, neg_bic_dc, dneg_dc);
        
        rcl_menu.getItems().addAll(premise, contr, decomp);
        return rcl_menu;
    }
    
    private HBox makeTextView(){
        HBox hb_row = new HBox();
        hb_row.setSpacing(0);
        TextField enter_st = new TextField("Enter Statement");
        enter_st.setPrefWidth(pants_node_width - 23);
        //enter_st.setStyle("-fx-text-fill: red");
        
        hb_row.getChildren().addAll(enter_st, makeTextFieldMenus());
        return hb_row;
    }
    
    private VBox makePants(){
        VBox pants = new VBox();
        //pants.setPrefSize(100, 80);
        pants.setAlignment(Pos.CENTER);
        
        // Initialize text entry and textbox properties;
        HBox init_row = makeTextView();
        statements.getChildren().add(init_row);
        
        // Buttons and button properties;
        /*Button close_br = new Button("X");
        close_br.setPrefWidth(button_width); 
        Button open_br = new Button("O");
        open_br.setPrefWidth(button_width);*/
        Button finish_br = new Button("✓");
        finish_br.setPrefWidth(button_width);
        // Stand-in for deselect.  Remove 
        finish_br.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e){
                deselect();
            }
        });
        Button more_br = new Button("⋏");
        more_br.setPrefWidth(button_width);
        Button add_st = new Button("+");
        add_st.setPrefWidth(button_width);
        add_st.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e){
                addStatement();
            }
        });
            // Button Tooltips (Hover Over)
        Tooltip fb_tt = new Tooltip("Finish Branch");
        Tooltip mb_tt = new Tooltip("Make Branch");
        Tooltip as_tt = new Tooltip("Add Statement");
        Tooltip.install(more_br, mb_tt);
        Tooltip.install(finish_br, fb_tt);
        Tooltip.install(add_st, as_tt);
        
        HBox hb_buttons = new HBox();
        hb_buttons.getChildren().addAll(add_st, more_br, finish_br);
        hb_buttons.setSpacing(pants_node_spacing);
        hb_buttons.setAlignment(Pos.CENTER);

        pants.getChildren().addAll(statements, hb_buttons);
        String vb_border = "-fx-border-color: grey;\n" +
                           "-fx-border-insets: 5;\n" +
                           "-fx-border-width: 1;\n" +
                           "-fx-border-radius: 3;\n" +
                           "-fx-border-style: solid;\n"; 
 
        pants.setStyle(vb_border);
        pants.setPadding(new Insets(pants_node_spacing, pants_node_spacing, 
                                    pants_node_spacing, pants_node_spacing));
        pants.setSpacing(pants_node_spacing);
        
        return pants;
    }
    
    private void addStatement(){
        HBox new_st = makeTextView();
        statements.getChildren().add(new_st);
    }
    
    private void select(){
        text_highlight = "yellow";
    }
    
    private void deselect(){
        text_highlight = "white";
    }
    
    double pants_node_width;
    double pants_node_spacing;
    double button_width;
    String text_highlight;
    boolean selected;
    VBox visual;
    VBox statements;
    
}

public class YggdrasilGuiJavaFX extends Application {
    
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
    Group drawer; // Holds pants.
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Yggdrasil");
        
        borderpane = new BorderPane();
        drawer = new Group();
        drawer.setLayoutX(230);
        drawer.setLayoutY(40);
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        //root.getChildren().add();
        
        Group root = new Group();
        SubScene subscene = new SubScene(root, 300, 300);
        
        // Make menu bar
        MenuBar menubar = makeMenuBar();
        menubar.prefWidthProperty().bind(stage.widthProperty());
        // Make some pants and add it to drawer
        Pants pants = new Pants();
        drawer.getChildren().add(pants.visual);
        borderpane.setTop(menubar);
        
        // Add event handlesers
        drawer.setOnMousePressed(mouseClick());
        drawer.setOnMouseDragged(mouseDrag());
        
        Scene scene = new Scene(root, 600, 500);
        root.getChildren().add(drawer);
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
                   nodeX = drawer.getLayoutX();
                   nodeY = drawer.getLayoutY();
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
                    drawer.setLayoutX(nodeX);
                    drawer.setLayoutY(nodeY);
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
