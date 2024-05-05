package WizardTD.Gamebase;

import javax.sound.sampled.Clip;
import java.util.HashMap;

public class GameSet {
    /**
     * Flag indicating whether the game is currently paused.
     */
    private boolean gamepause;
    /**
     * Flag indicating whether the game is in accelerated mode.
     */
    private boolean gameaccelerate;
    /**
     * Flag indicating whether the game has been won.
     */
    private boolean gamewin;
    /**
     * Flag indicating whether the game has been lost.
     */
    private boolean gamelose;
    /**
     * Flag indicating whether in-game sounds are enabled.
     */
    private boolean playsound;

    /**
     * A mapping of sound names to audio clips for in-game audio effects.
     */
    private HashMap<String, Clip> sound;
    /**
     * Constructs a new GameSet object to control the behavior of the game.
     * @param sound A mapping of sound names to audio clips for in-game audio effects.
     */
    public GameSet(HashMap<String,Clip> sound){
        this.gamepause=false;
        this.gameaccelerate=false;
        this.gamewin=false;
        this.gamelose=false;
        this.sound=sound;
        this.playsound=true;
    }
    /**
     * Pause-game
     */
    public void pausegame(){
        Clip soundClip = sound.get("pause");
        if (soundClip != null && !soundClip.isRunning()) {
            soundClip.setFramePosition(0);
            soundClip.start();
        }
        gamepause=true;
    }
    /**
     * start-game
     */
    public  void startgame(){
        gamepause=false;
    }
    /**
     * accelerate
     */
    public  void accelerategame(){
        Clip soundClip = sound.get("accelerate");
        if (soundClip != null && !soundClip.isRunning()) {
            soundClip.setFramePosition(0);
            soundClip.start();
        }
        this.gameaccelerate=true;
    }
    /**
     * dont accelerate
     */
    public void notaccelerategame(){
        this.gameaccelerate=false;
    }
    /**
     * win game
     */
    public  void wingame(){
        Clip soundClip = sound.get("win");
        if(playsound)
        {
            if (soundClip != null && !soundClip.isRunning()) {
                soundClip.setFramePosition(0);
                soundClip.start();
            }
            playsound=false;
        }
       this.gamewin=true;
    }
    /**
     * lose game
     */
    public void losegame(){
        Clip soundClip = sound.get("lose");
        if(playsound)
        {
            if (soundClip != null && !soundClip.isRunning()) {
                soundClip.setFramePosition(0); 
                soundClip.start();
            }
            playsound=false;
        }
        gamelose=true;
    }
    /**
     * Plays the specified sound effect by name.
     * @param soundname The name of the sound effect to play.
     */
    public void playsound(String soundname){
        Clip soundClip = sound.get(soundname);
        if (soundClip != null && !soundClip.isRunning()) {
            soundClip.setFramePosition(0);
            soundClip.start();
        }
    }
    /**
     * Checks whether the game is currently paused.
     * @return {@code true} if the game is paused, {@code false} otherwise.
     */
    public boolean isGamepause() {
        return gamepause;
    }
    /**
     * Checks whether the game has been lost.
     * @return {@code true} if the game has been lost, {@code false} otherwise.
     */
    public boolean isGamelose() {
        return gamelose;
    }
    /**
     * Checks whether the game has been won.
     * @return {@code true} if the game has been won, {@code false} otherwise.
     */
    public boolean isGamewin() {
        return gamewin;
    }
    /**
     * Checks whether the game is currently in accelerated mode.
     * @return {@code true} if the game is in accelerated mode, {@code false} otherwise.
     */
    public boolean isGameaccelerate() {
        return gameaccelerate;
    }


}
