package WizardTD.MODEL;

import WizardTD.App;
import WizardTD.Wavemanage.Wavemangement;
import org.checkerframework.checker.units.qual.Speed;

import javax.sound.sampled.Clip;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Iterator;

public class Tower {
    /**
     * The x-coordinate of the tower's position.
     */
    private int x;

    /**
     * The y-coordinate of the tower's position.
     */
    private int y;

    /**
     * The width of the tower.
     */
    private final int width;

    /**
     * The height of the tower.
     */
    private final int height;

    /**
     * The level of the tower's range upgrade.
     */
    private int Range_level;

    /**
     * The level of the tower's speed upgrade.
     */
    private int speed_level;

    /**
     * The level of the tower's power upgrade.
     */
    private int power_level;

    /**
     * The initial firing speed of the tower.
     */
    private double initial_tower_firing_speed;

    /**
     * The initial range of the tower.
     */
    private double initial_tower_range;

    /**
     * The initial damage of the tower.
     */
    private double initial_tower_damage;

    /**
     * The cost of building the tower.
     */
    private double tower_cost;

    /**
     * The cost of upgrading the tower's speed.
     */
    private double speedcost;

    /**
     * The cost of upgrading the tower's range.
     */
    private double rangecost;

    /**
     * The cost of upgrading the tower's damage.
     */
    private double damagecost;

    /**
     * The list of random monsters near the tower.
     */
    private ArrayList<Monsters> randonmonsters;

    /**
     * The list of fireballs fired by the tower.
     */
    private ArrayList<Fireballs> fireballslist;

    /**
     * A random number generator for tower behavior.
     */
    private Random random = new Random();

    /**
     * The mana resource associated with the tower.
     */
    private Mana mana;

    /**
     * The in-game time associated with tower activities.
     */
    private int time;
    /**
     * The wave management system for controlling enemy waves.
     */
    private Wavemangement wavemangement;

