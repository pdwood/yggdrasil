package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
//import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import tree.Step;
import tree.Link;
import tree.Rule;

public class StepView{// extends HBox{

	public static final String	DEFAULT_COLOR    = "white",
			SELECT_COLOR     = "turquoise",
			PREMISE_COLOR    = "lime",
			CONCLUSION_COLOR = "blueviolet";
	
	private Step contents;

	boolean selected;
	HBox hb_row;
	Button removebtn;
	TextField text_box;
	MenuButton rules_menu;
	String text_highlight = DEFAULT_COLOR;
	private Pants pants;

	public StepView(Pants containingPants){
		hb_row = makeTextView();
		selected = false;
		this.pants = containingPants;
		this.contents = new Step(this);
	}

	public void select(){
		if(GuiMain.selected != null) GuiMain.selected.deselect();
		GuiMain.selected = this;
		removebtn.setVisible(true);
		rules_menu.setVisible(true);
		text_box.setDisable(false);
		selected = true;
		for(Link l : contents.getPremises()){
			l.getPremise().getView().updateColor(PREMISE_COLOR);
		}
		for(Link l : contents.getConclusions()){
			l.getConclusion().getView().updateColor(CONCLUSION_COLOR);
		}
		updateColor("turquoise");
	}

	private void deselect(){
		selected = false;
		removebtn.setVisible(false);
		rules_menu.setVisible(false);
		text_box.setDisable(true);
		//        text_box.setStyle("-fx-opacity: 1.0;");
		for(Link l : contents.getPremises()){
			l.getPremise().getView().updateColor(DEFAULT_COLOR);
		}
		for(Link l : contents.getConclusions()){
			l.getConclusion().getView().updateColor(DEFAULT_COLOR);
		}
		updateColor("white");
	}

	private void updateColor(String colorName){
		text_highlight = colorName;
		text_box.setStyle("-fx-control-inner-background: " + text_highlight);
	}

	private HBox makeTextView(){
		HBox row = new HBox();
		row.setSpacing(0);

		removebtn = new Button("X");
		removebtn.setStyle("-fx-text-fill: crimson");
		removebtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				pants.removeStatement(StepView.this);
			}
		});
		removebtn.setVisible(false);

		text_box = new TextField("Enter Statement");
		text_box.setPrefWidth(Pants.NODE_WIDTH - 23);
		text_box.setDisable(true);
		//        text_box.setStyle("-fx-opacity: 1.0;");

		row.getChildren().addAll(removebtn, text_box, makeTextFieldMenus());
		row.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton()==MouseButton.SECONDARY){//right click
					//TODO toggle link, not just add
					Link.createLink(GuiMain.selected.contents.getOriginRule(), StepView.this.contents, GuiMain.selected.contents);
					updateColor(PREMISE_COLOR);
					event.consume();
				}else if(!selected) select();
			}        	
		});
		return row;
	}

	private MenuButton makeTextFieldMenus(){
		rules_menu = new MenuButton();
		rules_menu.setMinWidth(1);
		rules_menu.setMaxWidth(23);

		/*ContextMenu ctx_menu = new ContextMenu();
        MenuItem select_st = new MenuItem("Select");
        ctx_menu.getItems().add(select_st);
        select_st.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent t){
                //text_highlight = "pink";
                //updateColor();
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

		rules_menu.getItems().addAll(premise, contr, decomp);
		rules_menu.setVisible(false);
		return rules_menu;
	}
}

