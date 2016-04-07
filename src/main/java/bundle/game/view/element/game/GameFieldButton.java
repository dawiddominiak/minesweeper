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

public class GameFieldButton extends JButton implements Observer {
    private GameFieldState connectedGameFieldState;
    private GameBoardState connectedGameBoardState;
    private GameController controller;

    public GameFieldButton(GameFieldState gameFieldState, GameBoardState gameBoardState, GameController controller) {
        connectedGameFieldState = gameFieldState;
        connectedGameBoardState = gameBoardState;
        this.controller = controller;

        setBackground(Color.WHITE);
        setOpaque(true);
        setPreferredSize(new Dimension(30, 30));
        gameFieldState.registerObserver(InGameEventNames.FIELD_BECAME_VISIBLE, this);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                    controller.dispatchDigAction(gameFieldState, gameBoardState);
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

    @Override
    public void onNotification(InGameEventNames eventName) {
        if (eventName == InGameEventNames.FIELD_BECAME_VISIBLE) {
            makeVisible();
        }
    }

    private void makeVisible() {
        //TODO: better UI
        GameField gameField = connectedGameFieldState.getGameField();

        if (gameField.getGameFieldType() == GameFieldType.MINED) {
            setText("M");
        } else {
            int numberOfMinesInNeighborhood = connectedGameBoardState
                    .getGameBoard()
                    .countNumberOfMinesInNeighborhood(gameField);

            if (numberOfMinesInNeighborhood > 0) {
                setText(Integer.toString(numberOfMinesInNeighborhood));
            }

            if (numberOfMinesInNeighborhood == 0) {
                setText(".");
            }
        }
    }
}
