import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.Random;
/**
 * Representa una espiral de celdas (Silk Road), donde cada celda puede contener
 * robots o tiendas. 
 * 
 * La espiral se genera automáticamente en el constructor y sigue un patrón
 * en espiral, donde las celdas se colocan alrededor de un centro.
 * 
 * Permite:
 * - Agregar y quitar tiendas en celdas específicas.
 * - Agregar y quitar robots en celdas.
 * - Mover robots entre celdas y simular compras en tiendas.
 * - Reiniciar el sistema devolviendo robots a sus posiciones iniciales 
 *   y reiniciando las tiendas.
 * - Consultar ganancias, estado de la Silk Road y posiciones de robots y tiendas.
 * - Mostrar/ocultar visualmente toda la espiral.
 * - Finalizar el simulador.
 * Ciclo 1.0
 */
public class SilkRoad {
    private List<Cell> celdas;
    private Random random;
    private Cell barraTotal;   
    private Cell barraProgreso; 
    private int barraMaxWidth = 300;
    private int extraMax;
    private boolean isVisible;
    /**
     * Constructor que genera la espiral de celdas.
     * Además inicializa la barra de progreso global (ganancias) que se dibuja en la parte superior del canvas.
     * @param cantidadCeldas número total de celdas en la espiral
     */
    public SilkRoad(int cantidadCeldas) {
        barraTotal = new Cell(0, "black");
        barraTotal.moveTo(10, 10);   
        barraTotal.changeSize(20,barraMaxWidth);
        
        barraProgreso = new Cell(0, "green");
        barraProgreso.moveTo(10, 10);
        barraProgreso.changeSize(20, 0); 

        celdas = new ArrayList<>();
        random = new Random();
        int direccion = 0; 
        int pasos = 1;     
        int x = 450;
        int y = 410;
        int celdaNum = 1;
        boolean isVisible = false;
        while (celdaNum <= cantidadCeldas) {
            for (int i = 0; i < pasos && celdaNum <= cantidadCeldas; i++) {
                String color = (celdaNum == 1) ? "red" : "green"; 
                Cell nueva = new Cell(celdaNum, color);
                nueva.moveTo(x, y);
                celdas.add(nueva);
                int salto = 40 + 2;
                switch (direccion) {
                    case 0: y -= salto; break; 
                    case 1: x += salto; break; 
                    case 2: y += salto; break; 
                    case 3: x -= salto; break; 
                }
                celdaNum++;
            }
            direccion = (direccion + 1) % 4;
            pasos++;
        }
    }
    
    /**
     * Constructor que inicializa la SilkRoad usando la entrada del problema.
     * @param days matriz donde cada fila representa un día:
     *        - days[i][0] = tipo (1=robot, 2=tienda)
     *        - days[i][1] = posición de la celda (1-indexado)
     *        - days[i][2] = cantidad de tenges (si es tienda, si no es 0)
     */
    public SilkRoad(int[][] days) {
        this(getMaxPosition(days));
        for (int i = 0; i < days.length; i++) {
            int t = days[i][0];  
            int x = days[i][1];  
            if (t == 1) {
                placeRobot(x);
            } else if (t == 2) {
                int c = days[i][2]; 
                placeStore(x, c);
            }
        }
    }
    
    /**
     * Busca el robot con mayor ganancia en toda la Silk Road
     * y lo hace parpadear.
     */
    private void blinkMaxProfitRobot() {
        Robot maxRobot = null;
        int maxProfit = Integer.MIN_VALUE;
        for (Cell c : celdas) {
            for (Robot r : c.getRobots()) {
                if (r.getProfit() > maxProfit) {
                    maxProfit = r.getProfit();
                    maxRobot = r;
                }
            }
        }
        if (maxRobot != null) {
            maxRobot.blink(3, 200); 
        }
    }
    
    /**
     * Método auxiliar que obtiene la posición máxima de la entrada.
     */
    private static int getMaxPosition(int[][] days) {
        int maxPos = 0;
        for (int i = 0; i < days.length; i++) {
            maxPos = Math.max(maxPos, days[i][1]);
        }
        return maxPos;
    }
    
