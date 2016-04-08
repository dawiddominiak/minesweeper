package bundle.game.view.element.game;

import bundle.game.domain.entity.GameBoardState;
import com.google.inject.Inject;

import javax.swing.*;
import java.awt.*;

/**
 * Container to keep game widgets
 */
public class GameElementsContainer extends JPanel{
    private ScoreBoardWidget scoreBoardWidget;
    private GameBoardWidget gameBoardWidget;

    /**
     * Injects score board widget
     * @param scoreBoardWidget score board widget to be injected.
     */
    @Inject
    public void setScoreBoardWidget(ScoreBoardWidget scoreBoardWidget) {
        this.scoreBoardWidget = scoreBoardWidget;
    }

    /**
     * Injects game board widget
     * @param gameBoardWidget game board widget to be injected.
     */
    @Inject
    public void setGameBoardWidget(GameBoardWidget gameBoardWidget) {
        this.gameBoardWidget = gameBoardWidget;
    }

    /**
     * Renders container
     * @param gameBoardState game board state of current game
     */
    public void draw(GameBoardState gameBoardState) {
        setLayout(new FlowLayout());

        scoreBoardWidget.draw(gameBoardState);
        add(scoreBoardWidget);

        gameBoardWidget.draw(gameBoardState);
        add(gameBoardWidget);
    }
}
