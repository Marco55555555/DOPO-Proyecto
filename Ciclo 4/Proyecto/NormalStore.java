 /**
 * Tienda normal: vende siempre que tenga ganancias disponibles.
 */
public class NormalStore extends Store {
    /**
     * Representa una tienda normal dentro del sistema.
     * La {@code NormalStore} vende sus productos siempre que tenga 
     * ganancias disponibles, sin aplicar restricciones adicionales.
     * Hereda de la clase {@code Store} y se inicializa con un precio
     * específico y un color azul que la identifica visualmente.
     */
    public NormalStore(int price) {
        super(price);
        super.changeColorStore("blue");
    }
}