    /**
     * Actualiza la barra de progreso que representa las ganancias acumuladas
     * de todas las tiendas respecto al máximo posible en ese momento.
     * 
     * - El máximo posible se calcula como: 
     *   (precio × stock actual) + (ganancias ya acumuladas).
     * - El ancho verde de la barra representa el porcentaje de ganancias logradas.
     * - Si no hay tiendas o no existe un máximo posible (> 0), la barra se oculta (ancho 0).
     */
    private void updateProgressBar() {
        int totalProfit = 0;
        int maxPossible = 0;
        for (Cell c : celdas) {
            if (c.getStore() != null) {
                Store s = c.getStore();
    
                totalProfit += s.getProfit();    
                maxPossible += s.getPrice();     
            }
        }
        maxPossible += this.extraMax;
        if (maxPossible == 0) {
            barraProgreso.changeSize(20, 0); 
            return;
        }
        int totalCobrado = maxPossible - totalProfit;
        int ancho = (int) ((totalCobrado * 1.0 / maxPossible) * barraMaxWidth);
        barraProgreso.changeSize(20, ancho);
    }

    /** 
     * Agrega una tienda a la celda indicada con el precio especificado. 
     * Se le asigna un color aleatorio.
     * @param celdaIndex número de celda (1-basado)
     * @param price precio de los productos de la tienda
     */
    public void placeStore(int celdaIndex, int price) {
        if (celdaIndex > 0 && celdaIndex <= celdas.size()) {
            Store store = new Store(price);    
            celdas.get(celdaIndex - 1).setStore(store);
            updateProgressBar();
        }
    }
    
     /**
     * Elimina la tienda de una celda específica.
     * Se actualiza la barra de progreso global para reflejar el cambio.
     * @param celdaIndex número de celda
     */
    public void removeStore(int celdaIndex) {
        if (celdaIndex > 0 && celdaIndex <= celdas.size()) {
            Cell c = celdas.get(celdaIndex - 1);
            c.removeStore(); 
        }
        updateProgressBar();
    }
    
     /**
     * Reabastece todas las tiendas con una cantidad de productos.
     * Actualiza la barra de progreso global.
     */
    public void resupplyStores() {
        int tiendasAbastecidas = 0;
        for (Cell c : celdas) {
            if (c.getStore() != null) {
                Store tienda = c.getStore();
                tienda.resetProfit(); 
                tiendasAbastecidas++;
                tienda.changeColor();
                this.extraMax += tienda.getPrice();
            }
        }
        if (isVisible) {
            if (tiendasAbastecidas > 0) {
                JOptionPane.showMessageDialog(null, "Se han reabastecido " + tiendasAbastecidas + " tienda(s) con sus precios respectivos");
            } else {
                JOptionPane.showMessageDialog(null, "No hay tiendas para reabastecer");
            }
        }
        updateProgressBar();
    }
    
