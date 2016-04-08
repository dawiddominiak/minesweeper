package bundle.game.view.element.game;

import bundle.game.controller.GameController;
import bundle.game.domain.entity.GameBoardState;
import com.google.inject.Inject;

import javax.swing.*;
import java.awt.*;

/**
 * Container to keep game widgets
 */
public class GameElementsContainer extends JPanel{
    private GameController controller;

    public GameElementsContainer(GameController controller) {
        this.controller = controller;
    }

    /**
     * Renders container
     * @param gameBoardState game board state of current game
     */
    public void draw(GameBoardState gameBoardState) {
        setLayout(new FlowLayout());

        ScoreBoardWidget scoreBoardWidget = new ScoreBoardWidget(controller);
        scoreBoardWidget.draw(gameBoardState);
        add(scoreBoardWidget);

        GameBoardWidget gameBoardWidget = new GameBoardWidget(controller);
        gameBoardWidget.draw(gameBoardState);
        add(gameBoardWidget);
    }
}
