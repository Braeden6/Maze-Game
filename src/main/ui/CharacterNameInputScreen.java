package ui;

import javax.swing.*;
import java.awt.*;

// Adds an input screen to given JFrame and then continues game
public class CharacterNameInputScreen {

    private MainGameSystem mainInterface;

    private JPanel enterName;
    private JTextField nameInput;
    private JButton submit;


    public CharacterNameInputScreen(MainGameSystem mainInterface) {
        this.mainInterface = mainInterface;
        initializeFrame();
        addInputTextBox();
        addLabels();
        displayTextBox();
    }

    // MODIFIES: mainInterface
    // EFFECTS: initializes main JFrame window for name input interface
    private void initializeFrame() {
        mainInterface.setLayout(new BorderLayout());
        mainInterface.setSize(400, 400);
        mainInterface.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainInterface.setLocationRelativeTo(null);
    }

    // MODIFIES: mainInterface
    // EFFECTS: sets up panel and adds an text input box with a submit button and action listener
    private void addInputTextBox() {
        enterName = new JPanel();
        enterName.setLayout(new GridLayout(0,1));
        enterName.setSize(0, 0);
        nameInput = new JTextField(10);
        submit = new JButton("Submit");
        submit.addActionListener(e -> {
            String name = nameInput.getText();
            mainInterface.initializeGame(name);
            mainInterface.remove(enterName);
        });
    }

    // MODIFIES: mainInterface
    // EFFECTS: adds text to window saying "Enter Character Name Below"
    private void addLabels() {
        JLabel label1 = new JLabel("  Enter Character");
        JLabel label2 = new JLabel("    Name Below");
        JLabel label3 = new JLabel();
        label1.setFont(new Font("Monospaced", Font.BOLD, 35));
        label2.setFont(new Font("Monospaced", Font.BOLD, 35));
        enterName.add(label1);
        enterName.add(label2);
        enterName.add(label3);
        enterName.add(nameInput);
        enterName.add(submit);

    }

    // MODIFIES: mainInterface
    // EFFECTS: display panel to main frame
    private void displayTextBox() {
        mainInterface.add(enterName, BorderLayout.SOUTH);
        mainInterface.setVisible(true);
    }
}
