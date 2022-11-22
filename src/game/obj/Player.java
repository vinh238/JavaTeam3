/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game.obj;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import javax.swing.ImageIcon;

public class Player extends HpRender{
    public Player() {
        super(new HP(90,80));
        this.image = new ImageIcon(getClass().getResource("/game/image/plane.png")).getImage();
        this.image_speed = new ImageIcon(getClass().getResource("/game/image/plane_speed.png")).getImage();
        Path2D p = new Path2D.Double();
        p.moveTo(0, 15);
        p.lineTo(20, 5);
        p.lineTo(PLAYER_SIZE+15, PLAYER_SIZE/2);
        p.lineTo(20, PLAYER_SIZE-5);
        p.lineTo(0, PLAYER_SIZE-15);
        playerShape = new Area(p);
    }
    //kích thước trình phát gán giá trị tĩnh là 64
    public static final double PLAYER_SIZE = 64;
    //biến double x y lưu trữ giá trị bắt đầu của trình phát
    private double x;
    private double y;
    private final float MAX_SPEED = 1f;
    private float speed = 0f;
    //lưu hướng của đối tượng
    private float angle = 0f;
    private final Area playerShape;
    private final Image image;
    private final Image image_speed;
    private boolean speedUp;
    private boolean alive = true;
    
    public void changeLocation(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public void update(){
        x+=Math.cos(Math.toRadians(angle)) * speed;
        y+=Math.sin(Math.toRadians(angle))*speed;
    }
    //tạo phương thức thay dổi hướng của trình phát
    // đặt điều kiện cho góc từ 0 đến 359 độ
    public void changeAngle(float angle){
        if(angle <0){
            angle = 359;
        }else if(angle >359){
            angle = 0;    
        }
        this.angle = angle;
    }
    //vẽ đồ họa và trình phát
    public void draw(Graphics2D g2){
        AffineTransform oldTransform = g2.getTransform();
        g2.translate(x, y);
        //xoay ảnh sang góc 0 độ
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle+45),PLAYER_SIZE/2,PLAYER_SIZE/2);
        g2.drawImage(speedUp ? image_speed : image, tran, null);
        hpRender(g2, getShape(), y);
        g2.setTransform(oldTransform);
//        g2.setColor(new Color(12,173,84));
//        g2.draw(getShape());
//        g2.draw(getShape().getBounds());
    }
    //lớp định dạng tên lửa
    public Area getShape(){
        AffineTransform afx = new AffineTransform();
        afx.translate(x, y);
        afx.rotate(Math.toRadians(angle),PLAYER_SIZE/2,PLAYER_SIZE/2);
        return new Area(afx.createTransformedShape(playerShape));
    }
    
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getAngle(){
        return angle;
    }
    public void speedUp(){
        speedUp = true;
        if(speed > MAX_SPEED){
            speed = MAX_SPEED;
        }else{
            speed+=0.01f;
        }
    }
    public void speedDown(){
        speedUp = false;
        if(speed <=0){
            speed = 0;
        }else{
            speed -= 0.003f;
        }
    }
    //goi phương thức hp kết xuất rong phương thức vẽ
    public boolean isAlive(){
        return alive;
    }
    public void setAlive(boolean alive){
        this.alive = alive;
    }
    public void reset(){
        alive = true;
        resetHP();
        angle =0;
        speed = 0;
    }
}
