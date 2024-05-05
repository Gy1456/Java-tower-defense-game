package WizardTD.MODEL;

public class Wizard extends MapObject{
    /**
     * Constructs a Wizard with the specified position.
     * @param x The X-coordinate of the Wizard's position.
     * @param y The Y-coordinate of the Wizard's position.
     */
    public Wizard(int x, int y){
        super(x,y,48,48,false);
        this.isbuilded=true;
    }
    /**
     * Gets the X-coordinate of the Wizard's position.
     * @return The X-coordinate of the Wizard.
     */
    public int getx() {
        return x;
    }

    /**
     * Gets the Y-coordinate of the Wizard's position.
     * @return The Y-coordinate of the Wizard.
     */
    public int gety() {
        return y;
    }

    /**
     * Gets the width of the Wizard.
     * @return The width of the Wizard.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the Wizard.
     * @return The height of the Wizard.
     */
    public int getHeight() {
        return height;
    }
}
