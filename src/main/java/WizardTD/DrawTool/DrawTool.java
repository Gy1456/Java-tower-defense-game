package WizardTD.DrawTool;

import WizardTD.Gamebase.GameSet;
import processing.core.PApplet;
import processing.core.PImage;

import java.applet.Applet;
import java.util.HashMap;

public abstract class DrawTool {
    /**
     * A HashMap of images used for rendering map elements.
     */
    protected HashMap<String, PImage> maps;
    /**
     * Initializes a DrawTool object with the provided image mappings.
     * @param maps A HashMap of images used for rendering various elements.
     */
    public DrawTool(HashMap<String, PImage> maps){
        this.maps=maps;

    }
    /**
     * Abstract method that must be implemented by subclasses to define the specific drawing behavior.
     * @param app The PApplet used for drawing.
     */
    public abstract void draw(PApplet app);
}
