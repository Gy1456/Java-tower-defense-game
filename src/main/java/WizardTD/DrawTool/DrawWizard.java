package WizardTD.DrawTool;

import WizardTD.MODEL.Board;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.HashMap;

public class DrawWizard extends DrawTool{
    /**
     * The game board that contains the wizard character.
     */
    private Board board;
    /**
     * The image used for rendering the wizard character.
     */
    private PImage pimage;
    /**
     * The constructor of DrawWizard,Creates a DrawWizard object for drawing the wizard character.
     * @param board The game board containing the wizard.
     * @param maps  The images and textures used for drawing.
     */
    public DrawWizard(Board board, HashMap<String,PImage> maps){
        super(maps);
        this.board=board;
    }
    /**
     * Draws the wizard character on the game board. The wizard is displayed at the top of the layer.
     * @param app The main PApplet.
     */
    public void draw(PApplet app){
        this.pimage=maps.get("wizard_house");
        app.image(pimage,board.getWizard().getx(),board.getWizard().gety());

    }
}
