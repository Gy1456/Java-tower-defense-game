package WizardTD.DrawTool;

import WizardTD.Gamebase.GameSet;
import WizardTD.MODEL.Board;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.util.HashMap;
public class Drawfireballs extends DrawTool{
    /**
     * Represents the game board where fireballs are drawn.
     */
    private Board board;
    /**
     * Represents the image used for rendering fireballs.
     */
    private PImage pImage;
    /**
     * Represents the game settings for controlling fireball drawing behavior.
     */
    private GameSet set;
    /**
     *The constructor Drawfireballs of  A class responsible for drawing fireballs on the game board.
     * @param board The game board on which the fireballs will be drawn.
     * @param maps A collection of image resources, including the fireball image.
     * @param set The game settings that control the drawing behavior.
     */
    public Drawfireballs(Board board,HashMap<String, PImage> maps,GameSet set){
        super(maps);
        this.board=board;
        this.set=set;
    }
    /**
     * Draws fireballs on the game board using the provided PApplet.
     * @param app The PApplet instance used for rendering.
     */
    public void draw(PApplet app){
        this.pImage=maps.get("fireball");
        for(int i=0;i<board.getTowers().size();i++){
            for(int j=0;j<board.getTowers().get(i).getFireballslist().size();j++){
                if(!(set.isGamepause()||set.isGamelose())){
                    app.image(pImage,board.getTowers().get(i).getFireballslist().get(j).getX(),
                            board.getTowers().get(i).getFireballslist().get(j).getY());
                    board.getTowers().get(i).getFireballslist().get(j).move(set.isGameaccelerate());
                }
                else{
                    app.image(pImage,board.getTowers().get(i).getFireballslist().get(j).getX(),
                            board.getTowers().get(i).getFireballslist().get(j).getY());
                }

            }
        }
    }

}
