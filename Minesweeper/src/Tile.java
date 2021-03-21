import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class Tile implements Serializable {

    private boolean hasMine;
    private boolean state;
    private int display;
    private int numMinesAround;
    private boolean flagSet;

    //Inserts images
    public static final ImageIcon grass1 = new ImageIcon("DarkGrass.jpg");
    public static final ImageIcon grass2 = new ImageIcon("LightGrass.jpg");
    public static final ImageIcon dirt = new ImageIcon("Dirt.jpg");
    public static final ImageIcon dirt1 = new ImageIcon("Dirt1.jpg");
    public static final ImageIcon dirt2 = new ImageIcon("Dirt2.jpg");
    public static final ImageIcon dirt3 = new ImageIcon("Dirt3.jpg");
    public static final ImageIcon dirt4 = new ImageIcon("Dirt4.jpg");
    public static final ImageIcon dirt5 = new ImageIcon("Dirt5.jpg");
    public static final ImageIcon dirt6 = new ImageIcon("Dirt6.jpg");
    public static final ImageIcon dirt7 = new ImageIcon("Dirt7.jpg");
    public static final ImageIcon dirt8 = new ImageIcon("Dirt8.jpg");
    public static final ImageIcon mine = new ImageIcon("Mine.jpg");
    public static final ImageIcon flag = new ImageIcon("flag.jpg");
    public static final ImageIcon theTrahan = new ImageIcon("trahan.jpg");
    /* Possible states of hasMine: */
    //true - mine
    //false - no mine

    /* Possible states of state: */
    //true - clicked/uncovered
    //false - not clicked/covered

    public Tile () { // 0 arg constructor
        state = false;
        display = 10;
        hasMine = false;
        flagSet = false;
    }

    public ImageIcon largeImageChooser () { //sets the image based on the display value
        if (display == 0) {
            return dirt;
        } else if (display == 1) {
            return dirt1;
        } else if (display == 2) {
            return dirt2;
        } else if (display == 3) {
            return dirt3;
        } else if (display == 4) {
            return dirt4;
        } else if (display == 5) {
            return dirt5;
        } else if (display == 6) {
            return dirt6;
        } else if (display == 7) {
            return dirt7;
        } else if (display == 8) {
            return dirt8;
        } else if (display == 9) {
            return grass1;
        } else if (display == 10) {
            return grass2;
        } else if (display == 12) {// if its a mine it displays it, otherwise grass is shown
            return mine;
        } else  {
            return grass1;
        }
    }

    /*
    Bunch of getters and setter for setting and retrieving data
     */

    public void insertMines (boolean mine) {
        hasMine = mine;
    }

    public boolean getState(){
        return state;
    }

    public int getDisplay(){
        return display;
    }

    public boolean getHasMine(){
        return hasMine;
    }

    public void setState(boolean n) {
        state = n;
    }

    public void setDisplay() {
        for (int i = 0; i < 8; i++) {
            if (getHasMine()) {
                display = 12;
                break;
            } else if (getNumMinesAround() == i) {
                display = i;
            }
        }
    }

    public void setDisplay(int tile) {
        display = tile;
    }

    public void setHasMine(boolean n) {
        hasMine = n;
    }

    public boolean isFlagSet () {
        return flagSet;
    }

    public void setFlagSet (boolean n) {
        flagSet = n;
    }

    public int getNumMinesAround() {
        return numMinesAround;
    }

    public void setNumMinesAround (int n) {
        numMinesAround = n;
    }

    /* Different displays
      0 - 0
      1 - 1
      2 - 2
      3 - 3
      4 - 4
      5 - 5
      6 - 6
      7 - 7
      8 - 8
      9 - grass (light)
      10 - grass (dark)
      11 - flag
      12 - bomb
    */
}
