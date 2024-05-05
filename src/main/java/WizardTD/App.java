package WizardTD;
import WizardTD.DrawTool.*;
import WizardTD.Gamebase.GameSet;
import WizardTD.MODEL.*;
import WizardTD.UiObject.*;
import WizardTD.Wavemanage.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;

public class App extends PApplet {
    //Default variable
    public static final int CELLSIZE = 32;
    public static final int SIDEBAR = 120;
    public static final int TOPBAR = 40;
    public static final int BOARD_WIDTH = 20;
    public static int WIDTH = CELLSIZE*BOARD_WIDTH+SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH*CELLSIZE+TOPBAR;
    public static final int FPS = 60;
    public String configPath;
    //Created variables
    public HashMap<String,PImage> maps;
    public Board board;
    public Wavemangement wavemangement;
    public GameSet set;
    public Mana mana;
    public DrawTool drawMonsters;
    public DrawTool drawmap;
    public DrawTool drawTowers;
    public DrawTool drawfireballs;
    public DrawTool drawwave;
    public DrawTool drawWizard;
    public DrawUI drawUI;
    public UI accelerateUI;
    public UI buidUI;
    public UI damageUI;
    public UI manaUI;
    public UI pauseUI;
    public UI rangeUI;
    public UI speedUI;

    public int[] waves_duration=new int[3];
    public double[] waves_pre_wave_pause=new double[3];
    public String[] waves_pre_monsters_type=new String[3];
    public double[] waves_pre_monsters_hp=new double[3];
    public int[] waves_pre_monsters_speed=new int[3];
    public double[] waves_pre_monsters_armour=new double[3];
    public double[] waves_pre_monsters_mana_gained_on_kill=new double[3];
    public int[] waves_pre_monsters_quantity=new int[3];
    public double initial_tower_range;
    public double initial_tower_firing_speed;
    public double initial_tower_damage;
    public double initial_mana;
    public double initial_mana_cap;
    public double initial_mana_gained_per_second;
    public double tower_cost;
    public double mana_pool_spell_initial_cost;
    public double mana_pool_spell_cost_increase_per_use;
    public double mana_pool_spell_cap_multiplier;
    public double mana_pool_spell_mana_gained_multiplier;
    public Clip soundClip;
    public HashMap<String,Clip> sound;
    public Random random = new Random();
	// Feel free to add any additional methods or attributes you want. Please put classes in different files.
    public App() {
        this.configPath = "config.json";
    }
    /**
     * Initialise the setting of the window size.
     */
	@Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }
    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
	@Override
    public void setup() {
        frameRate(FPS);
        //load image
        maps=new HashMap<>();
        sound=new HashMap<>();
        set=new GameSet(sound);
        try {
            String[] sname=new String[]{"pause","fire","accelerate","plant","failto","levelup","mana","lose","win","pick"};
            for(String s:sname){
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().
                        getResource("WizardTD/"+s+".wav"));
                soundClip = AudioSystem.getClip();
                if (soundClip.isOpen()) {
                    soundClip.close();
                }
                soundClip.open(audioInputStream);
                sound.put(s,soundClip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject conf= loadJSONObject(new File(this.configPath));
        String level=conf.getString("layout");
        JSONArray waves = conf.getJSONArray("waves");
        String [] pname=new String[]{"beetle","fireball","grass","gremlin",
                "gremlin1","gremlin2","gremlin3","gremlin4","gremlin5","path0","path1","path2","path3","shrub","tower0",
                "tower1","tower2","wizard_house","worm"};
        for (String s : pname) {
            PImage image = loadImage(this.getClass().getClassLoader().getResource("WizardTD/" + s + ".png").getPath());
            maps.put(s, image);
        }
        //load initialization
        for(int i=0;i<3;i++){
            waves_duration[i]=waves.getJSONObject(i).getInt("duration");
            waves_pre_wave_pause[i]=waves.getJSONObject(i).getDouble("pre_wave_pause");
            waves_pre_monsters_type[i]=waves.getJSONObject(i).getJSONArray("monsters")
                    .getJSONObject(0).getString("type");
            waves_pre_monsters_hp[i]=waves.getJSONObject(i).getJSONArray("monsters")
                    .getJSONObject(0).getDouble("hp");
            waves_pre_monsters_speed[i]=waves.getJSONObject(i).getJSONArray("monsters")
                    .getJSONObject(0).getInt("speed");
            waves_pre_monsters_armour[i]=waves.getJSONObject(i).getJSONArray("monsters")
                    .getJSONObject(0).getDouble("armour");
            waves_pre_monsters_mana_gained_on_kill[i]=waves.getJSONObject(i).getJSONArray("monsters")
                    .getJSONObject(0).getDouble("mana_gained_on_kill");
            waves_pre_monsters_quantity[i]=waves.getJSONObject(i).getJSONArray("monsters")
                    .getJSONObject(0).getInt("quantity");
        }
        char[][]array=new char[20][20];
        try {
            Scanner scanner = new Scanner(new File(level));
            int row = 0;
            while (scanner.hasNextLine() && row < 20) {
                String line = scanner.nextLine();
                line = String.format("%-20s", line);
                for (int col = 0; col < 20; col++) {
                    char c = line.charAt(col);
                    if (c == ' ') {
                        array[row][col] = 'N';
                    } else {
                        array[row][col] = c;
                    }
                }
                row++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        initial_tower_range=conf.getDouble("initial_tower_range");
        initial_tower_firing_speed=conf.getDouble("initial_tower_firing_speed");
        initial_tower_damage=conf.getDouble("initial_tower_damage");
        initial_mana=conf.getDouble("initial_mana");
        initial_mana_cap=conf.getDouble("initial_mana_cap");
        initial_mana_gained_per_second=conf.getDouble("initial_mana_gained_per_second");
        tower_cost=conf.getDouble("tower_cost");
        mana_pool_spell_initial_cost=conf.getDouble("mana_pool_spell_initial_cost");
        mana_pool_spell_cost_increase_per_use=conf.getDouble("mana_pool_spell_cost_increase_per_use");
        mana_pool_spell_cap_multiplier=conf.getDouble("mana_pool_spell_cap_multiplier");
        mana_pool_spell_mana_gained_multiplier=conf.getDouble("mana_pool_spell_mana_gained_multiplier");
        mana=new Mana(initial_mana,initial_mana_cap,initial_mana_gained_per_second,mana_pool_spell_initial_cost,
                mana_pool_spell_cost_increase_per_use,mana_pool_spell_cap_multiplier,
                mana_pool_spell_mana_gained_multiplier,set);
        wavemangement=new Wavemangement(waves_duration,waves_pre_wave_pause,waves_pre_monsters_type,
                waves_pre_monsters_hp,waves_pre_monsters_speed,waves_pre_monsters_armour,
                waves_pre_monsters_mana_gained_on_kill,waves_pre_monsters_quantity,mana,set);
        board=new Board(array,mana,initial_tower_firing_speed,initial_tower_range,initial_tower_damage,tower_cost,
                wavemangement,set,sound);
        board.creatboard();
        accelerateUI=new AccelerateUI();
        buidUI=new BuidUI();
        damageUI=new DamageUI();
        manaUI=new ManaUI();
        pauseUI=new PauseUI();
        rangeUI=new RangeUI();
        speedUI=new SpeedUI();
        drawUI=new DrawUI(accelerateUI,buidUI,damageUI,manaUI,pauseUI,rangeUI,speedUI,set,mana,board,maps);
        drawMonsters=new DrawMonsters(board,maps,wavemangement,set);
        drawmap=new DrawMap(maps,board,mana,set);
        drawTowers=new DrawTowers(maps,board,set);
        drawfireballs=new Drawfireballs(board,maps,set);
        drawwave=new Drawwave(wavemangement,set,maps,mana);
        drawWizard=new DrawWizard(board,maps);
        wavemangement.addwave();
        // Load images during setup
		// Eg:
        // loadImage("src/main/resources/WizardTD/tower0.png");
        // loadImage("src/main/resources/WizardTD/tower1.png");
        // loadImage("src/main/resources/WizardTD/tower2.png");
    }
    /**
     * Receive key pressed signal from the keyboard.
     */
	@Override
    public void keyPressed(){
    }
    /**
     * Receive key released signal from the keyboard.
     */
	@Override
    public void keyReleased(){
        if (key == 'r' || key == 'R') {
            setup();
        }
        if(key == 'p' || key == 'P'){
            if(!set.isGamepause()) {
                set.pausegame();
            }
            else{
                set.startgame();
            }
        }
        if(key == 'f' || key == 'F'){
            if(!set.isGameaccelerate()) {
                set.accelerategame();
            }
            else{
                set.notaccelerategame();
            }
        }
        if(key == 'm' || key == 'M'){
            mana.manaspell();
        }
            if (key == 't' || key == 'T') {
                board.enablebuild();
            }
            if (key == '1') {
                board.enableRangelevelup();
            }
            if (key == '2') {
                board.enablespeedlevelup();
            }
            if (key == '3') {
                board.enableDamagelevelup();
            }
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        board.operate(mouseX,mouseY);
        if(accelerateUI.getcotain()){
            if(!set.isGameaccelerate()) {
                set.accelerategame();
            }
            else{
                set.notaccelerategame();
            }
        }
        if(pauseUI.getcotain()){
            if(!set.isGamepause()) {
                set.pausegame();
            }
            else{
                set.startgame();
            }
        }
        if(buidUI.getcotain()){
            board.enablebuild();
        }
        if(rangeUI.getcotain()){
            board.enableRangelevelup();
        }
        if(speedUI.getcotain()){
            board.enablespeedlevelup();
        }
        if(damageUI.getcotain()){
            board.enableDamagelevelup();
        }
        if(manaUI.getcotain()){
            mana.manaspell();
        }
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        accelerateUI.contain(e.getX(),e.getY());
        pauseUI.contain(e.getX(),e.getY());
        buidUI.contain(e.getX(),e.getY());
        rangeUI.contain(e.getX(),e.getY());
        speedUI.contain(e.getX(),e.getY());
        damageUI.contain(e.getX(),e.getY());
        manaUI.contain(e.getX(),e.getY());
        board.containtower(e.getX(),e.getY());
    }
    /*@Override
    public void mouseDragged(MouseEvent e) {

    }*/
    /**
     * Draw all elements in the game by current frame.
     */
	@Override
    public void draw() {
        background(100, 0, 200);
        drawmap.draw(this);
        drawTowers.draw(this);
        drawMonsters.draw(this);
        drawUI.draw(this);
        drawTowers.draw(this);
        drawfireballs.draw(this);
        drawwave.draw(this);
        drawWizard.draw(this);
    }
    public static void main(String[] args) {
        PApplet.main("WizardTD.App");
    }

    /**
     * Source: https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
     * @param pimg The image to be rotated
     * @param angle between 0 and 360 degrees
     * @return the new rotated image
     */
    public PImage rotateImageByDegrees(PImage pimg, double angle) {
        BufferedImage img = (BufferedImage) pimg.getNative();
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        PImage result = this.createImage(newWidth, newHeight, RGB);
        //BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        BufferedImage rotated = (BufferedImage) result.getNative();
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                result.set(i, j, rotated.getRGB(i, j));
            }
        }
        return result;
    }
}
