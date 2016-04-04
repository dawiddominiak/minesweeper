package bundle.core.view;

import bundle.core.controller.AbstractController;

import javax.swing.*;

abstract public class ControllerAwareWidget<TController extends AbstractController> extends JPanel {
    private TController controller;

    public void setController(TController controller) {
        this.controller = controller;
    }

    protected TController getController() {
        return controller;
    }
}
