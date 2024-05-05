package WizardTD.DrawTool;

import WizardTD.Gamebase.GameSet;
import WizardTD.MODEL.Board;
import WizardTD.MODEL.Mana;
import WizardTD.UiObject.*;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.HashMap;

public class DrawUI extends DrawTool{
    /**
     * UI for accelerating the game.
     */
    private UI accelerateUI;
    /**
     * UI for building towers.
     */
    private UI buidUI;
    /**
     * UI for upgrading tower damage.
     */
    private UI damageUI;

    /**
     * UI for managing the mana pool.
     */
    private UI manaUI;

    /**
     * UI for pausing the game.
     */
    private UI pauseUI;

    /**
     * UI for upgrading tower range.
     */
    private UI rangeUI;
    /**
     * UI for upgrading tower speed.
     */
    private UI speedUI;
    /**
     * The game settings that store game-related information.
     */
    private GameSet gameSet;
    /**
     * The mana management object for tracking mana resources.
     */
    private Mana mana;
    /**
     * The game board .
     */
    private Board board;
    /**
     * The constructor of DrawUI,Initializes a DrawUI object with UI elements, game set, mana, board, and image mappings.
     * @param accelerateUI The UI for game acceleration.
     * @param buidUI    The UI for tower building.
     * @param damageUI    The UI for tower damage upgrade.
     * @param manaUI      The UI for managing mana.
     * @param pauseUI     The UI for pausing the game.
     * @param rangeUI     The UI for tower range upgrade.
     * @param speedUI     The UI for tower speed upgrade.
     * @param gameSet     The game set containing game state information.
     * @param mana        The mana object for managing mana.
     * @param board       The game board.
     * @param maps        A HashMap of images used for rendering various elements.
     */
    public DrawUI(UI accelerateUI, UI buidUI, UI damageUI, UI manaUI, UI pauseUI, UI rangeUI, UI speedUI, GameSet gameSet,
                  Mana mana, Board board, HashMap<String,PImage> maps) {
        super(maps);
        this.accelerateUI =accelerateUI;
        this.buidUI = buidUI;
        this.damageUI = damageUI;
        this.manaUI = manaUI;
        this.pauseUI = pauseUI;
        this.rangeUI = rangeUI;
        this.speedUI = speedUI;
        this.gameSet=gameSet;
        this.mana=mana;
        this.board=board;
    }
    /**
     * Draws a UI element, including a rectangular background and text.
     * @param app1           The PApplet used for drawing.
     * @param ui             The UI element to be drawn.
     * @param textToDisplay  The main text to display on the UI element.
     * @param additionalText Additional text to display below the main text.
     * @param fillRect1      Whether to fill the main background rectangle.
     * @param fillRect2      Whether to fill the rectangle for additional text.
     * @param c1             The red color component for the main rectangle (0-255).
     * @param c2             The green color component for the main rectangle (0-255).
     * @param c3             The blue color component for the main rectangle (0-255).
     * @param c4             The red color component for the additional text rectangle (0-255).
     * @param c5             The green color component for the additional text rectangle (0-255).
     * @param c6             The blue color component for the additional text rectangle (0-255).
     */
    public void drawUI(PApplet app1, UI ui, String textToDisplay, String additionalText, boolean fillRect1,
                       boolean fillRect2, int c1,int c2,int c3,int c4,int c5,int c6) {
        float originalStrokeWeight = app1.g.strokeWeight;
        int originalFillColor = app1.g.fillColor;
        app1.noStroke();
        app1.noFill();
        if(fillRect2) {
            app1.fill(c1, c2, c3);
        }
        if (fillRect1) {
            app1.fill(c4,c5,c6);
        }
        float x = ui.getx();
        float y = ui.gety();
        float rectWidth = ui.getwidth();
        float rectHeight = ui.getwidth();
        app1.stroke(0);
        app1.strokeWeight(2);
        app1.rect(x, y, rectWidth, rectHeight);
        app1.fill(0);
        app1.textSize(30);
        float textWidth = app1.textWidth(textToDisplay);
        float textHeight = app1.textAscent() + app1.textDescent();
        float textX = x + (rectWidth - textWidth) / 2;
        float textY = y + (rectHeight + textHeight) / 2;
        app1.text(textToDisplay, textX, textY);
        app1.fill(0);
        app1.textSize(12);
        float textHeight2 = app1.textAscent() + app1.textDescent();
        float textX2 = x + rectWidth + 5;
        float textY2 = y + (rectHeight + textHeight2) / 5;
        app1.text(additionalText, textX2, textY2);
        app1.strokeWeight(originalStrokeWeight);
        app1.fill(originalFillColor);
    }

    /**
     * Draws the cost label for the UI element, including a rectangular background and text.
     * @param app       The PApplet used for drawing.
     * @param fillRect  Whether to fill the background rectangle for the cost label.
     * @param ui        The UI element to attach the cost label to.
     * @param n The cost value to display on the label.
     */
    public void drawLabel(PApplet app,boolean fillRect,UI ui,int n){
        if(fillRect){
            float x = ui.getx()-70;
            float y = ui.gety();
            app.fill(255);
            app.stroke(0);
            app.rect(x, y, 60, 15);
            app.fill(0);
            app.textSize(15);
            String costLabel = "cost:"+n;
            app.text(costLabel, x+3, y+12);
        }

    }
    /**
     * Draws all UI elements on the screen.
     * @param app The PApplet used for drawing.
     */
    public void draw(PApplet app){
        drawUI(app,accelerateUI,"FF","speedX2",gameSet.isGameaccelerate(),
                accelerateUI.getcotain(),200,200,200, 255,255,0);
        drawUI(app,pauseUI,"P","pause",gameSet.isGamepause(),
               pauseUI.getcotain(),200,200,200, 255,255,0);
        drawUI(app,buidUI,"T","build\ntower",board.getbuildenable(),
                buidUI.getcotain(),200,200,200, 255,255,0);
        drawUI(app,rangeUI,"U1","Upgrade\nrange",board.getRangelevelupenable(),
                rangeUI.getcotain(),200,200,200, 255,255,0);
        drawUI(app,speedUI,"U2","Upgrade\nspeed",board.getspeedlevelupenable(),
                speedUI.getcotain(),200,200,200, 255,255,0);
        drawUI(app,damageUI,"U3","Upgrade\ndamage",board.getDamagelevelupenable(),
                damageUI.getcotain(),200,200,200, 255,255,0);
        drawUI(app,damageUI,"U3","Upgrade\ndamage",board.getDamagelevelupenable(),
                damageUI.getcotain(),200,200,200, 255,255,0);
        drawUI(app,manaUI,"M","Mana Pool\n"+"cost"+
                        (int)mana.getMana_pool_spell_initial_cost(),false, manaUI.getcotain()
                ,200,200,200, 255,255,0);
        drawLabel(app,manaUI.getcotain(),manaUI,(int)mana.getMana_pool_spell_initial_cost());
        drawLabel(app,buidUI.getcotain(),buidUI,100);
    }

}
