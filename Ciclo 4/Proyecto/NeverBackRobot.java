/**
 * Robot que nunca retrocede: solo puede avanzar hacia adelante.
 */
public class NeverBackRobot extends Robot {
     /**
     * Constructor de la clase NeverBackRobot.
     * Crea un robot que no puede moverse hacia atrás y 
     * establece su color inicial en "salmon" mediante la
     * llamada al método {@code changeColorRobot("salmon")}
     * heredado de la clase {@code Robot}.
     */
    public NeverBackRobot() {
        super.changeColoRobot("salmon");
    }
    
     /**
     * Indica si el robot puede retroceder.
     * En este caso, siempre devuelve {@code false}, ya que 
     * el {@code NeverBackRobot} no tiene permitido moverse
     * hacia atrás.
     * @return {@code false}, porque este robot nunca puede retroceder.
     */
    @Override
    public boolean canMoveBack() {
        return false; 
    }
}
