/**
 * FighterStore: solo vende a robots con más dinero acumulado
 * que la tienda misma.
 */
public class FighterStore extends Store {
     /**
     * Constructor de la clase FighterStore.
     * Inicializa la tienda con el valor de ganancia inicial especificado
     * y cambia su color a "purple" mediante el método {@code changeColorStore("purple")}.
     * @param price el monto inicial de ganancias de la tienda.
     */
    public FighterStore(int price) {
        super(price);
        super.changeColorStore("purple");
    }
    
    /**
     * Determina si la tienda puede venderle a un robot específico.
     * La venta solo es posible si el robot tiene más dinero acumulado
     * que la tienda y si la tienda aún posee ganancias disponibles.
     * @param robot el robot que intenta comprar a la tienda.
     * @return {@code true} si el robot puede comprar; {@code false} en caso contrario.
     */
    @Override
    public boolean canSellTo(Robot robot) {
        return robot.getProfit() > this.profit && profit > 0;
    }
}
