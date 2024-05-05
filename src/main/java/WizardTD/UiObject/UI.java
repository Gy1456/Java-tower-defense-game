package WizardTD.UiObject;

public abstract class UI {
    /**
     * The x-coordinate of the UI's position.
     */
    protected final int x;

    /**
     * The y-coordinate of the UI's position.
     */
    protected final int y;

    /**
     * The width of the UI.
     */
    protected final int width;

    /**
     * The height of the UI.
     */
    protected final int height;

    /**
     * A flag indicating whether the UI contains a particular point.
     */
    protected boolean contain;
    /**
     * Constructs a new UI object with the specified coordinates.
     * @param x The x-coordinate of the UI element.
     * @param y The y-coordinate of the UI element.
     */
    public UI(int x,int y){
        this.x=x;
        this.y=y;
        this.width=50;
        this.height=50;
        this.contain=false;
    }
    /**
     * Determines if the UI contains a given point specified by mouse coordinates.
     * @param mouseX The x-coordinate of the mouse.
     * @param mouseY The y-coordinate of the mouse.
     */
    public abstract void contain(int mouseX, int mouseY);

    /**
     * Gets the x-coordinate of the UI's position.
     * @return The x-coordinate.
     */
    public abstract int getx();

    /**
     * Gets the y-coordinate of the UI's position.
     * @return The y-coordinate.
     */
    public abstract int gety();

    /**
     * Gets the width of the UI.
     * @return The width.
     */
    public abstract int getwidth();

    /**
     * Gets the height of the UI.
     * @return The height.
     */
    public abstract int getheight();

    /**
     * Checks if the UI contains the specified point.
     * @return true if the UI contains the point, false otherwise.
     */
    public abstract boolean getcotain();

}
