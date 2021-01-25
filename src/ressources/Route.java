package ressources;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Route
{
    public static double  FACTEUR =8f;

    private int[][] plan;
    private int height;
    private int width;
    private int gap;
    private List<Circle> listNoeud;
    private Group root;
    private List<Integer> sLocationX;
    private List<Integer> sLocationY;

    public Route(int height , int width, int gap)
    {
        this.gap = gap;
        this.height = height;
        this.width = width;
        root =  new Group();
        listNoeud = new ArrayList<>();
        sLocationX = new ArrayList<>();
        sLocationY = new ArrayList<>();
        plan = creerPlan(height, width, gap);
        for(int i = 0; i< height; i++)
        {
            for(int j = 0; j< width; j++)
            {
                Circle c = new Circle();
                c.setCenterX(i*FACTEUR);
                c.setCenterY(j*FACTEUR);
                c.setFill(Color.BLACK);
                if(plan[i][j] == 2) {
                    c.setRadius(8f);
                    listNoeud.add(c);
                }
                else if(plan[i][j] == 1)
                {
                    c.setRadius(2f);
                    c.setFill(Color.RED);
                }
                root.getChildren().add(c);
            }
        }
        findSpawn();
    }

    private int[][] creerPlan(int n, int m, int intervalle)
    {
        int[][] tableau =  new int[n][m];
        for(int i=0; i < n;i=i+intervalle)//Mets les valeur dans le tableu en fonction de l'intervalle, 1 pour les sous noeuds et 2 pour les noeuds.
        {
            for(int j = 0; j < m; j++)
            {
                tableau[i][j] = 1;
            }
        }
        for(int i = 0; i<m; i = i + intervalle)
        {
            for(int j = 0; j<n; j++)
            {
                if(tableau[j][i] == 1)
                    tableau[j][i] = 2;
                else
                    tableau[j][i] = 1;
            }
        }
        return tableau;
    }

    private void findSpawn()
    {
        for(int i = 0; i< height;i = i + (height-1)) {
            for (int j = 1; j < width; j++) {
                if(plan[i][j] == 2)
                {
                    sLocationX.add(i);
                    sLocationY.add(j);
                }
            }
        }
        for(int i =0; i < width;  i = i + (width-1)){
            for(int j = 1; j<height; j++)
            {
                if(plan[j][i] == 2)
                {
                    sLocationX.add(j);
                    sLocationY.add(i);
                }
            }
        }
    }

    public void spawnCar(int nb)
    {
        if(nb <= sLocationX.size())
        {
            List<Integer> stock = new ArrayList<>();

            for(int i = 0; i < nb; i++)
            {
                int localisation = (int)(Math.random()*sLocationX.size());
                while((stock.contains(localisation)))
                    localisation = (int)(Math.random()*sLocationX.size());
                stock.add(localisation);
                Voiture r = new Voiture(sLocationX.get(localisation)*FACTEUR, sLocationY.get(localisation)*FACTEUR);
                r.setFill(Color.BLUE);
                root.getChildren().addAll(r,r.getDetection(), r.getAccident());
            }
        }
    }

  public Circle getNextNoeud(Voiture voiture)
    {
        List<Circle> temp =  new ArrayList<>();
        Circle x = null;
        if(voiture.getStartX() == 0) {
            for (Circle c : listNoeud)
                if (voiture.getStartY() == c.getCenterY())
                    temp.add(c);
            x = temp.get(0);
            for (Circle t : temp) {
                double y =x.getCenterY()-voiture.getY();
                double z =t.getCenterX()-voiture.getX();
                if (y >= z)
                    x = t;
            }
        }
        if(voiture.getStartY() == 0) {
            for (Circle c : listNoeud)
                if (voiture.getStartX() == c.getCenterX())
                    temp.add(c);
            x = temp.get(0);
            for (Circle t : temp) {
                double y =x.getCenterX()-voiture.getX();
                double z =t.getCenterY()-voiture.getY();
                if (y <= z)
                    x = t;
            }
        }
        return x;
    }

    public Group getRoot()
    {
        return root;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
