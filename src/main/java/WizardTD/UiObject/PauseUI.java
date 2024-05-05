package WizardTD.UiObject;

public class PauseUI extends UI{

    public PauseUI(){
        super(650,120);
    }
    /**
     * Checks whether the specified coordinates are contained within the element.
     * @param mouseX The x-coordinate to check.
     * @param mouseY The y-coordinate to check.
     */
    public void contain(int mouseX, int mouseY) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + width) {
            this.contain = true;
        } else {
            this.contain = false;
        }
    }
    /**
     * Gets the x-coordinate of the element.
     * @return The x-coordinate.
     */
    public int getx() {
        return x;
    }
    /**
     * Gets the y-coordinate of the element.
     * @return The y-coordinate.
     */
    public int gety() {
        return y;
    }
    /**
     * Gets the width of the element.
     * @return The width.
     */
    public int getwidth() {
        return width;
    }
    /**
     * Gets the height of the element.
     * @return The height.
     */
    public int getheight() {
        return height;
    }
    /**
     * Checks whether a point is contained within the element.
     * @return {@code true} if the point is contained, {@code false} otherwise.
     */
    public boolean getcotain() {
        return contain;
    }
}
