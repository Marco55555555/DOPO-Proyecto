import java.util.ArrayList;
import java.util.List;
/**
 * Clase de concurso que utiliza la SilkRoad para resolver el problema
 * y también para simular paso a paso.
 */
public class SilkRoadContest {
    /**
     * Resuelve el problema .
     * @param days matriz con los días
     * @return arreglo de ganancias acumuladas por día
     */
    public long[] solve(int[][] days) {
        int maxPos = 0;
        for (int[] d : days) {
            if (d != null && d.length > 1) {
                maxPos = Math.max(maxPos, d[1]);
            }
        }
        SilkRoad road = new SilkRoad(Math.max(1, maxPos));
        int n = days.length;
        long[] resultados = new long[n];
        for (int i = 0; i < n; i++) {
            road.resupplyStores();
            road.returnRobots();
            int[] d = days[i];
            if (d[0] == 1) {
                road.placeRobot(d[1]);
            } else if (d[0] == 2) {
                road.placeStore(d[1], d[2]);
            }
            road.moveRobots();
            long totalProfit = road.porfit();
            resultados[i] = totalProfit;
            road.reboot();
        }
        return resultados;
    }

    /**
     * Simula la solución paso a paso.
     * @param days matriz con los días
     * @param slow si es true, hace pausas entre días
     */
    public void simulate(int[][] days, boolean slow) {
        int maxPos = 0;
        for (int[] d : days) {
            if (d != null && d.length > 1) {
                maxPos = Math.max(maxPos, d[1]);
            }
        }
        SilkRoad road = new SilkRoad(Math.max(1, maxPos));
        int n = days.length;
        road.makeVisible();
        for (int i = 0; i < n; i++) {
            System.out.println("=== Día " + (i + 1) + " ===");
            road.resupplyStores();
            road.returnRobots();
            int[] d = days[i];
            if (d[0] == 1) {
                System.out.println("Nuevo robot en celda " + d[1]);
                road.placeRobot(d[1]);
                road.makeVisible();
            } else if (d[0] == 2) {
                System.out.println("Nueva tienda en celda " + d[1] +
                                   " con " + d[2] + " tenges");
                road.placeStore(d[1], d[2]);
                road.makeVisible();
            }
            road.moveRobots();
            long totalProfit = road.porfit();
            System.out.println("Profit máximo acumulado: " + totalProfit);
            road.reboot();
            if (slow) {
                try { Thread.sleep(1500); } catch (InterruptedException e) {}
            }
        }
        System.out.println("=== Simulación finalizada ===");
    }
}
