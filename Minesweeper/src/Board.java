import javax.swing.*;
import java.io.Serializable;
import java.util.Random;

public class Board implements Serializable{

    
	/**
	 * 
	 */
	private static final long serialVersionUID = -4901836392714600843L;
	Random ran = new Random();
    private Tile[][] ar;
    private ImageIcon[][] imgs;
    private int mineNumber;
    private int totalNumber;
    private int rowsize;
    private int colsize;

    //constructor for board
    public Board(String n) { //Setting attributes for each board type
        if (n.equals("easy")) {//Easy
            ar = new Tile[9][9];
            imgs = new ImageIcon[9][9];
            rowsize = 9;
            colsize = 9;
            mineNumber = 10;
            totalNumber = 81;

        } else if (n.equals("medium")) {//Medium
            ar = new Tile[16][16];
            imgs = new ImageIcon[16][16];
            rowsize = 16;
            colsize = 16;
            mineNumber = 40;
            totalNumber = 256;

        } else if (n.equals("hard")) {//Hard
            ar = new Tile[16][24];
            imgs = new ImageIcon[16][24];
            rowsize = 16;
            colsize = 24;
            mineNumber = 99;
            totalNumber = 512;
        }

        for (int i = 0; i < ar.length; i++) {//Sets type of grass on each tile and initializes
            for (int j = 0; j < ar[i].length; j++) {
                ar[i][j] = new Tile();
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        imgs[i][j] = ar[i][j].grass1;
                    } else {
                        imgs[i][j] = ar[i][j].grass2;
                    }
                } else {
                    if (j % 2 == 1) {
                        imgs[i][j] = ar[i][j].grass1;
                    } else {
                        imgs[i][j] = ar[i][j].grass2;
                    }
                }
            }
        }
    }


    public void plantMines() {//sets mines on board
            int num = mineNumber;
            while (num > 0) {
                int x = ran.nextInt(rowsize);
                int y = ran.nextInt(colsize);
                if (!ar[x][y].getHasMine()) {//checks if tile already has mine
                    ar[x][y].setHasMine(true);
                    num--;
                }
            }
    }


    /*
    Getters and Setters
    */

    public boolean checkWin() {
        return mineNumber == totalNumber;
    }

    public Tile[][] getAr() {
        return ar;
    }

    public Tile getAr(int x, int y) {
        return ar[x][y];
    }

    public ImageIcon finalImageSelector (int x, int y) {
        totalNumber--;
        return ar[x][y].largeImageChooser();
    }

    public ImageIcon[][] getImgs() {
        return imgs;
    }

    public void generateNums() {//sets numbers around mines
        for (int i = 0; i < ar.length; i++) {
            for (int j = 0; j < ar[i].length; j++) {
                int num = 0;
                if (!ar[i][j].getHasMine()) {
                    if (j > 0 && ar[i][j - 1].getHasMine()) {
                        num++;
                    }
                    if (j < ar[i].length-1 && ar[i][j + 1].getHasMine()) {
                        num++;
                    }
                    if (j > 0 && i > 0 && ar[i - 1][j - 1].getHasMine()) {
                        num++;
                    }
                    if (j > 0 && i < ar.length-1 && ar[i + 1][j - 1].getHasMine()) {
                        num++;
                    }
                    if (i > 0 && j < ar[i].length-1 && ar[i - 1][j + 1].getHasMine()) {
                        num++;
                    }
                    if (i < ar.length-1 && j < ar[i].length-1 && ar[i + 1][j + 1].getHasMine()) {
                        num++;
                    }
                    if (i > 0 && ar[i - 1][j].getHasMine()) {
                        num++;
                    }
                    if (i < ar.length-1 && ar[i + 1][j].getHasMine()) {
                        num++;
                    }
                }
                ar[i][j].setNumMinesAround(num);
            }
        }
    }

    public void generateDisplay () {//sets display of all tiles
        for (int i = 0; i < ar.length; i++) {
            for (int j = 0; j < ar[i].length; j++) {
                ar[i][j].setDisplay();
            }
        }
    }

}