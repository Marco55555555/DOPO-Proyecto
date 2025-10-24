import java.util.Random;

/**
 * Representa un robot.
 * El robot tiene un cuerpo circular y dos ojos (rectángulos pequeños),
 * puede moverse por el canvas, cambiar de color y hacerse visible/invisible.
 * 
 * Ciclo 1.0
 */
public abstract class Robot {
    private Circle body;   
    private int profit;
    private int xPosition;
    private int yPosition;
    private int[] profitHistory;
    private boolean isVisible;
    private int initialCellIndex; 
    private Rectangle eye1;
    private Rectangle eye2;
    private Rectangle mouth;
    private String[] availableColors = {"yellow", "blue",  "magenta","cyan", "orange", 
        "pink", "gray", "brown", "purple", "indigo", "violet","gold","silver",
        "indigo","salmon","turquoise"};
    private Random random ;
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
        this.profit = 0;
        
        this.random = new Random();
        this.profitHistory = new int[0];
        
        changeColor();
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
     * Cambia el color del cuerpo del robot de manera aleatoria
     * a partir de la lista de colores disponibles.
     */
    public void changeColor(){
        String color = availableColors[random.nextInt(availableColors.length)];
        body.changeColor(color);
        eye1.changeColor("black");
        eye2.changeColor("black");
        mouth.changeColor("black");
    }
    
    /**
     * Cambia el color del robot.
     *@param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" ...
     */
    public void changeColoRobot(String color){
        body.changeColor(color);
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
     * Agrega ganancia al robot.
     * @param amount cantidad a sumar
     */
    public void addProfit(int amount) {
        this.profit += amount; 
        if (amount > 0) {
            int[] nuevoHistorial = new int[profitHistory.length + 1];
            for (int i = 0; i < profitHistory.length; i++) {
                nuevoHistorial[i] = profitHistory[i];
            }
            nuevoHistorial[profitHistory.length] = amount;
            profitHistory = nuevoHistorial;
        }
    }

    /**
     * Devuelve la ganancia acumulada del robot.
     * @return ganancia total
     */
    public int getProfit() {
        return profit;
    }
    
    /**
     * Reinicia la ganancia del robot a 0.
     */
    public void resetProfit() {
        this.profit = 0;
        this.profitHistory = new int[0];
    }
    
    /**
     * Devuelve un arreglo con el historial de ganancias del robot
     * en cada movimiento realizado.
     * @return historial de ganancias por movimiento
     */
    public int[] getProfitHistory() {
        return profitHistory;
    }
    
     /**
     * Hace que el robot parpadee.
     * El parpadeo consiste en alternar visibilidad on/off varias veces
     * con un retraso entre cada cambio.
     * @param veces número de veces que parpadea
     * @param delayMs tiempo en milisegundos entre cambios de visibilidad
     */
    public void blink(int veces, int delayMs) {
        for (int i = 0; i < veces; i++) {
            changeColor(); 
            try { 
                Thread.sleep(delayMs); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); 
            }
        }
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
    
    /**
     * Indica si este robot puede moverse hacia atrás.
     * @return true si puede retroceder, false en caso contrario.
     */
    public  boolean canMoveBack(){
        return true;
    }

    /**
     * Calcula cuánto dinero recoge el robot de una tienda.
     * @param storeProfit ganancia disponible en la tienda
     * @return cantidad que el robot realmente toma
     */
    public  int collectFromStore(int storeProfit){
        return storeProfit;
    }
}
