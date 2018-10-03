package com.rahul;

import java.util.*;

	public class CreateEdges{

	String e;
	ArrayList nw;

	 void identifyEdge(int dir)
	 {
	      switch (dir)
		  {
		  case 1: e = "North";
		       break;
			   
		  case 2:  e = "North-East";
	          	break;
	      case 3:  e = "East";
		        break;
		  case 4:  e = "South-East";
	            break;
	      case 5:  e = "South";
	            break;
	      case 6:  e = "South-West";		
	            break;
	      case 7:  e = "West";			
		        break;
		  case 8:  e = "North-West";
		  }
	 }
	 void addEdge  ()
	 {
	 nw = new ArrayList();
	 nw.add(e);
	 }
	 
	}

