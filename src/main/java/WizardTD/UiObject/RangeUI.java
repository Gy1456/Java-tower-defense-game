package WizardTD.UiObject;

public class RangeUI extends UI{
    /**
     * Constructor of RangeUI
     */
    public RangeUI(){
        super(650,260);
    }
    public void contain(int mouseX,int mouseY){
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + width) {
            this.contain=true;
        }
        else{
            this.contain=false;
        }
    }
    public int getx(){return x;}
    public int gety(){return y;}
    public int getwidth(){return width;}
    public int getheight(){return  height;}
    public boolean getcotain(){return contain;}
}

