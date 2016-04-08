package bundle.game.view.element.game;

import bundle.core.value.Observer;
import bundle.game.controller.GameController;
import bundle.game.domain.entity.GameBoardState;
import bundle.game.domain.entity.GameFieldState;
import bundle.game.domain.value.GameField;
import bundle.game.domain.value.GameFieldType;
import bundle.game.domain.value.InGameEventNames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Button that is a view for single game field.
 */
public class GameFieldButton extends JButton implements Observer {
    private GameFieldState connectedGameFieldState;
    private GameBoardState connectedGameBoardState;

    /**
     * Constructs game field button based on field state, board state.
     * Additionally we're injecting controller here.
     *
     * @param gameFieldState state of game field
     * @param gameBoardState state of game board
     * @param controller game controller to be injected.
     */
    public GameFieldButton(GameFieldState gameFieldState, GameBoardState gameBoardState, GameController controller) {
        connectedGameFieldState = gameFieldState;
        connectedGameBoardState = gameBoardState;

        setBackground(Color.WHITE);
        setOpaque(true);
        setPreferredSize(new Dimension(30, 30));
        gameFieldState.registerObserver(InGameEventNames.FIELD_BECAME_VISIBLE, this);
        gameFieldState.registerObserver(InGameEventNames.FLAG_TOGGLED, this);
        gameFieldState.registerObserver(InGameEventNames.MINE_EXPLODED, this);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isEnabled() && SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                    controller.dispatchDigAction(gameFieldState, gameBoardState);
                }

                if (isEnabled() && SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {
                    controller.dispatchToggleFlagAction(gameFieldState, gameBoardState);
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onNotification(InGameEventNames eventName) {
        if (eventName == InGameEventNames.FIELD_BECAME_VISIBLE) {
            makeVisible();
        }

        if (eventName == InGameEventNames.FLAG_TOGGLED) {
            changeFlagState();
        }

        if (eventName == InGameEventNames.MINE_EXPLODED) {
            showMine();
        }
    }

    /**
     * Shows the value in case of defeat
     */
    public void showOnDefeat() {
        if (connectedGameFieldState.getGameField().getGameFieldType() == GameFieldType.MINED) {
            showMine();
        } else {
            makeVisible();
        }
    }

    private void changeFlagState() {
        if (connectedGameFieldState.isFlagSet()) {
            setText("F");
        } else {
            setText("");
        }
    }

    private void showMine() {
        setText("M");
        setEnabled(false);
    }

    private void makeVisible() {
        //TODO: better UI
        GameField gameField = connectedGameFieldState.getGameField();

        if (gameField.getGameFieldType() != GameFieldType.MINED) {
            int numberOfMinesInNeighborhood = connectedGameBoardState
                    .getGameBoard()
                    .countNumberOfMinesInNeighborhood(gameField);

            if (numberOfMinesInNeighborhood > 0) {
                setText(Integer.toString(numberOfMinesInNeighborhood));
            }

            if (numberOfMinesInNeighborhood == 0) {
                setText(".");
            }

            setEnabled(false);
        }
    }
}
