package org.opencv.hdr;

import java.io.File;
import java.util.Arrays;
import org.opencv.core.Core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author spindizzy
 */
public class MertensCli {
    
    private static final Logger LOG = LoggerFactory.getLogger(MertensCli.class);
    
    public static void main(String[] args) {
        if(args.length == 3) {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            MertensProcessor processor = new MertensProcessor();
            File out = new File("result.jpg");
            processor.create(Arrays.asList(args), out);
        }else{
            LOG.warn("This application requires three files to merge.");
        }
    }
}
