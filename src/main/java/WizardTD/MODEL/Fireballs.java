package WizardTD.MODEL;

public class Fireballs {
    /**
     * The Monsters associated with this tower.
     */
    private Monsters monsters;

    /**
     * The x-coordinate of the tower's position.
     */
    private int x;

    /**
     * The y-coordinate of the tower's position.
     */
    private int y;

    /**
     * The speed of the tower's attacks or actions.
     */
    private int speed;

    /**
     * The power or damage inflicted by the tower's attacks.
     */
    private double power;

    /**
     * A flag to indicate if the tower's actions are completed or done.
     */
    private boolean done;
    /**
     * Constructs a Fireballs object with the specified position, power, and associated Monsters.
     * @param x The x-coordinate of the Fireballs' position.
     * @param y The y-coordinate of the Fireballs' position.
     * @param power The power or damage inflicted by the Fireballs.
     * @param monsters The Monsters associated with the Fireballs.
     */
    public Fireballs(int x, int y, double power, Monsters monsters) {
        this.x = x;
        this.y = y;
        this.power = power;
        this.monsters = monsters;
        this.done = false;
        this.speed=5;
    }
    /**
     * Moves the Fireballs, tracking Monsters if they are within proximity and inflicting damage.
     * @param accelerate A boolean flag indicating whether to accelerate the Fireballs' movement.
     */
    public void move(boolean accelerate) {
        if (done) {
            return;
        }
        int targetX = monsters.getx()+6;
        int targetY = monsters.gety()+6;
        double armour = monsters.getArmour();
        if (this.x > targetX - 3 && this.x < targetX + 3 && this.y > targetY - 3 && this.y < targetY + 3) {
            monsters.setCurrenthp(monsters.getCurrenthp() - power * armour);
            this.done = true;
        } else {
            double angle = Math.atan2(targetY - y, targetX - x);
            if (accelerate) {
                x += (int) (speed * Math.cos(angle) * 2);
                y += (int) (speed * Math.sin(angle) * 2);
            } else {
                x += (int) (speed * Math.cos(angle));
                y += (int) (speed * Math.sin(angle));
            }
        }
        if(targetX==0||targetY==0){
            this.done=true;
        }
        if(monsters.getisdead()){
            this.done=true;
        }
    }
    /**
     * Gets the X-coordinate of the Fireballs' current position.
     * @return The X-coordinate of the Fireballs.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the Y-coordinate of the Fireballs' current position.
     * @return The Y-coordinate of the Fireballs.
     */
    public int getY() {
        return y;
    }

    /**
     * Checks whether the Fireballs have completed their movement and achieved their purpose.
     * @return `true` if the Fireballs are done; otherwise, `false`.
     */
    public boolean getisdone() {
        return done;
    }
}
