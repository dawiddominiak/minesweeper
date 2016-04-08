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

public class GameBoardWidget extends JPanel implements Observer {
    private GameController controller;
    private java.util.List<GameFieldButton> buttonList;

    public GameBoardWidget() {
        buttonList = new ArrayList<>();
    }

    @Inject
    public void setGameController(GameController controller) {
        this.controller = controller;
    }

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
        setForeground(new Color(0, 0, 0, 50));
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
    }

    @Override
    public void onNotification(InGameEventNames eventName) {
        if (eventName == InGameEventNames.MINE_EXPLODED) {
            buttonList.stream().forEach(gameFieldButton -> {
                gameFieldButton.setEnabled(false);
                gameFieldButton.setBackground(new Color(0, 0, 0, 50));
            });
        }
    }
}
