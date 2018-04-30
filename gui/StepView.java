package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
//import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import tree.Step;
import tree.Link;
import tree.Rule;

public class StepView extends HBox{
	
	private Step contents;
	
    public StepView(Pants containingPants){
        hb_row = makeTextView();  
        text_highlight = "white";
        selected = false;
        this.pants = containingPants;
        this.contents = new Step(this);
    }

    private void select(){
    	GuiMain.selected.deselect();
    	GuiMain.selected = this;
    	// TODO Reset colors of all other steps...
        selected = true;
        text_highlight = "turquoise";
        for(Link l : contents.getPremises()){
        	l.getPremise().getView().text_highlight = "green";
        	l.getPremise().getView().updateColor();
        }
        for(Link l : contents.getConclusions()){
        	l.getPremise().getView().text_highlight = "purple";
        	l.getPremise().getView().updateColor();
        }
        updateColor();
    }

    private void deselect(){
        selected = false;
        text_highlight = "white";
        for(Link l : contents.getPremises()){
        	l.getPremise().getView().text_highlight = "white";
        	l.getPremise().getView().updateColor();
        }
        for(Link l : contents.getConclusions()){
        	l.getPremise().getView().text_highlight = "white";
        	l.getPremise().getView().updateColor();
        }
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

        /*ContextMenu ctx_menu = new ContextMenu();
        MenuItem select_st = new MenuItem("Select");
        ctx_menu.getItems().add(select_st);
        select_st.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent t){
                //text_highlight = "pink";
                //updateColor();
            	Link.createLink(GuiMain.selected.contents.getOriginRule(), this, GuiMain.selected.contents); 
			}
        });
        text_box.setContextMenu(ctx_menu);*/

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
        
        
        EventHandler<ActionEvent> selectRuleHandler = new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t){
                select();
            }
        };

        // Make Intro and Elim submenus
        Menu decomp = new Menu("Decomp. Rules");
        
        //for( Rule r : Rule.DECMP_RULES ){
        	//MenuItem item = new MenuItem(r.shortName);
        for(String r : Rule.DECMP_NAMES) {
        	MenuItem item = new MenuItem(r);
        	item.setOnAction(selectRuleHandler);
        	decomp.getItems().add(item);
        }

        rcl_menu.getItems().addAll(premise, contr, decomp, remove_st);
        return rcl_menu;
    }

    HBox hb_row; 
    TextField text_box;
    String text_highlight;
    boolean selected;
    private Pants pants;
}

