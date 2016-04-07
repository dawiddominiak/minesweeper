package bundle.game.view;

import bundle.game.domain.entity.GameBoardState;
import bundle.game.view.element.game.GameElementsContainer;
import com.google.inject.Inject;

import javax.swing.*;

public class GameView extends JFrame {
    private GameElementsContainer gameElementsContainer;

    @Inject
    public void setGameElementsContainer(GameElementsContainer gameElementsContainer) {
        this.gameElementsContainer = gameElementsContainer;
    }

    public void handleGameBoardState(GameBoardState gameBoardState) {
        setTitle("Minesweeper");
        gameElementsContainer.draw(gameBoardState);
        setContentPane(gameElementsContainer);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(500, 500); //TODO: auto - sizing
        setVisible(true);
    }
}
