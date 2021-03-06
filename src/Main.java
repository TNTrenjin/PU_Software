import ui.*;
import ui.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class Main {
    static JFrame frame;
    static GameCanvas canvas;

    static Thread gameThread;

    public static void main(String[] args) {
        initFrame();    // 初始化畫面
        initThread();   // 初始化多執行緒

        gameThread.start();
    }

    public static void initFrame() {
        frame = new JFrame();

        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new GameCanvas();
        canvas.setFocusable(true);
        canvas.requestFocusInWindow();
        canvas.addKeyListener(canvas);

        loadFonts();    // 載入字型

        frame.add(canvas);
        frame.setVisible(true);
    }

    public static void initThread() {
        gameThread = new Thread(canvas);
    }

    private static void loadFonts() {
        try {
            File fontPath = new File("font/");
            String[] fontList = fontPath.list();

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

            for (String f : fontList)
                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font/" + f)));

            //for(Font f : ge.getAllFonts())
            //    System.out.println(f.getFamily());
        } catch (IOException | FontFormatException e) {
            System.out.println(e.getMessage());
        }
    }
}

class GameCanvas extends JPanel implements Runnable, KeyListener {

    Scene[] sceneView = new Scene[3];

    static int scene;

    GameCanvas() {
        initScene();
    }

    public void initScene() {
        sceneView[0] = new Menu();
        sceneView[1] = new Game();
        sceneView[2] = new LeaderBoard();

        for (Scene g : sceneView) {
            g.initImages();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        sceneView[scene].draw(g);
    }

    @Override
    public void run() {

        int beforeScene = Scene.sceneID;

        try {
            while (true) {
                Thread.sleep(10);

                scene = Scene.sceneID;

                if (scene != beforeScene) {
                    sceneView[scene].reset();
                    System.out.println("Reset Scene:" + scene);
                    beforeScene = scene;
                }
                repaint();
            }
        } catch (Exception e) {
            System.out.println("Thread Error: " + e.getMessage());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        //System.out.println(keyCode);
        sceneView[scene].toggleKey(keyCode);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}