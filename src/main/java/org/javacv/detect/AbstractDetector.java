package org.javacv.detect;

import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;

/**
 * Base implementation of {@link Detectable}.
 */
public abstract class AbstractDetector implements Detectable {

    protected final Scalar color;
    private final OpenCVFrameConverter.ToMat converter;

    public AbstractDetector() {
        this.converter = new OpenCVFrameConverter.ToMat();
        this.color = new Scalar(CvScalar.GREEN);
    }

    @Override
    public long markObjects(Frame img) {
        return markObjects(converter.convert(img));
    }
}
