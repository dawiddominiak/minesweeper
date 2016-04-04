package bundle.core.view;

import bundle.core.controller.AbstractController;

import javax.swing.*;

abstract public class ControllerAwareView<TController extends AbstractController> extends JFrame {
    private TController controller;

    public void setController(TController controller) {
        this.controller = controller;
    }

    protected TController getController() {
        return controller;
    }
}
