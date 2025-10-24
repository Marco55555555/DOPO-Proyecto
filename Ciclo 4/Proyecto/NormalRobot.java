/**
 * Robot normal: puede moverse en cualquier dirección
 * y recoge todo el dinero disponible de las tiendas.
 */
public class NormalRobot extends Robot {
    
    /**
     * Constructor de la clase NormalRobot.
     * 
     * Crea un robot de tipo normal con la capacidad de moverse
     * en cualquier dirección y recoger todo el dinero disponible
     * de las tiendas. 
     * 
     * Al ser instanciado, el robot se inicializa con el color azul
     * mediante la llamada al método {@code changeColorRobot("blue")} 
     * de la clase padre {@code Robot}.
     */
    public NormalRobot() {
        super.changeColoRobot("blue");
    }
}
