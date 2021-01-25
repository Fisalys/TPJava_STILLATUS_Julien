package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import ressources.Route;
import ressources.Voiture;

import java.util.*;

public class Main extends Application {
    public void startAnimation(Route route)
    {

        AnimationTimer loop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                List<Node> children =  route.getRoot().getChildren();
                List<Voiture> voit = new ArrayList<>();
                for(Object e : children)
                {
                    if(e instanceof Voiture)
                        voit.add((Voiture) e);
                }
                for(Voiture v :voit)
                {
                    if(v.getX() >= route.getHeight()*Route.FACTEUR)
                        continue;
                    if(v.getY() >= route.getWidth()*Route.FACTEUR)
                        continue;
                    if(v.getStartY() == 0 && v.getX() < route.getHeight()*Route.FACTEUR)
                    {
                        Circle noeud = route.getNextNoeud(v);
                        Shape intersect = Shape.intersect(v.getDetection(), noeud);

                        boolean collision =  (intersect.getBoundsInLocal().getWidth() != -1);
                        if(!collision) {
                            v.setY(v.getY() + v.getSpeed());
                            v.getDetection().setCenterY(v.getDetection().getCenterY() + v.getSpeed());
                        }else
                        {
                            v.setY(v.getY()+v.getSpeed()*0.5);
                            v.getDetection().setCenterY(v.getDetection().getCenterY()+(v.getSpeed())*0.5);

                        }
                    }
                    if(v.getStartX() == 0 && v.getY() < route.getWidth()*Route.FACTEUR)
                    {
                        Circle noeud = route.getNextNoeud(v);
                        Shape intersect = Shape.intersect(v.getDetection(), noeud);

                        boolean collision =  (intersect.getBoundsInLocal().getWidth() != -1);
                        if(!collision)
                        {
                            v.setX(v.getX()+v.getSpeed());
                            v.getDetection().setCenterX(v.getDetection().getCenterX()+v.getSpeed());
                        }else
                        {
                            v.setX(v.getX()+v.getSpeed()*0.5);
                            v.getDetection().setCenterX(v.getDetection().getCenterX()+(v.getSpeed())*0.5);

                        }
                    }
                }
            }
        };
        loop.start();
    }
    //Idée : deux cercle autour de la voiture, un large pour ralentir quand il touche un noeud et un plus petit pour que si celui ci touche celui d'une autre voiture alors colision inévitable et les deux voiture se stop
    @Override
    public void start(Stage primaryStage) throws Exception{

        Route route = new Route(100,100, 40);
        route.spawnCar(4);
        startAnimation(route);
        primaryStage.setTitle("TP Chemin");
        primaryStage.setScene(new Scene(route.getRoot(), 800, 800));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
