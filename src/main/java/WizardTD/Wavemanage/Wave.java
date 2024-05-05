package WizardTD.Wavemanage;

import WizardTD.Gamebase.GameSet;
import WizardTD.MODEL.Mana;
import WizardTD.MODEL.Monsters;

import java.util.ArrayList;

public class Wave {
    /**
     * The list of monsters for the current wave.
     */
    private ArrayList<Monsters> monsters;
    /**
     * The duration of the pre-wave pause.
     */
    private double waves_pre_wave_pause;
    /**
     * The duration of the wave itself.
     */
    private double waves_duration;
    /**
     * The type of monsters for the wave.
     */
    private String waves_pre_monsters_type;
    /**
     * The health points (hp) of monsters for the wave.
     */
    private double waves_pre_monsters_hp;
    /**
     * The speed of monsters for the wave.
     */
    private double waves_pre_monsters_speed;
    /**
     * The armor of monsters for the wave.
     */
    private double waves_pre_monsters_armour;
    /**
     * The amount of mana gained when killing monsters in the wave.
     */
    private double waves_pre_monsters_mana_gained_on_kill;
    /**
     * The quantity of monsters in the wave.
     */
    private int waves_pre_monsters_quantity;
    /**
     * The mana resource of game.
     */
    private Mana mana;
    /**
     * The constructor of wave,the class use to control the number of monsters and time for each current wave.
     * @param waves_pre_wave_pause The duration of the pre-wave pause.
     * @param waves_duration The duration of the wave itself.
     * @param waves_pre_monsters_type The type of monsters for the wave.
     * @param waves_pre_monsters_hp The health points (hp) of monsters for the wave.
     * @param waves_pre_monsters_speed The speed of monsters for the wave.
     * @param waves_pre_monsters_armour The armor of monsters for the wave.
     * @param waves_pre_monsters_mana_gained_on_kill The amount of mana gained when killing monsters in the wave.
     * @param waves_pre_monsters_quantity The quantity of monsters in the wave.
     * @param mana The mana resource used for controlling the wave.
     */
    public Wave(double waves_pre_wave_pause,double waves_duration,
                String waves_pre_monsters_type,double waves_pre_monsters_hp,double waves_pre_monsters_speed,
                double waves_pre_monsters_armour,double waves_pre_monsters_mana_gained_on_kill,
                int waves_pre_monsters_quantity,Mana mana){
        this.waves_pre_wave_pause=waves_pre_wave_pause;
        this.waves_duration=waves_duration;
        this.waves_pre_monsters_type=waves_pre_monsters_type;
        this.waves_pre_monsters_hp=waves_pre_monsters_hp;
        this.waves_pre_monsters_speed=waves_pre_monsters_speed;
        this.waves_pre_monsters_armour=waves_pre_monsters_armour;
        this.waves_pre_monsters_mana_gained_on_kill=waves_pre_monsters_mana_gained_on_kill;
        this.waves_pre_monsters_quantity=waves_pre_monsters_quantity;
        this.mana=mana;
        this.monsters=new ArrayList<>();
    }
    /**
     *Add monster with current wave number
     */
    public void addmonster(){
        for(int i=0;i<waves_pre_monsters_quantity;i++) {
            Monsters monsters1 = new Monsters(waves_pre_monsters_type, waves_pre_monsters_hp, waves_pre_monsters_speed,
                    waves_pre_monsters_armour, waves_pre_monsters_mana_gained_on_kill,mana);
            monsters.add(monsters1);
        }
    }
    /**
     * Retrieves the list of monsters for the current wave.
     * @return The list of monsters for the wave.
     */
    public ArrayList<Monsters> getMonsters() {
        return monsters;
    }
    /**
     * Retrieves the duration of the pre-wave pause.
     * @return The duration of the pre-wave pause.
     */
    public double getWaves_pre_wave_pause() {
        return waves_pre_wave_pause;
    }
    /**
     * Retrieves the duration of the wave itself.
     * @return The duration of the wave.
     */
    public double getWaves_duration() {
        return waves_duration;
    }
    /**
     * Retrieves the quantity of monsters in the wave.
     * @return The quantity of monsters in the wave.
     */
    public int getWaves_pre_monsters_quantity() {
        return waves_pre_monsters_quantity;
    }
}
