import java.util.Random;

/**
 * Representa una tienda 
 * 
 * Cada tienda tiene:
 * - Un precio fijo por producto.
 * - Un inventario (stock) de productos.
 * - Una ganancia acumulada (profit).
 * 
 * Se representa mediante un cuadrado (base) y un triángulo (techo).
 * 
 * @Ciclo 1.0
 */
public class Store {
    private int xPosition;
    private int yPosition;
    private int price;
    private int profit; 
    private int timesEmptied = 0;
    private final int initialPrice;
    private boolean isVisible;
    private Rectangle square;   
    private Triangle triangle;  
    private String[] availableColors = {"yellow", "blue",  "magenta","cyan", "orange", 
        "pink", "gray", "brown", "purple", "indigo", "violet","gold",
        "indigo","salmon","turquoise"};
    private Random random ;
    /**
     * Constructor: crea una nueva tienda con un precio específico.
     * Inicialmente el stock es 0 y la tienda no es visible.
     * @param price precio que cobra la tienda por cada producto vendido
     */
    public Store(int price) {
        this.price = price;
        this.profit = price;
        this.initialPrice = price; 

        this.square = new Rectangle();
        this.square.changeSize(40, 40);
        this.square.changeColor("lightGray");

        this.triangle = new Triangle();
        this.triangle.changeSize(30, 30);
        triangle.moveTo(90, 20); 
        this.triangle.changeColor("red");
        
        this.random = new Random();
        changeColor();
    }

    /** 
     * Hace visible la tienda en el canvas (base y techo).
    */
    public void makeVisible() {
        square.makeVisible();
        triangle.makeVisible();
    }

    /**
     * Hace invisible la tienda en el canvas (base y techo).
     */
    public void makeInvisible() {
        square.makeInvisible();
        triangle.makeInvisible();
    }
    
    /**
     * Mueve la tienda a una nueva posición en el canvas.
     * El cuadrado se posiciona directamente en (x,y)
     * y el triángulo se ajusta para quedar centrado encima.
     * @param x nueva coordenada X
     * @param y nueva coordenada Y
     */
    public void moveTo(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        square.moveTo(x, y);
        triangle.moveTo(x + 15, y + 10); 
    }
    
    /**
     * Reinicia la ganancia del objeto a su valor inicial.
     * Esto generalmente se usa para devolver el profit a su estado base
     * antes de que se realicen movimientos o transacciones.
     */
    public void resetProfit() {
        this.profit = initialPrice;
    }
    
    /**
     * Reinicia la tienda, dejando el stock en 0 y eliminando las ganancias.
     */
    public void reset() {
        this.profit = 0;
    }
    
    /**
     * Devuelve la ganancia acumulada de la tienda.
     * @return ganancia total (profit)
     */
    public int getProfit() {
        return profit; 
    }
    
    /**
     * Devuelve el precio actual de los productos de la tienda.
     * @return precio por unidad
     */
    public int getPrice() {
        return price;
    }
    
    /**
     * Cambia el color del techo de la tienda (triángulo).
     *@param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" ...
     */
    public void changeColor(){
        String color = availableColors[random.nextInt(availableColors.length)];
        triangle.changeColor(color);
    }
    
        /**
     * Cambia el color del techo de la tienda (triángulo).
     *@param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" ...
     */
    public void StoreVacio(){
        triangle.changeColor("red");
    }
    
    /**
     * Incrementa en 1 el contador de veces que la tienda
     * ha sido desocupada (es decir, cuando un robot recoge
     * todas sus ganancias y la tienda queda vacía).
     */
    public void incrementTimesEmptied() {
        timesEmptied++;
    }
    
    /**
     * Devuelve el número de veces que la tienda ha sido
     * desocupada desde que fue creada.
     *
     * @return número total de veces que la tienda quedó vacía
     */
    public int getTimesEmptied() {
        return timesEmptied;
    }
}
