package org.javacv.detect;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;

/**
 * Base implementation of {@link Detectable}.
 */
public abstract class AbstractDetector implements Detectable {

    private final OpenCVFrameConverter.ToMat converter;

    public AbstractDetector() {
        this.converter = new OpenCVFrameConverter.ToMat();
    }

    @Override
    public long markObjects(Frame img) {
        return markObjects(converter.convert(img));
    }
}
