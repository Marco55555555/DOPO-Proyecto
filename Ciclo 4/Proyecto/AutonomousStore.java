/**
 * Tienda autónoma: se ubica aleatoriamente al crearse.
 */
public class AutonomousStore extends Store {
     /**
     * Constructor de la clase AutonomousStore.
     * Inicializa la tienda con el precio indicado y configura su color a "pink"
     * mediante el método {@code changeColorStore("pink")} heredado de {@code Store}.
     * @param price el precio de venta o ganancia inicial de la tienda.
     */
    public AutonomousStore(int price) {
        super(price);
        super.changeColorStore("pink");
    }
}
