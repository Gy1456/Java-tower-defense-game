
package WizardTD.MODEL;
import java.awt.*;
import java.util.Random;
import WizardTD.Gamebase.GameSet;

public class Monsters{
    /**
     * The type of monster.
     */
    private String type;

    /**
     * A random number generator for the monster's behavior.
     */
    private Random random;

    /**
     * The total health of the monster.
     */
    private double hp;

    /**
     * The current health of the monster.
     */
    private double currenthp;

    /**
     * The speed at which the monster moves on the map.
     */
    private double speed;

    /**
     * The armor (defense) value of the monster, reducing damage taken.
     */
    private double armour;

    /**
     * The amount of mana gained when this monster is killed.
     */
    private double mana_gained_on_kill;

    /**
     * A flag indicating whether the monster is dead.
     */
    private boolean isdead;

    /**
     * The mana object associated with the monster.
     */
    private Mana mana;

    /**
     * The x-coordinate position of the monster on the map.
     */
    private int x;

    /**
     * The y-coordinate position of the monster on the map.
     */
    private int y;

    /**
     * The direction in which the monster is moving,1 is left, 2 is right, 3 is up ,4 is down
     */
    private int direction;
    /**
     * The constructor of Monster class
     * @param type The type of the monster.
     * @param hp The  health of the monster.
     * @param speed The speed at which the monster moves.
     * @param armour The armor rating of the monster.
     * @param mana_gained_on_kill The amount of mana gained when the monster is killed.
     * @param mana The Mana object associated with the monster for mana management.
     */
    public Monsters (String type,double hp,double speed,double armour,double mana_gained_on_kill,Mana mana){
        this.type=type;
        this.hp=hp;
        this.speed=speed;
        this.armour=armour;
        this.mana_gained_on_kill=mana_gained_on_kill;
        this.currenthp=hp;
        this.isdead=false;
        this.mana=mana;
        this.random = new Random();
        int randomInt = random.nextInt(2);
        if (randomInt == 0) {
            x = 0;
            y = 136;
        } else {
            x = 295;
            y = 40;
        }
    }
    /**
     * Moves the object along the defined path based on its current direction and speed.
     * @param isGameaccelerate If true, the game is in accelerated mode; otherwise, normal speed is applied.
     */
    public void move(boolean isGameaccelerate) {
        findpath();
        if(isGameaccelerate){
            if (direction == 1) {
                this.x -= speed*2;
            }
            if (direction == 2) {
                this.x += speed*2;
            }
            if (direction == 3) {
                this.y -= speed*2;
            }
            if (direction == 4) {
                this.y += speed*2;
            }
        }
        else{
            if (direction == 1) {
                this.x -= speed;
            }
            if (direction == 2) {
                this.x += speed;
            }
            if (direction == 3) {
                this.y -= speed;
            }
            if (direction == 4) {
                this.y += speed;
            }
        }
    }
    /**
     *Control the monster's pathfinding so that it can walk along the path to the Wizard
     */
    public void findpath() {
            if (x>=0&&x<5) {
                setDirection(2);
            }
            else if (y>=40&&y<45) {
                setDirection(4);
            }
            else if ((x<141&&x>131)&&(y<141&&y>131)) {
               setDirection(4);
            }
            else if((x<141&&x>131)&&(y<210&&y>200)){
                setDirection(2);
            }
            else if((x<300&&x>290)&&(y<210&&y>200)){
                setDirection(2);
            }
            else if((x<527&&x>517)&&(y<210&&y>200)){
                setDirection(4);
            }
            else if((x<527&&x>517)&&(y<308&&y>298)){
                setDirection(1);
            }
            else if((x<327&&x>317)&&(y<308&&y>298)){
                setDirection(4);
            }
            else if((x<327&&x>317)&&(y<500&&y>490)){
                setDirection(1);
            }
    }
    /**
     *Banish the monster, deduct mana points, and return it to its origin
     */
    public void isbanished(){
        if((x<133&&x>123)&&(y<500&&y>490)){
            initiatelocation();
            mana.spendmana(currenthp);
            mana.lostgame();
        }
    }
    /**
     *Determine if the monster is dead
     */
    public void isdead(){
        if(currenthp<=0){
            mana.gainmama(mana_gained_on_kill);
            this.isdead=true;
        }
    }
    /**
     * Gets the current status of whether the object is dead or alive.
     * @return True if the object is dead, otherwise false.
     */
    public boolean getisdead(){return isdead;}
    /**
     * Sets the direction of the object's movement.
     * @param direction The new direction to set.
     */
    public void setDirection(int direction){
        this.direction=direction;
    }
    /**
     * Gets the current health points of the object.
     * @return The current health points of the object.
     */
    public double getCurrenthp(){return currenthp;}
    /**
     * Sets the current health of the object.
     * @param currenthp The new health points to set.
     */
    public void setCurrenthp(double currenthp){
        this.currenthp=currenthp;
    }
    /**
     * Initializes the location of the object based on a random selection.
     */
    public void initiatelocation(){
        int randomInt = random.nextInt(2);
        if (randomInt == 0) {
            this.x = 0;
            this.y = 136;
        } else {
            this.x = 295;
            this.y = 40;
        }
    }
    /**
     * Calculates and returns the Euclidean distance between the object's current location and the specified coordinates.
     * @param x The x-coordinate of the target location.
     * @param y The y-coordinate of the target location.
     * @return The Euclidean distance between the object and the specified coordinates.
     */
    public int getDistance(int x, int y) {
        return (int) (Math.sqrt((this.x - x) * (this.x - x) + (this.y - y)
                * (this.y - y)));
    }
    /**
     * Gets the current x-coordinate of the object.
     * @return The current x-coordinate.
     */
    public int getx() {
        return x;
    }
    /**
     * Gets the current y-coordinate of the object.
     * @return The current y-coordinate.
     */
    public int gety() {
        return y;
    }
    /**
     * Sets the x-coordinate of the object's location.
     * @param x The new x-coordinate to set.
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * Sets the y-coordinate of the object's location.
     * @param y The new y-coordinate to set.
     */
    public void setY(int y) {
        this.y = y;
    }
    /**
     * Gets the type of the object.
     * @return The type of the object.
     */
    public String getType() {
        return type;
    }
    /**
     * Gets the maximum health points (HP) of the object.
     * @return The maximum health points of the object.
     */
    public double getHp() {
        return hp;
    }
    /**
     * Gets the speed of the object.
     * @return The speed of the object.
     */
    public double getSpeed() {
        return speed;
    }
    /**
     * Gets the armor value of the object.
     * @return The armor value of the object.
     */
    public double getArmour() {
        return armour;
    }
    /**
     * Gets the amount of mana gained when this object is killed.
     * @return The amount of mana gained on killing this object.
     */
    public double getMana_gained_on_kill() {
        return mana_gained_on_kill;
    }

}