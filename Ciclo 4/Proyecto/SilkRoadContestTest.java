import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SilkRoadContestTest {
    private SilkRoadContest contest;

    @BeforeEach
    public void setUp() {
        contest = new SilkRoadContest();
    }

    @Test
    public void solveSingleRobotMJShouldReturnZeroProfitIfNoStore() {
        int[][] days = { {1, 1} }; 
        long[] resultados = contest.solve(days);
        assertEquals(1, resultados.length);
        assertEquals(0, resultados[0]);
    }

    @Test
    public void solveSingleStoreMJShouldReturnZeroProfitIfNoRobot() {
        int[][] days = { {2, 1, 10} }; 
        long[] resultados = contest.solve(days);
        assertEquals(1, resultados.length);
        assertEquals(0, resultados[0]);
    }

    @Test
    public void solveRobotAndStoreMJShouldReturnNonNegativeProfit() {
        int[][] days = {
            {1, 1}, 
            {2, 2, 20}
        };
        long[] resultados = contest.solve(days);
        assertEquals(2, resultados.length);
        assertEquals(0, resultados[0]); 
        assertTrue(resultados[1] >= 0, "Día 2 debe tener ganancias");
    }

    @Test
    public void solveMultipleRobotsAndStoresMJShouldReturnCorrectLength() {
        int[][] days = {
            {1, 1},
            {1, 2},
            {2, 3, 15},
            {2, 1, 10}
        };
        long[] resultados = contest.solve(days);
        assertEquals(4, resultados.length);
    }

    @Test
    public void solveEmptyDaysMJShouldReturnEmptyArray() {
        int[][] days = {};
        long[] resultados = contest.solve(days);
        assertEquals(0, resultados.length);
    }
}
