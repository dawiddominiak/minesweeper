package bundle.game.view.element.game;

import bundle.core.value.Observer;
import bundle.game.domain.entity.GameBoardState;
import bundle.game.domain.value.InGameEventNames;

import javax.swing.*;

/**
 * View of score board
 */
public class ScoreBoardWidget extends JPanel implements Observer{
    /**
     * Renders score board.
     * @TODO time, score, mines counter
     * @param gameBoardState
     */
    public void draw(GameBoardState gameBoardState) {
        gameBoardState.registerObserver(InGameEventNames.FLAG_TOGGLED, this);
    }

    /**
     * @TODO
     * {@inheritDoc}
     * @param eventName
     */
    @Override
    public void onNotification(InGameEventNames eventName) {
    }
}
