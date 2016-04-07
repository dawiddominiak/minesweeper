package bundle.game.view.element.game;

import bundle.game.domain.entity.GameBoardState;
import com.google.inject.Inject;

import javax.swing.*;
import java.awt.*;

public class GameElementsContainer extends JPanel {
    private ScoreBoard scoreBoard;

    @Inject
    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public void draw(GameBoardState gameBoardState) {
        setLayout(new FlowLayout());

        scoreBoard.draw(gameBoardState);
        add(scoreBoard);
    }
}
