package WizardTD.MODEL;

public class Grass extends MapObject{
    /**
     * The constructor of Grass
     * @param x The X-coordinate of the grass.
     * @param y The Y-coordinate of the grass.
     */
    public Grass(int x,int y){
        super(x,y,32,32,true);
    }
    /**
     * Mark the grass as buildable by setting 'isbuilded' to false.
     */
    public void build(){
        this.isbuilded=false;
    }

    /**
     * Gets the X-coordinate of the grass.
     * @return The X-coordinate of the grass.
     */
    public int getx() {
        return x;
    }
    /**
     * Gets the Y-coordinate of the grass.
     * @return The Y-coordinate of the grass.
     */
    public int gety() {
        return y;
    }
    /**
     * Gets the width of the grass.
     * @return The width of the grass.
     */
    public int getWidth() {
        return width;
    }
    /**
     * Gets the height of the grass.
     * @return The height of the grass.
     */
    public int getHeight() {
        return height;
    }
    /**
     * Checks if the grass is buildable.
     * @return `true` if the grass is buildable; otherwise, `false`.
     */
    public boolean getisbuild() {
        return isbuilded;
    }

}
