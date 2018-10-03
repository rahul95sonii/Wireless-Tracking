package com.rahul;


import java.applet.Applet;

/**
 *
 * @author Rahul
 */
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Point;
import java.awt.TextField;
import java.util.Random;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class TrackTarget extends Applet
{
 Button grid, deploySensors, reset;
 TextField gridSize, numofSensors, numofSteps, sRange, currStep, xLoc1, yLoc1, xLoc2, yLoc2;
 Checkbox showObject;
 StringBuilder sb = new StringBuilder();
 boolean resetFlag = false;
 int sizeofGrid = 0, constant = 10, numSensors = 0, maxSize = 0, sensor_range = 0,
         xpos1 = 0, ypos1 = 0, xpos2 = 0, ypos2 = 0, prevxpos1 = 0, prevypos1 = 0, prevxpos2 = 0,
         prevypos2 = 0, num11 = 0, num12 = 0, num13 = 0, randomChoice1 = 0, numSteps = 0,
         num21 = 0, num22 = 0, num23 = 0, randomChoice2 = 0, newxpos1 = 0, newxpos2 = 0,
         newypos1 = 0, newypos2 = 0, m = 0, direction1 = 0, direction2 = 0,
         currentSteps1 = 0, currentSteps2 = 0, move1 = 12, move2 = 24, move3 = 36, move4 = 48;
 double [] parr1, parr2;
 long startTime, endTime, estimatedTime;
 Random r = new Random();
 Vector<Integer> SensorsX = new Vector<>();
 Vector<Integer> SensorsY = new Vector<>();
 Image myjpg;
 SensorNode s = new SensorNode();
 public static final String NL = System.getProperty("line.separator"); 
 @Override
 public void init ()
 {
     add(new Label("Enter size of square grid (in increments of 100): "));
     gridSize = new TextField("", 5);
     add(gridSize);
     gridSize.setEditable(true);
     gridSize.setEnabled(true);

     grid = new Button("Draw square grid");
     add(grid);
     grid.setEnabled(true);

     add(new Label("Enter sensing range (in increments of 50): "));
     sRange = new TextField("", 5);
     add(sRange);
     sRange.setEditable(true);
     sRange.setEnabled(false);

     add(new Label("Enter number of sensors to be deployed: "));
     numofSensors = new TextField("", 5);
     add(numofSensors);
     numofSensors.setEditable(true);
     numofSensors.setEnabled(false);

     deploySensors = new Button("Deploy sensors");
     add(deploySensors);
     deploySensors.setEnabled(false);

     add(new Label("                                                                                                                                                                 "));
     
     add(new Label("Enter number of desired movement steps (maximum 50): "));
     numofSteps = new TextField("", 5);
     add(numofSteps);
     numofSteps.setEditable(true);
     numofSteps.setEnabled(false);

     add(new Label ("Object 1 "));

     add(new Label ("X: "));
     xLoc1 = new TextField("", 5);
     add(xLoc1);
     xLoc1.setEditable(true);
     xLoc1.setEnabled(false);

     add(new Label ("Y: "));
     yLoc1 = new TextField("", 5);
     add(yLoc1);
     yLoc1.setEditable(true);
     yLoc1.setEnabled(false);

     //add(new Label ("Object 2 "));

     add(new Label ("X: "));
     xLoc2 = new TextField("", 5);
     add(xLoc2);
     xLoc2.setEditable(true);
     xLoc2.setEnabled(false);

     add(new Label ("Y: "));
     yLoc2 = new TextField("", 5);
     add(yLoc2);
     yLoc2.setEditable(true);
     yLoc2.setEnabled(false);

     add(new Label ("Current Step: "));
     currStep = new TextField("", 5);
     add(currStep);
     //Rahul Soni Change 1
     currStep.setEditable(false);
     currStep.setEnabled(false);

     reset = new Button("Reset");
     add(reset);
     reset.setEnabled(false);

     showObject = new Checkbox();
     add(showObject);
     showObject.setEnabled(false);
 }

 public static double getRandomProbability (double [] array)
 {
     Random gen = new Random();
     int rnd = gen.nextInt(array.length);
     return array [rnd];
 }

 @Override
 public void paint (Graphics g)
 {
     super.paint(g);

     if(resetFlag){
         s=null;
         resetFlag=false;
         s=null;
         return;
     }
     s=new SensorNode();


     //xLoc2.setBounds(1002, 60, 60, 22);
     //yLoc2.setBounds(1098, 60, 60, 22);
     //g.drawString("Number of Movement Steps:", 620, 70);
     //label.setBounds(785, 150, 15, 15);
     showObject.setBounds(600, 80, 10, 10);
     g.drawString("Introduce Moving Objects", 620, 90);

     g.setColor(Color.BLACK);
     int x = 100, curStep1 = 0, curStep2 = 0, radius1 = constant * move1, radius2 = constant * move2,
             radius3 = constant * move3, radius4 = constant * move4;
     double res1, res2;

     if (!numofSteps.getText().equals("")){
         numSteps = Integer.parseInt(numofSteps.getText());
     }

     maxSize = x * (1 + sizeofGrid);

     //Draw square grid
     while (x <= maxSize)
     {
         g.drawLine (x, 100, x, maxSize);
         g.drawLine (100, x, maxSize, x);
         x += 100;
     }



     if(!resetFlag && numSensors>0){
         for (int i = 0; i < numSensors; i++)
         {
             s.x = SensorsX.elementAt(i);
             s.y = SensorsY.elementAt(i);
             s.color = Color.BLUE;
             s.draw(g, 10);
         }
     }


     g.drawImage(myjpg, 1000, 550, 150, 150, this);

     parr1 = new double [] {0.5, 0.3, 0.2};
     parr2 = new double [] {0.6, 0.3, 0.1};

     if (showObject.getState())
     {
         if (xpos1 == 0 && ypos1 == 0 && xpos2 == 0 && ypos2 == 0)
             initializeObjs();

         //Next Movement Direction for Object 1

         //North
         if (randomChoice1 == 1)
         {
             res1 = getRandomProbability(parr1);

             if (res1 == 0.5)
                 direction1 = 5;

             else if (res1 == 0.3)
                 direction1 = 6;

             else if (res1 == 0.2)
                 direction1 = 4;
         }

         //North-East
         else if (randomChoice1 == 2)
         {
             res1 = getRandomProbability(parr2);

             if (res1 == 0.6)
                 direction1 = 6;

             else if (res1 == 0.3)
                 direction1 = 7;

             else if (res1 == 0.1)
                 direction1 = 8;
         }

         //East
         else if (randomChoice1 == 3)
         {
             res1 = getRandomProbability(parr1);

             if (res1 == 0.5)
                 direction1 = 7;

             else if (res1 == 0.3)
                 direction1 = 6;

             else if (res1 == 0.2)
                 direction1 = 8;
         }

         //South-East
         else if (randomChoice1 == 4)
         {
             res1 = getRandomProbability(parr2);

             if (res1 == 0.6)
                 direction1 = 8;

             else if (res1 == 0.3)
                 direction1 = 7;

             else if (res1 == 0.1)
                 direction1 = 6;
         }

         //South
         else if (randomChoice1 == 5)
         {
             res1 = getRandomProbability(parr1);

             if (res1 == 0.5)
                 direction1 = 1;

             else if (res1 == 0.3)
                 direction1 = 8;

             else if (res1 == 0.2)
                 direction1 = 2;
         }

         //South-West
         else if (randomChoice1 == 6)
         {
             res1 = getRandomProbability(parr2);

             if (res1 == 0.6)
                 direction1 = 2;

             else if (res1 == 0.3)
                 direction1 = 3;

             else if (res1 == 0.1)
                 direction1 = 4;
         }

         //West
         else if (randomChoice1 == 7)
         {
             res1 = getRandomProbability(parr1);

             if (res1 == 0.5)
                 direction1 = 3;

             else if (res1 == 0.3)
                 direction1 = 2;

             else if (res1 == 0.2)
                 direction1 = 4;
         }

         //North-West
         else if (randomChoice1 == 8)
         {
             res1 = getRandomProbability(parr2);

             if (res1 == 0.6)
                 direction1 = 4;

             else if (res1 == 0.3)
                 direction1 = 3;

             else if (res1 == 0.1)
                 direction1 = 2;
         }

         //Default Direction
         else
         {
             res1 = getRandomProbability(parr2);

             if (res1 == 0.6)
                 direction1 = 4;

             else if (res1 == 0.3)
                 direction1 = 3;

             else if (res1 == 0.1)
                 direction1 = 2;
         }

         //Next Movement Direction for Object 2

         //North
         if (randomChoice2 == 1)
         {
             res2 = getRandomProbability(parr1);

             if (res2 == 0.5)
                 direction2 = 5;

             else if (res2 == 0.3)
                 direction2 = 6;

             else if (res2 == 0.2)
                 direction2 = 4;
         }

         //North-East
         else if (randomChoice2 == 2)
         {
             res2 = getRandomProbability(parr2);

             if (res2 == 0.6)
                 direction2 = 6;

             else if (res2 == 0.3)
                 direction2 = 7;

             else if (res2 == 0.1)
                 direction2 = 8;
         }

         //East
         else if (randomChoice2 == 3)
         {
             res2 = getRandomProbability(parr1);

             if (res2 == 0.5)
                 direction2 = 7;

             else if (res2 == 0.3)
                 direction2 = 6;

             else if (res2 == 0.2)
                 direction2 = 8;
         }

         //South-East
         else if (randomChoice2 == 4)
         {
             res2 = getRandomProbability(parr2);

             if (res2 == 0.6)
                 direction2 = 8;

             else if (res2 == 0.3)
                 direction2 = 7;

             else if (res2 == 0.1)
                 direction2 = 6;
         }

         //South
         else if (randomChoice2 == 5)
         {
             res2 = getRandomProbability(parr1);

             if (res2 == 0.5)
                 direction2 = 1;

             else if (res2 == 0.3)
                 direction2 = 8;

             else if (res2 == 0.2)
                 direction2 = 2;
         }

         //South-West
         else if (randomChoice2 == 6)
         {
             res2 = getRandomProbability(parr2);

             if (res2 == 0.6)
                 direction2 = 2;

             else if (res2 == 0.3)
                 direction2 = 3;

             else if (res2 == 0.1)
                 direction2 = 4;
         }

         //West
         else if (randomChoice2 == 7)
         {
             res2 = getRandomProbability(parr1);

             if (res2 == 0.5)
                 direction2 = 3;

             else if (res2 == 0.3)
                 direction2 = 2;

             else if (res2 == 0.2)
                 direction2 = 4;
         }

         //North-West
         else if (randomChoice2 == 8)
         {
             res2 = getRandomProbability(parr2);

             if (res2 == 0.6)
                 direction2 = 4;

             else if (res2 == 0.3)
                 direction2 = 3;

             else if (res2 == 0.1)
                 direction2 = 2;
         }

         //Default Direction
         else
         {
             res2 = getRandomProbability(parr2);

             if (res2 == 0.6)
                 direction2 = 4;

             else if (res2 == 0.3)
                 direction2 = 3;
 if (res2 == 0.1)
                 direction2 = 2;
         }

         //Object 1
         g.setColor(Color.WHITE);

         //Rahul Soni Change 4
//         int[] xpoints1 = {prevxpos1, prevxpos1 + 20, prevxpos1 + 10};
//         int[] ypoints1 = {prevypos1, prevypos1, prevypos1 - 20};
         //g.fillPolygon(xpoints1, ypoints1, 3);
         g.fillRoundRect(prevxpos1, prevypos1, 20, 20, 10, 10);

         g.setColor(Color.GREEN);
         //Rahul Soni Change 5
//         int[] xPoints1 = {xpos1, xpos1 + 20, xpos1 + 10};
//         int[] yPoints1 = {ypos1, ypos1, ypos1 - 20};
//         g.fillPolygon(xPoints1, yPoints1, 3);
         g.fillRoundRect(xpos1, ypos1, 20, 20, 10, 10);

         //Rahul Soni Change 6 for color
         //Show Object 1's track
         g.setColor(Color.RED);

         if (prevxpos1 > 0 && xpos1 > 0 && prevypos1 > 0 && ypos1 > 0)
             g.drawLine(prevxpos1, prevypos1, xpos1, ypos1);

         //Object 2
         g.setColor(Color.WHITE);
         int[] xpoints = {prevxpos2, prevxpos2 + 20, prevxpos2 + 10};
         int[] ypoints = {prevypos2, prevypos2, prevypos2 - 20};
         g.fillPolygon(xpoints, ypoints, 3);

         g.setColor(Color.MAGENTA);
         int[] xPoints = {xpos2, xpos2 + 20, xpos2 + 10};
         int[] yPoints = {ypos2, ypos2, ypos2 - 20};
         g.fillPolygon(xPoints, yPoints, 3);

         //Rahul Soni Change 7 for 2nd object track color
         //Show Object 2's track
         g.setColor(Color.BLACK);

         if (prevxpos2 > 0 && xpos2 > 0 && prevypos2 > 0 && ypos2 > 0)
             g.drawLine(prevxpos2, prevypos2, xpos2, ypos2);

         if (xpos1 > 0)
             xLoc1.setText(Integer.toString(xpos1));

         if (ypos1 > 0)
             yLoc1.setText(Integer.toString(ypos1));

         if (xpos2 > 0)
             xLoc2.setText(Integer.toString(xpos2));

         if (ypos2 > 0)
             yLoc2.setText(Integer.toString(ypos2));



         prevxpos1 = xpos1;
         prevypos1 = ypos1;

         prevxpos2 = xpos2;
         prevypos2 = ypos2;

         if(!resetFlag){
             for (int i = 0; i < numSensors; i++)
             {
                 if (Math.sqrt(((SensorsX.elementAt(i) - xpos1) * (SensorsX.elementAt(i) - xpos1)) +
                         ((SensorsY.elementAt(i) - ypos1) * (SensorsY.elementAt(i) - ypos1)))
                         <= sensor_range)
                 {
                     xLoc1.setForeground(Color.GREEN);
                     yLoc1.setForeground(Color.GREEN);
                     s.x = SensorsX.elementAt(i);
                     s.y = SensorsY.elementAt(i);
                     s.color = Color.GREEN;
                     s.draw (g, 10);
                     //g.setColor(Color.RED);
                     //g.drawString("X", xpos1, ypos1);
                 }

                 else
                 {
                     xLoc1.setForeground(Color.BLACK);
                     yLoc1.setForeground(Color.BLACK);
                 }
             }

             //Check if Object 2 gets detected by the sensors
             for (int j = 0; j < numSensors; j++)
             {
                 if (Math.sqrt(((SensorsX.elementAt(j) - xpos2) * (SensorsX.elementAt(j) - xpos2)) +
                         ((SensorsY.elementAt(j) - ypos2) * (SensorsY.elementAt(j) - ypos2)))
                         <= sensor_range)
                 {
                     xLoc2.setForeground(Color.MAGENTA);
                     yLoc2.setForeground(Color.MAGENTA);
                     s.x = SensorsX.elementAt(j);
                     s.y = SensorsY.elementAt(j);
                     s.color = Color.MAGENTA;
                     s.draw (g, 10);
                     //g.setColor(Color.RED);
                     //g.drawString("X", xpos2, ypos2);
                 }

                 else
                 {
                     xLoc2.setForeground(Color.BLACK);
                     yLoc2.setForeground(Color.BLACK);
                 }
             }
         }
         //Check if Object 1 is detected by the sensors


         startTime = System.currentTimeMillis();
         step();
         m++;
         //Rahul Soni Change 3
         if (m > 0&& m<=numSteps){
             currStep.setText(""+m);
         }


         //Draw contour lines for predicting Object 1's movements
         //for (int k = 0; k < numSensors; k++)
         //{
         /*if (hasEnteredGrid (xpos1, ypos1))
         {
             currentSteps1 = numSteps - m;

             //Contour Line for 1 minute prediction
             if ((currentSteps1 + move1) < numSteps)
             {
                 g.setColor(Color.RED);
                 g.drawOval(xpos1 - radius1, ypos1 - radius1, 2 * radius1, 2 * radius1);
             }

             //Contour Line for 2 minutes prediction
             if ((currentSteps1 + move2) < numSteps)
             {
                 g.setColor(Color.YELLOW);
                 g.drawOval(xpos1 - radius2, ypos1 - radius2, 2 * radius2, 2 * radius2);
             }

             //Contour Line for 3 minutes prediction
             if ((currentSteps1 + move3) < numSteps)
             {
                 g.setColor(Color.ORANGE);
                 g.drawOval(xpos1 - radius3, ypos1 - radius3, 2 * radius3, 2 * radius3);
             }

             //Contour Line for 4 minutes prediction
             if ((currentSteps1 + move4) < numSteps)
             {
                 g.setColor(Color.GRAY);
                 g.drawOval(xpos1 - radius4, ypos1 - radius4, 2 * radius4, 2 * radius4);
             }
             //return;
         }
         //}

         //Draw contour lines for predicting Object 2's movements
         //for (int l = 0; l < numSensors; l++)
         //{
         if (hasEnteredGrid (xpos2, ypos2))
         {
             currentSteps2 = numSteps - m;

             //Contour Line for 1 minute prediction
            *//* if ((currentSteps2 + move1) < numSteps)
             {
                 g.setColor(Color.RED);
                 g.drawOval(xpos2 - radius1, ypos2 - radius1, 2 * radius1, 2 * radius1);
             }

             //Contour Line for 2 minutes prediction
             if ((currentSteps2 + move2) < numSteps)
             {
                 g.setColor(Color.YELLOW);
                 g.drawOval(xpos2 - radius2, ypos2 - radius2, 2 * radius2, 2 * radius2);
             }

             //Contour Line for 3 minutes prediction
             if ((currentSteps2 + move3) < numSteps)
             {
                 g.setColor(Color.ORANGE);
                 g.drawOval(xpos2 - radius3, ypos2 - radius3, 2 * radius3, 2 * radius3);
             }

             //Contour Line for 4 minutes prediction
             if ((currentSteps2 + move4) < numSteps)
             {
                 g.setColor(Color.GRAY);
                 g.drawOval(xpos2 - radius4, ypos2 - radius4, 2 * radius4, 2 * radius4);
             }*//*
             //return;

             if(m==numSteps){
                 g.setColor(Color.RED);
                 g.drawOval(xpos1 - radius4, ypos1 - radius4, 2 * radius4, 2 * radius4);
             }
         }*/


         //}

         if (m > numSteps)
             return;

         else
             repaint();

         resetFlag=false;
         endTime = System.currentTimeMillis();
     }

     estimatedTime += (endTime - startTime) / 1000;
     g.dispose();
 }

 public Boolean hasEnteredGrid (int x, int y)
 {
     Boolean result = false;

     if (x >= 100 && x <= maxSize)
     {
         if (y >= 100 && y <= maxSize)
             result = true;
     }

     return result;
 }

 @Override
 public void update(Graphics g)
 {

     g.clearRect(0, 0, 200, this.getSize().height );  //Clear word area.

     if ( resetFlag )
     { g.clearRect(0, 0, this.getSize().width, this.getSize().height );
         resetFlag = false;
     }
     paint( g );
 }

 public void initializeObjs ()
 {
     int n = maxSize + 1, n1 = maxSize - 101;

     //Initialize Object 1

     num11 = r.nextInt(49) + 51;
     num12 = r.nextInt(49) + n;
     num13 = r.nextInt(Math.abs(n1)) + 101;
     randomChoice1 = r.nextInt(8) + 1;

     if (randomChoice1 == 1)
     {
         xpos1 = num13;
         ypos1 = num11;
     }

     else if (randomChoice1 == 2)
     {
         xpos1 = num12;
         ypos1 = num11;
     }

     else if (randomChoice1 == 3)
     {
         xpos1 = num12;
         ypos1 = num13;
     }

     else if (randomChoice1 == 4)
     {
         xpos1 = num12;
         ypos1 = num12;
     }

     else if (randomChoice1 == 5)
     {
         xpos1 = num13;
         ypos1 = num12;
     }

     else if (randomChoice1 == 6)
     {
         xpos1 = num11;
         ypos1 = num12;
     }

     else if (randomChoice1 == 7)
     {
         xpos1 = num11;
         ypos1 = num13;
     }

     else if (randomChoice1 == 8)
     {
         xpos1 = num11;
         ypos1 = num11;
     }

     else
     {
         xpos1 = 60;
         ypos1 = 60;
     }

     //Initialize Object 2

     num21 = r.nextInt(49) + 51;
     num22 = r.nextInt(49) + n;
     num23 = r.nextInt(Math.abs(n1)) + 101;
     randomChoice2 = r.nextInt(8) + 1;

     if (randomChoice2 == 1)
     {
         xpos2 = num23;
         ypos2 = num21;
     }

     else if (randomChoice2 == 2)
     {
         xpos2 = num22;
         ypos2 = num21;
     }

     else if (randomChoice2 == 3)
     {
         xpos2 = num22;
         ypos2 = num23;
     }

     else if (randomChoice2 == 4)
     {
         xpos2 = num22;
         ypos2 = num22;
     }

     else if (randomChoice2 == 5)
     {
         xpos2 = num23;
         ypos2 = num22;
     }

     else if (randomChoice2 == 6)
     {
         xpos2 = num21;
         ypos2 = num22;
     }

     else if (randomChoice2 == 7)
     {
         xpos2 = num21;
         ypos2 = num23;
     }

     else if (randomChoice2 == 8)
     {
         xpos2 = num21;
         ypos2 = num21;
     }

     else
     {
         xpos2 = 60;
         ypos2 = 60;
     }
 }

 public void step ()
 {
     try
     {
         Thread.sleep(500L);
     }

     catch (Exception e)
     {

     }

     if (direction1 > 0 && direction2 > 0)
     {
         //Move Object 1
         if (direction1 == 1)
         {
             newxpos1 = xpos1;
             newypos1 = ypos1 + constant;
         }

         else if (direction1 == 2)
         {
             newxpos1 = xpos1 + constant;
             newypos1 = ypos1 + constant;
         }

         else if (direction1 == 3)
         {
             newxpos1 = xpos1 + constant;
             newypos1 = ypos1;
         }

         else if (direction1 == 4)
         {
             newxpos1 = xpos1 + constant;
             newypos1 = ypos1 - constant;
         }

         else if (direction1 == 5)
         {
             newxpos1 = xpos1;
             newypos1 = ypos1 - constant;
         }

         else if (direction1 == 6)
         {
             newxpos1 = xpos1 - constant;
             newypos1 = ypos1 -  constant;
         }

         else if (direction1 == 7)
         {
             newxpos1 = xpos1 - constant;
             newypos1 = ypos1;
         }

         else if (direction1 == 8)
         {
             newxpos1 = xpos1 - constant;
             newypos1 = ypos1 + constant;
         }

         //Move Object 2
         if (direction2 == 1)
         {
             newxpos2 = xpos2;
             newypos2 = ypos2 + constant;
         }

         else if (direction2 == 2)
         {
             newxpos2 = xpos2 + constant;
             newypos2 = ypos2 + constant;
         }

         else if (direction2 == 3)
         {
             newxpos2 = xpos2 + constant;
             newypos2 = ypos2;
         }

         else if (direction2 == 4)
         {
             newxpos2 = xpos2 + constant;
             newypos2 = ypos2 - constant;
         }

         else if (direction2 == 5)
         {
             newxpos2 = xpos2;
             newypos2 = ypos2 - constant;
         }

         else if (direction2 == 6)
         {
             newxpos2 = xpos2 - constant;
             newypos2 = ypos2 -  constant;
         }

         else if (direction2 == 7)
         {
             newxpos2 = xpos2 - constant;
             newypos2 = ypos2;
         }

         else if (direction2 == 8)
         {
             newxpos2 = xpos2 - constant;
             newypos2 = ypos2 + constant;
         }
     }

     if (newxpos1 < 0)
         newxpos1 += constant;

     if (newypos1 < 0)
         newypos1 += constant;

     if (newxpos2 < 0)
         newxpos2 += constant;

     if (newypos2 < 0)
         newypos2 += constant;

     xpos1 = newxpos1;
     ypos1 = newypos1;

     xpos2 = newxpos2;
     ypos2 = newypos2;
 }

 @Override
 public boolean action (Event event, Object eventobject)
 {
     Point p = new Point();

     if (event.target == grid)
     {
         if (!gridSize.getText().equals(""))
             sizeofGrid = Integer.parseInt(gridSize.getText()) / 100;

         numofSensors.setEnabled(true);
         deploySensors.setEnabled(true);
         sRange.setEnabled(true);
         reset.setEnabled(true);
     }

     if (event.target == deploySensors)
     {

         if (!sRange.getText().equals(""))
             sensor_range = Integer.parseInt(sRange.getText());

         if (!numofSensors.getText().equals(""))
             numSensors = Integer.parseInt(numofSensors.getText());

         for (int i = 0; i < numSensors; i++)
         {
             p.x = r.nextInt(maxSize - 119) + 111;
             p.y = r.nextInt(maxSize - 119) + 111;
             SensorsX.add(i, p.x);
             SensorsY.add(i, p.y);
         }

         showObject.setEnabled(true);
         numofSteps.setEnabled(true);
         xLoc1.setEnabled(true);
         xLoc2.setEnabled(true);
         yLoc1.setEnabled(true);
         yLoc2.setEnabled(true);
         //Rahul Soni Change 2
         currStep.setEnabled(true);
     }

     /*if (event.target == reset)
     {
         resetFlag=true;
//             sizeofGrid=0;
//             gridSize.setText("");
//
//             numofSensors.setText("");
////             numofSensors=null;
////             numofSensors=new TextField();
//             numofSensors.setEnabled(false);
//
//             deploySensors.setEnabled(false);
//
//             sRange.setText("");
////             sRange=null;
////             sRange=new TextField();
//             sRange.setEditable(false);
//
//             reset.setEnabled(false);
//         deploySensors.setEnabled(false);
//         sensor_range=0;numSensors=0;
//         maxSize=0;


//         SensorsX = null;
//         SensorsX=new Vector<>();
//         SensorsY = null;
//         SensorsY = new Vector<>();
         //sRange=new TextField();

//         gridSize = new TextField();
//         numofSteps = new TextField();
         sRange.setEnabled(false);
         numofSensors.setEnabled(false);

         SensorsX.removeAllElements();
         SensorsY.removeAllElements();
         showObject.setEnabled(false);
         numofSteps.setEnabled(false);
         xLoc1.setEnabled(false);
         xLoc2.setEnabled(false);
         yLoc1.setEnabled(false);
         yLoc2.setEnabled(false);
         reset.setEnabled(false);
         gridSize.setText("");
         numofSensors.setText("");

         numofSteps.setText("");
         xLoc1.setText("");
         xLoc2.setText("");
         yLoc1.setText("");
         yLoc2.setText("");

     }*/

     if (event.target == reset)
     {
         xpos1 = 0; ypos1 = 0; xpos2 = 0; ypos2 = 0;
         prevxpos1=0; prevypos1=0; prevxpos2=0; prevypos2=0;
         m=0; maxSize=0;
         numSteps=0;sizeofGrid=0;
         resetFlag=true;
         showObject.setState(false);
         sRange.setEnabled(false);
         numofSensors.setEnabled(false);
         numSensors=0;
         deploySensors.setEnabled(false);
         SensorsX.removeAllElements();
         SensorsY.removeAllElements();
//         showObject.setEnabled(false);
         numofSteps.setEnabled(false);
         xLoc1.setEnabled(false);
         xLoc2.setEnabled(false);
         yLoc1.setEnabled(false);
//         showObject.setEnabled(false);
         yLoc2.setEnabled(false);
         reset.setEnabled(false);
         gridSize.setText("");
         numofSensors.setText("");
         sRange.setText("");
         numofSteps.setText("");
         xLoc1.setText("");
         xLoc2.setText("");
         yLoc1.setText("");
         yLoc2.setText("");
         currStep.setText("");
     }



     this.repaint();

     return true;
 }

 public void resetFunction(){

 }
}