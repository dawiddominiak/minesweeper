package bundle.game.view.element.game;

import bundle.game.domain.entity.GameBoardState;
import com.google.inject.Inject;

import javax.swing.*;
import java.awt.*;

public class GameElementsContainer extends JPanel{
    private ScoreBoardWidget scoreBoardWidget;
    private GameBoardWidget gameBoardWidget;

    @Inject
    public void setScoreBoardWidget(ScoreBoardWidget scoreBoardWidget) {
        this.scoreBoardWidget = scoreBoardWidget;
    }

    @Inject
    public void setGameBoardWidget(GameBoardWidget gameBoardWidget) {
        this.gameBoardWidget = gameBoardWidget;
    }

    public void draw(GameBoardState gameBoardState) {
        setLayout(new FlowLayout());

        scoreBoardWidget.draw(gameBoardState);
        add(scoreBoardWidget);

        gameBoardWidget.draw(gameBoardState);
        add(gameBoardWidget);
    }
}
