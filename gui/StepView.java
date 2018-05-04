package gui;

import java.nio.CharBuffer;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
//import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import logic.AbstractStatement;
import tree.Step;
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
	Button vrfbtn;
	TextField text_box;
	MenuButton rules_menu;
	String text_highlight = DEFAULT_COLOR;
	private Pants pants;

	public StepView(Pants containingPants){
		hb_row = makeHBox();
		selected = false;
		this.pants = containingPants;
		this.contents = new Step(this,pants.getBranch());
	}

	public void select(){
		if(GuiMain.selected != null) GuiMain.selected.deselect();
		GuiMain.selected = this;
		removebtn.setVisible(true);
		rules_menu.setVisible(true);
		vrfbtn.setVisible(true);
		text_box.setDisable(false);
		selected = true;
		for(Step s : contents.getPremises()){
			s.getView().updateColor(PREMISE_COLOR);
		}
		for(Step s : contents.getConclusions()){
			s.getView().updateColor(CONCLUSION_COLOR);
		}
		updateColor("turquoise");
	}

	private void deselect(){
		selected = false;
		removebtn.setVisible(false);
		rules_menu.setVisible(false);
		vrfbtn.setVisible(false);
		text_box.setDisable(true);
		//        text_box.setStyle("-fx-opacity: 1.0;");
		for(Step s : contents.getPremises()){
			s.getView().updateColor(DEFAULT_COLOR);
		}
		for(Step s : contents.getConclusions()){
			s.getView().updateColor(DEFAULT_COLOR);
		}
		updateColor("white");
	}

	private void updateColor(String colorName){
		text_highlight = colorName;
		text_box.setStyle("-fx-control-inner-background: " + text_highlight);
	}

	private HBox makeHBox(){
		HBox row = new HBox();
		row.setSpacing(0);
                row.setPrefWidth(Pants.NODE_WIDTH - Pants.NODE_SPACING);
                row.setMaxWidth(Pants.NODE_WIDTH - Pants.NODE_SPACING);

		removebtn = new Button("X");
		removebtn.setStyle("-fx-text-fill: crimson");
		removebtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				contents.removeFromBranch();
				pants.removeStatement(StepView.this);
			}
		});
		removebtn.setVisible(false);

		text_box = new TextField("Enter Statement"){
			//http://fxexperience.com/2012/02/restricting-input-on-a-textfield/
		    @Override public void replaceText(int start, int end, String text) {
	            super.replaceText(start, end, replaceSymbols(text));
		    }
		 
		    @Override public void replaceSelection(String text) {
	            super.replaceSelection(replaceSymbols(text));
		    }
		};
		text_box.setPrefWidth(150);
//		text_box.setPrefWidth(Pants.NODE_WIDTH - 23);
		text_box.setDisable(true);
		//        text_box.setStyle("-fx-opacity: 1.0;");

        vrfbtn = new Button("✓");
        //vrfbtn.setPrefWidth(button_width);
        vrfbtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e){
            	verify();
            }
        });
        vrfbtn.setVisible(false);
		
		row.getChildren().addAll(removebtn, text_box, makeTextFieldMenus(), vrfbtn);
		row.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton()==MouseButton.SECONDARY){//right click
					if(selected) return;
					//TODO toggle link, not just add
					if(GuiMain.selected.contents.hasPremise(contents) || contents.hasPremise(GuiMain.selected.contents)){
						Step.removeLink(StepView.this.contents, GuiMain.selected.contents);
						updateColor(DEFAULT_COLOR);
					}else if(Step.createLink(GuiMain.selected.contents.getOriginRule(), StepView.this.contents, GuiMain.selected.contents)){
						updateColor(PREMISE_COLOR);	
					}else{
						updateColor(CONCLUSION_COLOR);
					}
					event.consume();
				}else if(!selected) select();
			}        	
		});
		return row;
	}

	private MenuButton makeTextFieldMenus(){
		rules_menu = new MenuButton();
		rules_menu.setMinWidth(1);
		rules_menu.setPrefWidth(50);

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
	
	
	public void verify(){
		
		contents.setStatement(text_box.getText());
		
		contents.setStatement(text_box.getText());
		for(Step s : contents.getConclusions()){
			s.setStatement(s.getView().text_box.getText());
		}
		
		Pair<Set<AbstractStatement>,Set<Step>> result = contents.verify();
		if(result == null)new Alert(AlertType.NONE, "Statement is null").show();
		else if(result.getKey().isEmpty() && result.getValue().isEmpty()){
			new Alert(AlertType.INFORMATION, "Step is correct").show();
		}else{
			String error = "";
			if(!result.getKey().isEmpty()){
				error += "\nMissing decomposition into statements:";
				for(AbstractStatement as : result.getKey()) error += "\n\t"+ as.toString();
			}
			if(!result.getValue().isEmpty()){
				error += "\nUnexpected or superfluous statements:";
				for(Step s : result.getValue()){
					error += "\n\t"+ s.getStatement().toString();
					s.getView().updateColor("pink");
				}
			}
			new Alert(AlertType.ERROR, error).show();
		}
	}
	
	private static String replaceSymbols(String text){
		CharBuffer buf = CharBuffer.allocate(text.length());
		for(int i=0; i<text.length(); ++i){
			switch(text.charAt(i)){
			case '&': buf.put('∧'); break;
			case '|': buf.put('∨'); break;
			case '$': buf.put('→'); break;
			case '%': buf.put('↔'); break;
			case '~': buf.put('¬'); break;
			case '^': buf.put('⊥'); break;
			default: buf.put(text.charAt(i));
			}
		}
		buf.rewind();
		return buf.toString();
	}
}

