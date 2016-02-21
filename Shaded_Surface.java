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
/** Rotates the image and takes six faces shaded surface of short or color image stacks.   
* @author Karl Diedrich <ktdiedrich@gmail.com> 
*/
public class Shaded_Surface implements PlugInFilter 
{
    private ImagePlus _imp;
    
    public int setup(String arg, ImagePlus imp) 
	{
	    this._imp = imp;
	    return DOES_ALL+STACK_REQUIRED;
    }
    
    public void run(ImageProcessor ip) 
    {
	    ShadedSurface surf = new ShadedSurface();
	    
	    
	    GenericDialog gd = new GenericDialog("Shaded_Surface");
	    gd.addNumericField("X axis degrees", 0, 1);
        gd.addNumericField("Y axis degrees", 0, 1);
        gd.addNumericField("Z axis degrees", 0, 1);
        
	    gd.showDialog();
        if (gd.wasCanceled()) 
        {
            IJ.error("PlugIn canceled!");
            return;
        }
        double thetaX =  gd.getNextNumber();
        double thetaY =  gd.getNextNumber();
        double thetaZ =  gd.getNextNumber();
        
    	Free3DRotation rotate3D = new Free3DRotation(_imp);
    	if (thetaX != 0)
    	{
    		rotate3D.rotate(thetaX, Free3DRotation.X_AXIS);
    	}
    	if (thetaY != 0)
    	{
    		rotate3D.rotate(thetaY, Free3DRotation.Y_AXIS);
    	}
    	if (thetaZ != 0)
    	{
    		rotate3D.rotate(thetaZ, Free3DRotation.Z_AXIS);
    	}
    	ImagePlus rotated = rotate3D.getRotatedImage();
	    
	    surf.setShowSteps(false);
	    ImagePlus shadedSurface = surf.shade6(rotated);
        
        shadedSurface.show();
        shadedSurface.updateAndDraw();
    }
}
