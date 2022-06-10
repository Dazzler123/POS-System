package lk.ijse.pos.util;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class DateAndTime {
    public static void loadDateAndTime(Label lblDate, Label lblTime,Label lblAM_PM) {
        //set date
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        // set time
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour()+":"+currentTime.getMinute()+":"+currentTime.getSecond());
            if(currentTime.getHour()>12){
               lblAM_PM.setText("PM");
            }else{
                lblAM_PM.setText("AM");
            }
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
