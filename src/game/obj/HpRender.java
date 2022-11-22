/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game.obj;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author PC
 */
public class HpRender {
    private final HP hp;

    public HpRender(HP hp) {
        this.hp = hp;
    }
    //tạo hàm đồ họa thanh máu
    
    protected void hpRender(Graphics2D g2, Shape shape,double y){
        if(hp.getCurrenHp()!=hp.getMAX_HP()){
        double hpy = shape.getBounds().getY()-y-10;
        g2.setColor(new Color(70,70,70));
        g2.fill(new Rectangle2D.Double(0,hpy,Player.PLAYER_SIZE,2));
        g2.setColor(new Color(253,91,91));
        double hpSize = hp.getCurrenHp() / hp.getMAX_HP()*Player.PLAYER_SIZE;
        g2.fill(new Rectangle2D.Double(0,hpy,hpSize,2));
        }
    }
    //phương thức giảm hp khi người chơi bị tên lửa tấn công
    public boolean updateHP(double cutHP){
        hp.setCurrenHp(hp.getCurrenHp()-cutHP);
        return hp.getCurrenHp()>0;
    }
    public double getHP(){
        return hp.getCurrenHp();
    }
    public void resetHP(){
        hp.setCurrenHp(hp.getMAX_HP());
    }
}
