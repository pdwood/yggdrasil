
package yggdrasil_gui;

import java.util.Vector;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 *
 * @author Mario
 */

class Drawer extends Pane{
    Drawer(){
        pane = new Pane();
        root = new Pants();
        Pants init_pants = new Pants();
        init_pants.visual.setLayoutX(220);
        init_pants.visual.setLayoutY(80);
        pane.getChildren().add(init_pants.visual);
    }
    
    Pants root;
    Pane pane;
    
    class Pants extends VBox{
        Pants(){
            pants_node_width = 130;
            pants_node_spacing = 5;
            button_width = (pants_node_width / 3) - pants_node_spacing;
            statements = new VBox();
            statements.setSpacing(1);
            visual = makePants();
            children = new Vector();
            num_statements = 0;
        }

        class Statement extends HBox{
            Statement(){
                hb_row = makeTextView();  
                text_highlight = "white";
                selected = false;
            }

            private void select(){
                selected = true;
                text_highlight = "yellow";
                updateColor();
            }

            private void deselect(){
                selected = false;
                text_highlight = "white";
                updateColor();
            }

            private void updateColor(){
                text_box.setStyle("-fx-control-inner-background: " + text_highlight);
            }

            private HBox makeTextView(){
                HBox row = new HBox();
                row.setSpacing(0);
                text_box = new TextField("Enter Statement");
                text_box.setPrefWidth(pants_node_width - 23);

                row.getChildren().addAll(text_box, makeTextFieldMenus());
                return row;
            }

            private MenuButton makeTextFieldMenus(){
                MenuButton rcl_menu = new MenuButton();
                rcl_menu.setMinWidth(1);
                rcl_menu.setMaxWidth(23);

                ContextMenu ctx_menu = new ContextMenu();
                MenuItem select_st = new MenuItem("Select");
                ctx_menu.getItems().add(select_st);
                select_st.setOnAction(new EventHandler<ActionEvent>(){
                    @Override public void handle(ActionEvent t){
                        text_highlight = "pink";
                        updateColor();

                    }
                });
                text_box.setContextMenu(ctx_menu);

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
                        select();
                        // Contr.
                    }
                });

                MenuItem remove_st = new MenuItem("Remove...");
                remove_st.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent t){
                        removeStatement(Statement.this);
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
                MenuItem or_dc = new MenuItem("∨");
                or_dc.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent t){
                        select();
                        // Or...
                    }
                });
                MenuItem cond_dc = new MenuItem("→");
                cond_dc.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent t){
                        select();
                        // ''
                    }
                });
                MenuItem neg_and_dc = new MenuItem("¬∧");
                neg_and_dc.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent t){
                        select();
                        // ''
                    }
                });
                MenuItem neg_or_dc = new MenuItem("¬∨");
                neg_or_dc.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent t){
                        select();
                        // ''
                    }
                });
                MenuItem neg_cond_dc = new MenuItem("¬→");
                neg_cond_dc.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent t){
                        select();
                        // ''
                    }
                });
                MenuItem bic_dc = new MenuItem("↔");
                bic_dc.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent t){
                        select();
                        //
                    }
                });
                MenuItem neg_bic_dc = new MenuItem("¬↔");
                neg_bic_dc.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent t){
                        select();
                        //
                    }
                });
                MenuItem dneg_dc = new MenuItem("¬¬");
                dneg_dc.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent t){
                        select();
                        //
                    }
                });

                decomp.getItems().addAll(and_dc, or_dc, cond_dc, neg_and_dc, neg_or_dc, neg_cond_dc, bic_dc, neg_bic_dc, dneg_dc);

                rcl_menu.getItems().addAll(premise, contr, decomp, remove_st);
                return rcl_menu;
            }

            HBox hb_row; 
            TextField text_box;
            String text_highlight;
            boolean selected;
        }


        private VBox makePants(){
            VBox pants = new VBox();
            //pants.setPrefSize(100, 80);
            pants.setAlignment(Pos.CENTER);

            // Initialize text entry and textbox properties;
            Statement init_row = new Statement();
            statements.getChildren().add(init_row.hb_row);

            Button finish_br = new Button("✓");
            finish_br.setPrefWidth(button_width);
            // Stand-in for deselect.  Remove 
            finish_br.setOnAction(new EventHandler<ActionEvent>(){
                @Override public void handle(ActionEvent e){
                    //
                }
            });
            Button more_br = new Button("⋏");
            more_br.setPrefWidth(button_width);
            more_br.setOnAction(new EventHandler<ActionEvent>(){
                @Override public void handle(ActionEvent e){
                    // MAKE BRANCH
                    addChild();
                }
            });
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
        
        private void addChild(){
            Pants child = new Pants();
            children.addElement(child);
            child.visual.setLayoutX(visual.getLayoutX());
            updateChildLocations();
            
            Drawer.this.pane.getChildren().add(child.visual);
        }
        
        private void updateChildLocations(){
            // Consider the number of statements in the parent node (this) and shift the children down accordingly.
            // Consider the number of children the parent contains and set their X coordinates accordingly, relative to the parent.
            // Recursively call this on all children of children of the parent(this).
            double starting_x = visual.getLayoutX() - (((pants_node_width + 30) * children.size())/3);
            for (Pants child : children){
                child.visual.setLayoutY(visual.getLayoutY() + 90 + (num_statements * 26));
                // 26 is the height of each statement object;
                child.visual.setLayoutX(starting_x);
                starting_x += (pants_node_width + 30);
            }
        }

        private void addStatement(){
            Statement new_st = new Statement();
            statements.getChildren().add(new_st.hb_row);
            num_statements++;
            updateChildLocations();
        }

        private void removeStatement(Statement to_rem){
            statements.getChildren().remove(to_rem.hb_row);
            num_statements--;
            updateChildLocations();
        }

        double pants_node_width;
        double pants_node_spacing;
        double button_width;
        String text_highlight;
        boolean selected;
        VBox visual;
        VBox statements;
        int num_statements;
        Vector<Pants> children;

    }
    
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
    Drawer drawer; // Holds pants.
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Yggdrasil");
        
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
