import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TicTacToe implements ActionListener {

    private final Random random = new Random(42);
    private final JFrame frame = new JFrame();
    private final JPanel titlePanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();
    private JLabel textField = new JLabel();
    private final JButton[] buttons = new JButton[9];
    boolean player1Turn;

    private static final int[][] ROWS = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8}
    };

    private static final int[][] COLUMNS = {
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8}
    };

    private static final int[][] DIAGONALS = {
            {0, 4, 8},
            {2, 4, 6}
    };

    TicTacToe() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        textField.setBackground(Color.white);
        textField.setForeground(Color.black);
        textField.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setText("Tic-Tac-Toe");
        textField.setOpaque(true);

        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 800, 100);

        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setBackground(new Color(150, 150, 150));

        for (int i = 0; i < 9; ++i) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 100));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }
        buttons[0].setBackground(Color.GREEN);

        titlePanel.add(textField);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel);

        firstTurn();
    }

    public void firstTurn() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Something went wrong in firstTurn() method.");
            e.printStackTrace();
        }

        if (random.nextInt(2) == 0) {
            player1Turn = true;
            textField.setText("X turn");
        } else {
            player1Turn = false;
            textField.setText("O turn");
        }
    }

    private void check(String player) {
        // Check all axis
        int[] rowCheck = checkAxis(ROWS, player);
        int[] columnsCheck = checkAxis(COLUMNS, player);
        int[] diagCheck = checkAxis(DIAGONALS, player);

        if(rowCheck != null) {
            gameOver(player, rowCheck);
        } else if(columnsCheck != null) {
            gameOver(player, columnsCheck);
        } else if(diagCheck != null) {
            gameOver(player, diagCheck);
        }
    }

    private int[] checkAxis(int[][] axis, String player) {
        for (int i = 0; i < axis.length; ++i) {
            boolean win = true;
            for (int j = 0; j < axis[i].length; ++j) {
                if (!Objects.equals(buttons[axis[i][j]].getText(), player)) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return axis[i];
            }
        }
        return null;
    }

    public void gameOver(String player, int[] winButtons) {
        for(int i = 0; i < winButtons.length; ++i) {
            buttons[winButtons[i]].setBackground(Color.GREEN);
        }

        for(int i = 0; i < 9; ++i) {
            buttons[i].setEnabled(false);
        }

        textField.setText(player + " wins");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; ++i) {
            if (e.getSource() == buttons[i]) {
                if (player1Turn) {
                    if (Objects.equals(buttons[i].getText(), "")) {
                        buttons[i].setText("X");
                        player1Turn = false;
                        textField.setText("O turn");
                        check("X");
                    }
                } else {
                    if (Objects.equals(buttons[i].getText(), "")) {
                        buttons[i].setText("O");
                        player1Turn = true;
                        textField.setText("X turn");
                        check("O");
                    }
                }
            }
        }
    }
}
