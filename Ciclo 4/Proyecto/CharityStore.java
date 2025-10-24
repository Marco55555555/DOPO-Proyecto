/**
 * CharityStore: tienda que dona la mitad del profit a los robots
 * en lugar de vaciarse completamente.
 */
public class CharityStore extends Store {
    /**
     * Constructor de la clase CharityStore.
     * Inicializa la tienda con el valor de ganancia inicial proporcionado
     * y cambia su color a "turquoise" mediante el método {@code changeColorStore("turquoise")}.
     * @param price el monto inicial de ganancia de la tienda.
     */
    public CharityStore(int price) {
        super(price);
        super.changeColorStore("turquoise"); 
    }

     /**
     * Realiza una venta al robot, donándole la mitad de las ganancias actuales.
     * Si la tienda tiene ganancias disponibles, calcula la mitad de su 
     * {@code profit} como monto de donación, lo transfiere al robot mediante 
     * {@code addProfit()}, y reduce su ganancia actual en esa misma cantidad.
     * Si el profit llega a cero tras la transacción, la tienda se marca como vacía
     * utilizando {@code markEmpty()}.
     * @param robot el robot que recibe la donación de la tienda.
     */
    @Override
    public void sellTo(Robot robot) {
        if (profit > 0) {
            int donateAmount = profit / 2;
            robot.addProfit(donateAmount);
            profit -= donateAmount; 
            if (profit == 0) {
                markEmpty();
            }
        }
    }
    
    /**
     * Verifica si la tienda puede realizar una donación o venta.
     * La tienda solo puede donar si aún tiene ganancias disponibles.
     * @param robot el robot que intenta recibir la donación.
     * @return {@code true} si la tienda tiene ganancias; {@code false} en caso contrario.
     */
    @Override
    public boolean canSellTo(Robot robot) {
        return profit > 0;
    }
}
