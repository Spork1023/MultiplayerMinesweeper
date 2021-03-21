import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Server implements Runnable {
    boolean keepReading = true;
    private Socket connection;
    private ServerSocket serverSock;
    private ObjectOutputStream ostream;
    private ObjectInputStream istream;
    private int port = 2050;
    private Board bord;
    private Guesser guesser;
    private boolean done = false;

    public Server (Board board, Guesser guesser) { //constructor for server. Sets instance variables.
        bord = board;
        this.guesser = guesser;
    }

    public void writeGuess (Guess guess) { //writes guesses to the stream.
        try {
            ostream.writeObject(guess);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getIp () { //gets the IP of the host computer
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "Cannot find Ip";
        }
    }
    
    public Socket getConnection() {
    	return connection;
    } //returns the socket

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

    public void run () { //accepts connection sets up streams.
        try {
            serverSock = new ServerSocket();
            serverSock.bind(new InetSocketAddress(getIp(), port));
            connection = serverSock.accept();
            ostream = new ObjectOutputStream(connection.getOutputStream());
            istream = new ObjectInputStream(connection.getInputStream());
            System.err.println("Connection Received\n");
            connection.setKeepAlive(true);
            connection.setTcpNoDelay(true);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try { //sets while loop to constantly read fromt the socket
            ostream.writeObject(bord);
            while(!done && getConnection().isConnected()) {
                Guess guess = readGuess();
                guesser.guessTile(guess.getX(), guess.getY(), guess.isFlag());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (Exception e) {
        }
        try {
            serverSock.close();
        } catch (Exception e) {
        }
    }

    public void closeSocket () {
        done = true;
    } //sets done to true to close socket



}
