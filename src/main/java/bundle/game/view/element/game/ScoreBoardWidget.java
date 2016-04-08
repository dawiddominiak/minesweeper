package bundle.game.view.element.game;

import bundle.core.value.Observer;
import bundle.game.domain.entity.GameBoardState;
import bundle.game.domain.value.InGameEventNames;

import javax.swing.*;

public class ScoreBoardWidget extends JPanel implements Observer{
    //TODO: time
    public void draw(GameBoardState gameBoardState) {
        gameBoardState.registerObserver(InGameEventNames.FLAG_TOGGLED, this);
    }

    @Override
    public void onNotification(InGameEventNames eventName) {
        //TODO:
    }
}
