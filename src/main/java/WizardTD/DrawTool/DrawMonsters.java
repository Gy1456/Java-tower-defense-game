package WizardTD.DrawTool;
import WizardTD.App;
import WizardTD.Gamebase.GameSet;
import WizardTD.MODEL.*;
import processing.core.PImage;
import processing.core.PApplet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;

import WizardTD.Wavemanage.*;
public class DrawMonsters extends DrawTool{
    /**
     * Represents the wave management for controlling monster behavior.
     */
    private Wavemangement wavemangement;

    /**
     * Represents the game settings for controlling drawing behavior.
     */
    private GameSet gameSet;

    /**
     * Represents the image used for rendering monster animations.
     */
    private PImage pimage;

    /**
     * Keeps track of dead monsters and their animation state.
     */
    private HashMap<Monsters, Integer> deadmonster;
    /**
     * The constructor DrawMonsters of ,Initializes a DrawMonsters object for drawing monsters and related elements.
     * @param board        The game board for drawing monsters.
     * @param maps         A HashMap of images used for rendering monsters and animations.
     * @param wavemangement The wave management for controlling monster behavior.
     * @param gameSet      The game settings for controlling drawing behavior.
     */
    public DrawMonsters(Board board,HashMap<String, PImage> maps,Wavemangement wavemangement,GameSet gameSet){
        super(maps);
        this.wavemangement=wavemangement;
        this.gameSet=gameSet;
        this.deadmonster=new HashMap<>();
    }
    /**
     * Draws the health bar for a monster at the specified location.
     * @param app       The PApplet used for drawing.
     * @param hp        The total health points of the monster.
     * @param currenthp The current health points of the monster.
     * @param x         The x-coordinate of the health bar.
     * @param y         The y-coordinate of the health bar.
     */
    public void drawheathbar(PApplet app,double hp, double currenthp, int x, int y){
        int originalFillColor = app.g.fillColor;
        float originalStrokeWeight = app.g.strokeWeight;
        x=x-5;
        if (currenthp < 0) {
            currenthp = 0;
        }
        float redLength =(float) ((hp- currenthp) / hp );
        redLength = redLength > 0 ? redLength : 0;
        app.fill(0, 255, 0);
        app.rect(x, y-5, 32, 5);
        app.fill(255, 0, 0);
        app.rect(x+32- (int)(32 * redLength), y-5, (int)(32 * redLength), 5);
        app.fill(originalFillColor);
        app.strokeWeight(originalStrokeWeight);
    }
    /**
     * Draws death animations for monsters whose animation is in progress.
     * @param app         The PApplet used for drawing.
     * @param deadmonster A HashMap of dead monsters and their animation state.
     */
    public void drawdeadmonster(PApplet app, HashMap<Monsters, Integer> deadmonster) {
        Iterator<HashMap.Entry<Monsters, Integer>> iterator = deadmonster.entrySet().iterator();
        while (iterator.hasNext()) {
            HashMap.Entry<Monsters, Integer> entry = iterator.next();
            Monsters monsters = entry.getKey();
            int count = entry.getValue();
            if (count >= 0 && count < 4) {
                this.pimage = maps.get("gremlin1");
                app.image(pimage, monsters.getx(), monsters.gety());
                entry.setValue(count + 1);
            } else if (count >= 4 && count < 8) {
                this.pimage = maps.get("gremlin2");
                app.image(pimage, monsters.getx(), monsters.gety());
                entry.setValue(count + 1);
            } else if (count >= 8 && count < 12) {
                this.pimage = maps.get("gremlin3");
                app.image(pimage, monsters.getx(), monsters.gety());
                entry.setValue(count + 1);
            } else if (count >= 12 && count < 16) {
                this.pimage = maps.get("gremlin4");
                app.image(pimage, monsters.getx(), monsters.gety());
                entry.setValue(count + 1);
            } else if (count >= 16 && count < 20) {
                this.pimage = maps.get("gremlin5");
                app.image(pimage, monsters.getx(), monsters.gety());
                entry.setValue(count + 1);
            } else if (count >= 20) {
                iterator.remove();
            }
        }
    }
    /**
     * Draws the overall behavior of monsters, including movement, health bars, and animations.
     * @param app The PApplet used for drawing.
     */
    public void draw(PApplet app){
        if(!(gameSet.isGamepause()||gameSet.isGamelose())){
            wavemangement.releasemonster();
            wavemangement.timing();
                for(int i=0;i<wavemangement.getCurrentmonster().size();i++){
                    if(wavemangement.getCurrentmonster().get(i).getisdead()){
                        deadmonster.put(wavemangement.getCurrentmonster().get(i),0);
                        wavemangement.getCurrentmonster().remove(i);
                    }
                    else {
                        wavemangement.getCurrentmonster().get(i).move(gameSet.isGameaccelerate());
                        wavemangement.getCurrentmonster().get(i).isbanished();
                        this.pimage = maps.get("gremlin");
                        app.image(pimage, wavemangement.getCurrentmonster().get(i).getx(),
                                wavemangement.getCurrentmonster().get(i).gety());
                        wavemangement.getCurrentmonster().get(i).isdead();
                        drawheathbar(app,wavemangement.getCurrentmonster().get(i).getHp(),
                                wavemangement.getCurrentmonster().get(i).getCurrenthp(),
                                wavemangement.getCurrentmonster().get(i).getx(),
                                wavemangement.getCurrentmonster().get(i).gety());
                    }
                }
                drawdeadmonster(app,deadmonster);
            }
        else{
            for(int i=0;i<wavemangement.getCurrentmonster().size();i++){
                this.pimage = maps.get("gremlin");
                app.image(pimage, wavemangement.getCurrentmonster().get(i).getx(),
                        wavemangement.getCurrentmonster().get(i).gety());
                if(wavemangement.getCurrentmonster().get(i).getCurrenthp()<=0){
                    wavemangement.getCurrentmonster().get(i).isdead();
                }
                drawheathbar(app,wavemangement.getCurrentmonster().get(i).getHp()
                        ,wavemangement.getCurrentmonster().get(i).getCurrenthp(),
                        wavemangement.getCurrentmonster().get(i).getx(),
                        wavemangement.getCurrentmonster().get(i).gety());
            }
            drawdeadmonster(app,deadmonster);
        }
    }
}
