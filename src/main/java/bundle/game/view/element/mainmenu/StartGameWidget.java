package bundle.game.view.element.mainmenu;

import bundle.game.controller.MainMenuController;
import bundle.game.domain.value.BoardSize;
import com.google.inject.Inject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        JTextField boardWidthTextField = new JTextField(2);
        formPane.add(boardWidthTextField);

        formPane.add(new JLabel("Height", JLabel.RIGHT));
        JTextField boardHeightTextField = new JTextField(2);
        formPane.add(boardHeightTextField);

        add(formPane);

        MainMenuButton startButton = new MainMenuButton();
        startButton.setText("Start game");
        startButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                    controller.createNewGame(
                        //TODO: validation int, and business
                        new BoardSize(
                                Integer.parseInt(boardWidthTextField.getText()),
                                Integer.parseInt(boardHeightTextField.getText())
                        ),
                        Integer.parseInt(minesCountTextField.getText())
                    );
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        add(startButton);
    }
}
