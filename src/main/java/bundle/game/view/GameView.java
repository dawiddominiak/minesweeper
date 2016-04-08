package bundle.game.view;

import bundle.game.controller.GameController;
import bundle.game.domain.entity.GameBoardState;
import bundle.game.view.element.game.GameElementsContainer;
import com.google.inject.Inject;

import javax.swing.*;

/**
 * View of game
 */
public class GameView extends JFrame {
    private GameController controller;

    /**
     * Creates new GameView
     * @param controller injects controller
     */
    public GameView(GameController controller) {
        this.controller = controller;
    }

    /**
     * Draws current window based on game board state.
     * @param gameBoardState state of game board
     */
    public void handleGameBoardState(GameBoardState gameBoardState) {
        setTitle("Minesweeper");
        GameElementsContainer gameElementsContainer = new GameElementsContainer(controller);
        gameElementsContainer.draw(gameBoardState);
        setContentPane(gameElementsContainer);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(getPreferredSize());
    }

}
