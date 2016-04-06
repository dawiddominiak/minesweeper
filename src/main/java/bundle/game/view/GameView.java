package bundle.game.view;

import bundle.game.domain.entity.GamePlainState;
import bundle.game.view.element.game.GameElementsContainer;
import com.google.inject.Inject;

import javax.swing.*;

public class GameView extends JFrame {
    private GameElementsContainer gameElementsContainer;

    @Inject
    public void setGameElementsContainer(GameElementsContainer gameElementsContainer) {
        this.gameElementsContainer = gameElementsContainer;
    }

    public void handleGamePlainState(GamePlainState gamePlainState) {
        setTitle("Minesweeper");
        gameElementsContainer.draw(gamePlainState);
        setContentPane(gameElementsContainer);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(500, 500); //TODO: auto - sizing
        setVisible(true);
    }
}
