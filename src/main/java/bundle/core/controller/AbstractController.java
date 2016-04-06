package bundle.core.controller;

import com.google.inject.Injector;

abstract public class AbstractController<TView> {

    private TView view;

    public void setView(TView view) {
        this.view = view;
    }

    public TView getView() {
        return view;
    }
}
