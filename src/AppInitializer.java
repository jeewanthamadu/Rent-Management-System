import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.stage.Stage;
import controller.IntPreLoader;
import java.io.IOException;


public class AppInitializer extends Application {

    public static Stage primaryStage=null;
    public static Stage primaryScene=null;

    @Override
    public void init() throws Exception {
        IntPreLoader init=new IntPreLoader();
        init.checkFunction();
    }

    public static void main(String[] args) {
        LauncherImpl.launchApplication(AppInitializer.class,controller.LaunchPreLoader.class,args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        AppInitializer.primaryStage = primaryStage;
        /*primaryStage.setScene(new Scene(load(getClass().getResource("view/loginForm.fxml"))));
        primaryStage.setTitle(" GREEN LIBRARY MANAGMENT SYSTEM");
        //primaryStage.setAlwaysOnTop(true);
        primaryStage.show();*/

    }
}