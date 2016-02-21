/*=========================================================================
 *
 *  Copyright (c)   Karl T. Diedrich 
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *=========================================================================*/

import ij.*;
import ij.gui.GenericDialog;
import ij.process.*;
import ij.plugin.filter.*;
import ktdiedrich.imagek.*;
// import jRenderer3D.JRenderer3D;
import java.awt.*;

/** Keep smooth parts of an image like a Maximum Intensity Projection Z-Buffer  
* @author Karl Diedrich <ktdiedrich@gmail.com> 
*/
public class Surface_Volume implements PlugInFilter
{
    ImagePlus _imp;

    public int setup(String arg, ImagePlus imp) {
        this._imp = imp;
        return DOES_ALL;
    }
    
    public void run(ImageProcessor ip) 
    {      
        /* 
         * 
         JRenderer3D render = new JRenderer3D();
        render.setBackgroundColor(Color.green);
        
        render.setVolume(_imp);
        
        render.setSurfacePlotLut(JRenderer3D.LUT_ORANGE); // select a LUT
        render.setSurfacePlotLight(0.5); // set lighting
        render.setSurfacePlotMode(JRenderer3D.SURFACEPLOT_MESH); 
        
        render.doRendering();
        Image image = render.getImage();
        ImagePlus volume = new ImagePlus("Volume Image", image);
        volume.show();
        volume.updateAndDraw();
        */
    }
}
