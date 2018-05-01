package gui;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

public class Pants extends VBox{

	public static final double NODE_WIDTH = 130, NODE_SPACING = 5;

    Pants(Drawer drawer){
        button_width = (NODE_WIDTH / 3) - NODE_SPACING;
        statements = new VBox();
        statements.setSpacing(1);
        visual = makePants();
        children = new ArrayList<Pants>();
		lines = new ArrayList<Line>();
        num_statements = 0;
        this.drawer = drawer;
    }

    private VBox makePants(){
        VBox pants = new VBox();
        //pants.setPrefSize(100, 80);
        pants.setAlignment(Pos.CENTER);

        // Initialize text entry and textbox properties;
        StepView init_row = new StepView(this);
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
        hb_buttons.setSpacing(NODE_SPACING);
        hb_buttons.setAlignment(Pos.CENTER);

        pants.getChildren().addAll(statements, hb_buttons);
        String vb_border = "-fx-border-color: grey;\n" +
                           "-fx-border-insets: 5;\n" +
                           "-fx-border-width: 1;\n" +
                           "-fx-border-radius: 3;\n" +
                           "-fx-border-style: solid;\n";

        pants.setStyle(vb_border);
        pants.setPadding(new Insets(NODE_SPACING, NODE_SPACING,
                                    NODE_SPACING, NODE_SPACING));
        pants.setSpacing(NODE_SPACING);

        return pants;
    }

    private void addChild(){
        Pants child = new Pants(drawer);
        children.add(child);
        child.visual.setLayoutX(visual.getLayoutX());
				drawer.pane.getChildren().add(child.visual);
				Line line = new Line();
				lines.add(line);
				drawer.pane.getChildren().add(line);
        updateChildLocations();
    }

    private void updateChildLocations(){
			// Consider the number of statements in the parent node (this) and shift the children down accordingly.
			// Consider the number of children the parent contains and set their X coordinates accordingly, relative to the parent.
			// Recursively call this on all children of children of the parent(this).
			double starting_x = visual.getLayoutX();
							//visual.getLayoutX() - (((NODE_WIDTH + 30) * children.size())/3);
			if (children.size() % 2 == 0){
					starting_x -= (NODE_WIDTH / 2) + 30;
					starting_x -= ((children.size() / 2) - 1.1) * (NODE_WIDTH + 30);
			}
			else{
					starting_x -= (children.size() / 2) * (NODE_WIDTH + 30);
			}

			for (int i = 0; i < children.size(); i++){
					children.get(i).visual.setLayoutY(visual.getLayoutY() + 90 + (num_statements * 26));
					// 26 is the height of each statement object;
					children.get(i).visual.setLayoutX(starting_x);
					starting_x += (NODE_WIDTH + 30);

				 // Line line = new Line();
					lines.get(i).setStartX(visual.getLayoutX() + (NODE_WIDTH / 2));
					lines.get(i).setStartY(visual.getLayoutY() + 72 + (num_statements * 26));
					lines.get(i).setEndX(children.get(i).visual.getLayoutX() + (NODE_WIDTH / 2));
					lines.get(i).setEndY(children.get(i).visual.getLayoutY() + 5);

					if (!children.get(i).children.isEmpty()){
							children.get(i).updateChildLocations();
					}
			}
    }

    public void addStatement(){
        StepView new_st = new StepView(this);
        statements.getChildren().add(new_st.hb_row);
        new_st.select();
        num_statements++;
        updateChildLocations();
    }

    public void removeStatement(StepView to_rem){
        statements.getChildren().remove(to_rem.hb_row);
        num_statements--;
        updateChildLocations();
    }

    double button_width;
    String text_highlight;
    boolean selected;
    VBox visual;
    VBox statements;
    int num_statements;
    ArrayList<Pants> children;
    ArrayList<Line> lines;
    Drawer drawer;

}
