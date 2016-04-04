package bundle.core.controller;

import com.google.inject.Injector;

abstract public class AbstractController<TView> {

    private TView view;
    private Injector injector;

    public void setInjector(Injector injector) {
        this.injector = injector;
    }

    public Injector getInjector() {
        return injector;
    }

    public void setView(TView view) {
        this.view = view;
    }

    public TView getView() {
        return view;
    }
}
