package WizardTD.MODEL;

import WizardTD.App;
import WizardTD.Gamebase.GameSet;

import javax.sound.sampled.Clip;
import java.util.HashMap;

public class Mana {
    /**
     * Manages the mana resource in the game.
     */
    private double initial_mana;
    /**
     * The current amount of mana available.
     */
    private double currentmana;
    /**
     * The initial mana capacity.
     */
    private double initial_mana_cap;
    /**
     * The initial mana gained per second.
     */
    private double initial_mana_gained_per_second;
    /**
     * The initial cost of the mana pool spell.
     */
    private double mana_pool_spell_initial_cost;
    /**
     * The cost increase per use of the mana pool spell.
     */
    private double mana_pool_spell_cost_increase_per_use;

    /**
     * The multiplier for mana pool spell capacity.
     */
    private double mana_pool_spell_cap_multiplier;

    /**
     * The multiplier for mana gained through the mana pool spell.
     */
    private double mana_pool_spell_mana_gained_multiplier;

    /**
     * The X-coordinate of the mana object.
     */
    private final int x;

    /**
     * The Y-coordinate of the mana object.
     */
    private final int y;

    /**
     * The width of the mana object.
     */
    private final int width;

    /**
     * The height of the mana object.
     */
    private final int height;

    /**
     * Used to control the time it takes to obtain mana points per second
     */
    private int time;
    /**
     * Object used to control game state
     */
    private GameSet set;
    /**
     * The constructer of mana object
     * @param initial_mana The initial amount of mana.
     * @param initial_mana_cap The initial mana capacity.
     * @param initial_mana_gained_per_second The initial rate of mana gained per second.
     * @param mana_pool_spell_initial_cost The initial cost of the mana pool spell.
     * @param mana_pool_spell_cost_increase_per_use The cost increase per use of the mana pool spell.
     * @param mana_pool_spell_cap_multiplier The multiplier for the mana pool spell capacity.
     * @param mana_pool_spell_mana_gained_multiplier The multiplier for mana gained through the mana pool spell.
     * @param set The GameSet to which this mana object belongs.
     */
    public Mana(double initial_mana,double initial_mana_cap,double initial_mana_gained_per_second,
                double mana_pool_spell_initial_cost,double mana_pool_spell_cost_increase_per_use,
                double mana_pool_spell_cap_multiplier,double mana_pool_spell_mana_gained_multiplier,GameSet set
               ){
        this.initial_mana=initial_mana;
        this.initial_mana_cap=initial_mana_cap;
        this.initial_mana_gained_per_second=initial_mana_gained_per_second;
        this.mana_pool_spell_initial_cost=mana_pool_spell_initial_cost;
        this.mana_pool_spell_cost_increase_per_use=mana_pool_spell_cost_increase_per_use;
        this.mana_pool_spell_cap_multiplier=mana_pool_spell_cap_multiplier;
        this.mana_pool_spell_mana_gained_multiplier=mana_pool_spell_mana_gained_multiplier;
        this.currentmana=initial_mana;
        this.x=330;
        this.y=10;
        this.width=370;
        this.height=20;
        this.set=set;
        this.time=0;
    }
    /**
     * Spell mana, used to expand the mana cap
     */
    public void manaspell(){
        if(currentmana-mana_pool_spell_initial_cost>0){
            this.currentmana=currentmana-mana_pool_spell_initial_cost;
            this.initial_mana_cap=initial_mana_cap*mana_pool_spell_cap_multiplier;
            this.initial_mana_gained_per_second=initial_mana_gained_per_second*mana_pool_spell_mana_gained_multiplier;
            this.mana_pool_spell_mana_gained_multiplier=mana_pool_spell_mana_gained_multiplier+0.1;
            this.mana_pool_spell_initial_cost=mana_pool_spell_initial_cost+mana_pool_spell_cost_increase_per_use;
            set.playsound("mana");
        }
        else{
            set.playsound("failto");
        }
    }
    /**
     * Used to obtain mana obtained by killing monsters
     * @param mana The amount of mana gained from defeating monsters
     */
    public void gainmama(double mana){
        if(this.currentmana+mana*mana_pool_spell_mana_gained_multiplier>=initial_mana_cap){
            this.currentmana=initial_mana_cap;
        }
        else{
            this.currentmana=currentmana+(mana*mana_pool_spell_mana_gained_multiplier);
        }
    }
    /**
     * Used to obtain mana per second
     */
    public void gianmanapersecond(){
        time++;
        if(!(set.isGamepause()||set.isGamelose()||set.isGamewin())) {
            if (set.isGameaccelerate()) {
                time++;
            }
        if (this.currentmana + initial_mana_gained_per_second * mana_pool_spell_mana_gained_multiplier >= initial_mana_cap) {
            this.currentmana = initial_mana_cap;
        } else {
                if (time > App.FPS) {
                    this.time = 0;
                    this.currentmana = (currentmana + (initial_mana_gained_per_second *
                            mana_pool_spell_mana_gained_multiplier));
                }
            }
        }
    }
    /**
     * Monsters being exiled and upgraded, building towers, and extending mana caps all require mana
     * @param currenthp The amount of mana to be spent for the action.
     */
    public void spendmana(double currenthp){
        System.out.println(currentmana);
        this.currentmana=currentmana-currenthp;
    }
    /**
     * Set the current mana value for testing
     * @param mana The mana value to set for testing.
     */
    public void setcurrentmana(double mana){
        this.currentmana=mana;
    }
    /**
     * If the mana less than 0,the game will lose
     */
    public void lostgame(){
        if(currentmana<0){
            System.out.println(currentmana);
            set.losegame();
        }
    }
    /**
     * Returns the initial mana value.
     * @return The initial mana value representing the starting amount of mana.
     */
    public double getInitialMana() {
        return initial_mana;
    }
    /**
     * Returns the rate at which mana is gained per second.
     * @return The initial mana gained per second value.
     */
    public double getInitialManaGainedPerSecond() {
        return initial_mana_gained_per_second;
    }
    /**
     * Returns the initial cost of the mana pool spell.
     * @return The initial cost of the mana pool spell.
     */
    public double getManaPoolSpellInitialCost() {
        return mana_pool_spell_initial_cost;
    }
    /**
     * Returns the increase in the cost of the mana pool spell per use.
     * @return The cost increase of the mana pool spell per use.
     */
    public double getManaPoolSpellCostIncreasePerUse() {
        return mana_pool_spell_cost_increase_per_use;
    }
    /**
     * Returns the multiplier for increasing the mana pool spell's cap.
     * @return The multiplier used for increasing the mana pool spell's cap.
     */
    public double getManaPoolSpellCapMultiplier() {
        return mana_pool_spell_cap_multiplier;
    }
    /**
     * Returns the multiplier for increasing the mana gained by the mana pool spell.
     * @return The multiplier for increasing the mana gained by the mana pool spell.
     */
    public double getManaPoolSpellManaGainedMultiplier() {
        return mana_pool_spell_mana_gained_multiplier;
    }
    /**
     * Returns the current cost of the mana pool spell.
     * @return The current cost of the mana pool spell.
     */
    public double getMana_pool_spell_initial_cost() {
        return mana_pool_spell_initial_cost;
    }
    /**
     * Returns the current mana cap.
     * @return The current mana cap value.
     */
    public double getInitial_mana_cap() {
        return initial_mana_cap;
    }
    /**
     * Returns the x-coordinate of the mana bar's position.
     * @return The x-coordinate of the mana bar's position on the screen.
     */
    public int getx() {
        return x;
    }
    /**
     * Returns the y-coordinate of the mana bar's position.
     * @return The y-coordinate of the mana bar's position on the screen.
     */
    public int gety() {
        return y;
    }
    /**
     * Returns the width of the mana bar.
     * @return The width of the mana bar.
     */
    public int getWidth() {
        return width;
    }
    /**
     * Returns the height of the mana bar.
     * @return The height of the mana bar.
     */
    public int getHeight() {
        return height;
    }
    /**
     * Returns the current mana level.
     * @return The current mana level, representing the amount of mana available.
     */
    public double getCurrentmana() {
        return currentmana;
    }
}
