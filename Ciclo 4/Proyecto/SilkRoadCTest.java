import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SilkRoadCTest {

    private SilkRoad silk;

    @BeforeEach
    public void setUp() {
        silk = new SilkRoad(6);
        silk.makeInvisible();
    }

    @Test
    public void moveRobotsShouldNotMoveWithoutStores() {
        silk.placeRobot(1);
        silk.moveRobots();
        int[][] robots = silk.robots();
        assertEquals(1, robots[0][0], "El robot no debería moverse si no hay tiendas");
    }

    @Test
    public void rebootShouldNotLoseRobots() {
        silk.placeRobot(1);
        silk.placeStore(4, 10);
        silk.moveRobots();
        silk.reboot();
        int[][] robots = silk.robots();
        assertTrue(robots.length > 0, "Los robots no deberían desaparecer con reboot");
    }

    @Test
    public void returnRobotsShouldRestoreOriginalPosition() {
        silk.placeRobot(2);
        silk.moveRobot(2, 2);
        silk.returnRobots();
        int[][] robots = silk.robots();
        assertEquals(2, robots[0][0], "El robot debería regresar a su posición original");
    }

    @Test
    public void storesShouldBeEmptyInitially() {
        int[][] stores = silk.stores();
        assertEquals(0, stores.length, "Al inicio no debería haber tiendas");
    }

    @Test
    public void emptiedStoresShouldIncrementOnlyAfterMove() {
        silk.placeStore(2, 10);
        silk.placeRobot(1);
        int[][] emptied = silk.emptiedStores();
        assertEquals(1, emptied.length, "No debería aparecer tienda vaciada sin mover robots");
    }

    @Test
    public void testRobotCannotReachStoreTooFar() {
        silk.placeRobot(1);
        silk.placeStore(6, 5); 
        silk.moveRobots();
        int totalProfit = silk.porfit();
        assertEquals(0, totalProfit, "El robot no debería recolectar profit de una tienda lejana con bajo valor");
    }

    @Test
    public void testMultipleRobotsCompeteForSameStore() {
        silk.placeRobot(1);
        silk.placeRobot(4);
        silk.placeStore(3, 10);
        silk.moveRobots();
        int totalProfit = silk.porfit();
        assertTrue(totalProfit > 0, "Algún robot debería recoger la tienda");
        int[][] emptied = silk.emptiedStores();
        assertEquals(1, emptied.length, "La tienda debería vaciarse solo una vez");
    }

    @Test
    public void testResupplyRestoresStoreProfit() {
        silk.placeRobot(1);
        silk.placeStore(2, 20);
        silk.moveRobots();
        assertTrue(silk.porfit() > 0, "El robot debería recoger algo de profit");
        silk.reboot();
        silk.resupplyStores();
        int[][] stores = silk.stores();
        assertTrue(stores[0][1] > 0, "La tienda debería reponerse después del resupply");
    }

    @Test
    public void testReturnRobotsAfterMoves() {
        silk.placeRobot(2);
        silk.placeStore(5, 15);
        silk.moveRobots();
        int[][] robotsAfterMove = silk.robots();
        assertEquals(5, robotsAfterMove[0][0], "El robot debería moverse hacia la tienda");
        silk.returnRobots();
        int[][] robotsAfterReturn = silk.robots();
        assertEquals(2, robotsAfterReturn[0][0], "El robot debería regresar a su posición original");
    }

    @Test
    public void testEmptyRoadHasZeroProfit() {
        long totalProfit = silk.porfit();
        assertEquals(0, totalProfit, "Si no hay robots ni tiendas, el profit debe ser 0");
        int[][] emptied = silk.emptiedStores();
        assertEquals(0, emptied.length, "No debe haber tiendas vaciadas en un camino vacío");
    }

    @Test
    public void testComplexScenario() {
        silk.placeRobot(1);
        silk.placeRobot(2);
        silk.placeRobot(4);
        silk.placeStore(3, 10);
        silk.placeStore(6, 15);

        silk.moveRobots();
        int totalProfit = silk.porfit();
        assertTrue(totalProfit > 0, "Los robots deberían haber recogido ganancias");

        int[][] emptied = silk.emptiedStores();
        assertEquals(2, emptied.length, "Deberían existir 2 tiendas vaciadas");
        for (int[] store : emptied) {
            assertTrue(store[1] > 0, "Cada tienda debería haber sido vaciada al menos una vez");
        }

        int[][] robots = silk.robots();
        assertEquals(3, robots.length, "Deben quedar 3 robots");

        silk.resupplyStores();
        int[][] storesAfterResupply = silk.stores();
        for (int[] store : storesAfterResupply) {
            assertTrue(store[1] > 0, "Cada tienda debe tener profit después de reabastecer");
        }

        silk.reboot();
        int[][] robotsAfterReboot = silk.robots();
        assertEquals(3, robotsAfterReboot.length, "Deben volver a existir 3 robots");
        for (int[] r : robotsAfterReboot) {
            assertEquals(0, r[1], "Profit de robots debe reiniciarse");
        }
    }
}
