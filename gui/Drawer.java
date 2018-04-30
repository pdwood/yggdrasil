package gui;

import javafx.scene.layout.Pane;

/**
 *
 * @author Mario
 */

public class Drawer extends Pane{
    Drawer(){
        pane = new Pane();
        Pants init_pants = new Pants(this);
        init_pants.visual.setLayoutX(220);
        init_pants.visual.setLayoutY(80);
        pane.getChildren().add(init_pants.visual);
    }
    
    Pane pane;
 
}

