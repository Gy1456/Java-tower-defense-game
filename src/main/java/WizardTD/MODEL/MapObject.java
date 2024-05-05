package WizardTD.MODEL;

public abstract class MapObject {
    /**
     * The x-coordinate of the map object's position.
     */
    protected final int x;

    /**
     * The y-coordinate of the map object's position.
     */
    protected final int y;

    /**
     * The width of the map object.
     */
    protected final int width;

    /**
     * The height of the map object.
     */
    protected final int height;

    /**
     * A flag indicating whether the map object is built as a tower or not.
     */
    protected boolean isbuilded;
    /**
     * The constructor of MapObject
     * @param x The x-coordinate of the map object's position.
     * @param y The y-coordinate of the map object's position.
     * @param width The width of the map object.
     * @param height The height of the map object.
     * @param isbuilded A flag indicating whether the map object is built or not.
     */
    public MapObject(int x, int y,int width,int height,boolean isbuilded) {
        this.x = x;
        this.y = y;
        this.width=width;
        this.height=height;
        this.isbuilded=isbuilded;
    }

    /**
     * Sets the build status of the map object to 'built'.
     */
    public void build() {
        this.isbuilded = true;
    }

    /**
     * Gets the width of the map object.
     * @return The width of the map object.
     */
    public abstract int getWidth();

    /**
     * Gets the height of the map object.
     * @return The height of the map object.
     */
    public abstract int getHeight();

    /**
     * Gets the x-coordinate of the map object's position.
     * @return The x-coordinate of the map object.
     */
    public abstract int getx();

    /**
     * Gets the y-coordinate of the map object's position.
     * @return The y-coordinate of the map object.
     */
    public abstract int gety();

    /**
     * Checks if the map object is built.
     * @return True if the map object is built, false otherwise.
     */
    public boolean isBuilded() {
        return isbuilded;
    }
}
