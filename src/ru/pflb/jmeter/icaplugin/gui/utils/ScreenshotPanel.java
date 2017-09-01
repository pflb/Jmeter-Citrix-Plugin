package ru.pflb.jmeter.icaplugin.gui.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class ScreenshotPanel extends JPanel {

    Point mStartPoint;
    boolean mDragging;
    boolean isRectangleSet;
    private Rectangle mRectangle;
    private BufferedImage mImage;
    private ScreenshotAreaSelector mFrame;

    public ScreenshotPanel(ScreenshotAreaSelector screenshotAreaSelector, BufferedImage image) {
        this.mFrame = screenshotAreaSelector;
        this.mImage = image;
        init();
    }

    public void init() {

        setBackground(Color.black);
        setBorder(BorderFactory.createEtchedBorder());
        mRectangle = new Rectangle();

        addListeners();
    }

    private void addListeners() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (isRectangleSet) {
                    setRectangleSize(mStartPoint, mStartPoint);

                    isRectangleSet = false;

                } else {

                    mStartPoint = e.getPoint();

                    mDragging = true;
                    isRectangleSet = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mDragging = false;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (mDragging) {
                    setRectangleSize(mStartPoint, e.getPoint());
                }
            }
        });

    }

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(mImage, 0, 0, null);

        g2.setPaint(Color.red);
        g2.draw(mRectangle);
        g2.setColor(new Color(255, 128, 128, 56));
        g2.fillRect(mRectangle.x, mRectangle.y, mRectangle.width, mRectangle.height);
    }

    public void setRectangleSize(Point start, Point end) {

        if (end.x > mImage.getWidth()) {
            end.x = mImage.getWidth();
        }

        if (end.y > mImage.getHeight()) {
            end.y = mImage.getHeight();
        }

        if (end.x < 0) {
            end.x = 0;
        }

        if (end.y < 0) {
            end.y = 0;
        }

        mRectangle.setFrameFromDiagonal(start, end);

        mFrame.scroll(mRectangle);

        repaint();

    }

    public Dimension getPreferredSize() {

        return new Dimension(mImage.getWidth(), mImage.getHeight());

    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(mImage.getWidth(), mImage.getHeight());
    }

    public Rectangle getRectangle() {
        return mRectangle;
    }
}
