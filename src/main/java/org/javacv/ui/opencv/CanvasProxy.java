package org.javacv.ui.opencv;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.javacv.glue.ImageShowable;

/**
 * Proxy for the {@link CanvasFrame}.
 */
class CanvasProxy implements ImageShowable {
    private final CanvasFrame canvas;

    CanvasProxy(CanvasFrame canvas) {
        this.canvas = canvas;
    }

    @Override
    public void setSize(int width, int height) {
        canvas.setCanvasSize(width, height);
    }

    @Override
    public void showImage(Frame image) {
        canvas.showImage(image);
    }
}