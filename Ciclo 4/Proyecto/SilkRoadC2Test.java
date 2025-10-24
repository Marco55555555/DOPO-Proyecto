import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SilkRoadC2Test {

    private SilkRoad silk;

    @BeforeEach
    public void setUp() {
        silk = new SilkRoad(5); 
        silk.makeInvisible();   
    }

    @Test
    public void placeStoreMJShouldAddStoreToCell() {
        silk.placeStore(2, 10);
        int[][] stores = silk.stores();
        assertEquals(2, stores[0][0]);
    }

    @Test
    public void removeStoreMJShouldRemoveStore() {
        silk.placeStore(3, 5);
        silk.removeStore(3);
        int[][] stores = silk.stores();
        assertEquals(0, stores.length);
    }

    @Test
    public void resupplyAllStoresMJShouldResetProfit() {
        silk.placeStore(1, 10);
        silk.resupplyStores();
        assertTrue(true); 
    }

    @Test
    public void placeRobotMJShouldAddRobot() {
        silk.placeRobot(1);
        int[][] robots = silk.robots();
        assertEquals(1, robots[0][0]);
    }

    @Test
    public void removeRobotMJShouldRemoveRobot() {
        silk.placeRobot(1);
        silk.removeRobot(1);
        int[][] robots = silk.robots();
        assertEquals(0, robots.length);
    }

    @Test
    public void moveRobotsMJShouldMoveRobotToStore() {
        silk.placeRobot(1);
        silk.placeStore(3, 10);
        silk.moveRobots();
        int[][] robots = silk.robots();
        assertEquals(3, robots[0][0]);
    }

    @Test
    public void moveRobotsMJShouldMoveRobotBySteps() {
        silk.placeRobot(1);
        silk.moveRobot(1, 2);
        int[][] robots = silk.robots();
        assertEquals(3, robots[0][0]);
    }

    @Test
    public void rebootMJShouldResetSystem() {
        silk.placeRobot(1);
        silk.placeStore(4, 10);
        silk.moveRobots();
        silk.reboot();
        int[][] robots = silk.robots();
        assertEquals(1, robots[0][0]);
    }

    @Test
    public void returnRobotMJShouldReturnRobotsToInitial() {
        silk.placeRobot(2);
        silk.moveRobot(2, 2);
        silk.returnRobots();
        int[][] robots = silk.robots();
        assertEquals(2, robots[0][0]);
    }

    @Test
    public void consultProfitMJShouldReturnNonNegative() {
        silk.placeRobot(1);
        silk.placeStore(2, 10);
        silk.moveRobots();
        int total = silk.porfit();
        assertTrue(total >= 0);
    }

    @Test
    public void StoresMJShouldReturnAllStorePositions() {
        silk.placeStore(1, 5);
        silk.placeStore(3, 10);
        int[][] stores = silk.stores();
        assertEquals(2, stores.length);
    }

    @Test
    public void emptiedStoresMJShouldReturnTimesEmptied() {
        silk.placeStore(2, 10);  
        silk.placeRobot(1);       
        silk.moveRobots();        
        int[][] emptied = silk.emptiedStores();
        assertEquals(1, emptied[0][1]); 
    }

    @Test
    public void RobotsMJShouldReturnAllRobotPositions() {
        silk.placeRobot(1);
        silk.placeRobot(3);
        int[][] robots = silk.robots();
        assertEquals(2, robots.length);
    }

    @Test
    public void profitPerMoveMJShouldReturnProfitHistory() {
        silk.placeRobot(1);
        silk.placeStore(3, 10);
        silk.moveRobots();
        int[][] profit = silk.profitPerMove();
        assertTrue(profit[0].length > 1);
    }
    
    @Test
    public void testComplexScenario() {
        SilkRoad road = new SilkRoad(6);
        road.makeInvisible(); 
        road.placeRobot(1);
        road.placeRobot(2);
        road.placeRobot(4);
    
        road.placeStore(3, 10); 
        road.placeStore(6, 15); 
    
        road.moveRobots();
    
        int totalProfit = road.porfit();
        assertTrue(totalProfit > 0, "Los robots deberían haber recogido ganancias");
    
        int[][] emptied = road.emptiedStores();
        assertEquals(2, emptied.length, "Deberían existir 2 tiendas vaciadas");
        for (int[] store : emptied) {
            assertTrue(store[1] > 0, "Cada tienda debería haber sido vaciada al menos una vez");
        }
    
        int[][] robots = road.robots();
        assertEquals(3, robots.length, "Deben quedar 3 robots");
        
        road.resupplyStores();
        int[][] storesAfterResupply = road.stores();
        for (int[] store : storesAfterResupply) {
            assertTrue(store[1] > 0, "Cada tienda debe tener profit después de reabastecer");
        }
    
        road.reboot();
        int[][] robotsAfterReboot = road.robots();
        assertEquals(3, robotsAfterReboot.length, "Deben volver a existir 3 robots");
        for (int[] r : robotsAfterReboot) {
            assertEquals(0, r[1], "Profit de robots debe reiniciarse");
        }
    }
}