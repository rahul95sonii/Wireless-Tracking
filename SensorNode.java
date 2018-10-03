package com.rahul;

import java.awt.Color;
import java.awt.Graphics;

//Sensor Class


public class SensorNode {
	

	
	
	    Color color;
	    int x, y;

	    void draw (Graphics g1, int shapeRadius)
	    {
	        g1.setColor(this.color);
	        g1.fillOval(this.x, this.y, shapeRadius, shapeRadius);
	    }    
	}

