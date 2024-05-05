package WizardTD.Wavemanage;
import WizardTD.App;
import WizardTD.Gamebase.GameSet;
import WizardTD.MODEL.Board;
import WizardTD.MODEL.Mana;
import WizardTD.MODEL.Monsters;
import java.util.ArrayList;
public class Wavemangement {
    /**
     * A list of waves, each containing a group of monsters.
     */
    private ArrayList<Wave> waves;

    /**
     * The current monsters in the game.
     */
    private ArrayList<Monsters> currentmonster;

    /**
     * The mana resource used to control the game.
     */
    private Mana mana;

    /**
     * The game settings.
     */
    private GameSet set;
    /**
     * An array of wave durations.
     */
    private int[] waves_duration;

    /**
     * An array of wave pre-wave pause durations.
     */
    private double[] waves_pre_wave_pause;

    /**
     * An array of monster types for each wave.
     */
    private String[] waves_pre_monsters_type;

    /**
     * An array of monster health points (hp) for each wave.
     */
    private double[] waves_pre_monsters_hp;

    /**
     * An array of monster speeds for each wave.
     */
    private int[] waves_pre_monsters_speed;

    /**
     * An array of monster armor values for each wave.
     */
    private double[] waves_pre_monsters_armour;

    /**
     * An array of mana gained on killing monsters for each wave.
     */
    private double[] waves_pre_monsters_mana_gained_on_kill;

    /**
     * An array of monster quantities for each wave.
     */
    private int[] waves_pre_monsters_quantity;

    /**
     * The total time elapsed.
     */
    private int totaltime;

    /**
     * The total time elapsed for a wave countdown.
     */
    private int totaltime2;

