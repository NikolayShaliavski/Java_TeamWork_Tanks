package core;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    public static final int WINDOW_WIDTH = 620;
    public static final int WINDOW_HEIGHT = 460;

    private JFrame frame;
    private Canvas canvas;

    private String gameTitle;

    public GameWindow(String gameTitle) {
        this.gameTitle = gameTitle;
        this.init();
    }

    private void init() {
        initFrame();
        initCanvas();

        this.frame.add(canvas);
        this.frame.pack();
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    private void initFrame() {
        this.frame = new JFrame(this.gameTitle);

        this.frame.setVisible(true);
        this.frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.frame.setFocusable(true);
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initCanvas() {
        this.canvas = new Canvas();

        this.canvas.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.canvas.setMaximumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.canvas.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        this.canvas.setFocusable(false);
    }
}