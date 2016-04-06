package bundle.game.view.element.game;

import bundle.game.domain.entity.GamePlainState;
import com.google.inject.Inject;

import javax.swing.*;
import java.awt.*;

public class GameElementsContainer extends JPanel {
    private ScoreBoard scoreBoard;

    @Inject
    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public void draw(GamePlainState gamePlainState) {
        setLayout(new FlowLayout());

        scoreBoard.draw(gamePlainState);
        add(scoreBoard);
    }
}