    /**
     * Agrega un robot a la celda indicada, con color aleatorio,
     * siempre y cuando la celda no tenga ya un robot.
     * @param celdaIndex número de celda (1-indexado)
     */
    public void placeRobot(int celdaIndex) {
        if (celdaIndex <= 0 || celdaIndex > celdas.size()) {
            if (isVisible) {
                JOptionPane.showMessageDialog(null,"Índice de celda inválido: " + celdaIndex);
            }
            return;
        }
        Cell celda = celdas.get(celdaIndex - 1);
        if (!celda.getRobots().isEmpty()) {
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Ya existe un robot en la celda " + celdaIndex);
            }
            return;
        }
        Robot robot = new Robot(); 
        celda.addRobot(robot);
    }

     /**
     * Elimina el robot de la celda indicada (si existe).
     *  Después de cada intento de compra, se actualiza la barra de progreso global.
     * @param celdaIndex número de celda 
     */
    public void removeRobot(int celdaIndex) {
        if (celdaIndex > 0 && celdaIndex <= celdas.size()) {
            celdas.get(celdaIndex - 1).removeRobot();
        }
    }
    
    /**
     * Mueve todos los robots hacia adelante buscando la tienda más cercana
     * que les dé ganancia positiva.
     * Si no hay tienda adelante o no hay ganancia, el robot no se mueve.
     */
    public void moveRobots() {
        for (int i = 0; i < celdas.size(); i++) {
            Cell origen = celdas.get(i);
            if (!origen.getRobots().isEmpty()) {
                List<Robot> robots = new ArrayList<>(origen.getRobots());
                for (Robot robot : robots) {
                    List<Integer> destinosValidos = new ArrayList<>();
                    for (int j = 0; j < celdas.size(); j++) {
                        if (j == i) continue;
                        Cell destino = celdas.get(j);
                        if (destino.getStore() != null && destino.getStore().getProfit() > 0) {
                            int distancia = Math.abs(j - i);
                            int ganancia = destino.getStore().getProfit() - distancia;
                            if (ganancia > 0) {
                                destinosValidos.add(j);
                            }
                        }
                    }
                    int posicionActual = i;
                    for (int destinoIndex : destinosValidos) {
                        int pasos = destinoIndex - posicionActual;
                        moveRobot(posicionActual + 1, pasos); 
                        posicionActual = destinoIndex; 
                    }
                }
            }
        }
    }

    /**
     * Mueve todos los robots de una casilla específica la cantidad de pasos indicada.
     * Si un robot cae en una celda con tienda, automáticamente compra un producto (si hay stock).
     * @param celdaIndex número de la celda de origen (1-basado)
     * @param pasos número de casillas a avanzar (positivo) o retroceder (negativo)
     */
    public void moveRobot(int celdaIndex, int pasos) { 
        if (celdaIndex <= 0 || celdaIndex > celdas.size()) {
            return; 
        }
        Cell origen = celdas.get(celdaIndex - 1);
        if (!origen.getRobots().isEmpty()) {
            Robot robot = origen.getRobots().get(0); 
            origen.getRobots().remove(0); 
            int destinoIndex = (celdaIndex - 1) + pasos;
            if (destinoIndex < 0 || destinoIndex >= celdas.size()) {
                origen.addRobot(robot);
                if (isVisible) {
                    JOptionPane.showMessageDialog(null,"Movimiento inválido: el robot no puede salir de la red de "+ celdas.size() + " casillas.");
                }    
                return; 
            } 
            Cell destino = celdas.get(destinoIndex);
            destino.addRobot(robot);
            if (destino.getStore() != null) {
                Store tienda = destino.getStore();
                int ganancias = tienda.getProfit();
                if (ganancias > 0) {
                    robot.addProfit(ganancias);
                    if (isVisible) {
                        JOptionPane.showMessageDialog(null, "El robot ha recogido $" + ganancias+ " de la tienda en la celda " + destino.getRoadPosition());
                    }                    
                    tienda.incrementTimesEmptied();
                    tienda.reset();
                    destino.getStore().StoreVacio();
                    int costoMovimiento = Math.abs(pasos);
                    robot.addProfit(-costoMovimiento);
                    updateProgressBar();
                } else {
                    if (isVisible) {
                        JOptionPane.showMessageDialog(null,"La tienda en la celda " + destino.getRoadPosition()+ " no tiene ganancias disponibles.");
                    }    
                }
            }
            blinkMaxProfitRobot();
        }
    }
    
     /**
     * Reinicia el sistema:
     * - Devuelve los robots a sus posiciones iniciales.
     * - Restablece las tiendas (stock y profit).
     * - Actualiza la barra de progreso global.
     */
    public void reboot() {
        returnRobots();
         for (Cell cell : celdas) {
            if (cell.getStore() != null) {
                cell.getStore().reset(); 
            }
        }
        for (Cell cell : celdas) {
            for (Robot robot : cell.getRobots()) {
                robot.resetProfit();
            }
        }
        if (isVisible) {
            JOptionPane.showMessageDialog(null,"Sistema reiniciado: Robots en posiciones iniciales y tiendas restablecidas");
            updateProgressBar();
            }
    }
    
    /**
     * Devuelve únicamente los robots a sus posiciones iniciales,
     * sin afectar el estado de las tiendas.
     */
    public void returnRobots() {
        for (int i = 0; i < celdas.size(); i++) {
            Cell cell = celdas.get(i);
            List<Robot> copiaRobots = new ArrayList<>(cell.getRobots());  
    
            for (Robot robot : copiaRobots) {
                int initialIndex = robot.getInitialCellIndex();   
                if (initialIndex > 0) { 
                    int pasos = (initialIndex - 1) - i;  
                    moveRobot(i + 1, pasos);            
                }
            }
        }
        if (isVisible) {
                    JOptionPane.showMessageDialog(null,"Todos los robots han regresado a sus posiciones iniciales");
        }   
    }

    /**
     * Consulta la ganancia total y el detalle de ganancias de cada robot.
     * @return total acumulado de profits
     */
    public int porfit() {
        int total = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("Ganancias de los robots:\n\n");
        int robotId = 1; 
        for (Cell c : celdas) {
            for (Robot r : c.getRobots()) {
                int profit = r.getProfit();
                total += profit;
                sb.append("Robot ").append(robotId)
                  .append(" (en celda ").append(c.getRoadPosition()).append(")")
                  .append(" → Ganancia acumulada: $").append(profit)
                  .append("\n");
                robotId++;
            }
        }
        sb.append("\nGanancia total de todos los robots: $").append(total);
        if (isVisible) {
            JOptionPane.showMessageDialog(null, sb.toString());
        }
        return total;
    }

    /**
     * Obtiene las posiciones de todas las tiendas en la Silk Road.
     * @return arreglo bidimensional con los números de celda
     */
    public int[][] stores() {
        int count = 0;
        for (Cell cell : celdas) {
            if (cell.getStore() != null) {
                count++;
            }
        }
        int[][] celdasConTiendas = new int[count][2]; 
        int index = 0;
        for (Cell cell : celdas) {
            if (cell.getStore() != null) {
                Store tienda = cell.getStore();
                celdasConTiendas[index][0] = cell.getRoadPosition(); 
                celdasConTiendas[index][1] = tienda.getProfit();     
                index++;
            }
        }
        return celdasConTiendas;
    }
    
    /**
     * Devuelve una lista de las tiendas y las veces que han sido desocupadas.
     * Cada fila contiene: [posiciónCelda, vecesDesocupada]
     * El resultado se ordena por posición de celda en orden ascendente.
     * @return matriz int[][] con la información de las tiendas
     */
    public int[][] emptiedStores() {
        List<int[]> lista = new ArrayList<>();
        for (Cell c : celdas) {
            if (c.getStore() != null) {
                lista.add(new int[]{c.getRoadPosition(), c.getStore().getTimesEmptied()});
            }
        }
        lista.sort((a, b) -> Integer.compare(a[0], b[0]));
        int[][] resultado = new int[lista.size()][2];
        for (int i = 0; i < lista.size(); i++) {
            resultado[i][0] = lista.get(i)[0]; 
            resultado[i][1] = lista.get(i)[1]; 
        }
        return resultado;
    }

     /**
     * Obtiene las posiciones de todos los robots en la Silk Road.
     * @return arreglo bidimensional con los números de celda
     */
    public int[][] robots() {
        List<int[]> lista = new ArrayList<>();
        for (Cell cell : celdas) {
            if (!cell.getRobots().isEmpty()) {
                for (Robot robot : cell.getRobots()) {
                    lista.add(new int[]{cell.getRoadPosition(), robot.getProfit()});
                }
            }
        }
        int[][] celdasConRobots = new int[lista.size()][2];
        for (int i = 0; i < lista.size(); i++) {
            celdasConRobots[i] = lista.get(i);
        }
        return celdasConRobots;
    }
    
    /**
     * Devuelve las ganancias por movimiento de cada robot,
     * ordenadas por posición de celda.
     * Formato: [location, profit_move_1, profit_move_2, ...]
     */
    public int[][] profitPerMove() {
        List<int[]> lista = new ArrayList<>();
        for (Cell c : celdas) {
            if (!c.getRobots().isEmpty()) {
                for (Robot r : c.getRobots()) {
                    int[] history = r.getProfitHistory(); 
                    int[] fila = new int[history.length + 1];
                    fila[0] = c.getRoadPosition(); 
                    for (int j = 0; j < history.length; j++) {
                        fila[j + 1] = history[j];  
                    }
                    lista.add(fila);
                }
            }
        }
        lista.sort((a, b) -> Integer.compare(a[0], b[0]));
        int[][] resultado = new int[lista.size()][];
        for (int i = 0; i < lista.size(); i++) {
            resultado[i] = lista.get(i);
        }
        return resultado;
    }

    /**
     * Hace visible toda la espiral.
     */
    public void makeVisible() {
        for (Cell c : celdas) {
            c.makeVisible();
        }
        barraTotal.makeVisible();
        barraProgreso.makeVisible();
        isVisible = true;
    }

    /**
     * Hace invisible toda la espiral.
     */
    public void makeInvisible() {
        for (Cell c : celdas) {
            c.makeInvisible();
        }
        barraProgreso.makeInvisible();
        barraTotal.makeInvisible();
        isVisible = false; 
    }
    
    /**
     * Finaliza el simulador mostrando un mensaje de despedida
     * y cierra la aplicación.
     */
    public void finish() {
        if (isVisible) {
            JOptionPane.showMessageDialog(null,"El simulador ha finalizado.\n¡Gracias por usar la Silk Road!");
        }
        System.exit(0); 
    }
}