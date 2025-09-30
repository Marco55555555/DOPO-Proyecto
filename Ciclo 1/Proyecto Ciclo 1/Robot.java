/**
 * Representa un robot.
 * El robot tiene un cuerpo circular y dos ojos (rectángulos pequeños),
 * puede moverse por el canvas, cambiar de color y hacerse visible/invisible.
 * 
 * Ciclo 1.0
 */
public class Robot {
    private Circle body;   
    private int xPosition;
    private int yPosition;
    private boolean isVisible;
    private int initialCellIndex; 
    private Rectangle eye1;
    private Rectangle eye2;
    private Rectangle mouth;
    /**
     * Constructor: crea un nuevo robot en la posición (0,0),
     * con cuerpo circular y dos ojos rectangulares.
     */
    public Robot() {
        body = new Circle();
        eye1 = new Rectangle();
        eye2 = new Rectangle();
        
        eye1.changeSize(5,5);
        eye2.changeSize(5,5);
        
        eye1.moveTo(28, 20);
        eye2.moveTo(38, 20);
        
        mouth= new Rectangle();
        mouth.changeSize(5,15);
        mouth.moveTo(28, 30);

        this.xPosition = 0;
        this.yPosition = 0;
        this.initialCellIndex = -1; 
        this.isVisible = false;
        
    }
    
    /**
     * Asigna el índice de la celda inicial en la que se colocó el robot.
     * Este valor solo se puede establecer una vez.
     * @param index índice de la celda inicial (>= 0)
     */
    public void setInitialCellIndex(int index) {
        if (this.initialCellIndex == -1) {
            this.initialCellIndex = index;
        }
    }
    
    /**
     * Devuelve el índice de la celda inicial del robot.
     * @return índice de la celda inicial, o -1 si no fue asignado
     */
    public int getInitialCellIndex() {
        return initialCellIndex;
    }
    
    /**
     * Mueve el robot a la posición indicada en el canvas.
     * Ajusta tanto el cuerpo como los ojos en la nueva ubicación.
     * @param x nueva coordenada X
     * @param y nueva coordenada Y
     */
    public void moveTo(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        body.Teletransport(x, y);
        eye1.moveTo(x+8, y+5);
        eye2.moveTo(x+18, y+5);
        mouth.moveTo(x+8, y+15);

    }
    
    /**
     * Cambia el color del cuerpo del robot.
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" ...
     */
    public void changeColor(String newColor){
        body.changeColor(newColor);
    }
    
    /**
     * Hace visible al robot en el canvas (cuerpo y ojos).
     */
    public void makeVisible() {
        isVisible = true;
        body.makeVisible();
        eye1.makeVisible();
        eye2.makeVisible();
        mouth.makeVisible();
    }
    
    /**
     * Hace invisible al robot en el canvas (cuerpo y ojos).
     */
    public void makeInvisible() {
        isVisible = false;
        body.makeInvisible();
        eye1.makeInvisible();
        eye2.makeInvisible();
        mouth.makeInvisible();
    }
}
