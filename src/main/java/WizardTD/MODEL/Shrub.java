package WizardTD.MODEL;

public class Shrub extends MapObject{
    /**
     * The constructor of Shrub on the map.
     * @param x The x-coordinate of the Shrub.
     * @param y The y-coordinate of the Shrub.
     */
    public Shrub(int x, int y){
        super(x,y,32,32,false);
        this.isbuilded=false;
    }
    /**
     * Get the x-coordinate of the Shrub.
     * @return The x-coordinate.
     */
    public int getx() {
        return x;
    }

    /**
     * Get the y-coordinate of the Shrub.
     * @return The y-coordinate.
     */
    public int gety() {
        return y;
    }

    /**
     * Get the width of the Shrub.
     * @return The width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of the Shrub.
     * @return The height.
     */
    public int getHeight() {
        return height;
    }
}
