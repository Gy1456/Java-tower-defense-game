package WizardTD;
import WizardTD.Gamebase.GameSet;
import WizardTD.MODEL.Mana;
import org.junit.jupiter.api.Test;
import javax.sound.sampled.Clip;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;
public class Testmana {
    private Mana mana;
    private GameSet gameSet;
    public void setUp() {
        HashMap<String, Clip> sound = new HashMap<>();
        gameSet = new GameSet(sound);
        mana = new Mana(100, 200, 5, 10, 1, 2, 1.5, gameSet);
    }
    /**
     * Will the game lose when testing mana below 0
     */
    @Test
    public void testlostgame() {
        setUp();
        mana.setcurrentmana(20);
        mana.lostgame();
        assertFalse(gameSet.isGamelose());
        mana.setcurrentmana(-10);
        mana.lostgame();
        assertTrue(gameSet.isGamelose());

    }
    /**
     * Test whether all values are normal after mana spell
     */
    @Test
    public void testmanaspell(){
        setUp();
        mana.manaspell();
        assertEquals(mana.getCurrentmana(),90);
        assertEquals(mana.getInitial_mana_cap(),400);
        assertEquals(mana.getInitialManaGainedPerSecond(),7.5);
        assertEquals(mana.getManaPoolSpellManaGainedMultiplier(),1.6);
        assertEquals((mana.getManaPoolSpellInitialCost()),11);
        assertEquals((mana.getManaPoolSpellInitialCost()),11);
        assertEquals(mana.getManaPoolSpellCostIncreasePerUse(),1);
        assertEquals(mana.getManaPoolSpellCapMultiplier(),2);
        assertEquals(mana.getMana_pool_spell_initial_cost(),11);
        assertEquals(mana.getInitialMana(),100);
        mana.setcurrentmana(1);
        mana.manaspell();
        assertEquals(mana.getCurrentmana(),1);//Testing cannot cost mana to be less than 0
    }
    /**
     * Test whether the mana acquisition is normal
     */
    @Test
    public void gainmana(){
        setUp();
        mana.gainmama(10);
        assertEquals(mana.getCurrentmana(),115);
        for(int i=0;i<61;i++){
            mana.gianmanapersecond();
        }
        assertEquals(mana.getCurrentmana(),122.5);//Test whether the mana acquisition per second is normal
        mana.gainmama(100000);
        assertEquals(mana.getCurrentmana(),200);//Test if the mana value will be greater than the mana cap
        mana.gianmanapersecond();
        assertEquals(mana.getCurrentmana(),200);//Test if the mana value will be greater than the mana cap
    }
    /**
     * Determine whether the mana cost is normal and Test whether you will lose the game after being attacked by monsters
     */
    @Test
    public void spendmana(){
        setUp();
        mana.spendmana(10);
        assertEquals(mana.getCurrentmana(),90);//Determine whether the mana cost is normal
        mana.spendmana(10000);//Test whether you will lose the game after being attacked by monsters
        mana.lostgame();
        assertTrue(gameSet.isGamelose());

    }
}
