package Graphics;

import Engine.Board;
import Engine.Mark;
import Engine.MoveListener;
import Engine.Symbol;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class UI {
    private JFrame frame;
    private JLabel currPlayerLabel;
    private final ArrayList<JButton> allButtons = new ArrayList<>();
    private MoveListener listener;
    private final ImageIcon crossIcon = scaleImg(new ImageIcon(Objects.requireNonNull(getClass().getResource("TTC_Cross.png"))));
    private final ImageIcon circleIcon = scaleImg(new ImageIcon(Objects.requireNonNull(getClass().getResource("TTC_Circle.png"))));

    public void setCurrentPlayerLabel(Symbol symbol) {
        switch (symbol) {
            case CROSS -> currPlayerLabel.setText("Current Player: X");
            case CIRCLE -> currPlayerLabel.setText("Current Player: O");
            default -> System.out.println("Hehe buggy.");
        }
    }

    public void announceWinner(Board board) {
        switch (board.getWinner()) {
            case CIRCLE -> currPlayerLabel.setText("O has won the game!");
            case CROSS -> currPlayerLabel.setText("X has won the game!");
            case EMPTY -> currPlayerLabel.setText("The game ended with a draw!");
            default -> System.out.println("glitch in the matrix");
        }
    }
    public void setMoveListener(MoveListener listener) {
        this.listener = listener;
    }

    public void startUI() {
        SwingUtilities.invokeLater(() -> {
            createMainFrame();
            createCurrentPlayerLabel();
            createButtons();
            setButtonAction();
        });
    }
    private void createButtons() {
        JPanel mainPanel = new JPanel(new GridLayout(3,3));
        frame.add(mainPanel,BorderLayout.CENTER);
        for (int i = 0; i < 9; i++) {
            JButton newButton = new JButton();
            allButtons.add(newButton);
            mainPanel.add(newButton);
        }
    }
    private void createMainFrame() {
        frame = new JFrame("Tic Tac Toe");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(596, 620);
        frame.setLocation(650, 200);
        frame.setLayout(new BorderLayout());
    }

    private void createCurrentPlayerLabel() {
        JPanel labelPanel = new JPanel();
        currPlayerLabel = new JLabel("Current Player: X");
        labelPanel.add(currPlayerLabel,BorderLayout.CENTER);
        frame.add(labelPanel,BorderLayout.SOUTH);
        currPlayerLabel.setForeground(Color.RED);
    }

    private void setButtonAction() {
        for (JButton button : allButtons) {
            int i = allButtons.indexOf(button);
            button.addActionListener(e -> {
                if (listener != null) listener.onMoveSelected(i);
            });
        }
    }

    private void setMarkAt(int index, Symbol symbol) throws IllegalArgumentException {
        if (index < 0 || index > 8) throw new IllegalArgumentException("invalid index range");
        if (symbol == null) throw new IllegalArgumentException("symbol is null");
        ImageIcon playerIcon = (symbol == Symbol.CROSS ? crossIcon : circleIcon);
        allButtons.get(index).setIcon(playerIcon);
    }

    public ImageIcon scaleImg(ImageIcon icon) {
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(180,180,Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public void updateUI(Board board) {
        Mark[] marks = board.getMarks();
        for (Mark mark : marks) {
            switch (mark.symbol()) {
                case CROSS -> setMarkAt(mark.index(), Symbol.CROSS);
                case CIRCLE -> setMarkAt(mark.index(), Symbol.CIRCLE);
                case EMPTY -> {}
            }
        }
    }

}
