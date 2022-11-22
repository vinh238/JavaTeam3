/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game.compoment;
//bảng điều khiển

import game.obj.Bullet;
import game.obj.Effect;
import game.obj.Player;
import game.obj.Rocket;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.Timer;

public class PanelGame extends JComponent {

    private Graphics2D g2;
    private BufferedImage image;
    private int width;
    private int height;
    private boolean start = true;
    private Key key;
    //thời gian bắn
    private int shortTime;
    private int diem = 0;
    private final int FPS = 60;
    private final int TARGET_TIME = 1000000000 / FPS;
    private Player player;
    private List<Bullet> bullets;
    //tạo lớp lưu danh sach đối tượng tên lửa
    private List<Rocket> rockets;
    private List<Effect> boomEffects;
    Timer timer;

    //phương thức khởi động trò chơi
    public void start() {
        width = getWidth();
        height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
                    long startTime = System.nanoTime();
                    drawBackground();
                    drawGame();
                    render();
                    long time = System.nanoTime() - startTime;
                    if (time < TARGET_TIME) {
                        long sleep = (TARGET_TIME - time) / 1000000;
                        sleep(sleep);
                        //System.out.println(sleep);

                    }
                }
            }
        });
        initObjectGame();
        initKeyboard();
        initBullets();
        thread.start();

    }

    //phương thức addrocket tạo tên lửa ngẫu nhiên 
    private void addRocket() {
        Random ran = new Random();
        int locationY = ran.nextInt(height - 50) + 25;
        Rocket rocket = new Rocket();
        rocket.changeLocation(0, locationY);
        rocket.changeAngle(0);
        rockets.add(rocket);
        int locationY2 = ran.nextInt(height - 50) + 25;
        Rocket rocket2 = new Rocket();
        rocket2.changeLocation(width, locationY2);
        rocket2.changeAngle(180);
        rockets.add(rocket2);
    }

    //khởi tạo đối tượng người chơi
    private void initObjectGame() {
        player = new Player();
        //đặt đối tượng người chơi ở vị trí x = 150 , y =150
        player.changeLocation(150, 150);
        rockets = new ArrayList<>();
        boomEffects = new ArrayList<>();
        //tạo method int tạo luông để gọi chạy phương thức thêm tên lửa
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
                    addRocket();
                    sleep(3000);
                }
            }
        }).start();
    }
    private void resetGame(){
        diem =0;
        rockets.clear();
        bullets.clear();
        player.changeLocation(150, 150);
        player.reset();
    }
    //tao bảng điều khiển , tạo key cho đối tượng và tạo phím
    private void initKeyboard() {
        key = new Key();
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    key.setKey_left(true);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    key.setKey_right(true);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    key.setKey_space(true);
                } else if (e.getKeyCode() == KeyEvent.VK_J) {
                    key.setKey_j(true);
                } else if (e.getKeyCode() == KeyEvent.VK_K) {
                    key.setKey_k(true);
                }
                else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    key.setKey_enter(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    key.setKey_left(false);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    key.setKey_right(false);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    key.setKey_space(false);
                } else if (e.getKeyCode() == KeyEvent.VK_J) {
                    key.setKey_j(false);
                } else if (e.getKeyCode() == KeyEvent.VK_K) {
                    key.setKey_k(false);
                }else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    key.setKey_enter(false);
                }
                
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                float s = 0.5f;
                while (start) {
//                    float angle = (float) player.getAngle();
//                    if (key.isKey_left()) {
//                        angle -= s;
//                    }
//                    if (key.isKey_right()) {
//                        angle += s;
//                    }
//                    if (key.isKey_j() || key.isKey_k()) {
//                        if (shortTime == 0) {
//                            if (key.isKey_j()) {
//                                bullets.add(0, new Bullet(player.getX(), player.getY(), (float) player.getAngle(), 5, 3f));
//                            } else {
//                                bullets.add(0, new Bullet(player.getX(), player.getY(), (float) player.getAngle(), 20, 1f));
//                            }
//                        }
//                        shortTime++;
//                        if (shortTime == 20) {
//                            shortTime = 0;
//                        }
//                    } else {
//                        shortTime = 0;
//                    }
//                    if (key.isKey_space()) {
//                        player.speedUp();
//                    } else {
//                        player.speedDown();
//                    }
//                    player.update();
//                    player.changeAngle(angle);
                    if (player.isAlive()) {
                        float angle = (float) player.getAngle();
                        if (key.isKey_left()) {
                            angle -= s;
                        }
                        if (key.isKey_right()) {
                            angle += s;
                        }
                        if (key.isKey_j() || key.isKey_k()) {
                            if (shortTime == 0) {
                                if (key.isKey_j()) {
                                    bullets.add(0, new Bullet(player.getX(), player.getY(), (float) player.getAngle(), 5, 3f));
                                } else {
                                    bullets.add(0, new Bullet(player.getX(), player.getY(), (float) player.getAngle(), 20, 1f));
                                }
                            }
                            shortTime++;
                            if (shortTime == 20) {
                                shortTime = 0;
                            }
                        } else {
                            shortTime = 0;
                        }
                        if (key.isKey_space()) {
                            player.speedUp();
                        } else {
                            player.speedDown();
                        }
                        player.update();
                        player.changeAngle(angle);
                    }else{
                        if(key.isKey_enter()){
                            resetGame();
                        }
                    }
                    //cập nhật vị trí của đối tượng tên lửa
                    for (int i = 0; i < rockets.size(); i++) {
                        Rocket rocket = rockets.get(i);
                        if (rocket != null) {
                            rocket.update();
                            if (!rocket.check(width, height)) {
                                rockets.remove(rocket);
                            } else {
                                if (player.isAlive()) {
                                    checkPlayer(rocket);
                                }
                            }
                        }
                    }
                    sleep(5);
                }
            }
        }).start();
    }

    //phương thức đạn và khởi tạo danh sách đạn với lớp mảng
    private void initBullets() {
        bullets = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
                    for (int i = 0; i < bullets.size(); i++) {
                        Bullet bullet = bullets.get(i);
                        if (bullet != null) {
                            bullet.update();
                            checkBullets(bullet);
                            if (!bullet.check(width, height)) {
                                bullets.remove(bullet);
                            }
                        } else {
                            bullets.remove(bullet);
                        }
                    }
                    for (int i = 0; i < boomEffects.size(); i++) {
                        Effect boomEffect = boomEffects.get(i);
                        if (boomEffect != null) {
                            boomEffect.update();
                            if (!boomEffect.check()) {
                                boomEffects.remove(boomEffect);
                            }
                        } else {
                            boomEffects.remove(boomEffect);
                        }
                    }
                    sleep(1);
                }
            }
        }).start();
    }

    //phương thức xóa tên lửa khi bị bắn
    public void checkPlayer(Rocket rocket) {
        if (rocket != null) {
            Area area = new Area(player.getShape());
            area.intersect(rocket.getShape());
            if (!area.isEmpty()) {
                double rocketHp = rocket.getHP();
                if (!rocket.updateHP(player.getHP())) {
                    rockets.remove(rocket);
                    double x = rocket.getX() + Rocket.ROCKET_SIZE / 2;
                    double y = rocket.getY() + Rocket.ROCKET_SIZE / 2;
                    boomEffects.add(new Effect(x, y, 5, 5, 75, 0.05f, new Color(32, 178, 169)));
                    boomEffects.add(new Effect(x, y, 5, 5, 75, 0.1f, new Color(32, 178, 169)));
                    boomEffects.add(new Effect(x, y, 10, 10, 100, 0.3f, new Color(230, 207, 105)));
                    boomEffects.add(new Effect(x, y, 10, 5, 100, 0.5f, new Color(255, 70, 70)));
                    boomEffects.add(new Effect(x, y, 10, 5, 150, 0.2f, new Color(255, 255, 255)));
                }
                if (!player.updateHP(rocketHp)) {
                    player.setAlive(false);
                    double x = rocket.getX() + Rocket.ROCKET_SIZE / 2;
                    double y = rocket.getY() + Rocket.ROCKET_SIZE / 2;
                    boomEffects.add(new Effect(x, y, 5, 5, 75, 0.05f, new Color(32, 178, 169)));
                    boomEffects.add(new Effect(x, y, 5, 5, 75, 0.1f, new Color(32, 178, 169)));
                    boomEffects.add(new Effect(x, y, 10, 10, 100, 0.3f, new Color(230, 207, 105)));
                    boomEffects.add(new Effect(x, y, 10, 5, 100, 0.5f, new Color(255, 70, 70)));
                    boomEffects.add(new Effect(x, y, 10, 5, 150, 0.2f, new Color(255, 255, 255)));
                }
            }
        }

    }

    public void checkBullets(Bullet bullet) {
        for (int i = 0; i < rockets.size(); i++) {
            Rocket rocket = rockets.get(i);
            if (rocket != null) {
                Area area = new Area(bullet.getShape());
                area.intersect(rocket.getShape());
                if (!area.isEmpty()) {
                    boomEffects.add(new Effect(bullet.getCenterX(), bullet.getCenterY(), 3, 5, 60, 0.5f, new Color(230, 207, 105)));
                    if (!rocket.updateHP(bullet.getSize())) {
                        diem++;
                        rockets.remove(rocket);
                        double x = rocket.getX() + Rocket.ROCKET_SIZE / 2;
                        double y = rocket.getY() + Rocket.ROCKET_SIZE / 2;
                        boomEffects.add(new Effect(x, y, 5, 5, 75, 0.05f, new Color(32, 178, 169)));
                        boomEffects.add(new Effect(x, y, 5, 5, 75, 0.1f, new Color(32, 178, 169)));
                        boomEffects.add(new Effect(x, y, 10, 10, 100, 0.3f, new Color(230, 207, 105)));
                        boomEffects.add(new Effect(x, y, 10, 5, 100, 0.5f, new Color(255, 70, 70)));
                        boomEffects.add(new Effect(x, y, 10, 5, 150, 0.2f, new Color(255, 255, 255)));
                    }
                    // 
                    bullets.remove(bullet);
                    //diem++;
                }
            }
        }
    }

    //tạo nền
    private void drawBackground() {
        g2.setColor(new Color(30, 30, 30));
        //phuong thức tô màu nền
        g2.fillRect(0, 0, width, height);
    }

    private void drawGame() {
        // 
        if (player.isAlive()) {
            player.draw(g2);
        }
        //dùng vòng lặp for để xuất đồ họa
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (bullet != null) {
                bullet.draw(g2);
            }
        }
        //tạo phương thức hiển thị tên lửa ra màn hình
        for (int i = 0; i < rockets.size(); i++) {
            Rocket rocket = rockets.get(i);
            if (rocket != null) {
                rocket.draw(g2);
            }
        }
        for (int i = 0; i < boomEffects.size(); i++) {
            Effect boomEffect = boomEffects.get(i);
            if (boomEffect != null) {
                boomEffect.draw(g2);
            }
        }
        g2.setColor(Color.red);
        g2.setFont(new Font("Tahoma", Font.BOLD, 40));
