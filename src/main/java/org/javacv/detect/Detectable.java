package org.javacv.detect;

import org.bytedeco.javacv.Frame;

/**
 * Interface to detect an object in a image.
 */
public interface Detectable {

    long markObjects(Frame img);
}
