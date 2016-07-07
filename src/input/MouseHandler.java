package input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    public static int mousePressedX = -1;
    public static int mousePressedY = -1;

    public MouseHandler(JFrame frame, Canvas canvas) {
        canvas.addMouseListener(this);
        frame.addMouseListener(this);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        mousePressedX = mouseEvent.getX();
        mousePressedY = mouseEvent.getY();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        mousePressedX = -1;
        mousePressedY = -1;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}