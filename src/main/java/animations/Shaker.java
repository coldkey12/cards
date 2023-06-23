package animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shaker {
    private TranslateTransition translateTransition;
    public Shaker(Node node){
        translateTransition = new TranslateTransition(Duration.millis(60),node);
        translateTransition.setFromX(-5f);
        translateTransition.setByX(5f);
        translateTransition.setCycleCount(2);
        translateTransition.setAutoReverse(true);
    }
    public void shake(){
        translateTransition.playFromStart();
    }
}
