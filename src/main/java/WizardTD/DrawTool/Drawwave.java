package WizardTD.DrawTool;

import WizardTD.App;
import WizardTD.Gamebase.GameSet;
import WizardTD.MODEL.Mana;
import WizardTD.Wavemanage.Wavemangement;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.HashMap;

public class Drawwave extends DrawTool{

    /**
     * The wave management system responsible for handling waves.
     */
    private Wavemangement wavemangement;

    /**
     * The game settings that store game-related information.
     */
    private GameSet gameSet;
    /**
     * The images and textures used for drawing elements on the screen.
     */
    private HashMap<String, PImage> maps;
    /**
     * The mana management system for tracking mana resources.
     */
    private Mana mana;
    /**
     * The constructor Drawwave,Creates a Drawwave object for displaying wave-related information.
     * @param wavemangement The wave management system.
     * @param gameSet       The game settings.
     * @param maps          The images and textures.
     * @param mana          The mana management system.
     */
    public Drawwave(Wavemangement wavemangement,GameSet gameSet,HashMap<String, PImage> maps,Mana mana){
        super(maps);
        this.wavemangement=wavemangement;
        this.gameSet=gameSet;
        this.maps=maps;
        this.mana=mana;
    }
    /**
     * Draws the current wave number and prepares the countdown for the next wave.
     * @param app The main PApplet.
     */
    public void drawwave(PApplet app){
        if(wavemangement.getnextWaveCountdown()>=0){
            app.textSize(24);
            app.fill(0);
            app.text("Wave: " + wavemangement.getnextwave(), 20, 30);
            app.textSize(24);
            app.fill(0);
            app.text("Starts: " + (int)(wavemangement.getnextWaveCountdown()/ App.FPS), 120, 30);
        }
    }
    /**
     * Draws the current wave number and prepares the countdown for the next wave ,and whether the is win or lose.
     *
     * @param app The main PApplet.
     */
    public void draw(PApplet app){
        wavemangement.gamejudeg();
        if(gameSet.isGamewin()){
            app.textSize(70);
            app.fill(128, 0, 128);
            app.text("     You win\npress 'r' to restart", 100, 280);
        }
        if(gameSet.isGamelose()){
            app.textSize(70);
            app.fill(128, 0, 128);
            app.text("     You Lost\npress 'r' to restart", 100, 280);
        }
        drawwave(app);
    }
}