    /**
     * The sound effects associated with the tower.
     */
    private HashMap<String, Clip> sound;
    /**
     * Constructs a new Tower object with the specified attributes.
     * @param x The x-coordinate of the tower's position.
     * @param y The y-coordinate of the tower's position.
     * @param initial_tower_firing_speed The initial firing speed of the tower.
     * @param initial_tower_range The initial range of the tower.
     * @param initial_tower_damage The initial damage of the tower.
     * @param tower_cost The cost of building the tower.
     * @param mana The mana resource associated with the tower.
     * @param wavemangement The wave management system for controlling enemy waves.
     * @param sound The sound effects associated with the tower.
     */
    public Tower(int x, int y, double initial_tower_firing_speed, double initial_tower_range, double initial_tower_damage,
                 double tower_cost, Mana mana, Wavemangement wavemangement,HashMap<String,Clip> sound) {
        this.x = x;
        this.y = y;
        this.width =32;
        this.height =32;
        this.Range_level =0;
        this.speed_level =0;
        this.power_level =0;
        this.initial_tower_firing_speed = initial_tower_firing_speed;
        this.initial_tower_range = initial_tower_range;
        this.initial_tower_damage = initial_tower_damage;
        this.tower_cost = tower_cost;
        this.speedcost =20;
        this.rangecost =20;
        this.damagecost =20;
        this.mana = mana;
        this.time=0;
        this.wavemangement=wavemangement;
        this.fireballslist=new ArrayList<>();
        this.sound=sound;
    }
    /**
     *Speed upgrade of tower
     */
    public void speed_level_up(){
        if(mana.getCurrentmana()-speedcost>0){
            speed_level++;
            mana.spendmana(speedcost);
            speedcost=speedcost+10;
            initial_tower_firing_speed=initial_tower_firing_speed+0.5;
        }
    }
    /**
     *Damage upgrade of tower
     */
    public void damage_level_up(){
        if(mana.getCurrentmana()-damagecost>0){
            power_level++;
            mana.spendmana(damagecost);
            damagecost=damagecost+10;
            initial_tower_damage=initial_tower_damage+20;
        }
    }
    /**
     *Range of tower
     */
    public void range_level_up(){
        if(mana.getCurrentmana()-rangecost>0){
            Range_level++;
            mana.spendmana(rangecost);
            rangecost=rangecost+10;
            initial_tower_range=initial_tower_range+32;
        }
    }
    /**
     *Add monsters in range to the attack list
     */
    public void updaterandommonster () {
            this.randonmonsters=new ArrayList<>();
            if(!wavemangement.getCurrentmonster().isEmpty()){
                for (Monsters monster : wavemangement.getCurrentmonster()) {
                    if (monster.getDistance(x, y) <= initial_tower_range) {
                        randonmonsters.add(monster);
                    }
                }
        }
    }
    /**
     * Initiates an attack by the tower, randomly targeting monsters.
     * The tower's attack speed is determined by its firing speed.
     * @param accelerate If true, the attack is accelerated.
     */
    public void attack(boolean accelerate){
        if(accelerate){
            time++;
        }
        time++;
        if(time >= (int)(App.FPS / initial_tower_firing_speed)) {
            if (!randonmonsters.isEmpty()) {
                int randomInt = random.nextInt(randonmonsters.size());
                Fireballs fireballs = new Fireballs(x+10, y+10, initial_tower_damage, randonmonsters.get(randomInt));
                Clip soundClip = sound.get("fire");
                if (soundClip != null && !soundClip.isRunning()) {
                    soundClip.setFramePosition(0);
                    soundClip.start();
                }
                fireballslist.add(fireballs);
            }
                time = 0;
                Iterator<Fireballs> iterator = fireballslist.iterator();
                while (iterator.hasNext()) {
                Fireballs fireball = iterator.next();
                if (fireball.getisdone()) {
                    iterator.remove();
                }
            }
        }
    }
    /**
     * Executes the tower's actions, including searching for nearby enemies and initiating attacks.
     * @param accelerate If true, the tower's actions are accelerated.
     */
    public void toweraction(boolean accelerate){
        updaterandommonster();
        attack(accelerate);

    }
    /**
     * Determines if the current mana is sufficient to upgrade the tower's firing speed.
     * @return True if mana is sufficient; otherwise, false.
     */
    public boolean allowupgratespeed(){
        if(mana.getCurrentmana()-speedcost>0){
            return true;
        }
        return  false;
    }
    /**
     * Determines if the current mana is sufficient to upgrade the tower's range.
     * @return True if mana is sufficient; otherwise, false.
     */
    public boolean allowupgraterange(){
        if(mana.getCurrentmana()-rangecost>0){
            return true;
        }
        return  false;
    }
    /**
     * Determines if the current mana is sufficient to upgrade the tower's damage.
     * @return True if mana is sufficient; otherwise, false.
     */
    public boolean allowupgratedamage(){
        if(mana.getCurrentmana()-rangecost>0){
            return true;
        }
        return  false;
    }
    /**
     * Gets the X-coordinate of the tower's position.
     * @return The X-coordinate of the tower.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the Y-coordinate of the tower's position.
     * @return The Y-coordinate of the tower.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the width of the tower.
     * @return The width of the tower.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the tower.
     * @return The height of the tower.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the level of tower's range upgrade.
     * @return The range level of the tower.
     */
    public int getRange_level() {
        return Range_level;
    }

    /**
     * Gets the level of tower's speed upgrade.
     * @return The speed level of the tower.
     */
    public int getSpeed_level() {
        return speed_level;
    }

    /**
     * Gets the level of tower's power upgrade.
     * @return The power level of the tower.
     */
    public int getPower_level() {
        return power_level;
    }

    /**
     * Gets the cost of upgrading tower speed.
     * @return The cost of upgrading tower speed.
     */
    public double getSpeedcost() {
        return speedcost;
    }

    /**
     * Gets the cost of upgrading tower range.
     * @return The cost of upgrading tower range.
     */
    public double getRangecost() {
        return rangecost;
    }

    /**
     * Gets the cost of upgrading tower damage.
     * @return The cost of upgrading tower damage.
     */
    public double getDamagecost() {
        return damagecost;
    }

    /**
     * Gets the current range of the tower.
     * @return The current range of the tower.
     */
    public double getrange() {
        return initial_tower_range;
    }

    /**
     * Gets the current damage of the tower.
     * @return The current damage of the tower.
     */
    public double getdamage() {
        return initial_tower_damage;
    }

    /**
     * Gets the current firing speed of the tower.
     * @return The current firing speed of the tower.
     */
    public double getspeed() {
        return initial_tower_firing_speed;
    }

    /**
     * Gets the list of fireballs associated with the tower.
     * @return The list of fireballs.
     */
    public ArrayList<Fireballs> getFireballslist() {
        return fireballslist;
    }

    /**
     * Gets the list of monsters targeted by the tower.
     * @return The list of monsters.
     */
    public ArrayList<Monsters> getRandonmonsters() {
        return randonmonsters;
    }
}

