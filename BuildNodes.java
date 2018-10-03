/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahul;

/**
*
* @author Rahul
*/

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

public class BuildNodes {
	
	 Point n;
	    Queue<Point> nw;
	    
	    void createNode (int xCoord, int yCoord)
	    {
	        n.x = xCoord;
	        n.y = yCoord;
	    }
	    
	    void addNode (Point node)
	    {
	        nw = new LinkedList<Point>();
	        nw.add(node);
	    }
}
