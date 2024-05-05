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
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.*;
import javax.sound.sampled.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import WizardTD.MODEL.Mana;
import processing.event.MouseEvent;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class Testboard extends PApplet{
    char[][] map;
    private int[] waves_duration = new int[3];
    private double[] waves_pre_wave_pause = new double[3];
    private String[] waves_pre_monsters_type = new String[3];
    private double[] waves_pre_monsters_hp = new double[3];
    private int[] waves_pre_monsters_speed = new int[3];
    private double[] waves_pre_monsters_armour = new double[3];
    private double[] waves_pre_monsters_mana_gained_on_kill = new double[3];
    private int[] waves_pre_monsters_quantity = new int[3];
    private double initial_tower_range;
    private double initial_tower_firing_speed;
    private double initial_tower_damage;
    private double initial_mana;
    private double initial_mana_cap;
    private double initial_mana_gained_per_second;
    private double tower_cost;
    private double mana_pool_spell_initial_cost;
    private double mana_pool_spell_cost_increase_per_use;
    private double mana_pool_spell_cap_multiplier;
    private double mana_pool_spell_mana_gained_multiplier;
    private GameSet gameSet;
    private Board board;
    private Mana mana;
    private Wavemangement wavemangement;
    private UI accelerateUI;
    private UI buidUI;
    private UI damageUI;
    private UI manaUI;
    private UI pauseUI;
    private UI rangeUI;
    private UI speedUI;
    private Clip soundClip;
    private DrawTool drawMonsters;
    private DrawMap drawmap;
    private DrawTool drawTowers;
    private DrawTool drawfireballs;
    private DrawTool drawwave;
    private DrawTool drawWizard;
    private DrawUI drawUI;
    private HashMap<String, PImage> maps;
    private HashMap<String, Clip> sound;
    /**
     * initial test
     */
    public void setup(){
        maps=new HashMap<>();
        map=new char[20][20];
        sound=new HashMap<String,Clip>();
        JSONObject conf= loadJSONObject(new File("config.json"));
        String level=conf.getString("layout");
        JSONArray waves = conf.getJSONArray("waves");
        String [] pname=new String[]{"beetle","fireball","grass","gremlin",
                "gremlin1","gremlin2","gremlin3","gremlin4","gremlin5","path0","path1","path2","path3","shrub","tower0",
                "tower1","tower2","wizard_house","worm"};
        for (String s : pname) {
            PImage image = new PImage();
            maps.put(s, image);
        }
        try {
            Scanner scanner = new Scanner(new File(level));
            int row = 0;
            while (scanner.hasNextLine() && row < 20) {
                String line = scanner.nextLine();
                for (int col = 0; col < 20 && col < line.length(); col++) {
                    char c = line.charAt(col);
                    if (c == ' ') {
                        map[row][col] = 'N';
                    } else {
                        map[row][col] = c;
                    }
                }
                row++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String[] sname=new String[]{"pause","fire","accelerate","plant","failto","levelup","mana","lose","win","pick"};
            for(String s:sname){
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().
                        getResource("WizardTD/"+s+".wav"));
                soundClip = AudioSystem.getClip();
                soundClip.open(audioInputStream);
                sound.put(s,soundClip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        gameSet = new GameSet(sound);
        mana=new Mana(initial_mana,initial_mana_cap,initial_mana_gained_per_second,mana_pool_spell_initial_cost,
                mana_pool_spell_cost_increase_per_use,mana_pool_spell_cap_multiplier,
                mana_pool_spell_mana_gained_multiplier,gameSet);
        wavemangement=new Wavemangement(waves_duration,waves_pre_wave_pause,waves_pre_monsters_type,
                waves_pre_monsters_hp,waves_pre_monsters_speed,waves_pre_monsters_armour,
                waves_pre_monsters_mana_gained_on_kill,waves_pre_monsters_quantity,mana,gameSet);
        board=new Board(map,mana,initial_tower_firing_speed,initial_tower_range,initial_tower_damage,tower_cost,
                wavemangement,gameSet,sound);
        board.creatboard();
        accelerateUI=new AccelerateUI();
        buidUI=new BuidUI();
        damageUI=new DamageUI();
        manaUI=new ManaUI();
        pauseUI=new PauseUI();
        rangeUI=new RangeUI();
        speedUI=new SpeedUI();
        drawUI=new DrawUI(accelerateUI,buidUI,damageUI,manaUI,pauseUI,rangeUI,speedUI,gameSet,mana,board,maps);
        drawMonsters=new DrawMonsters(board,maps,wavemangement,gameSet);
        drawmap=new DrawMap(maps,board,mana,gameSet);
        drawTowers=new DrawTowers(maps,board,gameSet);
        drawfireballs=new Drawfireballs(board,maps,gameSet);
        drawwave=new Drawwave(wavemangement,gameSet,maps,mana);
        drawWizard=new DrawWizard(board,maps);
        wavemangement.addwave();
    }
    /**
     * Test whether the layout of the map is normal
     */
    @Test
    public void testmap(){
        setup();
        assertEquals(board.getGrass().get(0).getx(),32);
        assertEquals(board.getGrass().get(0).gety(),40);
        assertEquals(board.getGrass().get(0).getHeight(),32);
        assertEquals(board.getGrass().get(0).getWidth(),32);
        assertEquals(board.getShrubs().get(0).getx(),0);
        assertEquals(board.getShrubs().get(0).gety(),40);
        assertEquals(board.getShrubs().get(0).getHeight(),32);
        assertEquals(board.getShrubs().get(0).getWidth(),32);
        assertEquals(board.getWizard().getx(),96);
        assertEquals(board.getWizard().gety(),488);
        assertEquals(board.getWizard().getHeight(),48);
        assertEquals(board.getWizard().getWidth(),48);
        assertEquals(board.getPath().get(0).getx(),288);
        assertEquals(board.getPath().get(0).gety(),40);
        assertEquals(board.getWizard().getHeight(),48);
        assertEquals(board.getWizard().getWidth(),48);
        assertEquals((board.getPath().get(0).gettype()),"path0_clockwise_90");
        assertEquals(board.getPath().get(3).gettype(),"path0");
        assertEquals(board.getPath().get(7).gettype(),"path1");
        assertEquals(board.getPath().get(13).gettype(),"path2_clockwise_180");
        assertEquals(board.getPath().get(15).gettype(),"path3");
        assertEquals(board.getPath().get(29).gettype(),"path1_clockwise_-90");
        assertEquals(board.getPath().get(30).gettype(),"path2_clockwise_90");
    }
    /**
     * Test whether the conditions for building the tower are normal
     */
    @Test
    public void testbuildtower(){
        setup();
        for(int i=0;i<board.getGrass().size();i++){
            assertTrue(board.getGrass().get(i).isBuilded());//The tower can be built on grass
        }
        for(int i=0;i<board.getPath().size();i++){
            assertFalse(board.getPath().get(i).isBuilded());//The tower can't be built on path
        }
        for(int i=0;i<board.getShrubs().size();i++){
            assertFalse(board.getShrubs().get(i).isBuilded());//The tower can't be built on shrub
        }
        board.operate(32,40);//Tower cannot be built while construction is not active
        assertEquals(board.getTowers().size(),0);
        board.enablebuild();
        assertTrue(board.getbuildenable());
        assertEquals(board.getTowers().size(),0);//Tower construction cannot be activated without operation
        mana.setcurrentmana(0);
        board.operate(32,40);
        assertEquals(board.getTowers().size(),0);//Unable to build tower when mana is insufficient
        mana.setcurrentmana(200);
        board.operate(32,40);
        assertEquals(board.getTowers().size(),1);
        assertFalse(board.getGrass().get(0).getisbuild());//After construction, the grass block cannot be used to build a tower anymore
        board.containtower(100,200);//Determine if the mouse contains a tower
        assertEquals(board.getselectedTowers().size(),0);
        board.containtower(32,40);
        assertEquals(board.getselectedTowers().size(),1);
    }
    /**
     * Test whether the conditions of the upgrade tower are normal
     */
    @Test
    public void testtowerlevelup(){
        setup();
        board.enablebuild();
        board.operate(32,40);
        board.enablebuild();
        mana.setcurrentmana(0);//Unable to upgrade when mana is insufficient
        board.enablespeedlevelup();
        assertTrue(board.getspeedlevelupenable());
        board.enableDamagelevelup();
        assertTrue(board.getDamagelevelupenable());
        board.enableRangelevelup();
        assertTrue(board.getRangelevelupenable());
        board.operate(32,40);
        assertEquals(board.getTowers().get(0).getPower_level(),0);
        assertEquals(board.getTowers().get(0).getSpeed_level(),0);
        assertEquals(board.getTowers().get(0).getRange_level(),0);
        mana.setcurrentmana(200);
        board.operate(320,400);//Clicking cannot upgrade when the mouse position is not on the tower
        assertEquals(board.getTowers().get(0).getPower_level(),0);
        assertEquals(board.getTowers().get(0).getSpeed_level(),0);
        assertEquals(board.getTowers().get(0).getRange_level(),0);
        board.operate(32,40);//Successfully upgraded, all attributes of the tower are prompted, and the cost increases
        assertEquals(board.getTowers().get(0).getPower_level(),1);
        assertEquals(board.getTowers().get(0).getSpeed_level(),1);
        assertEquals(board.getTowers().get(0).getRange_level(),1);
        assertEquals(board.getTowers().get(0).getDamagecost(),30);
        assertEquals(board.getTowers().get(0).getRangecost(),30);
        assertEquals(board.getTowers().get(0).getSpeedcost(),30);
        assertEquals(board.getTowers().get(0).getrange(),128);
        assertEquals(board.getTowers().get(0).getdamage(),60);
        assertEquals(board.getTowers().get(0).getspeed(),2);
        board.enablespeedlevelup();
        assertFalse(board.getspeedlevelupenable());
        board.enableDamagelevelup();
        assertFalse(board.getDamagelevelupenable());
        board.enableRangelevelup();
        assertFalse(board.getRangelevelupenable());
    }
    /**
     * Test whether the number, duration, and interval of monsters per wave are normal
     */
    @Test
    public void testwave(){
        setup();
        assertEquals(wavemangement.getwave().size(),3);//There are only 3 waves in the JSON file
        assertEquals(wavemangement.getwave().get(0).getMonsters().size(),//Test whether the values of each wave are normal
                wavemangement.getwave().get(0).getWaves_pre_monsters_quantity());
        assertEquals(wavemangement.getwave().get(0).getWaves_pre_wave_pause(),0.5);
        assertEquals(wavemangement.getwave().get(0).getWaves_duration(),8);
        assertEquals(wavemangement.getwave().get(1).getMonsters().size(),//Test whether the values of each wave are normal
                wavemangement.getwave().get(1).getWaves_pre_monsters_quantity());
        assertEquals(wavemangement.getwave().get(1).getWaves_pre_wave_pause(),10);
        assertEquals(wavemangement.getwave().get(1).getWaves_duration(),5);
        assertEquals(wavemangement.getwave().get(1).getMonsters().size(),//Test whether the values of each wave are normal
                wavemangement.getwave().get(1).getWaves_pre_monsters_quantity());
        assertEquals(wavemangement.getwave().get(1).getWaves_pre_wave_pause(),10);
        assertEquals(wavemangement.getwave().get(1).getWaves_duration(),5);
    }
    /**
     * Test whether the release time, wave front preparation time, and duration of the monster are normal
     */
    @Test
    public void testwavemanage(){
        setup();
        assertEquals(wavemangement.getIndex1(),0);
        assertEquals(wavemangement.getCurrentmonster().size(),0);//Do not release monsters at the beginning of the interval
        for(int i=0;i<78;i++){
            wavemangement.releasemonster();//60 seconds per frame, according to the requirement, the time for the first wave of monster release should be 1.3 seconds, which is frame 78
        }
        assertEquals(wavemangement.getCurrentmonster().size(),1);
        for(int i=0;i<433;i++){
            wavemangement.releasemonster();
        }
        assertEquals(wavemangement.getCurrentmonster().size(),10);
        assertEquals(wavemangement.getIndex1(),1);//The first wave is over, and it's time to prepare for the second wave
        setup();//Reset Wavenumber
        wavemangement.addwave();
        gameSet.accelerategame();//Test whether the data during acceleration is normal
        assertEquals(wavemangement.getIndex1(),0);
        assertEquals(wavemangement.getCurrentmonster().size(),0);//Do not release monsters at the beginning of the interval
        for(int i=0;i<39;i++){
            wavemangement.releasemonster();//60 seconds per frame, according to the requirement, the time for the first wave of monster release should be 1.3 seconds, which is frame 78
        }
        assertEquals(wavemangement.getCurrentmonster().size(),1);
        for(int i=0;i<217;i++){
            wavemangement.releasemonster();
        }
        assertEquals(wavemangement.getCurrentmonster().size(),10);
        assertEquals(wavemangement.getIndex1(),1);//The first wave is over, and it's time to prepare for the second wave
        for(int i=0;i<4000;i++){
            wavemangement.releasemonster();
        }
        wavemangement.getCurrentmonster().clear();
        wavemangement.gamejudeg();
        assertTrue(gameSet.isGamewin());//The last wave ends, the monster is cleared, and the game wins
        setup();//Reset Wavenumber
        wavemangement.addwave();
        for(int i=0;i<30;i++){
            wavemangement.timing();//Skip the first wave of preparation time
        }
        assertEquals(wavemangement.getnextwave(),2);
        assertEquals(wavemangement.getnextWaveCountdown(),1110);
        for(int i=0;i<=1110;i++){
            wavemangement.timing();
        }
        assertEquals(wavemangement.getnextwave(),3);
        assertEquals(wavemangement.getnextWaveCountdown(),930);//Preparation time for the next wave
        setup();//Reset Wavenumber
        wavemangement.addwave();
        gameSet.accelerategame();//Test whether the wave preparation during acceleration is normal
        for(int i=0;i<15;i++){
            wavemangement.timing();//Skip the first wave of preparation time
        }
        assertEquals(wavemangement.getnextwave(),2);
        assertEquals(wavemangement.getnextWaveCountdown(),1110);
        for(int i=0;i<=555;i++){
            wavemangement.timing();
        }
        assertEquals(wavemangement.getnextwave(),3);
        assertEquals(wavemangement.getnextWaveCountdown(),930);//Preparation time for the next wave
    }
    /**
     * Can the test tower attack monsters normally, and can the fireball search for monsters normally
     */
    @Test
    public void  testtowerandfireballs(){
        setup();
        board.enablebuild();
        board.operate(32,40);
        Monsters testmonster=new Monsters("a",1,2,3,1,mana);
        wavemangement.getCurrentmonster().add(testmonster);
        wavemangement.getCurrentmonster().get(0).setX(200);
        wavemangement.getCurrentmonster().get(0).setY(200);
        for(int i=0;i<2000;i++){
            board.getTowers().get(0).toweraction(gameSet.isGameaccelerate());
        }
        assertEquals(board.getTowers().get(0).getFireballslist().size(),0);//Unable to attack monsters outside of range
        assertEquals(board.getTowers().get(0).getRandonmonsters().size(),0);
        wavemangement.getCurrentmonster().get(0).setX(40);
        wavemangement.getCurrentmonster().get(0).setY(40);
        for(int i=0;i<120;i++){
            board.getTowers().get(0).toweraction(gameSet.isGameaccelerate());
        }
        assertEquals(board.getTowers().get(0).getFireballslist().size(),3);//Test attacking monsters and check if the number of fireballs fired per second is normal
        assertEquals(board.getTowers().get(0).getRandonmonsters().size(),1);
        assertEquals(board.getTowers().get(0).getFireballslist().get(0).getX(),42);//The launch location is in the middle of the tower
        assertEquals(board.getTowers().get(0).getFireballslist().get(0).getY(),50);
        for(int i=0;i<200;i++){//Determine whether Fireball will track monsters and damage them
            board.getTowers().get(0).getFireballslist().get(0).move(gameSet.isGameaccelerate());
        }
        wavemangement.getCurrentmonster().get(0).isdead();
        assertTrue(board.getTowers().get(0).getFireballslist().get(0).getisdone());
        assertTrue(wavemangement.getCurrentmonster().get(0).getisdead());
        board.getTowers().get(0).getFireballslist().clear();
        gameSet.accelerategame();//When accelerating, the launch speed and fireballs speed also increases
        for(int i=0;i<60;i++){
            board.getTowers().get(0).toweraction(gameSet.isGameaccelerate());
        }
        assertEquals(board.getTowers().get(0).getFireballslist().size(),3);
        for(int i=0;i<200;i++){//Determine whether Fireball will track monsters and damage them
            board.getTowers().get(0).getFireballslist().get(0).move(gameSet.isGameaccelerate());
        }
        wavemangement.getCurrentmonster().get(0).isdead();
        assertTrue(board.getTowers().get(0).getFireballslist().get(0).getisdone());
        assertTrue(wavemangement.getCurrentmonster().get(0).getisdead());
    }
    /**
     * Test the monster's various behaviors and logic for correctness
     */
    @Test
    public void testmonster(){
        setup();
        Monsters testmonster=new Monsters("a",500,2,3,1,mana);
        boolean monsterOnPath = false;
        assertFalse(gameSet.isGamelose());
        for (int i = 0; i < 1000; i++) {
            testmonster.move(gameSet.isGameaccelerate());
            testmonster.isbanished();
            mana.lostgame();
            for (int j = 0; j < board.getPath().size(); j++) {
                int monsterX = testmonster.getx();
                int monsterY = testmonster.gety();
                int pathX = board.getPath().get(j).getx();
                int pathY = board.getPath().get(j).gety();
                int pathWidth = 32;
                int pathHeight = 32;
                if (monsterX >= pathX && monsterX < pathX + pathWidth &&
                        monsterY >= pathY && monsterY < pathY + pathHeight) {
                    monsterOnPath = true;
                }
            }
            assertTrue(monsterOnPath);
            monsterOnPath = false;
        }
        gameSet.accelerategame();
        for (int i = 0; i < 1000; i++) {
            testmonster.move(gameSet.isGameaccelerate());
            testmonster.isbanished();
            mana.lostgame();
            for (int j = 0; j < board.getPath().size(); j++) {
                int monsterX = testmonster.getx();
                int monsterY = testmonster.gety();
                int pathX = board.getPath().get(j).getx();
                int pathY = board.getPath().get(j).gety();
                int pathWidth = 32;
                int pathHeight = 32;
                if (monsterX >= pathX && monsterX < pathX + pathWidth &&
                        monsterY >= pathY && monsterY < pathY + pathHeight) {
                    monsterOnPath = true;
                }
            }
            assertTrue(monsterOnPath);
            monsterOnPath = false;
        }
        assertTrue(gameSet.isGamelose());
        testmonster.setX(1);
        testmonster.setY(1);
        assertEquals(testmonster.getx(),1);
        assertEquals(testmonster.gety(),1);
        assertEquals(testmonster.getArmour(),3);
        assertEquals(testmonster.getHp(),500);
        assertEquals(testmonster.getSpeed(),2);
        assertEquals(testmonster.getMana_gained_on_kill(),1);
        assertEquals(testmonster.getType(),"a");
    }
    /**
     * Test the location and functionality of the UI
     */
    @Test
    public void testUI(){
        setup();
        assertEquals(accelerateUI.getx(),650);
        assertEquals(accelerateUI.gety(),50);
        assertEquals(accelerateUI.getwidth(),50);
        assertEquals(accelerateUI.getheight(),50);
        accelerateUI.contain(10,20);
        assertFalse(accelerateUI.getcotain());
        accelerateUI.contain(670,70);
        assertTrue(accelerateUI.getcotain());
        assertEquals(buidUI.getx(),650);
        assertEquals(buidUI.gety(),190);
        assertEquals(buidUI.getwidth(),50);
        assertEquals(buidUI.getheight(),50);
        buidUI.contain(10,20);
        assertFalse(buidUI.getcotain());
        buidUI.contain(670,210);
        assertTrue(buidUI.getcotain());
        assertEquals(damageUI.getx(),650);
        assertEquals(damageUI.gety(),400);
        assertEquals(damageUI.getwidth(),50);
        assertEquals(damageUI.getheight(),50);
        damageUI.contain(10,20);
        assertFalse(damageUI.getcotain());
        damageUI.contain(670,430);
        assertTrue(damageUI.getcotain());
        assertEquals(manaUI.getx(),650);
        assertEquals(manaUI.gety(),470);
        assertEquals(manaUI.getwidth(),50);
        assertEquals(manaUI.getheight(),50);
        manaUI.contain(10,20);
        assertFalse(manaUI.getcotain());
        manaUI.contain(670,520);
        assertTrue(manaUI.getcotain());
        assertEquals(pauseUI.getx(),650);
        assertEquals(pauseUI.gety(),120);
        assertEquals(pauseUI.getwidth(),50);
        assertEquals(pauseUI.getheight(),50);
        pauseUI.contain(10,20);
        assertFalse(pauseUI.getcotain());
        pauseUI.contain(670,140);
        assertTrue(pauseUI.getcotain());
        assertEquals(rangeUI.getx(),650);
        assertEquals(rangeUI.gety(),260);
        assertEquals(rangeUI.getwidth(),50);
        assertEquals(rangeUI.getheight(),50);
        rangeUI.contain(10,20);
        assertFalse(rangeUI.getcotain());
        rangeUI.contain(670,270);
        assertTrue(rangeUI.getcotain());
        assertEquals(speedUI.getx(),650);
        assertEquals(speedUI.gety(),330);
        assertEquals(speedUI.getwidth(),50);
        assertEquals(speedUI.getheight(),50);
        speedUI.contain(10,20);
        assertFalse(speedUI.getcotain());
        speedUI.contain(670,340);
        assertTrue(speedUI.getcotain());
    }
    /**
     * Test various functions in the app
     */
    @Test
    public void testApp(){
        setup();
        App app = new App();
        PApplet.runSketch(new String[] { "App" }, app);
        if (soundClip.isOpen()) {
            soundClip.close();
        }
        app.settings();
        app.setup();
        app.key='p';//Testing the Control of Keyboard Keys on Games
        app.keyReleased();
        assertTrue(app.set.isGamepause());
        app.keyReleased();
        assertFalse(app.set.isGamepause());
        app.key='F';
        app.keyReleased();
        assertTrue(app.set.isGameaccelerate());
        app.keyReleased();
        assertFalse(app.set.isGameaccelerate());
        app.key='T';
        app.keyReleased();
        app.key='1';
        app.keyReleased();
        app.key='2';
        app.keyReleased();
        app.key='3';
        app.keyReleased();
        assertTrue(app.board.getbuildenable());
        assertTrue(app.board.getRangelevelupenable());
        assertTrue(app.board.getspeedlevelupenable());
        assertTrue(app.board.getDamagelevelupenable());
        app.key='T';
        app.keyReleased();
        app.key='1';
        app.keyReleased();
        app.key='2';
        app.keyReleased();
        app.key='3';
        app.keyReleased();
        assertFalse(app.board.getbuildenable());
        assertFalse(app.board.getRangelevelupenable());
        assertFalse(app.board.getspeedlevelupenable());
        assertFalse(app.board.getDamagelevelupenable());
        assertEquals(app.mana.getInitial_mana_cap(),app.initial_mana_cap);
        app.key='M';
        app.keyReleased();
        assertEquals(app.mana.getInitial_mana_cap(),1500);
        app.key='R';
        app.keyReleased();
        assertEquals(app.mana.getInitial_mana_cap(),app.initial_mana_cap);
    }
    /**
     * All basic game logic has been run on it. Now, test whether the game functions properly in drawing mode
     */
    @Test
    public void testdraw(){
        setup();
        App app = new App();
        PApplet.runSketch(new String[] { "App" }, app);
        if (soundClip.isOpen()) {
            soundClip.close();
        }
        app.settings();
        app.setup();
        app.set.pausegame();
        app.delay(1000);//Test whether initial drawing can be carried out
        assertEquals(app.wavemangement.getCurrentmonster().size(),0);
        app.set.startgame();//Test whether tower attacks are normal in draw mode, monsters have death animations.
        app.delay(1000);
        for(int i=0;i<6000;i++){
            app.wavemangement.releasemonster();
            app.wavemangement.timing();
        }
        app.wavemangement.getCurrentmonster().clear();
        Monsters monster1=new Monsters("a",10,0,1,11,app.mana);
        monster1.setX(32);
        monster1.setY(180);
        app.set.pausegame();
        app.board.enablebuild();
        app.board.operate(32,40);
        app.board.containtower(32,40);
        app.board.enableRangelevelup();//Determine whether the tower upgrade function is normal in drawing mode
        app.board.enablespeedlevelup();
        app.board.enableDamagelevelup();
        for(int i=0;i<4;i++){
            app.mana.setcurrentmana(2000);
            app.delay(100);
            assertEquals(app.board.getTowers().get(0).getRange_level(),i);
            app.delay(100);
            assertEquals(app.board.getTowers().get(0).getSpeed_level(),i);
            app.delay(100);
            app.board.operate(32,40);
            assertEquals(app.board.getTowers().get(0).getPower_level(),i+1);
        }
        app.set.startgame();//Determine whether the game victory conditions are normal in draw mode
        app.wavemangement.getCurrentmonster().add(monster1);
        app.delay(400);
        app.set.pausegame();//Fireball does not move when the game is paused in draw mode
        app.delay(100);
        app.set.startgame();
        app.delay(1000);
        assertTrue(app.set.isGamewin());
        app.setup();//Reset Game
        for(int i=0;i<6000;i++){
            app.wavemangement.releasemonster();
            app.wavemangement.timing();
        }
        app.wavemangement.getCurrentmonster().clear();
        Monsters monster2=new Monsters("a",1000,0,1,11,app.mana);
        monster2.setX(128);
        monster2.setY(495);
        app.wavemangement.getCurrentmonster().add(monster2);
        app.delay(300);
        assertTrue(app.set.isGamelose());//Judgment of game failure conditions in drawing mode
        app.manaUI.contain(670,520);//Test whether the UI interface is normal in drawing mode
        app.accelerateUI.contain(670,70);
        app.buidUI.contain(670,210);
        app.damageUI.contain(670,430);
        app.pauseUI.contain(670,140);
        app.rangeUI.contain(670,270);
        app.speedUI.contain(670,340);
        System.out.println(manaUI.getcotain());
        app.delay(100);
        assertTrue(app.accelerateUI.getcotain());
        assertTrue(app.buidUI.getcotain());
        assertTrue(app.damageUI.getcotain());
        assertTrue(app.manaUI.getcotain());
        assertTrue(app.pauseUI.getcotain());
        assertTrue(app.rangeUI.getcotain());
        assertTrue(app.speedUI.getcotain());
    }
}
