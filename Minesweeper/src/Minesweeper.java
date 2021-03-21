import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class Minesweeper implements ActionListener, MouseListener, Guesser {
    JFrame menu = new JFrame();
    JFrame game = new JFrame();
    JPanel difficulty = new JPanel(new GridLayout());
    JButton easy = new JButton("Easy");
    JButton medium = new JButton("Medium");
    JButton hard = new JButton("Hard");
    Font but = new Font("Ariel", Font.BOLD, 25);
    Font titleFont = new Font("Ariel", Font.BOLD, 40);

    JLabel title = new JLabel("Minesweeper with friend");

    JPanel panel = new JPanel();
    JButton singleplayerButton = new JButton("Singleplayer");
    JButton multiplayerButton = new JButton("Multiplayer");

    JPanel multiplayer = new JPanel(new GridLayout());
    JButton host = new JButton("Host Game");
    JButton join = new JButton("Join Game");
    JTable yuri;
    Board board;
    TableModel dataTable;

    Server server;
    Client client;

    boolean runMulti;
    boolean clerver;
    
    TableColumnModel columnModel;

    public static void main(String[] args) {
        new Minesweeper();
    }

    public Minesweeper() {
        JCreate();
    }

    //JCreate adds all JComponents and buttons and sets all JComponents
    public void JCreate() {
        menu.getContentPane().add(panel);
        panel.add(title);
        title.setFont(titleFont);
        panel.add(singleplayerButton);
        panel.add(multiplayerButton);
        difficulty.add(easy);
        difficulty.add(medium);
        difficulty.add(hard);
        panel.setBackground(new Color(146, 90, 61));

        menu.setResizable(false);
        game.setResizable(false);

        menu.setSize(500, 500);
        menu.setVisible(true);
        menu.setResizable(false);
        menu.setTitle("Minesweeper");
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        easy.setFont(but);
        medium.setFont(but);
        hard.setFont(but);

        multiplayer.add(host);
        multiplayer.add(join);

        host.setFont(but);
        join.setFont(but);


        singleplayerButton.addActionListener(this);
        multiplayerButton.addActionListener(this);
        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);
        host.addActionListener(this);
        join.addActionListener(this);

        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame.setDefaultLookAndFeelDecorated(true);
    }

    //Action performed is an action listener class where we check for each button and execute methods accordingly
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == singleplayerButton) {
            menu.setContentPane(difficulty);
            menu.revalidate();
        } else if (e.getSource() == multiplayerButton) {
            menu.setContentPane(multiplayer);
            menu.revalidate();
        } else if (e.getSource() == easy) {
            board = new Board("easy");
            board.plantMines();
            board.generateNums();
            board.generateDisplay();
            //tile 100 x 100 pixels
            menu.dispose();


            Object[] cols = new Object[9];

            dataTable = new DefaultTableModel(board.getImgs(), cols) {
                public Class getColumnClass(int col) {
                    return getValueAt(0, col).getClass();
                }
            };

            yuri = new JTable(dataTable){
                public boolean isCellEditable(int row,int column){
                    return false;
                }
            };
            columnModel = yuri.getColumnModel();
            for (int i = 0; i < 9; i++) {
                columnModel.getColumn(i).setPreferredWidth(56);
            }
            yuri.setCellSelectionEnabled(true);
            yuri.addMouseListener(this);
            yuri.setRowHeight(56);

            game.add(yuri);
            yuri.setVisible(true);
            game.pack();
            game.setVisible(true);

        } else if (e.getSource() == medium) {
            board = new Board("medium");
            board.plantMines();
            board.generateNums();
            board.generateDisplay();
            //tile 100 x 100 pixels
            menu.dispose();


            Object[] cols = new Object[16];

            TableModel dataTable = new DefaultTableModel(board.getImgs(), cols) {
                public Class getColumnClass(int col) {
                    return getValueAt(0, col).getClass();
                }
            };

            yuri = new JTable(dataTable){
                public boolean isCellEditable(int row,int column){
                    return false;
                }
            };
            columnModel = yuri.getColumnModel();
            for (int i = 0; i < 16; i++) {
                columnModel.getColumn(i).setPreferredWidth(56);
            }
            yuri.setCellSelectionEnabled(true);
            yuri.addMouseListener(this);
            yuri.setRowHeight(56);

            game.add(yuri);
            yuri.setVisible(true);
            game.pack();
            game.setVisible(true);
            
        } else if (e.getSource() == hard) {
            board = new Board("hard");
            board.plantMines();
            board.generateNums();
            board.generateDisplay();
            //tile 100 x 100 pixels
            menu.dispose();

            Object[] cols = new Object[24];

            TableModel dataTable = new DefaultTableModel(board.getImgs(), cols) {
                public Class getColumnClass(int col) {
                    return getValueAt(0, col).getClass();
                }
            };

            yuri = new JTable(dataTable){
                public boolean isCellEditable(int row,int column){
                    return false;
                }
            };
            columnModel = yuri.getColumnModel();
            for (int i = 0; i < 24; i++) {
                columnModel.getColumn(i).setPreferredWidth(56);
            }
            yuri.setCellSelectionEnabled(true);
            yuri.addMouseListener(this);
            yuri.setRowHeight(56);

            game.add(yuri);
            yuri.setVisible(true);
            game.pack();
            game.setVisible(true);
        }
        else if(e.getSource() == host) {
            board = new Board("hard");
            board.plantMines();
            board.generateNums();
            board.generateDisplay();
            //tile 100 x 100 pixels

            menu.dispose();
            server = new Server(board, this);
            Thread thread = new Thread(server);
            thread.start();
            JOptionPane.showMessageDialog(null, "Your IP Address is " + server.getIp());
            runMulti = true;
            clerver = true;
            
            Object[] cols = new Object[24];

            TableModel dataTable = new DefaultTableModel(board.getImgs(), cols) {

				public Class getColumnClass(int col) {
                    return getValueAt(0, col).getClass();
                }
            };

            yuri = new JTable(dataTable){
                public boolean isCellEditable(int row,int column){
                    return false;
                }
            };
            columnModel = yuri.getColumnModel();
            for (int i = 0; i < 24; i++) {
                columnModel.getColumn(i).setPreferredWidth(56);
            }
            yuri.setCellSelectionEnabled(true);
            yuri.addMouseListener(this);
            yuri.setRowHeight(56);

            game.add(yuri);
            yuri.setVisible(true);
            game.pack();
            game.setVisible(true);
        }
        else if(e.getSource() == join) {
            String ip = JOptionPane.showInputDialog("What is the ip you would like to connect to?");
            client = new Client(ip, this);
            
            board = client.readB();
            board.generateDisplay();
            //tile 100 x 100 pixels
            menu.dispose();
            
            runMulti = true;
            clerver = false;
            
            Object[] cols = new Object[24];

            TableModel dataTable = new DefaultTableModel(board.getImgs(), cols) {

				public Class getColumnClass(int col) {
                    return getValueAt(0, col).getClass();
                }
            };

            yuri = new JTable(dataTable){
                public boolean isCellEditable(int row,int column){
                    return false;
                }
            };
            columnModel = yuri.getColumnModel();
            for (int i = 0; i < 24; i++) {
                columnModel.getColumn(i).setPreferredWidth(56);
            }
            yuri.setCellSelectionEnabled(true);
            yuri.addMouseListener(this);
            yuri.setRowHeight(56);

            game.add(yuri);
            yuri.setVisible(true);
            game.pack();
            game.setVisible(true);

            Thread thread = new Thread(client);
            thread.start();
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    //Mouse released checks when a mouse button has been released.
    //We check which mouse button has been released and we act accordingly
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        int row = yuri.rowAtPoint(mouseEvent.getPoint());
        int col = yuri.columnAtPoint(mouseEvent.getPoint());

        if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
            if (row >= 0 && col >= 0 && !board.getAr(row, col).isFlagSet() && !board.getAr(row, col).getState()) {
                board.getAr(row, col).setState(true);
                yuri.setValueAt(board.finalImageSelector(row, col), row, col);
                if(runMulti) {
                	if(clerver) {              		
                	server.writeGuess(new Guess(row, col, false));
                	}
                	else {
                		client.writeGuess(new Guess(row, col, false));
                	}
                }
                if (board.getAr(row, col).getDisplay() == 0) {
                    explode(row, col);
                }
                checkEnd(row, col);
            }
        } else if (SwingUtilities.isRightMouseButton(mouseEvent)) {
            if ((row >= 0) && (col >= 0) && (!(board.getAr(row, col).getState()))) {
            	if(runMulti) {
            		if(clerver) {
                	server.writeGuess(new Guess(row, col, true));
            		}
            		else {
            			client.writeGuess(new Guess(row, col, true));
            		}
                }
                if (!board.getAr(row, col).isFlagSet()) {
                    board.getAr(row, col).setFlagSet(true);
                    yuri.setValueAt(Tile.flag, row, col);
                } else {
                    board.getAr(row, col).setFlagSet(false);
                    if ((row % 2 == 0 && col % 2 == 0) || (row % 2 == 1 && col % 2 == 1)) {
                        yuri.setValueAt(Tile.grass1, row, col);
                    } else {
                        yuri.setValueAt(Tile.grass2, row, col);
                    }
                }

            }
        }
    }

    //checks for win or lose conditions
    public void checkEnd(int row, int col) {
        if (board.getAr(row, col).getDisplay() == 12) {
            for (int i = 0; i < board.getAr().length; i++) {
                for (int j = 0; j < board.getAr()[i].length; j++) {
                    yuri.setValueAt(board.finalImageSelector(i, j), i, j);
                }
            }
            if(runMulti) {
                if(clerver) {
                    server.closeSocket();
                }
                else {
                    client.close();
                }
            }
            JOptionPane.showMessageDialog(null, "You Lose!");
            System.exit(0);
        }
        if (board.checkWin()) {
            if(runMulti) {
                if(clerver) {
                    server.closeSocket();
                }
                else {
                    client.close();
                }
            }
            JOptionPane.showMessageDialog(null, "You Win!");
            System.exit(0);
        }
    }
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    //code for the clearing of all tiles that don't have a mine when you click on a plain dirt tile.
    public void explode(int x, int y) {
        if (y > 0 && !board.getAr(x, y - 1).getState()) {
            board.getAr(x, y - 1).setState(true);
            yuri.setValueAt(board.finalImageSelector(x, y - 1), x, y - 1);
            if (board.getAr(x, y - 1).getDisplay() == 0) {
                explode(x, y - 1);
            }
        }
        if (y < board.getAr()[0].length - 1 && !board.getAr(x, y + 1).getState()) {
            board.getAr(x, y + 1).setState(true);
            yuri.setValueAt(board.finalImageSelector(x, y + 1), x, y + 1);
            if (board.getAr(x, y + 1).getDisplay() == 0) {
                explode(x, y + 1);
            }
        }
        if (y > 0 && x > 0 && !board.getAr(x - 1, y - 1).getState()) {
            board.getAr(x - 1, y - 1).setState(true);
            yuri.setValueAt(board.finalImageSelector(x - 1, y - 1), x - 1, y - 1);
            if (board.getAr(x - 1, y - 1).getDisplay() == 0) {
                explode(x - 1, y - 1);
            }
        }
        if (y > 0 && x < board.getAr().length - 1 && !board.getAr(x + 1, y - 1).getState()) {
            board.getAr(x + 1, y - 1).setState(true);
            yuri.setValueAt(board.finalImageSelector(x + 1, y - 1), x + 1, y - 1);
            if (board.getAr(x + 1, y - 1).getDisplay() == 0) {
                explode(x + 1, y - 1);
            }
        }
        if (x > 0 && y < board.getAr()[0].length - 1 && !board.getAr(x - 1, y + 1).getState()) {
            board.getAr(x - 1, y + 1).setState(true);
            yuri.setValueAt(board.finalImageSelector(x - 1, y + 1), x - 1, y + 1);
            if (board.getAr(x - 1, y + 1).getDisplay() == 0) {
                explode(x - 1, y + 1);
            }
        }
        if (x < board.getAr().length - 1 && y < board.getAr()[0].length - 1 && !board.getAr(x + 1, y + 1).getState()) {
            board.getAr(x + 1, y + 1).setState(true);
            yuri.setValueAt(board.finalImageSelector(x + 1, y + 1), x + 1, y + 1);
            if (board.getAr(x + 1, y + 1).getDisplay() == 0) {
                explode(x + 1, y + 1);
            }
        }
        if (x > 0 && !board.getAr(x - 1, y).getState()) {
            board.getAr(x - 1, y).setState(true);
            yuri.setValueAt(board.finalImageSelector(x - 1, y), x - 1, y);
            if (board.getAr(x - 1, y).getDisplay() == 0) {
                explode(x - 1, y);
            }
        }
        if (x < board.getAr().length - 1 && !board.getAr(x + 1, y).getState()) {
            board.getAr(x + 1, y).setState(true);
            yuri.setValueAt(board.finalImageSelector(x + 1, y), x + 1, y);
            if (board.getAr(x + 1, y).getDisplay() == 0) {
                explode(x + 1, y);
            }
        }
    }

    //edits board based off of guesses recieved from another player.
    public void guessTile (int row, int col, boolean flag) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (flag)
                {
                    if (!board.getAr(row, col).isFlagSet()) {
                        board.getAr(row, col).setFlagSet(true);
                        yuri.setValueAt(Tile.flag, row, col);
                    } else {
                        board.getAr(row, col).setFlagSet(false);
                        if ((row % 2 == 0 && col % 2 == 0) || (row % 2 == 1 && col % 2 == 1)) {
                            yuri.setValueAt(Tile.grass1, row, col);
                        } else {
                            yuri.setValueAt(Tile.grass2, row, col);
                        }
                    }
                } else{
                    board.getAr(row, col).setState(true);
                    yuri.setValueAt(board.finalImageSelector(row, col), row, col);
                    if (board.getAr(row, col).getDisplay() == 0) {
                        explode(row, col);
                    }
                    checkEnd(row, col);
                }
            }
        });
    }
}
