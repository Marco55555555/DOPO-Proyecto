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
    private int stock;          
    private boolean isVisible;
    private int profit; 
    private Rectangle square;   
    private Triangle triangle;  

    /**
     * Constructor: crea una nueva tienda con un precio específico.
     * Inicialmente el stock es 0 y la tienda no es visible.
     * @param price precio que cobra la tienda por cada producto vendido
     */
    public Store(int price) {
        this.price = price;
        this.stock = 0;          
        
        this.square = new Rectangle();
        this.square.changeSize(40, 40);
        this.square.changeColor("lightGray");

        this.triangle = new Triangle();
        this.triangle.changeSize(30, 30);
        triangle.moveTo(90, 20); 
        this.triangle.changeColor("red");
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
     * Reabastece el inventario de la tienda.
     * @param cantidad número de productos a añadir (si es mayor que 0)
     */
    public void resupply(int cantidad) {
        if (cantidad > 0) {
            this.stock += cantidad;
        }
    }
    
    /**
     * Vende un producto de la tienda, si hay stock disponible.
     * Disminuye el stock en 1 y aumenta la ganancia acumulada.
     */
    public void sell() {
        if (stock > 0) {
            stock--;
            profit += price;   
        }
    }
    
    /**
     * Reinicia la tienda, dejando el stock en 0 y eliminando las ganancias.
     */
    public void reset() {
        this.stock = 0;
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
     * Devuelve la cantidad de productos disponibles en inventario.
     * @return stock actual
     */
    public int getStock() {
        return stock;
    }
    
    /**
     * Cambia el color del techo de la tienda (triángulo).
     *@param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" ...
     */
    public void changeColor(String newColor){
        triangle.changeColor(newColor);
    }
}