    /**
     * The duration of the wave preparation.
     */
    private int prepare;
    /**
     * The duration of the wave preparation for the next wave.
     */
    private int prepare2;
    /**
     * The duration of the wave preparation for the next wave.
     */
    private int prepare3;
    /**
     * The duration of the current wave.
     */
    private int duration;
    /**
     * The duration of the current wave for the next wave.
     */
    private int duration2;
    /**
     * Index for the current wave.
     */
    private int index1;
    /**
     * Index for the current monster in the wave.
     */
    private int index2;
    /**
     * A counter for controlling monster release.
     */
    private int index3;
    /**
     * Index for the next wave countdown.
     */
    private int index4;
    /**
     * Countdown for the next wave.
     */
    private int nextWaveCountdown;
    /**
     * The constructor of Wavemangement.
     * @param waves_duration                 An array of wave durations in seconds.
     * @param waves_pre_wave_pause           An array of pre-wave pause durations in seconds.
     * @param waves_pre_monsters_type        An array of monster types for each wave.
     * @param waves_pre_monsters_hp          An array of monster health points (hp) for each wave.
     * @param waves_pre_monsters_speed       An array of monster speeds for each wave.
     * @param waves_pre_monsters_armour      An array of monster armor values for each wave.
     * @param waves_pre_monsters_mana_gained_on_kill An array of mana gained on killing monsters for each wave.
     * @param waves_pre_monsters_quantity    An array of monster quantities for each wave.
     * @param mana                           The mana resource used to control the game.
     * @param set                            The game settings.
     */
    public Wavemangement(int[] waves_duration, double[] waves_pre_wave_pause,
                         String[] waves_pre_monsters_type, double[] waves_pre_monsters_hp,
                         int[] waves_pre_monsters_speed, double[] waves_pre_monsters_armour,
                         double[] waves_pre_monsters_mana_gained_on_kill, int[] waves_pre_monsters_quantity, Mana mana,
                         GameSet set){
        this.waves_duration=waves_duration;
        this.waves_pre_wave_pause=waves_pre_wave_pause;
        this.waves_pre_monsters_type=waves_pre_monsters_type;
        this.waves_pre_monsters_hp=waves_pre_monsters_hp;
        this.waves_pre_monsters_speed=waves_pre_monsters_speed;
        this.waves_pre_monsters_armour=waves_pre_monsters_armour;
        this.waves_pre_monsters_mana_gained_on_kill=waves_pre_monsters_mana_gained_on_kill;
        this.waves_pre_monsters_quantity=waves_pre_monsters_quantity;
        this.currentmonster=new ArrayList<>();
        this.mana=mana;
        this.set=set;
        this.totaltime=0;
        this.totaltime2=0;
        this.index1=0;
        this.index2=0;
        this.index3=1;
        this.index4=0;
        this.nextWaveCountdown=0;
        this.waves=new ArrayList<>();
    }
    /**
     *Used to add wave numbers
     */
    public void addwave(){
        for(int i=0;i<3;i++){
            Wave wave = new Wave(waves_pre_wave_pause[i], waves_duration[i], waves_pre_monsters_type[i],
                    waves_pre_monsters_hp[i], waves_pre_monsters_speed[i], waves_pre_monsters_armour[i],
                    waves_pre_monsters_mana_gained_on_kill[i],waves_pre_monsters_quantity[i],mana);
            waves.add(wave);
        }
        for(int i=0;i<waves.size();i++)
        {
            waves.get(i).addmonster();
        }
    }
    /**
     *Used to release monsters and control the time for releasing monsters
     */
    public void releasemonster() {
        try {
            if (set.isGameaccelerate()) {
                this.totaltime += 2;
            } else {
                this.totaltime++;
            }
            prepare = (int) (waves.get(index1).getWaves_pre_wave_pause() * App.FPS);
            duration = (int) waves.get(index1).getWaves_duration() * App.FPS;
            if (totaltime < prepare) {
            } else if ((totaltime - prepare) >= (duration * index3 / (waves.get(index1).getWaves_pre_monsters_quantity()))) {
                currentmonster.add(waves.get(index1).getMonsters().get(index2));
                index2++;
                index3++;
            } else if ((totaltime) >= duration + prepare) {
                index1++;
                index2 = 0;
                index3 = 1;
                totaltime = 0;
            }
        } catch (IndexOutOfBoundsException e) {}
    }
    /**
     *Used to control the current wave number, as well as the duration and preparation time of the wave
     */
    public void timing(){
        try {
            if (set.isGameaccelerate()) {
                this.totaltime2 += 2;
            } else {
                this.totaltime2++;
            }
            this.prepare2 = (int) (waves.get(index4).getWaves_pre_wave_pause() * App.FPS);
            this.duration2 = (int) waves.get(index4).getWaves_duration() * App.FPS;
            if (index4 < waves.size() - 1) {
                this.prepare3 = (int) waves.get(index4+1).getWaves_pre_wave_pause() * App.FPS;
                nextWaveCountdown = App.FPS+duration2 + prepare3-totaltime2;
                if (totaltime2 > prepare2 + duration2 + prepare3) {
                    index4++;
                    this.totaltime2=0;
                }
            }
        }
        catch (IndexOutOfBoundsException e){}
    }
    /**
     * Retrieves the index of the next wave.
     * @return The index of the next wave.
     */
    public int getnextwave(){return index4+2;}
    /**
     * Determines if the game is won or lost.
     */
    public void gamejudeg(){
        if(index1>=waves.size()&&currentmonster.isEmpty()){
            set.wingame();
        }
        mana.lostgame();
    }
    /**
     * Retrieves the current wave index.
     * @return The current wave index.
     */
    public int getIndex1(){return index1;}
    /**
     * Retrieves the countdown for the next wave.
     * @return The countdown for the next wave.
     */
    public int getnextWaveCountdown(){return nextWaveCountdown;}
    /**
     * Retrieves the current monsters in the game.
     * @return The current monsters in the game.
     */
    public ArrayList<Monsters> getCurrentmonster(){return currentmonster;}
    /**
     * Retrieves the list of waves in the game.
     * @return The list of waves in the game.
     */
    public ArrayList<Wave> getwave(){return  waves;}
}

