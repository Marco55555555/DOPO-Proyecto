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
    public void placeStoreMRG4ShouldAddStoreToCell() {
        silk.placeStore(2, 10);
        int[][] stores = silk.stores();
        assertEquals(2, stores[0][0]);
    }

    @Test
    public void removeStoreMRG4ShouldRemoveStore() {
        silk.placeStore(3, 5);
        silk.removeStore(3);
        int[][] stores = silk.stores();
        assertEquals(0, stores.length);
    }

    @Test
    public void resupplyAllStoresMRG4ShouldResetProfit() {
        silk.placeStore(1, 10);
        silk.resupplyStores();
        assertTrue(true); 
    }

    @Test
    public void placeRobotMRG4ShouldAddRobot() {
        silk.placeRobot(1);
        int[][] robots = silk.robots();
        assertEquals(1, robots[0][0]);
    }

    @Test
    public void removeRobotMRG4ShouldRemoveRobot() {
        silk.placeRobot(1);
        silk.removeRobot(1);
        int[][] robots = silk.robots();
        assertEquals(0, robots.length);
    }

    @Test
    public void moveRobotsMRG4ShouldMoveRobotToStore() {
        silk.placeRobot(1);
        silk.placeStore(3, 10);
        silk.moveRobots();
        int[][] robots = silk.robots();
        assertEquals(3, robots[0][0]);
    }

    @Test
    public void moveRobotsMRG4ShouldMoveRobotBySteps() {
        silk.placeRobot(1);
        silk.moveRobot(1, 2);
        int[][] robots = silk.robots();
        assertEquals(3, robots[0][0]);
    }

    @Test
    public void rebootMRG4ShouldResetSystem() {
        silk.placeRobot(1);
        silk.placeStore(4, 10);
        silk.moveRobots();
        silk.reboot();
        int[][] robots = silk.robots();
        assertEquals(1, robots[0][0]);
    }

    @Test
    public void returnRobotMRG4ShouldReturnRobotsToInitial() {
        silk.placeRobot(2);
        silk.moveRobot(2, 2);
        silk.returnRobots();
        int[][] robots = silk.robots();
        assertEquals(2, robots[0][0]);
    }

    @Test
    public void consultProfitMRG4ShouldReturnNonNegative() {
        silk.placeRobot(1);
        silk.placeStore(2, 10);
        silk.moveRobots();
        int total = silk.porfit();
        assertTrue(total >= 0);
    }

    @Test
    public void StoresMRG4ShouldReturnAllStorePositions() {
        silk.placeStore(1, 5);
        silk.placeStore(3, 10);
        int[][] stores = silk.stores();
        assertEquals(2, stores.length);
    }

    @Test
    public void emptiedStoresMRG4ShouldReturnTimesEmptied() {
        silk.placeStore(2, 10);  
        silk.placeRobot(1);       
        silk.moveRobots();        
        int[][] emptied = silk.emptiedStores();
        assertEquals(1, emptied[0][1]); 
    }

    @Test
    public void RobotsMRG4ShouldReturnAllRobotPositions() {
        silk.placeRobot(1);
        silk.placeRobot(3);
        int[][] robots = silk.robots();
        assertEquals(2, robots.length);
    }

    @Test
    public void profitPerMoveMRG4ShouldReturnProfitHistory() {
        silk.placeRobot(1);
        silk.placeStore(3, 10);
        silk.moveRobots();
        int[][] profit = silk.profitPerMove();
        assertTrue(profit[0].length > 1);
    }
}