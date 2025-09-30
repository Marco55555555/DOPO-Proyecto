import java.util.ArrayList;
import java.util.List;

/**
 * Representa una celda 
 * 
 *  Cada celda tiene:
 * - Una posición en la ruta (roadPosition).
 * - Una representación gráfica (rectángulo de 40x40 por defecto).
 * - Una tienda opcional.
 * - Una lista de robots que pueden ubicarse en ella.
 * 
 * La celda es responsable de dibujarse en el canvas, manejar su
 * visibilidad y controlar la colocación de tiendas y robots.
 * 
 * @Ciclo 1.0
 */
public class Cell {
    private String color;
    private Rectangle rect;     
    private int xPosition;
    private int yPosition;
    private int roadPosition;   
    private boolean isVisible;
    private List<Robot> robots = new ArrayList<>();  
    private Store store;

    /**
     * Constructor de la celda.
     * @param roadPosition posición en la ruta
     * @param color color inicial de la celda
     */
    public Cell(int roadPosition, String color) {
        this.roadPosition = roadPosition;
        this.color = color; 
        
        rect = new Rectangle();
        rect.changeColor(color);
        rect.changeSize(40, 40); 
        
        this.xPosition = 0;
        this.yPosition = 0;
    }
    
    /**
     * Mueve la celda a la posición especificada en el canvas.
     * @param x coordenada x
     * @param y coordenada y
     */
    public void moveTo(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        rect.moveTo(x, y);

        if (store != null) {
            store.moveTo(x, y);
        }
    }
    
    /**
     * Cambia el color de la celda.
     * @param newColor nuevo color
     */
    public void changeColor(String newColor){
        this.color = newColor;
        rect.changeColor(newColor); 
    }

    /**
     * Asigna una tienda a la celda.
     * @param store la tienda a asignar
     */
    public void setStore(Store store) {
        this.store = store;
        store.moveTo(xPosition, yPosition); 
    }

    /**
     * Devuelve la tienda de esta celda (si existe).
     * @return la tienda o null
     */
    public Store getStore() {
        return store;
    }

    /**
     * Elimina la tienda de la celda.
     */
    public void removeStore() {
        if (store != null) {
            store.makeInvisible();
            store = null;
        }
    }
    
    /**
     * Obtiene la posición X de la celda en el canvas.
     * @return posición X
     */
    public int getXPosition() {
        return xPosition;
    }
    
    /**
     * Obtiene la posición Y de la celda en el canvas.
     * @return posición Y
     */
    public int getYPosition() {
        return yPosition;
    }
    
     /**
     * Hace visible la celda en el canvas junto con su tienda (si existe)
     * y todos los robots contenidos en ella.
     */
    public void makeVisible() {
        isVisible = true;
        rect.makeVisible();

        if (store != null) {
            store.makeVisible();
        }

        for (Robot r : robots) {
            r.makeVisible();
        }
    }
    
    /**
     * Hace invisible la celda en el canvas junto con su tienda (si existe)
     * y todos los robots contenidos en ella.
     */
    public void makeInvisible() {
        isVisible = false;
        rect.makeInvisible();

        if (store != null) {
            store.makeInvisible();
        }

        for (Robot r : robots) {
            r.makeInvisible();
        }
    }
    
    /**
     * Agrega un robot a la celda, lo posiciona en sus coordenadas
     * y lo hace visible si la celda está activa.
     * También guarda la celda como su posición inicial.
     * 
     * @param robot el robot a añadir
     */
    public void addRobot(Robot robot) {
        robots.add(robot);
        robot.moveTo(xPosition, yPosition);
        robot.setInitialCellIndex(this.roadPosition);
    }

    /**
     * Elimina el primer robot que llegó a la celda,
     * ocultándolo del canvas.
     */
    public void removeRobot() {
        if (!robots.isEmpty()) {
            Robot r = robots.get(0); 
            r.makeInvisible();
            robots.remove(0); 
        }
    }

    /**
     * Devuelve el número de posición de esta celda en la Silk Road.
     * @return posición de la celda
    */
 
    public int getRoadPosition() {
        return roadPosition;
    }
    
    /**
     * Devuelve la lista de robots actualmente ubicados en la celda.
     * @return lista de robots
     */
    public List<Robot> getRobots() {
        return robots;
    }
    
    /**
     * Cambia el tamaño del rectángulo que representa la celda.
     * 
     * @param width ancho en píxeles
     * @param height alto en píxeles
     */
    public void changeSize(int width, int height) {
        rect.changeSize(width, height);
    }
}