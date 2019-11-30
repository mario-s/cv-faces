package org.javacv.detect.face.haar;

import org.bytedeco.javacv.Frame;

/**
 * Interface to detect an object in a image.
 */
public interface Detectable {

    long markFaces(Frame img);
}
