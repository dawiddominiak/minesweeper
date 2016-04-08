package bundle.game.view.element.game;

import bundle.core.value.Observer;
import bundle.game.controller.GameController;
import bundle.game.domain.entity.GameBoardState;
import bundle.game.domain.entity.GameFieldState;
import bundle.game.domain.value.BoardSize;
import bundle.game.domain.value.Coordinates;
import bundle.game.domain.value.GameBoard;
import bundle.game.domain.value.InGameEventNames;
import com.google.inject.Inject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Game board view.
 */
public class GameBoardWidget extends JPanel implements Observer {
    private GameController controller;
    private java.util.List<GameFieldButton> buttonList;

    /**
     * Constructs GameBoardWidget
     */
    public GameBoardWidget(GameController controller) {
        buttonList = new ArrayList<>();
        this.controller = controller;
    }

    /**
     * Renders game board based on game board state.
     * Registers this as game board state observer.
     * @param gameBoardState game board state to be rendered.
     */
    public void draw(GameBoardState gameBoardState) {
        GameBoard gameBoard = gameBoardState.getGameBoard();
        BoardSize gameBoardSize = gameBoard.getBoardSize();
        int horizontalGap = 1;
        int verticalGap = 1;

        GridLayout gridLayout = new GridLayout(
            gameBoardSize.getHeight(),
            gameBoardSize.getWidth(),
            horizontalGap,
            verticalGap
        );
        setLayout(gridLayout);
        setBackground(new Color(0, 0, 0));

        for (int y = gameBoardSize.getHeight(); y >= 1; --y) {
            for (int x = 1; x <= gameBoardSize.getWidth(); ++x) {
                GameFieldState fieldState = gameBoardState.getGameFieldState(new Coordinates<>(x, y));
                GameFieldButton button = new GameFieldButton(fieldState, gameBoardState, controller);
                buttonList.add(button);
                add(button);
            }
        }

        gameBoardState.registerObserver(InGameEventNames.MINE_EXPLODED, this);
        gameBoardState.registerObserver(InGameEventNames.GAME_WON, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onNotification(InGameEventNames eventName) {
        if (eventName == InGameEventNames.MINE_EXPLODED) {
            buttonList.stream().forEach(gameFieldButton -> {
                gameFieldButton.showOnDefeat();
                gameFieldButton.setBackground(new Color(0, 0, 0, 10));
            });
        }

        if (eventName == InGameEventNames.GAME_WON) {
            buttonList.stream().forEach(gameFieldButton -> {
                gameFieldButton.setEnabled(false);
                gameFieldButton.setBackground(new Color(0, 255, 0, 60));
            });
        }
    }
}