//        FontMetrics metrics = getFontMetrics(g2.getFont());
        g2.drawString("Score " + diem, 1, g2.getFont().getSize());
        g2.setColor(Color.WHITE);
        if(!player.isAlive()){
            String text = "GAME OVER";
            String textKey = "Press key enter to Continue...";
            g2.setFont(getFont().deriveFont(Font.BOLD,50f));
            FontMetrics fm = g2.getFontMetrics();
            Rectangle2D r2 = fm.getStringBounds(text, g2);
            double textWidth = r2.getWidth();
            double textHeight = r2.getHeight();
            double x = (width - textWidth)/2;
            double y = (height - textHeight)/2;
            g2.drawString(text,(int)x, (int)y+fm.getAscent());
            g2.setFont(getFont().deriveFont(Font.BOLD,15f));
            fm = g2.getFontMetrics();
            r2 = fm.getStringBounds(textKey, g2);
            textWidth = r2.getWidth();
            textHeight = r2.getHeight();
            x = (width - textWidth)/2;
            y = (height-textHeight)/2;
            g2.drawString(textKey, (int)x, (int)y +fm.getAscent()+50);
        }

    }

    //xuất ra màn hình
    private void render() {
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
    }

    private void sleep(long speed) {
        try {
            Thread.sleep(speed);
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }
}
