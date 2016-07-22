package entities;

import java.awt.*;

public class Message extends AbstractEntity {

    private String message;
    private Font font;

    public Message(int x,
                   int y,
                   Font font,
                   String message,
                   Graphics graphics) {
        // Setting default values on width and height,
        // because we haven't calculated them.
        super(x, y, -1, -1);
        this.message = message;
        this.calcWidthAndHeight(graphics, font);
        this.font = font;
    }

    @Override
    public boolean intersect(Rectangle rectangle) {
        return this.getBoundingBox().intersects(rectangle);
    }

    @Override
    public void print(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.setFont(this.font);
        graphics.drawString(this.message, this.x, this.y);

        int fontHeight = graphics.getFontMetrics().getHeight();
        this.setBoundingBox(this.x, this.y - fontHeight + 5, this.width, this.height);

        //// Message bounding box
//        graphics.drawRect(
//                (int) this.getBoundingBox().getX(),
//                (int) this.getBoundingBox().getY(),
//                (int) this.getBoundingBox().getWidth(),
//                (int) this.getBoundingBox().getHeight());
    }

    public String getMessage() {
        return this.message;
    }

    private void calcWidthAndHeight(Graphics graphics, Font font) {
        graphics.setFont(font);
        this.height = graphics.getFontMetrics().getHeight();
        this.width = graphics.getFontMetrics().stringWidth(this.message);
        this.setBoundingBox(this.x, this.y, this.width, this.height);
    }
}