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
import ij.process.*;
import ij.plugin.filter.*;
import ktdiedrich.imagek.*;
/** Colors increasing values in an image stack cool to hot colors.   
* @author Karl Diedrich <ktdiedrich@gmail.com> 
*/
public class Cool_Hot_Color implements PlugInFilter 
{
    private ImagePlus _imp;
    
    public int setup(String arg, ImagePlus imp) 
	{
	    this._imp = imp;
	    return DOES_ALL+STACK_REQUIRED;
    }
    
    public void run(ImageProcessor ip) 
    {
    	short[][] voxels = ImageProcess.getShortStackVoxels(_imp.getImageStack());
    	
        CoolHotColor colorDFE = new CoolHotColor(voxels, _imp.getWidth(), _imp.getHeight(), 
                _imp.getShortTitle()+"CoolHot");
        ImagePlus colorIm = colorDFE.getImage();
        colorIm.show();
        colorIm.updateAndDraw();
    }
}


