package com.vojat;

import java.io.File;

import com.vojat.Geometry.Geometry;
import com.vojat.Panels.BluePrint;

import java.awt.image.BufferedImage;

public class Export {
    
    public Export() {}

    public static void exportToPNG(File destinationFile, Geometry[] geometry) {
        
        BluePrint bp = Main.bluePrint;
        BufferedImage outputImg;
        for (int i = 0; i < geometry.length; i++) {

            Geometry geom = bp.getGeometryAt(i);
            
        }
    }
}
