package WizardTD.MODEL;

public class Path extends MapObject{
    /**
     * The type of the Path
     */
    private String type;
    /**
     * The constructor of Path.
     * @param x    The x-coordinate of the path.
     * @param y    The y-coordinate of the path.
     * @param type The type of the path.
     */
    public Path(int x, int y,String type){
        super(x,y,32,32,false);
        this.type=type;
    }
    /**
     * Get the x-coordinate of the path.
     * @return The x-coordinate of the path.
     */
    public int getx() {
        return x;
    }

    /**
     * Get the y-coordinate of the path.
     * @return The y-coordinate of the path.
     */
    public int gety() {
        return y;
    }

    /**
     * Get the type of the path.
     * @return The type of the path.
     */
    public String gettype() {
        return type;
    }

    /**
     * Get the width of the path.
     * @return The width of the path.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of the path.
     * @return The height of the path.
     */
    public int getHeight() {
        return height;
    }
}
