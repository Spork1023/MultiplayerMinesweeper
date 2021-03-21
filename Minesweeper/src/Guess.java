import java.io.Serializable;

public class Guess implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4874219816824814678L;
	public int x;
    public int y;
    public boolean flag;

    //getters for the variables

    public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isFlag() {
		return flag;
	}

	public Guess () { //zero argument constructor for serializable

    }

    public Guess (int x, int y, boolean flag) { //constructor for guesser
        this.x = x;
        this.y = y;
        this.flag = flag;
    }

}
