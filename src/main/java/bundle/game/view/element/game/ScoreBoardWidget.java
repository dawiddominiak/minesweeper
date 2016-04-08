package bundle.game.view.element.game;

import bundle.core.value.Observer;
import bundle.game.controller.GameController;
import bundle.game.domain.entity.GameBoardState;
import bundle.game.domain.value.InGameEventNames;
import bundle.game.view.element.mainmenu.MainMenuButton;
import com.google.inject.Inject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * View of score board
 */
public class ScoreBoardWidget extends JPanel implements Observer{
    private GameController controller;
    private JLabel minesCounterWidget;
    private JLabel amountOfDugFieldsWidget;
    private GameBoardState gameBoardState;

    public ScoreBoardWidget(GameController controller) {
        this.controller = controller;
    }

    /**
     * Renders score board.
     * @TODO time, score
     * @param gameBoardState connected state of game board
     */
    public void draw(GameBoardState gameBoardState) {
        this.gameBoardState = gameBoardState;

        GridLayout gridLayout = new GridLayout(0, 2);
        setLayout(gridLayout);

        add(new JLabel("Mines left: "));
        minesCounterWidget = new JLabel();
        add(minesCounterWidget);
        updateLeftMinesCounter();

        add(new JLabel("Amount of dug fields: "));
        amountOfDugFieldsWidget = new JLabel();
        add(amountOfDugFieldsWidget);
        updateAmountOfDugFields();

        MainMenuButton mainMenuButton = new MainMenuButton();
        mainMenuButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                    controller.moveToMainMenu();
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
        mainMenuButton.setText("Main menu");
        add(mainMenuButton);

        gameBoardState.registerObserver(InGameEventNames.FLAG_TOGGLED, this);
        gameBoardState.registerObserver(InGameEventNames.DUG_SAFELY, this);
    }

    /**
     * {@inheritDoc}
     * @param eventName
     */
    @Override
    public void onNotification(InGameEventNames eventName) {
        if (eventName == InGameEventNames.FLAG_TOGGLED) {
            updateLeftMinesCounter();
        }

        if (eventName == InGameEventNames.DUG_SAFELY) {
            updateAmountOfDugFields();
        }
    }

    private void updateLeftMinesCounter() {
        int leftMinesCounter = gameBoardState.getLeftMinesCounter();
        minesCounterWidget.setText(
                Integer.toString(leftMinesCounter)
        );
    }

    private void updateAmountOfDugFields() {
        int amountOfDugFields = gameBoardState.getNumberOfVisitedFields();
        amountOfDugFieldsWidget.setText(
                Integer.toString(amountOfDugFields)
        );
    }
}
