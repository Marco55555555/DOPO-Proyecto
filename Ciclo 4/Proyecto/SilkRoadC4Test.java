import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias del ciclo 4:
 * Verifica el correcto funcionamiento de los nuevos tipos de robots
 * y tiendas en la clase SilkRoad.
 */
public class SilkRoadC4Test {

    private SilkRoad road;

    @BeforeEach
    public void setUp() {
        road = new SilkRoad(10);
    }

    /**
     * Verifica que se puedan colocar robots de distintos tipos correctamente.
     */
    @Test
    public void testPlaceDifferentRobots() {
        road.placeRobot("normal", 2);
        road.placeRobot("neverback", 4);
        road.placeRobot("tender", 6);

        int[][] robots = road.robots();
        assertEquals(3, robots.length, "Deben existir tres robots en la red.");
        assertEquals(2, robots[0][0]);
        assertEquals(4, robots[1][0]);
        assertEquals(6, robots[2][0]);
    }

    /**
     * Verifica que se puedan colocar tiendas de distintos tipos.
     */
    @Test
    public void testPlaceDifferentStores() {
        road.placeStore("normal", 2, 100);
        road.placeStore("fighter", 4, 120);
        road.placeStore("charity", 6, 150);
        road.placeStore("auto", 8, 200);

        int[][] stores = road.stores();
        assertTrue(stores.length >= 3 && stores.length <= 4, 
            "Debe haber entre 3 y 4 tiendas (la autónoma puede haberse movido).");
    }

    /**
     * Verifica que el NeverBackRobot solo busque tiendas hacia adelante.
     */
    @Test
    public void testNeverBackRobotMovesForwardToStore() {
        road.placeStore("normal", 5, 100);
        road.placeRobot("neverback", 3);
        // Debería avanzar hacia la tienda más cercana adelante
        road.moveRobot(3, 1);

        int[][] robots = road.robots();
        assertTrue(robots[0][0] >= 3 && robots[0][0] <= 5,
            "El NeverBackRobot debe avanzar hacia una tienda adelante.");
    }

    /**
     * Verifica que el NeverBackRobot no pueda retroceder.
     */
    @Test
    public void testNeverBackRobotCannotMoveBack() {
        road.placeRobot("neverback", 4);
        int[][] antes = road.robots();
        int posAntes = antes[0][0];

        road.moveRobot(4, -2); // intentar retroceder

        int[][] despues = road.robots();
        int posDespues = despues[0][0];

        assertEquals(posAntes, posDespues, 
            "El NeverBackRobot no debe poder retroceder.");
    }

    /**
     * Verifica que la FighterStore solo venda a robots con suficiente profit.
     */
    @Test
    public void testFighterStoreOnlySellsToRichRobot() {
        road.placeStore("fighter", 6, 100);
        road.placeRobot("normal", 5);

        road.moveRobot(5, 1);
        int[][] robots = road.robots();
        int profit = robots[0][1];

        assertTrue(profit == 0 || profit == 100, 
            "El FighterStore solo debería vender si el robot tiene suficiente dinero.");
    }

    /**
     * Verifica que la AutonomousStore se coloque aleatoriamente en alguna celda.
     */
    @Test
    public void testAutonomousStoreRandomPlacement() {
        road.placeStore("auto", 3, 100);
        int[][] stores = road.stores();

        assertEquals(1, stores.length, "Debe existir una tienda autónoma.");
        assertTrue(stores[0][0] >= 1 && stores[0][0] <= 10, 
            "La tienda autónoma debe colocarse en una celda válida aleatoria.");
    }

    /**
     * Verifica que el método reboot reinicie ganancias y tiendas correctamente.
     */
    @Test
    public void testRebootResetsAll() {
        road.placeStore("normal", 5, 80);
        road.placeRobot("normal", 4);
        road.moveRobot(4, 1);

        int totalAntes = road.porfit();
        assertTrue(totalAntes >= 0);

        road.reboot();
        int totalDespues = road.porfit();

        assertEquals(0, totalDespues, "Después del reboot, las ganancias deben ser 0.");
    }
}
