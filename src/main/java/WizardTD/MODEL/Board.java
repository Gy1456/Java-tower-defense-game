package WizardTD.MODEL;
import WizardTD.App;
import WizardTD.Gamebase.GameSet;
import WizardTD.Wavemanage.Wavemangement;
import org.checkerframework.checker.units.qual.A;

import javax.sound.sampled.Clip;
import java.util.ArrayList;
import java.util.HashMap;
public class Board {
    /**
     * A two-dimensional grid representing the game map.
     */
    char[][] map;

    /**
     * The initial firing speed of towers.
     */
    private double initial_tower_firing_speed;

    /**
     * The initial range of towers.
     */
    private double initial_tower_range;

    /**
     * The initial damage dealt by towers.
     */
    private double initial_tower_damage;

    /**
     * The cost of building towers.
     */
    private double tower_cost;

    /**
     * A list of grass tiles on the game board.
     */
    private ArrayList<Grass> grass;

    /**
     * A list of path tiles on the game board.
     */
    private ArrayList<Path> path;

    /**
     * A list of shrub objects on the game board.
     */
    private ArrayList<Shrub> shrubs;

    /**
     * A list of tower objects on the game board.
     */
    private ArrayList<Tower> towers;

    /**
     * A list of selected tower objects.
     */
    private ArrayList<Tower> selectedTowers;

    /**
     * The wizard character in the game.
     */
    private Wizard wizard;

    /**
     * The mana resource for the game.
     */
    private Mana mana;

    /**
     * The game set configuration.
     */
    private GameSet set;

    /**
     * The wave management system.
     */
    private Wavemangement wavemangement;

    /**
     * A boolean flag indicating whether tower building is enabled.
     */
    private boolean buidenable;

    /**
     * A boolean flag indicating whether tower speed level up is enabled.
     */
    private boolean speedlevelupenable;

    /**
     * A boolean flag indicating whether tower range level up is enabled.
     */
    private boolean rangelevelupenable;

    /**
     * A boolean flag indicating whether tower damage level up is enabled.
     */
    private boolean damagelevelupenable;

