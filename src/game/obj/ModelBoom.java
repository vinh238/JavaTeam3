/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game.obj;

/**
 *
 * @author PC
 */
//tạo kích thước và thay đổi
public class ModelBoom {
    private double size;
    private float angle;

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public float getAngle() {
        return angle;
    }
    public ModelBoom (double size, float angle){
        this.size = size;
        this.angle = angle;
    }
    public ModelBoom(){
        
    }
    public void setAngle(float angle) {
        this.angle = angle;
    }
}
