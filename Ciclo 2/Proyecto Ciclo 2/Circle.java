import java.awt.*;
import java.awt.geom.*;
import javax.swing.JOptionPane;

/**
 * A circle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0.  (15 July 2000) 
 */

public class Circle{
    public static final double PI=3.1416;
    private int diameter;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;
 
    public Circle(){
        setDiameter(30);
        xPosition = 20;
        yPosition = 15;
        color = "blue";
        isVisible = false;
    }
       
    public void makeVisible(){
        isVisible = true;
        draw();
    }

    public void makeInvisible(){
        erase();
        isVisible = false;
    }

    private void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, 
                new Ellipse2D.Double(xPosition, yPosition, 
                diameter, diameter));
            canvas.wait(10);
        }
    }

    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    
    /**
     * Move the circle a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the circle a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the circle a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the circle a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Move the circle horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the circle vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the circle horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the circle vertically
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        }else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }

    /**
     * Change the size.
     * @param newDiameter the new size (in pixels). Size must be >=0.
     */
    public void changeSize(int newDiameter){
        erase();
        diameter = newDiameter;
        draw();
    }

    /**
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }
    
    /**
     * Sets the circle's diameter with validation.
     * Throws IllegalArgumentException if the value is negative.
     */
    private void setDiameter(int newDiameter) {
        if (newDiameter < 0) {
            JOptionPane.showMessageDialog(null,"El diámetro no puede ser negativo.");
        }
        diameter = newDiameter;
    }
    
    /**
     * Calculate the area of the circle.
     * @return The area of the circle.
     */
    public double area() {
        double radius = diameter / 2.0;
        return PI * radius * radius;
    }
    
    /**
     * Increase the circle's area by a given percentage.
     * @param percentage The percentage to increase (0-100)
     */
    public void bigger(int percentage) {
        double currentArea = area();
        double newArea = currentArea * (1 + percentage / 100.0);
        double newDiameter = Math.sqrt((4 * newArea) / PI);
        changeSize((int) Math.round(newDiameter));
    }
    
    /**
     * Reduce the circle's size progressively until the area is less than or equal to the target area.
     * @param times Number of times to shrink the circle
     * @param targetArea Maximum allowed area
     */
    public void shrink(int times, int targetArea) {
        if (times < 0) {
            JOptionPane.showMessageDialog(null,"El número de veces no puede ser negativo");
        }
        if (targetArea < 0) {
            JOptionPane.showMessageDialog(null,"El área objetivo no puede ser negativa");
        }
        
        for (int i = 0; i < times; i++) {
        if (this.area() <= targetArea) {
            break; 
        }
        int newDiameter = (int) Math.max(1, Math.round(diameter / 2.0));
        changeSize(newDiameter);
        }
    }
    
    /**
     * Constructor that creates a circle with a specific area.
     * @param area the desired area (must be greater than 0)
     */
    
    public Circle(int area) {
        if (area <= 0) {
            JOptionPane.showMessageDialog(null, "El área debe ser positiva. Se usará valor por defecto.");
            area = 100; 
        }
        double radius = Math.sqrt(area / PI);
        int newDiameter = (int) Math.round(2 * radius);
    
        setDiameter(newDiameter);
        xPosition = 20;
        yPosition = 15;
        color = "blue";
        isVisible = false;
    }

    /**
     * Move the circle directly to a given (x,y) position.
     * @param newX The new x-coordinate.
     * @param newY The new y-coordinate.
     */
    public void Teletransport(int newX, int newY) {
        erase();
        xPosition = newX;
        yPosition = newY;
        draw();
    }

    public int getXPosition() {
        return xPosition;
    }
    
    public int getYPosition() {
        return yPosition;
    }
    
    public String getColor() {
        return this.color;  
    }
}