    /**
     * A collection of sound clips used in the game.
     */
    private HashMap<String, Clip> sound;
    /**
     * Constructs a game board with the provided configuration.
     *
     * @param map The 2D grid representing the game map.
     * @param mana The mana resource for the game.
     * @param initial_tower_firing_speed The initial firing speed of towers.
     * @param initial_tower_range The initial range of towers.
     * @param initial_tower_damage The initial damage dealt by towers.
     * @param tower_cost The cost of building towers.
     * @param wavemangement The wave management system.
     * @param set The game set configuration.
     * @param sound A collection of sound clips used in the game.
     */
    public Board(char[][] map, Mana mana, double initial_tower_firing_speed, double initial_tower_range,
                 double initial_tower_damage, double tower_cost,Wavemangement wavemangement,GameSet set,HashMap<String, Clip> sound){
        this.map=map;
        this.mana=mana;
        this.wavemangement=wavemangement;
        this.initial_tower_firing_speed=initial_tower_firing_speed;
        this.initial_tower_range=initial_tower_range;
        this.initial_tower_damage=initial_tower_damage;
        this.tower_cost=tower_cost;
        this.grass=new ArrayList<>();
        this.path=new ArrayList<>();
        this.shrubs=new ArrayList<>();
        this.towers=new ArrayList<>();
        this.buidenable=false;
        this.set=set;
        this.speedlevelupenable=false;
        this.rangelevelupenable=false;
        this.damagelevelupenable=false;
        this.selectedTowers=new ArrayList<>();
        this.sound=sound;
    }
    /**
     * Determines the type of path under the specified coordinate in a 2D grid.
     * @param path The 2D grid of characters representing the path.
     * @param i The row index of the coordinate.
     * @param j The column index of the coordinate.
     * @return A string representing the type of path, such as "path0" or "path1_clockwise_90".
     */
    public String pathtype(char[][] path,int i,int j) {
        if(i==0||i==19){
            return("path0_clockwise_90");
        }
        else if(j==0||j==19){
            return("path0");
        }
        else if((path[i+1][j]=='X'||path[i-1][j]=='X')&&(path[i][j+1]!='X'&&path[i][j-1]!='X')){
            return("path0_clockwise_90");
        }
        else if((path[i+1][j]!='X'&&path[i-1][j]!='X')&&(path[i][j+1]=='X'||path[i][j-1]=='X')){
            return("path0");
        }
        else if((path[i+1][j]=='X'&&path[i][j+1]=='X')&&(path[i-1][j]!='X'&&path[i][j-1]!='X')){
            return ("path1_clockwise_-90");
        }
        else if((path[i+1][j]=='X'&&path[i][j-1]=='X')&&(path[i-1][j]!='X'&&path[i][j+1]!='X')){
            return("path1");
        }
        else if((path[i-1][j]=='X'&&path[i][j+1]=='X')&&(path[i+1][j]!='X'&&path[i][j-1]!='X')){
            return("path1_clockwise_180");
        }
        else if((path[i-1][j]=='X'&&path[i][j-1]=='X')&&(path[i+1][j]!='X'&&path[i][j+1]!='X')){
            return("path1_clockwise_90");
        }
        else if((path[i-1][j]=='X'&&path[i+1][j]=='X')&&path[i][j+1]=='X'&&path[i][j-1]!='X'){
            return("path2_clockwise_-90");
        }
        else if((path[i-1][j]=='X'&&path[i+1][j]=='X')&&path[i][j-1]=='X'&&path[i][j+1]!='X'){
            return("path2_clockwise_90");
        }
        else if((path[i][j-1]=='X'&&path[i][j+1]=='X')&&path[i+1][j]=='X'&&path[i-1][j]!='X'){
            return("path2");
        }
        else if((path[i][j-1]=='X'&&path[i][j+1]=='X')&&path[i-1][j]=='X'&&path[i+1][j]!='X'){
            return("path2_clockwise_180");
        }
        else{
            return("path3");
        }
    }
    /**
     * Initializes and creates the game map by processing the provided map data.
     * This method populates the game board with grass, shrubs, paths.
     * based on the configuration defined in the provided 2D grid (map).
     */
    public void creatboard(){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if(this.map[i][j]=='N'){
                    Grass grass=new Grass(j*32,i*32+40);
                    this.grass.add(grass);
                }
                else if(this.map[i][j]=='S'){
                    Shrub shrub=new  Shrub(j*32,40+i*32);
                    this.shrubs.add(shrub);
                }
                else if(this.map[i][j]=='X'){
                        Path path=new Path(j*32,40+i*32,pathtype(map,i,j));
                        this.path.add(path);
                }
                else{
                    wizard=new Wizard(j*32,40+i*32);
                }
            }
        }
    }
    /**
     * Determines whether the mouse cursor is positioned over a grass area, which allows for
     * the operation of creating a tower on that grass area.
     * @param mouseX The x-coordinate of the mouse cursor.
     * @param mouseY The y-coordinate of the mouse cursor.
     * @return The Grass object if the mouse is over a grass area; otherwise, null.
     */
    public Grass containsgrass(int mouseX, int mouseY) {
        for (Grass value : grass) {
            if (mouseX >= value.getx() && mouseX <= value.getx() + value.getWidth() && mouseY >=
                    value.gety() && mouseY <= value.gety() + value.getHeight()) {
                return value;
            }
        }
        return null;
    }
    /**
     * Determines if the mouse cursor is positioned over a tower, allowing for the operation of updating a tower.
     * If the mouse is over a tower, the selected tower is added to the list of selected towers.
     * @param mouseX The x-coordinate of the mouse cursor.
     * @param mouseY The y-coordinate of the mouse cursor.
     */
    public void containtower(int mouseX, int mouseY) {
        selectedTowers.clear();
        if (!towers.isEmpty()) {
            for (int i = 0; i < towers.size(); i++) {
                if (mouseX >= towers.get(i).getX() && mouseX <= towers.get(i).getX() + towers.get(i).getWidth() &&
                        mouseY >= towers.get(i).getY() && mouseY <= towers.get(i).getY() + towers.get(i).getHeight()) {
                    selectedTowers.add(towers.get(i));
                }
            }
        }
    }
    /**
     * Determine if the operation to create a tower has been executed
     */
    public void enablebuild(){
        this.buidenable= !buidenable;
        if(buidenable){
            Clip soundClip = sound.get("pick");
            if (soundClip != null && !soundClip.isRunning()) {
                soundClip.setFramePosition(0);
                soundClip.start();
            }
        }
    }
    /**
     * Determine if the operation to update tower speed has been executed
     */
    public void enablespeedlevelup(){
        this.speedlevelupenable= !speedlevelupenable;
        if(speedlevelupenable){
            set.playsound("pick");
        }
    }
    /**
     * Determine if the operation to update tower range has been executed
     */
    public void enableRangelevelup() {
        rangelevelupenable = !rangelevelupenable;
        if(rangelevelupenable){
            set.playsound("pick");
        }
    }
    /**
     * Determine if the operation to update tower damage has been executed
     */
    public void enableDamagelevelup() {
        damagelevelupenable = !damagelevelupenable;
        if(damagelevelupenable){
            set.playsound("pick");
        }
    }
    /**
     * Perform operations such as building and upgrading towers based on mouse input.
     * @param mouseX The x-coordinate of the mouse pointer.
     * @param mouseY The y-coordinate of the mouse pointer.
     */
    public void operate(int mouseX, int mouseY) {
        if (buidenable) {
            if(containsgrass(mouseX, mouseY) != null&&containsgrass(mouseX, mouseY).getisbuild() &&
                    (mana.getCurrentmana() - tower_cost > 0)){
                mana.spendmana(tower_cost);
                Tower tower = new Tower(containsgrass(mouseX, mouseY).getx(), containsgrass(mouseX, mouseY).gety(),
                            initial_tower_firing_speed, initial_tower_range, initial_tower_damage,
                            tower_cost, mana, wavemangement,sound);
                towers.add(tower);
                containsgrass(mouseX, mouseY).build();
                set.playsound("plant");
            }
            else {
                set.playsound("failto");
            }
        }
        for (int i = 0; i < towers.size(); i++) {
            Tower tower = towers.get(i);
            if (rangelevelupenable && isMouseOverTower(tower, mouseX, mouseY)) {
                if (tower.allowupgraterange()) {
                    tower.range_level_up();
                    set.playsound("levelup");
                }
                else{
                    set.playsound("failto");
                }
            }
            if (speedlevelupenable && isMouseOverTower(tower, mouseX, mouseY)) {
                if (tower.allowupgratespeed()) {
                    tower.speed_level_up();
                    set.playsound("levelup");
                }
                else{
                    set.playsound("failto");
                }
            }

            if (damagelevelupenable && isMouseOverTower(tower, mouseX, mouseY)) {
                if (tower.allowupgratedamage()) {
                    tower.damage_level_up();
                    System.out.println("damage level up");
                    set.playsound("levelup");
                }
                else{
                    set.playsound("failto");
                }
            }
        }
    }
    /**
     * Check if the mouse pointer is over a tower, enabling tower update operations.
     * @param tower The tower to check.
     * @param mouseX The x-coordinate of the mouse pointer.
     * @param mouseY The y-coordinate of the mouse pointer.
     * @return {@code true} if the mouse pointer is over the tower, {@code false} otherwise.
     */
    private boolean isMouseOverTower(Tower tower, int mouseX, int mouseY) {
        int towerX = tower.getX();
        int towerY = tower.getY();
        int towerWidth = tower.getWidth();
        int towerHeight = tower.getHeight();
        return mouseX >= towerX && mouseX <= towerX + towerWidth && mouseY >= towerY && mouseY <= towerY + towerHeight;
    }
    /**
     * Get the list of shrubs on the board.
     * @return An ArrayList of Shrub objects.
     */
    public ArrayList<Shrub> getShrubs(){
        return  shrubs;
    }
    /**
     * Get the list of grass on the board.
     * @return An ArrayList of Grass objects.
     */
    public ArrayList<Grass> getGrass(){
        return  grass;
    }
    /**
     * Get the list of paths on the board.
     * @return An ArrayList of Path objects.
     */
    public ArrayList<Path> getPath(){
        return  path;
    }
    /**
     * Get the wizard character on the board.
     * @return The Wizard object.
     */
    public Wizard getWizard(){
        return wizard;
    }
    /**
     * Get the list of selected towers for updates.
     * @return An ArrayList of Tower objects.
     */
    public ArrayList<Tower> getselectedTowers(){return selectedTowers;}
    /**
     * Get the list of towers on the board.
     * @return An ArrayList of Tower objects.
     */
    public ArrayList<Tower> getTowers(){return towers;}
    /**
     * Check if the operation to build towers is enabled.
     * @return {@code true} if tower building is enabled, {@code false} otherwise.
     */
    public boolean getbuildenable(){return buidenable;}
    /**
     * Check if the operation to upgrade tower speed is enabled.
     * @return {@code true} if tower speed upgrading is enabled, {@code false} otherwise.
     */
    public boolean getspeedlevelupenable(){return speedlevelupenable;}
    /**
     * Check if the operation to upgrade tower range is enabled.
     * @return {@code true} if tower range upgrading is enabled, {@code false} otherwise.
     */
    public boolean getRangelevelupenable(){return  rangelevelupenable;}
    /**
     * Check if the operation to upgrade tower damage is enabled.
     * @return {@code true} if tower damage upgrading is enabled, {@code false} otherwise.
     */
    public boolean getDamagelevelupenable(){return damagelevelupenable;}
}
