import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class NumberGuessingGameGUI extends JFrame {
    private final int LOWER_BOUND = 1;
    private final int UPPER_BOUND = 100;
    private final int MAX_ATTEMPTS = 7;

    private int randomNumber;
    private int attemptsLeft;

    private JTextField guessInput;
    private JLabel messageLabel;
    private JLabel attemptsLabel;
    private JButton guessButton;
    private JButton newGameButton;

    public NumberGuessingGameGUI() {
        setTitle("Number Guessing Game 🎮");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        setLayout(new GridLayout(5, 1, 10, 10));

        JLabel titleLabel = new JLabel("Guess a number between 1 and 100", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel);

        messageLabel = new JLabel("Enter your guess below!", JLabel.CENTER);
        add(messageLabel);

        guessInput = new JTextField();
        guessInput.setHorizontalAlignment(JTextField.CENTER);
        add(guessInput);

        attemptsLabel = new JLabel("Attempts left: " + MAX_ATTEMPTS, JLabel.CENTER);
        add(attemptsLabel);

        JPanel buttonPanel = new JPanel();
        guessButton = new JButton("Submit Guess");
        newGameButton = new JButton("New Game");
        buttonPanel.add(guessButton);
        buttonPanel.add(newGameButton);
        add(buttonPanel);

        initializeGame();

        guessButton.addActionListener(e -> checkGuess());
        newGameButton.addActionListener(e -> initializeGame());
    }
    private void initializeGame() {
        Random random = new Random();
        randomNumber = random.nextInt(UPPER_BOUND - LOWER_BOUND + 1) + LOWER_BOUND;
        attemptsLeft = MAX_ATTEMPTS;

        messageLabel.setText("Enter your guess below!");
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
        guessInput.setText("");
        guessInput.setEnabled(true);
        guessButton.setEnabled(true);
    }

    private void checkGuess() {
        try {
            int userGuess = Integer.parseInt(guessInput.getText().trim());

            if (userGuess < LOWER_BOUND || userGuess > UPPER_BOUND) {
                messageLabel.setText("⚠️ Please guess between " + LOWER_BOUND + " and " + UPPER_BOUND + "!");
                return;
            }

            attemptsLeft--;

            if (userGuess == randomNumber) {
                messageLabel.setText("🎉 Correct! You win!");
                endGame(true);
            } else if (userGuess > randomNumber) {
                messageLabel.setText("📉 Too high! Try again.");
            } else {
                messageLabel.setText("📈 Too low! Try again.");
            }

            if (attemptsLeft > 0) {
                attemptsLabel.setText("Attempts left: " + attemptsLeft);
            } else {
                endGame(false);
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("❌ Invalid input! Enter a number.");
        }
    }
    private void endGame(boolean won) {
        if (!won) {
            messageLabel.setText("❌ Out of attempts! Number was: " + randomNumber);
        }
        guessInput.setEnabled(false);
        guessButton.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NumberGuessingGameGUI game = new NumberGuessingGameGUI();
            game.setVisible(true);
        });
    }
}
