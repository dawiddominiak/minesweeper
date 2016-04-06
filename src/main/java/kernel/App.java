package kernel;

import bundle.game.GameModule;
import bundle.game.controller.MainMenuController;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.awt.*;

public class App {
    public static void run() {
        EventQueue.invokeLater(() -> {
            Injector gameModuleInjector = Guice.createInjector(new GameModule());
            MainMenuController mainMenuController = gameModuleInjector.getInstance(MainMenuController.class);
        });
    }
}
