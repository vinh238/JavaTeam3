/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game.obj;

/**
 *
 * @author PC
 */
public class HP {
    

    public double getMAX_HP() {
        return MAX_HP;
    }

    public void setMAX_HP(double MAX_HP) {
        this.MAX_HP = MAX_HP;
    }

    public double getCurrenHp() {
        return currenHp;
    }

    public void setCurrenHp(double currenHp) {
        this.currenHp = currenHp;
    }

    public HP(double MAX_HP, double currenHp) {
        this.MAX_HP = MAX_HP;
        this.currenHp = currenHp;
    }
    
    public HP(){
        
    }
    double MAX_HP;
    double currenHp;
}
