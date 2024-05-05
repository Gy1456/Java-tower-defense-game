package WizardTD.UiObject;

public class DamageUI extends UI{
    /**
     * Constructor of DamageUI
     */
    public DamageUI(){
        super(650,400);
    }
    /**
     * Checks whether the given coordinates are contained within the DamageUI element.
     * @param mouseX The x-coordinate of the point to check.
     * @param mouseY The y-coordinate of the point to check.
     */
    public void contain(int mouseX, int mouseY) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + width) {
            this.contain = true;
        } else {
            this.contain = false;
        }
    }
    /**
     * Gets the x-coordinate of the DamageUI element.
     * @return The x-coordinate.
     */
    public int getx() {
        return x;
    }
    /**
     * Gets the y-coordinate of the DamageUI element.
     * @return The y-coordinate.
     */
    public int gety() {
        return y;
    }
    /**
     * Gets the width of the DamageUI element.
     * @return The width.
     */
    public int getwidth() {
        return width;
    }

    /**
     * Gets the height of the DamageUI element.
     * @return The height.
     */
    public int getheight() {
        return height;
    }
    /**
     * Checks whether a point is contained within the DamageUI element.
     * @return {@code true} if the point is contained, {@code false} otherwise.
     */
    public boolean getcotain() {
        return contain;
    }
}

