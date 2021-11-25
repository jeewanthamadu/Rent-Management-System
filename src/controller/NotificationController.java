package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.util.Objects;

public class NotificationController {
    public void set(String title, String txt, int showMode, int image) {
        
        Image img = null;
        switch (image) {
            case 1:
                img = new Image("/assets/Notification Icons/remove.png");
                break;
            case 2:
                img = new Image("assets/Notification Icons/add.png");
                break;
            case 3:
                img = new Image("/assets/Notification Icons/update.png");
                break;
            case 4:
                img = new Image("/assets/Notification Icons/Rent.png");
                break;
            case 5:
                img = new Image("/assets/Notification Icons/return.png");
                break;
            case 6:
                img = new Image("/assets/Notification Icons/wrong.png");
                break;
            case 7:
                img = new Image("/assets/Notification Icons/try again.png");
                break;
            case 8:
                img = new Image("/assets/Notification Icons/empty.png");
                break;
            case 9:
                img = new Image("/assets/Notification Icons/save.png");
                break;
            case 10:
                img = new Image("/assets/Notification Icons/vehicle.png");
                break;
            case 11:
                img = new Image("/assets/Notification Icons/repair.png");
                break;
            case 12:
                img = new Image("/assets/Notification Icons/loginInvalid.png");
                break;
            default:
                System.out.println("");
                break;
        }

        Notifications notificationBuilder = Notifications.create();
        notificationBuilder.title(title)
                .text(txt)
                .graphic(new ImageView(img))
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                    }
                });

        switch (showMode) {
            case 1:
                notificationBuilder.showConfirm();
                break;
            case 2:
                notificationBuilder.showInformation();
                break;
            case 3:
                notificationBuilder.showError();
                break;
            case 4:
                notificationBuilder.show();
                break;
            default:
                notificationBuilder.darkStyle();
                notificationBuilder.show();
                break;
        }

    }


}
