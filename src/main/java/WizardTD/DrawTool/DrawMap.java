package WizardTD.DrawTool;
import WizardTD.App;
import WizardTD.Gamebase.GameSet;
import WizardTD.MODEL.*;
import processing.core.PImage;
import processing.core.PApplet;

import java.util.HashMap;

public class DrawMap extends DrawTool{
    /**
     * Represents the game board for drawing maps and mana slots.
     */
    private Board boards;

    /**
     * Represents the image used for rendering map elements and the mana pool.
     */
    private PImage pimage;
    /**
     * Represents the game settings for controlling drawing behavior.
     */
    private GameSet set;
    /**
     * Represents the mana pool associated with the game.
     */
    private Mana mana;
    /**
     * Initializes a DrawMap object for drawing maps and mana slots.
     * @param maps   A HashMap of images used for rendering map elements.
     * @param boards The game board for drawing map elements.
     * @param mana   The mana pool associated with the game.
     * @param set    The game settings for controlling drawing behavior.
     */
    public DrawMap(HashMap<String, PImage> maps, Board boards, Mana mana, GameSet set){
        super(maps);
        this.boards=boards;
        this.mana=mana;
        this.set=set;
    }
    /**
     * Draws the mana pool, updates mana gain, and displays mana information on the screen.
     * @param app The PApplet used for drawing.
     */
    public void drawmanapool(PApplet app){
        mana.gianmanapersecond();
        app.fill(255);
        app.rect(mana.getx(), mana.gety(), mana.getWidth(), mana.getHeight());
        app.fill(135, 206, 250);
        app.rect(mana.getx(), mana.gety(),(int)((mana.getCurrentmana()/mana.getInitial_mana_cap())*mana.getWidth()),
                mana.getHeight());
        app.fill(0);
        app.textAlign(50);
        app.textSize(20);
        app.text((int)mana.getCurrentmana() + " / " + (int)mana.getInitial_mana_cap(), 470, 27);
        app.fill(0);
        app.textAlign(50);
        app.textSize(20);
        app.text("Mana:", 270, 27);
    }
    /**
     * Draws the map elements, including paths, shrubs, and the wizard.
     * @param app The PApplet used for drawing.
     */
    public void drawmap(PApplet app){
          App ap=new App();
          for(int i=0;i<boards.getGrass().size();i++){
              this.pimage=maps.get("grass");
              app.image(pimage,boards.getGrass().get(i).getx(),boards.getGrass().get(i).gety());
          }
          for(int i=0;i<boards.getPath().size();i++){
                if(boards.getPath().get(i).gettype().equals("path0")){
                  this.pimage=maps.get(("path0"));
                  app.image(pimage,boards.getPath().get(i).getx(),boards.getPath().get(i).gety());
              }
              else if(boards.getPath().get(i).gettype().equals("path0_clockwise_90")){
                  this.pimage=maps.get(("path0"));
                  this.pimage=ap.rotateImageByDegrees(pimage,90);
                  app.image(pimage,boards.getPath().get(i).getx(),boards.getPath().get(i).gety());
              }
              else if(boards.getPath().get(i).gettype().equals("path1_clockwise_180")){
                  this.pimage=maps.get(("path1"));
                  this.pimage=ap.rotateImageByDegrees(pimage,180);
                  app.image(pimage,boards.getPath().get(i).getx(),boards.getPath().get(i).gety());
              }
              else if(boards.getPath().get(i).gettype().equals("path1_clockwise_90")){
                 this.pimage=maps.get(("path1"));
                 this.pimage=ap.rotateImageByDegrees(pimage,90);
                  app.image(pimage,boards.getPath().get(i).getx(),boards.getPath().get(i).gety());
              }
              else if(boards.getPath().get(i).gettype().equals("path1_clockwise_-90")){
                  this.pimage=maps.get(("path1"));
                 this.pimage=ap.rotateImageByDegrees(pimage,-90);
                  app.image(pimage,boards.getPath().get(i).getx(),boards.getPath().get(i).gety());
              }
              else if(boards.getPath().get(i).gettype().equals("path1")){
                  this.pimage=maps.get(("path1"));
                 app.image(pimage,boards.getPath().get(i).getx(),boards.getPath().get(i).gety());
              }
                else if(boards.getPath().get(i).gettype().equals("path2_clockwise_-90")){
                    this.pimage=maps.get(("path2"));
                    this.pimage=ap.rotateImageByDegrees(pimage,-90);
                    app.image(pimage,boards.getPath().get(i).getx(),boards.getPath().get(i).gety());
                }
                else if(boards.getPath().get(i).gettype().equals("path2_clockwise_90")){
                    this.pimage=maps.get(("path2"));
                    this.pimage=ap.rotateImageByDegrees(pimage,90);
                    app.image(pimage,boards.getPath().get(i).getx(),boards.getPath().get(i).gety());
                }
                else if(boards.getPath().get(i).gettype().equals("path2_clockwise_180")){
                    this.pimage=maps.get(("path2"));
                    this.pimage=ap.rotateImageByDegrees(pimage,180);
                    app.image(pimage,boards.getPath().get(i).getx(),boards.getPath().get(i).gety());
                }else if(boards.getPath().get(i).gettype().equals("path2")){
                    this.pimage=maps.get(("path2"));
                    app.image(pimage,boards.getPath().get(i).getx(),boards.getPath().get(i).gety());
                }
                else{
                    this.pimage=maps.get(("path3"));
                    app.image(pimage,boards.getPath().get(i).getx(),boards.getPath().get(i).gety());
                }
          }
          for(int i=0;i<boards.getShrubs().size();i++){
              this.pimage=maps.get("shrub");
              app.image(pimage,boards.getShrubs().get(i).getx(),boards.getShrubs().get(i).gety());
          }
          this.pimage=maps.get("grass");
          app.image(pimage,boards.getWizard().getx(),boards.getWizard().gety());
    }
    /**
     * Draws both the mana pool and the map elements.
     * @param app The PApplet used for drawing.
     */
    public void draw(PApplet app){
        drawmanapool(app);
        drawmap(app);
    }
}
