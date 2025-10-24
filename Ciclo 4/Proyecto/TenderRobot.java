/**
 * Robot tierno: solo toma la mitad del dinero de las tiendas que visita.
 */
public class TenderRobot extends Robot {
    /**
     * Constructor de la clase TenderRobot.
     * Crea un robot de tipo tierno que se inicializa con el color 
     * "gold" mediante la llamada al método {@code changeColorRobot("gold")}
     * heredado de la clase {@code Robot}.
     */
    public TenderRobot() {
        super.changeColoRobot("gold");
    }
    
    /**
     * Recolecta dinero de una tienda, pero solo la mitad del total disponible.
     * Este método redefine el comportamiento original del robot, limitando
     * la cantidad de dinero recolectado al 50% del valor recibido.
     * @param storeProfit la cantidad total de dinero disponible en la tienda.
     * @return la mitad del dinero disponible ({@code storeProfit / 2}).
     */
    @Override
    public int collectFromStore(int storeProfit) {
        return storeProfit / 2; 
    }
}
