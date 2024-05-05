package WizardTD.DrawTool;
import WizardTD.Gamebase.GameSet;
import WizardTD.MODEL.*;
import processing.core.PImage;
import processing.core.PApplet;
import java.util.HashMap;

public class DrawTowers extends DrawTool{
    /**
     * Represents the game board where Towers are drawn.
     */
    private Board board;
    /**
     * Represents the game settings for controlling drawing behavior.
     */
    private GameSet set;
    /**
     * Represents the image used for rendering Towers.
     */
    private PImage pimage;
    /**
     * The constructor of DrawTowers,Initializes a DrawTowers object with the provided image mappings, game board, and game set.
     * @param maps  A HashMap of images used for rendering various elements.
     * @param board The game board containing towers to be drawn.
     * @param set   The game set containing game state information.
     */
    public DrawTowers(HashMap<String, PImage> maps,Board board,GameSet set){
        super(maps);
        this.maps=maps;
        this.board=board;
        this.set=set;
    }
    /**
     * Draws the range upgrade level of a tower.
     * @param app        The PApplet used for drawing.
     * @param x          The x-coordinate of the tower.
     * @param y          The y-coordinate of the tower.
     * @param rangelevel The tower's range upgrade level.
     */
    public void drawrange(PApplet app,int x,int y,int rangelevel){
        for(int i=0;i<rangelevel;i++){
            app.textSize(15);
            app.fill(255, 0, 255);
            app.text("o", x+i*5, y+7);
        }
    }
    /**
     * Draws the damage upgrade level of a tower.
     * @param app        The PApplet used for drawing.
     * @param x          The x-coordinate of the tower.
     * @param y          The y-coordinate of the tower.
     * @param powerlevel The tower's damage upgrade level.
     */
    public void drawdamage(PApplet app,int x,int y,int powerlevel){
        for(int i=0;i<powerlevel;i++){
            app.textSize(10);
            app.fill(255, 0, 255);
            app.text("X", x+i*5, y+32);
        }
    }
    /**
     * Draws the speed upgrade level of a tower.
     * @param app        The PApplet used for drawing.
     * @param x          The x-coordinate of the tower.
     * @param y          The y-coordinate of the tower.
     * @param speedlevel The tower's speed upgrade level.
     */
    public void drawspeed(PApplet app,int x,int y,int speedlevel){
        for(int i=0;i<speedlevel;i++){
            app. pushStyle();
            app.stroke(135, 206, 235);
            app.strokeWeight(speedlevel+2);
            app.noFill();
            app.rect(x+6, y+6, 20, 20);
            app.popStyle();
        }
    }
    /**
     * Draws a label in the bottom right corner regarding tower information when a tower is selected.
     * @param app The PApplet used for drawing.
     */
    public void drawlabel(PApplet app){
        if(!board.getselectedTowers().isEmpty()){
            for(Tower tower:board.getselectedTowers()){
                double speedcost=0;
                double rangecost=0;
                double damagecost=0;
                app.pushStyle();
                app.noFill();
                app.stroke(255, 204, 0);
                app.strokeWeight(3);
                app.ellipse(tower.getX()+16, tower.getY()+16, (float)tower.getrange()*2,
                        (float)tower.getrange()*2);
                app.popStyle();
                app.fill(255);
                app.rect(650, 530, 130, 50);
                app.fill(0);
                app.text("Upgrade cost", 660, 545);
                app.fill(255);
                app.rect(650, 580, 150, 70);
                app.fill(0);
                if (board.getRangelevelupenable()) {
                    rangecost = tower.getRangecost();
                    app.text("range: " +(int)rangecost, 660, 595);
                }
                if (board.getspeedlevelupenable()) {
                    speedcost = tower.getSpeedcost();
                    app.text("speed: " + (int)speedcost, 660, 610);
                }
                if (board.getDamagelevelupenable()) {
                    damagecost = tower.getDamagecost();
                    app.text("damage: " + (int)damagecost, 660, 625);
                }
                app.fill(255);
                app.rect(650, 630, 150, 40);
                app.fill(0);
                app.text("Total: " + (int)(speedcost + rangecost + damagecost), 660, 650);
            }
        }
    }
    /**
     * Draws towers on the game board along with their upgrades and labels.
     * @param app The PApplet used for drawing.
     */
    public void draw(PApplet app){
        float originalStrokeWeight = app.g.strokeWeight;
        int originalFillColor = app.g.fillColor;
        drawlabel(app);
        for(int i=0;i<board.getTowers().size();i++){
                int x=board.getTowers().get(i).getX();
                int y=board.getTowers().get(i).getY();
                int speedlevel=board.getTowers().get(i).getSpeed_level();
                int rangelevel=board.getTowers().get(i).getRange_level();
                int powerlevel=board.getTowers().get(i).getPower_level();
                if(speedlevel<1||rangelevel<1||powerlevel<1){
                    this.pimage = maps.get("tower0");
                    app.image(pimage, x, y);
                    drawspeed(app,x,y,speedlevel);
                    drawrange(app,x,y,rangelevel);
                    drawdamage(app,x,y,powerlevel);
            }
            else if((speedlevel>=1||rangelevel>=1||powerlevel>=1)&&(speedlevel<2||rangelevel<2||powerlevel<2)){
                this.pimage = maps.get("tower1");
                app.image(pimage, x, y);
                drawspeed(app,x,y,speedlevel-1);
                drawrange(app,x,y,rangelevel-1);
                drawdamage(app,x,y,powerlevel-1);
            }
            else if(speedlevel>=2||rangelevel>=2||powerlevel>=2){
                this.pimage = maps.get("tower2");
                app.image(pimage, x, y);
                drawspeed(app,x,y,speedlevel-2);
                drawrange(app,x,y,rangelevel-2);
                drawdamage(app,x,y,powerlevel-2);
            }
                if(!(set.isGamepause()||set.isGamelose())) {
                    board.getTowers().get(i).toweraction(set.isGameaccelerate());
                }
        }
        app.strokeWeight(originalStrokeWeight);
        app.fill(originalFillColor);
    }
}
