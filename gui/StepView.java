package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import tree.Step;

public class StepView extends HBox{
	
	private Step contents;
	
    StepView(Pants containingPants){
        hb_row = makeTextView();  
        text_highlight = "white";
        selected = false;
        this.pants = containingPants;
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
        text_box.setPrefWidth(Pants.NODE_WIDTH - 23);

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
                pants.removeStatement(StepView.this);
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
    private Pants pants;
}

