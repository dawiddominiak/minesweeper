package bundle.game.view.element.game;

import bundle.core.value.Observer;
import bundle.game.domain.entity.GamePlainState;
import bundle.game.domain.value.InGameEventNames;

import javax.swing.*;

public class ScoreBoard extends JPanel implements Observer {
    //TODO: time
    public void draw(GamePlainState gamePlainState) {
        gamePlainState.registerObserver(InGameEventNames.FLAG_SET, this);
        gamePlainState.registerObserver(InGameEventNames.FLAG_TAKEN_AWAY, this);
    }

    @Override
    public void onNotification(InGameEventNames eventName) {
    }
}
