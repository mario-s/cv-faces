package org.opencv.hdr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
        if(args.length == 1) {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            MertensProcessor processor = new MertensProcessor();
            File out = new File("result.jpg");
            try {
                processor.create(readPaths(args[0]), out);
            } catch (IOException ex) {
                LOG.warn(ex.getMessage(), ex);
            }
        }else{
            LOG.warn("This application requires a text file with the path to the images to merge.");
        }
    }
    
    private static List<String> readPaths(String src) throws IOException {
        List<String> paths = new ArrayList<>();
        InputStream in = new FileInputStream(new File(src));
        new BufferedReader(new InputStreamReader(in)).lines().forEach(l -> paths.add(l));
        return paths;
    }
}
