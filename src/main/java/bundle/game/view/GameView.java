package bundle.game.view;

import bundle.game.domain.entity.GameBoardState;
import bundle.game.view.element.game.GameElementsContainer;
import com.google.inject.Inject;

import javax.swing.*;

/**
 * View of game
 */
public class GameView extends JFrame {
    private GameElementsContainer gameElementsContainer;

    /**
     * Injects game elements container
     * @param gameElementsContainer game elements container to be injected.
     */
    @Inject
    public void setGameElementsContainer(GameElementsContainer gameElementsContainer) {
        this.gameElementsContainer = gameElementsContainer;
    }

    /**
     * Draws current window based on game board state.
     * @param gameBoardState
     */
    public void handleGameBoardState(GameBoardState gameBoardState) {
        setTitle("Minesweeper");
        gameElementsContainer.draw(gameBoardState);
        setContentPane(gameElementsContainer);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(getPreferredSize());
    }
}
