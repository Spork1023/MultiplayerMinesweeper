import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client implements Runnable {
	private Socket sock;
	private ObjectOutputStream ostream;
	private ObjectInputStream istream;
	private boolean done = false;
	private Guesser guesser;
	private int port = 2050;

	public Client(String host, Guesser guesser ) { //constructor for the client. Connects to server
		try {
			this.guesser = guesser;
			sock = new Socket();
			sock.connect(new InetSocketAddress(host, port));
			ostream = new ObjectOutputStream(sock.getOutputStream());
			istream = new ObjectInputStream(sock.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Socket getSock() {
		return sock;
	} //getter for socket
	
	public void close() {
		done = true;
	} //changes the done variable to true to end loop

	public void writeGuess(Guess x) { //writes a guess to the stream
		try {
			ostream.writeObject(x);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void run () { //loop to be run while game is going for constant reading
		try {
			while(!done && getSock().isConnected()) {
				Guess guess = readGuess();
				guesser.guessTile(guess.getX(), guess.getY(), guess.isFlag());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			sock.close();
		} catch (Exception e) {
		}
	}

	public Board readB() { //reads a board from the stream
		try {
			Object o = istream.readObject();
			return (Board) o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Guess readGuess () { //reads guesses from the stream
		try {
			Object obj = istream.readObject();
			if (obj instanceof Guess) {
				return (Guess) obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
