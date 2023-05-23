package com.sam.webtasks.patternCopy;

import java.util.ArrayList;
import java.util.List;

import com.ait.lienzo.client.core.event.NodeDragEndEvent;
import com.ait.lienzo.client.core.event.NodeDragEndHandler;
import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Line;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.shared.core.types.Color;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.TextAlign;
import com.ait.lienzo.shared.core.types.TextBaseLine;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class PatternDisplay {
	//we can use this to check whether the display has been initialised, if not it needs to be done
	public static boolean isInitialised = false;
		
	/*------------display parameters------------*/	
	public static int gridSize = 5; 	  //how many squares?
	public static int gridPixels = 60;    //size of each square in pixels
	public static int gridLineWidth = 5;
	public static int nFilledSquares = 10; //how many filled squares?
	public static int copyRows = 2;        //how many rows in the initial display of the copy squares?
	public static int copyCols = 5;        //how many columjns in the initial display of the copy squares?
	public static final int panelSize = (3 * gridPixels * gridSize) + (2 * gridLineWidth);
	
	/*------------initialise Lienzo objects------------*/
	public static final LienzoPanel panel = new LienzoPanel(panelSize, panelSize);
	
	public static final Layer gridLayer = new Layer();
	public static final Layer templateLayer = new Layer();
	public static final Layer copyLayer = new Layer();

	//we put the lienzo panel into the lienzoWrapper, then we put the lienzoWrapper into another wrapper to center it
	public static final HorizontalPanel lienzoWrapper = new HorizontalPanel();
	public static final HorizontalPanel wrapper = new HorizontalPanel();

	public static Line[] leftHorizontalLines = new Line[gridSize+1];
	public static Line[] leftVerticalLines = new Line[gridSize+1];
	public static Line[] rightHorizontalLines = new Line[gridSize+1];
	public static Line[] rightVerticalLines = new Line[gridSize+1];
	
	//we have one rectangle for each possible square in the template grid
	public static final Rectangle[] templateRectangles = new Rectangle[gridSize * gridSize];
	
	//but for the copy squares, we only need one for each filled square, which can be dragged into position
    public static final Rectangle[] copyRectangles = new Rectangle[nFilledSquares];
    
    public static Color[] palette = new Color[nFilledSquares];  
    
    //list of all possible squares, which we use to generate the pattern, initialise copy square, and check response
    public static List<Integer> allSquares = new ArrayList<Integer>();
    public static List<Integer> allCopySquares = new ArrayList<Integer>();
    
    //use this variable to check whether all copy squares have been placed
    public static Boolean[] copySquarePlaced = new Boolean[nFilledSquares];
    public static Boolean allSquaresPlaced = false;
    
    public static final Text finishText = new Text("Finished", "Verdana, sans-serif", null, 20);

    /*------------set up Lienzo objects------------*/
	public static void Init() {
		isInitialised = true;
		
		wrapper.setWidth(Window.getClientWidth() + "px");
		wrapper.setHeight(Window.getClientHeight() + "px");
		wrapper.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		wrapper.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		lienzoWrapper.add(panel);
        wrapper.add(lienzoWrapper);
		
		//gridlines
        final int rightOffset = 2*gridPixels*gridSize;
        
        for (int i = 0; i <= gridSize; i++) {
            leftHorizontalLines[i] = new Line(gridLineWidth/2,i*gridPixels+gridLineWidth,gridPixels*gridSize+(1.5*gridLineWidth),i*gridPixels+gridLineWidth).setStrokeColor(ColorName.BLACK).setStrokeWidth(gridLineWidth);
            leftVerticalLines[i] = new Line(i*gridPixels+gridLineWidth,gridLineWidth,i*gridPixels+gridLineWidth,gridPixels*gridSize+gridLineWidth).setStrokeColor(ColorName.BLACK).setStrokeWidth(gridLineWidth);

            rightHorizontalLines[i] = new Line(gridLineWidth/2+rightOffset,i*gridPixels+gridLineWidth,gridPixels*gridSize+(1.5*gridLineWidth)+rightOffset,i*gridPixels+gridLineWidth).setStrokeColor(ColorName.BLACK).setStrokeWidth(gridLineWidth);
            rightVerticalLines[i] = new Line(i*gridPixels+gridLineWidth+rightOffset,gridLineWidth,i*gridPixels+gridLineWidth+rightOffset,gridPixels*gridSize+gridLineWidth).setStrokeColor(ColorName.BLACK).setStrokeWidth(gridLineWidth);
            
            gridLayer.add(leftHorizontalLines[i]);
            gridLayer.add(leftVerticalLines[i]);
            gridLayer.add(rightHorizontalLines[i]);
            gridLayer.add(rightVerticalLines[i]);
        }
        
        //colours
        palette[0] = new Color(0,0,255); 
        palette[1] = new Color(255,127,0); 
        palette[2] = new Color(255,0,0); 
        palette[3] = new Color(0,255,255); 
        palette[4] = new Color(0,238,0);
        palette[5] = new Color(0,100,0);
        palette[6] = new Color(255,255,0);
        palette[7] = new Color(255,20,147);
        palette[8] = new Color(255,228,196);
        palette[9] = new Color(139,71,38);
        palette[10]= new Color(160,32,240);
        palette[11]= new Color(105,105,105);

        /*----------set up stimulus squares------------*/
        
        //one template square for each grid position
        int rectangleIndex = 0;

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                int xPixel = x * gridPixels + 2*gridLineWidth;
                int yPixel = y * gridPixels + 2*gridLineWidth;

                templateRectangles[rectangleIndex] = new Rectangle(gridPixels - (2*gridLineWidth), gridPixels - (2*gridLineWidth));

                templateRectangles[rectangleIndex].setX(xPixel).setY(yPixel).setStrokeColor(ColorName.WHITE);
                templateLayer.add(templateRectangles[rectangleIndex++]);
            }
        }
        
        //add the 'finished' button
        final Group finishGroup = new Group();
      
        finishText.setFillColor(ColorName.BLACK).setAlpha(0.5).setTextAlign(TextAlign.CENTER).setTextBaseLine(TextBaseLine.MIDDLE);
        
        int rectX=130,rectY=50;
        
        final Rectangle finishRectangle = new Rectangle(rectX,rectY);
        finishRectangle.setFillColor(ColorName.GREENYELLOW).setFillAlpha(0.3).setCornerRadius(10);
        finishGroup.add(finishText);
        finishGroup.add(finishRectangle);
        finishText.setX(rectX/2).setY(rectY/2);
        
        gridLayer.add(finishGroup);
        finishGroup.setX(1.5*gridPixels*gridSize-rectX/2);
        finishGroup.setY(0.666*gridPixels*gridSize);
        
        
        finishRectangle.addNodeMouseClickHandler(new NodeMouseClickHandler() {
        	public void onNodeMouseClick (NodeMouseClickEvent event) {
        		if (allSquaresPlaced) {
        			//check if correct
        			Boolean allCorrect=true;
        			
        			for (int i=0; i<nFilledSquares; i++) {
        				//Window.alert("allsquare: " + allSquares.get(i) + ", copysquare: " + allCopySquares.get(i));
        				if (allCopySquares.get(i) != allSquares.get(i)) {
        					allCorrect=false;	
              			} 
        			}
        			
        			if (allCorrect) {
        				Window.alert("Correct");
        			} else {
        				Window.alert("Incorrect");
        			}
        		}
        	}
        });
        
        //add the display switch button

        //now set up the copy rectangles. their colours and locations are set up in the PatternTrial code
        for (int i = 0; i < nFilledSquares; i++) {
        	final int finali = i; //use this to reference which square was clicked
        	
        	copyRectangles[i] = new Rectangle(gridPixels - (2*gridLineWidth), gridPixels - (2*gridLineWidth));
        	copyRectangles[i].setDraggable(true).setFillColor(palette[i]);
        	copyLayer.add(copyRectangles[i]);
        	
        	//make the copy rectangles snap to the grid
        	copyRectangles[i].addNodeDragEndHandler((NodeDragEndHandler) new NodeDragEndHandler() {
				public void onNodeDragEnd(NodeDragEndEvent event) {
					int clickedX=(int) copyRectangles[finali].getX();
					int clickedY=(int) copyRectangles[finali].getY();
					
					int rectangle=0;
					int closestDistance = gridPixels*gridPixels*gridSize*gridSize;
					
					//find out which grid position is closer to this x and y co-ordinate
					for (int x = 0; x < gridSize; x++) {
			            for (int y = 0; y < gridSize; y++) {
			                int xPixel = x * gridPixels + 2*gridLineWidth + rightOffset;
			                int yPixel = y * gridPixels + 2*gridLineWidth;
			                
			                int distance = (clickedX-xPixel)*(clickedX-xPixel) + (clickedY-yPixel)*(clickedY-yPixel);
			                
			                if (distance<closestDistance) {
			                	allCopySquares.set(finali, rectangle);
			                	closestDistance=distance;
			                	
			                	copyRectangles[finali].setX(xPixel).setY(yPixel);
			                	copySquarePlaced[finali]=true;

			                	copyLayer.draw();
			                	
			                	//check to see whether all squares have been placed
			                	allSquaresPlaced=true;
			                	
			                	for (int i=0; i<nFilledSquares; i++) {
			                		if (!copySquarePlaced[i]) {
			                			allSquaresPlaced=false;
			                		}
			                	}
			                	
			                	if(allSquaresPlaced) {
			                		finishText.setAlpha(1);
			                		gridLayer.draw();
			                	}
			                }
			                
			                rectangle++;
			            }
					}
        		}
        	});
        }

        //set up a list of all the possible squares. we randomize this to select the template pattern
        for (int i=0; i<(gridSize*gridSize); i++) {
        	allSquares.add(i);
        }
        
        //also a list of all the copy squares. we use this to initialise them into random positions at beginning of trial, and verify response
        for (int i=0; i<nFilledSquares; i++) {
        	allCopySquares.add(i);
        }
        
        
	}
}
