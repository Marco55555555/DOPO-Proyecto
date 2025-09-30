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
public class Espiral {
    private List<Cell> celdas;
    private String[] availableColors = {"yellow", "blue",  "magenta","cyan", "orange", 
        "pink", "gray", "brown", "purple", "indigo", "violet","gold","silver",
        "indigo","salmon","turquoise"};
    private Random random;
    private Cell barraTotal;   
    private Cell barraProgreso; 
    private int barraMaxWidth = 300;

    /**
     * Constructor que genera la espiral de celdas.
     * Además inicializa la barra de progreso global (ganancias) que se dibuja en la parte superior del canvas.
     * @param cantidadCeldas número total de celdas en la espiral
     */
    public Espiral(int cantidadCeldas) {
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
                maxPossible += s.getPrice() * (s.getStock() + (s.getProfit() / s.getPrice()));
            }
        }
    
        if (maxPossible == 0) {
            barraProgreso.changeSize(0, 20);
            return;
        }
    
        int ancho = (int) ((totalProfit * 1.0 / maxPossible) * barraMaxWidth);
        barraProgreso.changeSize(20,ancho);
    }


    /** 
     * Agrega una tienda a la celda indicada con el precio especificado. 
     * Se le asigna un color aleatorio.
     * @param celdaIndex número de celda (1-basado)
     * @param price precio de los productos de la tienda
     */
    public void addStore(int celdaIndex, int price) {
        if (celdaIndex > 0 && celdaIndex <= celdas.size()) {
            Store store = new Store(price);    
            String randomColor = availableColors[random.nextInt(availableColors.length)];
            store.changeColor(randomColor);
            celdas.get(celdaIndex - 1).setStore(store);
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
    public void resupplyAllStores() {
        int tiendasAbastecidas = 0;
        int cantidad = 1;
        for (int i = 0; i < celdas.size(); i++) {
            Cell c = celdas.get(i);
            if (c.getStore() != null) {
                c.getStore().resupply(cantidad);
                tiendasAbastecidas++;
            }
        }
        
        if (tiendasAbastecidas > 0) {
            JOptionPane.showMessageDialog(
                null,"Se han agregado " + cantidad + " productos a " + tiendasAbastecidas + " tienda(s)");
        } else {
            JOptionPane.showMessageDialog(
                null, "No hay tiendas para reabastecer");
        }
        updateProgressBar();
    }
    
    /**
     * Agrega un robot a la celda indicada, con color aleatorio,
     * siempre y cuando la celda no tenga ya un robot.
     * @param celdaIndex número de celda (1-indexado)
     */
    public void addRobot(int celdaIndex) {
        if (celdaIndex > 0 && celdaIndex <= celdas.size()) {
            Cell celda = celdas.get(celdaIndex - 1);
                if (celda.getRobots().isEmpty()) {
                Robot robot = new Robot(); 
                String randomColor = availableColors[random.nextInt(availableColors.length)];
                robot.changeColor(randomColor);
                celda.addRobot(robot);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Ya existe un robot en la celda " + celdaIndex);
            }
        } else {
            JOptionPane.showMessageDialog( null,"Índice de celda inválido: " + celdaIndex);
        }
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
     * Mueve todos los robots de una casilla específica la cantidad de pasos indicada.
     * Si un robot cae en una celda con tienda, automáticamente compra un producto (si hay stock).
     * @param celdaIndex número de la celda de origen (1-basado)
     * @param pasos número de casillas a avanzar (positivo) o retroceder (negativo)
     */
     public void moveRobots(int celdaIndex, int pasos) {
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
                JOptionPane.showMessageDialog(null,"Movimiento inválido: el robot no puede salir de la red de "+ celdas.size() + " casillas.");
                return; 
            }
                
            Cell destino = celdas.get(destinoIndex);
            destino.addRobot(robot);
    
            if (destino.getStore() != null) {
                Store tienda = destino.getStore();
                if (tienda.getStock() > 0) {
                    tienda.sell();  
                    JOptionPane.showMessageDialog(null, "Un robot ha comprado en la tienda de la celda "+ destino.getRoadPosition() + " por $" + tienda.getPrice());
                    updateProgressBar();
                } else {
                    JOptionPane.showMessageDialog(null,"La tienda en la celda " + destino.getRoadPosition()+ " no tiene stock disponible.");
                }
            }
        }
    }
    
     /**
     * Reinicia el sistema:
     * - Devuelve los robots a sus posiciones iniciales.
     * - Restablece las tiendas (stock y profit).
     * - Actualiza la barra de progreso global.
     */
    public void reboot() {
        for (int i = 0; i < celdas.size(); i++) {
            Cell cell = celdas.get(i);
            List<Robot> copiaRobots = new ArrayList<>(cell.getRobots());  
    
            for (Robot robot : copiaRobots) {
                int initialIndex = robot.getInitialCellIndex();   
                if (initialIndex > 0) { 
                    int pasos = (initialIndex - 1) - i;           
                    moveRobots(i + 1, pasos);                     
                }
            }
        }
        
         for (Cell cell : celdas) {
        if (cell.getStore() != null) {
            cell.getStore().reset(); 
        }
        }
        JOptionPane.showMessageDialog(null,"Sistema reiniciado: Robots en posiciones iniciales y tiendas restablecidas");
        updateProgressBar();
    }
    
    /**
     * Devuelve únicamente los robots a sus posiciones iniciales,
     * sin afectar el estado de las tiendas.
     */
    public void retuturnRobot() {
        for (int i = 0; i < celdas.size(); i++) {
            Cell cell = celdas.get(i);
            List<Robot> copiaRobots = new ArrayList<>(cell.getRobots());  
    
            for (Robot robot : copiaRobots) {
                int initialIndex = robot.getInitialCellIndex();   
                if (initialIndex > 0) { 
                    int pasos = (initialIndex - 1) - i;           
                    moveRobots(i + 1, pasos);                     
                }
            }
        }
        JOptionPane.showMessageDialog( null,"Sistema reiniciado: Robots en posiciones iniciales");
    }

    /**
     * Consulta la ganancia total de todas las tiendas en la Silk Road.
     * @return total acumulado de profits
     */
    public int consultPorfit() {
        int total = 0;
        for (Cell c : celdas) {
            if (c.getStore() != null) {
                total += c.getStore().getProfit();
            }
        }
        JOptionPane.showMessageDialog(null,"La ganancia total de todas las tiendas es: " + total);
         return total; 
    }

     /**
     * Consulta el estado actual de la Silk Road.
     * Solo muestra celdas que tienen tiendas o robots.
     */
    public void consultSilkRoad() {
        StringBuilder sb = new StringBuilder();
        sb.append(" Estado actual de la Silk Road:\n\n");
    
        for (Cell c : celdas) {
            boolean tieneTienda = (c.getStore() != null);
            boolean tieneRobots = !c.getRobots().isEmpty();
    
            if (tieneTienda || tieneRobots) {
                sb.append("Celda ").append(c.getRoadPosition());
    
                if (tieneTienda) {
                    sb.append("  Tienda → Profit: ")
                      .append(c.getStore().getProfit())
                      .append(", Stock: ")
                      .append(c.getStore().getStock());
                }
    
                if (tieneRobots) {
                    sb.append("  Robots: ")
                      .append(c.getRobots().size());
                }
                sb.append("\n");
            }
        }
    
        if (sb.toString().equals(" Estado actual de la Silk Road :\n\n")) {
            sb.append("No hay celdas con tiendas ni robots actualmente.");
        }
        JOptionPane.showMessageDialog(null,sb.toString());
    }
    
    /**
     * Obtiene las posiciones de todas las tiendas en la Silk Road.
     * @return arreglo bidimensional con los números de celda
     */
    public int[][] Stores() {
        int count = 0;
        for (Cell cell : celdas) {
            if (cell.getStore() != null) {
                count++;
            }
        }
        
        int[][] celdasConTiendas = new int[count][1];
        int index = 0;
        
        for (Cell cell : celdas) {
            if (cell.getStore() != null) {
                celdasConTiendas[index][0] = cell.getRoadPosition();  
                index++;
            }
        }
        return celdasConTiendas;
    }
    
     /**
     * Obtiene las posiciones de todos los robots en la Silk Road.
     * @return arreglo bidimensional con los números de celda
     */
    public int[][] Robots() {
        int count = 0;
        for (Cell cell : celdas) {
            if (!cell.getRobots().isEmpty()) {
                count++;
            }
        }
        
        int[][] celdasConRobots = new int[count][1];
        int index = 0;
        
        for (Cell cell : celdas) {
            if (!cell.getRobots().isEmpty()) {
                celdasConRobots[index][0] = cell.getRoadPosition();  
                index++;
            }
        }
        return celdasConRobots;
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
    }
    
    /**
     * Finaliza el simulador mostrando un mensaje de despedida
     * y cierra la aplicación.
     */
    public void finish() {
        JOptionPane.showMessageDialog(null,"El simulador ha finalizado.\n¡Gracias por usar la Silk Road!");
        System.exit(0); 
    }
}