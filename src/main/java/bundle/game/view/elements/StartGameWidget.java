package bundle.game.view.elements;

import bundle.game.controller.MainMenuController;
import bundle.game.domain.value.PlainSize;
import com.google.inject.Inject;

import javax.swing.*;
import java.awt.*;

public class StartGameWidget extends JPanel {

    private MainMenuController controller;

    @Inject
    public void setMainMenuController(MainMenuController controller) {
        this.controller = controller;
    }

    public void draw() {
        BoxLayout currentLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(currentLayout);

        JPanel formPane = new JPanel();
        GridLayout formLayout = new GridLayout(0, 2);
        formPane.setLayout(formLayout);

        formPane.add(new JLabel("Mines count", JLabel.RIGHT));
        JTextField minesCountTextField = new JTextField(3);
        formPane.add(minesCountTextField);

        formPane.add(new JLabel("Width", JLabel.RIGHT));
        JTextField plainWidthTextField = new JTextField(2);
        formPane.add(plainWidthTextField);

        formPane.add(new JLabel("Height", JLabel.RIGHT));
        JTextField plainHeightTextField = new JTextField(2);
        formPane.add(plainHeightTextField);

        add(formPane);

        MainMenuButton startButton = new MainMenuButton();
        startButton.setText("Start game");
        startButton.addActionListener(e -> {
            controller.createNewGame(
                new PlainSize<>(
                        Integer.parseInt(plainWidthTextField.getText()),
                        Integer.parseInt(plainHeightTextField.getText())
                ),
                Integer.parseInt(minesCountTextField.getText())
            );
        });
        add(startButton);
    }
}
