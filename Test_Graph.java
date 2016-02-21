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

import java.util.List;

import ktdiedrich.imagek.Centerline;
import ktdiedrich.imagek.CenterlineGraph;
import ktdiedrich.imagek.Centerlines;
import ktdiedrich.imagek.Graph;
import ktdiedrich.imagek.GraphNode;
import ktdiedrich.imagek.ImageProcess;
import ktdiedrich.imagek.ShortestPaths;

import ktdiedrich.math.MatrixUtil;

import ij.ImagePlus;
import ij.plugin.*;
import ij.text.TextWindow;

/** Test building a graph from a 3D image 
 * @author Karl Diedrich <ktdiedrich@gmail.com>
 * */
public class Test_Graph implements PlugIn
{
    
    public void run(String s) 
    {        
        
        // two masses with a thick connection 
        short[][][] masses2 = {
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,1,1,1,1,1,1,1,1,1,0}, 
                    {0,1,1,1,1,1,1,1,1,1,0},
                    {0,1,1,1,1,1,1,1,1,1,0},
                    {0,0,0,1,1,1,1,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,1,1,1,0,0,0},
                    {0,0,0,0,0,1,1,1,0,0,0},
                    {0,0,0,0,0,1,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,1,1,1,1,1,1,1,1,1,0}, 
                    {0,1,2,2,2,2,2,2,2,1,0},
                    {0,1,1,1,2,2,1,1,1,1,0},
                    {0,0,0,1,1,1,1,0,0,0,0},
                    {0,0,1,0,0,0,0,0,0,0,0},
                    {0,0,0,1,0,1,1,1,0,0,0},
                    {0,0,0,0,1,1,2,1,1,1,0},
                    {0,0,0,0,0,1,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,1,1,1,1,1,1,1,1,1,0}, 
                    {0,1,1,1,1,1,1,1,1,1,0},
                    {0,1,1,1,1,1,1,1,1,1,0},
                    {0,0,0,1,1,1,1,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,1,1,1,0,0,0},
                    {0,0,0,0,0,1,1,1,0,0,0},
                    {0,0,0,0,0,1,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                }
        };
        // tall rectangle 
        short[][][] tall = {
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,1,1,1,1,1,0,0,0}, 
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,1,1,1,1,1,0,0,0}, 
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,1,1,1,1,1,0,0,0}, 
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,2,3,2,1,0,0,0},
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,1,1,1,1,1,0,0,0}, 
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,2,3,2,1,0,0,0},
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,1,1,1,1,1,0,0,0}, 
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,2,3,2,1,0,0,0},
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,1,1,1,1,1,0,0,0}, 
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,2,3,2,1,0,0,0},
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,1,1,1,1,1,0,0,0}, 
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,2,3,2,1,0,0,0},
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,1,1,1,1,1,0,0,0}, 
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,2,3,2,1,0,0,0},
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,1,1,1,1,1,0,0,0}, 
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,2,3,2,1,0,0,0},
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,1,1,1,1,1,0,0,0}, 
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,2,3,2,1,0,0,0},
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,1,1,1,1,1,0,0,0}, 
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,2,3,2,1,0,0,0},
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,1,1,1,1,1,0,0,0}, 
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,2,3,2,1,0,0,0},
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,1,1,1,1,1,0,0,0}, 
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,2,3,2,1,0,0,0},
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,1,1,1,1,1,0,0,0}, 
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,2,3,2,1,0,0,0},
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,1,1,1,1,1,0,0,0}, 
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,2,2,2,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,1,1,1,1,1,0,0,0}, 
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0}
                }
        };
        // thick 
        short[][][] thick = {
                {
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0}, 
                    {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0}, 
                    {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0}, 
                    {0,1,1,1,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0}, 
                    {0,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0,0,0,0}, 
                    {0,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0,0,0}, 
                    {0,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0}, 
                    {0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0}, 
                    {0,1,2,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,0}, 
                    {0,1,2,1,0,0,0,1,1,2,1,1,0,0,0,0,0,0,0,0}, 
                    {0,1,2,1,1,1,0,0,1,1,2,1,1,1,1,1,0,0,1,0}, 
                    {0,1,2,2,2,1,0,0,0,1,1,2,2,2,2,1,1,0,1,0}, 
                    {0,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0}, 
                    {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0}, 
                    {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0}, 
                    {0,1,1,1,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0}, 
                    {0,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0,0,0,0}, 
                    {0,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0,0,0}, 
                    {0,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                },
        };
        // branching 
        short[][][] branching = {
        {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
        },
        {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,1,1,1,1,1,0,0,1,1,1,1,1,0}, 
            {0,0,0,0,0,0,1,1,1,1,0,0,0,1,0,0,0,0,0,0}, 
            {0,1,1,1,1,1,1,1,0,0,0,0,1,0,0,1,1,0,0,0}, 
            {0,1,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,0,0,0}, 
            {0,0,1,0,0,0,1,0,0,0,0,0,0,1,0,0,1,1,0,0}, 
            {0,0,1,0,0,0,0,1,0,0,0,1,1,0,0,1,0,0,0,0}, 
            {0,0,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
        },
        {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
        },
    };
        // multiple graphs
        short[][][] multiple = {
                {
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,1,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0}, 
                    {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,1,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                },
                {
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                } };
        short [][][] x = {
                {
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                },
                {
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,1,0,0,1,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0}, 
                {0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0}, 
                {0,1,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,1,0,0}, 
                {0,0,0,0,0,1,0,1,0,1,0,0,0,0,0,0,0,1,0,0}, 
                {0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0}, 
                {0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0}, 
                {0,0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,0,1,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                },
                {
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                },
                {
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                },
                {
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
                },
        };
        // testDFE(masses2);
        //testDFE(tall);
        //testDFE(thick);
        //testDFE(branching);
        //testDFE(multiple);
        //testDFE(x);
        testCenterline(tall);
    }
    
    /* 
    protected void testDFE(short[][][] cubicDFEs)
    {
        int zSize = cubicDFEs.length;
        int height = cubicDFEs[0].length;
        int width = cubicDFEs[0][0].length;
        short[][] dfes = MatrixUtil.cube2rect(cubicDFEs);
        
        List<Graph> graphs = Graph.makeGraphs(dfes, width, height, 0);
        // List<Graph> graphs = Graph.makeGraphList(dfes, width, height);
        //Graph.displayDFE(graphs, width, height, zSize, "Distance_From_Edge");
        
        Centerlines center = new Centerlines();
        center.setMinLineLength(4);
        center.modifyDFEs(graphs);
        //Graph.displayMDFE(graphs, width, height, zSize, "Modified_Distance_From_Edge");
        
        center.weight(graphs);
        //Graph.displayWeight(graphs, width, height, zSize, "Weight");
        ShortestPaths sp = new ShortestPaths();
        // GraphNode.algorithmResetGraphs(graphs);
        List<Graph> shortestPaths = sp.dijkstraLowestCostPaths(graphs, zSize); // TODO needs VoxelDistance 
        TextWindow twin = new TextWindow("Test_Graph", 
                "Graphs="+shortestPaths.size(), 450, 400);
        
        Graph.pathCostImage(shortestPaths, width, height, zSize, "Path_Costs");
       
        List<CenterlineGraph> centerlines = center.backTraceCenterlines(shortestPaths);
        
        ImagePlus centerlineImage = Centerline.makeColorImage(centerlines, width, height, zSize, "Centerlines");
        centerlineImage.show();
        centerlineImage.updateAndDraw();
        // Graph.displayOne(centerLineGraphs, width, height, zSize, "Centerlines");
    }
    */
    protected void testCenterline(short[][][] cubic)
    {
        int height = cubic[0].length;
        int width = cubic[0][0].length;
        short[][] voxels = MatrixUtil.cube2rect(cubic);
        int count = 0;
        for (int i=0; i<voxels.length; i++)
        {
        	for (int j=0; j < voxels[0].length; j++)
        	{
        		if (voxels[i][j] > 0) count++;
        	}
        }
        System.out.println("Voxels="+count);
        ImagePlus image = ImageProcess.display(voxels, width, height, "cubicImage");
        Centerlines center = new Centerlines();
        center.setXRes(1.0f);
        center.setYRes(1.0f);
        center.setZRes(1.0f);
        center.setShowSteps(true);
        center.setMinLineLength(5);
        center.setDfeThreshold(0.0f);
        // TODO findCenterlines overlays on the original image which doesn't exist in this test case. 
        center.findCenterlines(image);
    }
}