package com.sam.webtasks.patternCopy;

import java.util.ArrayList;
import java.util.Date;
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
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sam.webtasks.basictools.PHP;
import com.sam.webtasks.client.SequenceHandler;

public class PatternDisplay {
	//we can use this to check whether the display has been initialised, if not it needs to be done
	public static boolean isInitialised = false;
	
	//which panel is visible?
	public static int TEMPLATE=0;
	public static int COPY=1;
	public static int visiblePanel=TEMPLATE;
		
	/*------------display parameters------------*/	
	public static int gridSize = 5; 	  //how many squares?
	public static int gridPixels = 60;    //size of each square in pixels
	public static int gridLineWidth = 5;
	public static int copyRows = 2;        //how many rows in the initial display of the copy squares?
	public static int copyCols = 5;        //how many columns in the initial display of the copy squares?
	public static int nFilledSquares = copyRows * copyCols; //how many filled squares in total?
	public static final int panelSize_h = (3 * gridPixels * gridSize) + (2 * gridLineWidth);
	public static final int panelSize_v = 2 * ((gridPixels * gridSize) + (2 * gridLineWidth));
	
	/*------------initialise Lienzo objects------------*/
	public static final LienzoPanel panel = new LienzoPanel(panelSize_h, panelSize_v);
	
	public static final Layer gridLayer = new Layer();
	public static final Layer templateLayer = new Layer();
	public static final Layer copyLayer = new Layer();

	//we put the lienzo panel into the lienzoWrapper, then we put the lienzoWrapper into another wrapper to center it
	public static final VerticalPanel lienzoWrapper = new VerticalPanel();
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
    
    //objects that need to be accessed outside the class
    public static final Text finishText = new Text("Finished", "Verdana, sans-serif", null, 20);
    public static final Group sideSwitchGroup = new Group();

    /*------------set up Lienzo objects------------*/
	public static void Init() {
		isInitialised = true;

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
        
      
        
        //add the side switch button
        int rectX=220,rectY=50;

        final Rectangle sideSwitchRectangle = new Rectangle(rectX,rectY);
        sideSwitchRectangle.setFillColor(ColorName.CORNFLOWERBLUE).setFillAlpha(0.3).setCornerRadius(10);
        final Text sideSwitchText = new Text("Show Copy", "Verdana, sans-serif", null, 20);
        sideSwitchText.setFillColor(ColorName.BLACK).setTextAlign(TextAlign.CENTER).setTextBaseLine(TextBaseLine.MIDDLE);
        
        sideSwitchGroup.add(sideSwitchText);
        sideSwitchGroup.add(sideSwitchRectangle);
        sideSwitchText.setX(rectX/2).setY(rectY/2);
        
        gridLayer.add(sideSwitchGroup);
        
        sideSwitchGroup.setX(1.5*gridPixels*gridSize-rectX/2);
        sideSwitchGroup.setY(0.333*gridPixels*gridSize);
        
        sideSwitchRectangle.addNodeMouseClickHandler(new NodeMouseClickHandler() {
        	public void onNodeMouseClick (NodeMouseClickEvent event) {
        		PatternTrial.switches++;
        		
        		if (visiblePanel==TEMPLATE) {
        			copyLayer.setVisible(true);
        			templateLayer.setVisible(false);
        			sideSwitchText.setText("Show Template");
        			copyLayer.draw();
        			gridLayer.draw();
        			visiblePanel=COPY;
        		} else {
        			templateLayer.setVisible(true);
        			copyLayer.setVisible(false);
        			sideSwitchText.setText("Show Copy");
        			templateLayer.draw();
        			gridLayer.draw();
        			visiblePanel=TEMPLATE;
        		}
        	}
        });  
        
        //add the 'finished' button
        final Group finishGroup = new Group();
      
        finishText.setFillColor(ColorName.BLACK).setAlpha(0.5).setTextAlign(TextAlign.CENTER).setTextBaseLine(TextBaseLine.MIDDLE);
        
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
        		//if (allSquaresPlaced) {
        		if (true) {
        			//check if correct
        			Boolean allCorrect=true;
        			
        			for (int i=0; i<nFilledSquares; i++) {
        				if (allCopySquares.get(i) != allSquares.get(i)) {
        					allCorrect=false;	
              			} 
        			}
        			
        			if (allCorrect) {
        				PatternBlock.nCorrect++;
        				PatternBlock.lastRespCorrect=true;
        			}  else {
        				PatternBlock.lastRespCorrect=false;
        			}
        					
        			gridLayer.setVisible(false);
        			templateLayer.setVisible(false);
        			copyLayer.setVisible(false);
        					
        			//reset the button text
        			sideSwitchText.setText("Show Copy");
        				
        			//reset the initial copy squares
        			for (int i=0; i<nFilledSquares; i++) {
        				allCopySquares.set(i, i);
        			}
        				
        			new Timer() {
        				public void run() {
        					if(++PatternBlock.trial==PatternBlock.nTrials) {
        						RootPanel.get().remove(PatternDisplay.wrapper);
        					}
        					
        					int corr;
        					
        					if (PatternBlock.lastRespCorrect) {
        						corr=1;
        					} else {
        						corr=0;
        					}
        					
        					final Date endTime = new Date();

							int duration = (int) (endTime.getTime() - PatternTrial.trialStart.getTime());
        					
        					String data = "" + PatternBlock.block + ","
        							         + PatternBlock.trial + ","
        							         + corr + "," 
        									 + PatternTrial.switches + "," 
        									 + PatternTrial.blockMoves + "," 
        									 + duration; 
        					
        					PHP.logData("PCtrial", data, true);
        				}
        			}.schedule(500);		 
        		} else {
        			Window.alert("You need to move all of the colour blocks into the grid first.");
        		}
        	}
        });

        //now set up the copy rectangles. their colours and locations are set up in the PatternTrial code
        for (int i = 0; i < nFilledSquares; i++) {
        	final int finali = i; //use this to reference which square was clicked
        	
        	copyRectangles[i] = new Rectangle(gridPixels - (2*gridLineWidth), gridPixels - (2*gridLineWidth));
        	copyRectangles[i].setDraggable(true).setFillColor(palette[i]);
        	copyLayer.add(copyRectangles[i]);
        	
        	//make the copy rectangles snap to the grid
        	copyRectangles[i].addNodeDragEndHandler((NodeDragEndHandler) new NodeDragEndHandler() {
				public void onNodeDragEnd(NodeDragEndEvent event) {
					PatternTrial.blockMoves++;
					
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
        
        //set up the display for the first trial
        PatternDisplay.panel.add(PatternDisplay.gridLayer);
		PatternDisplay.panel.add(PatternDisplay.templateLayer);
		PatternDisplay.panel.add(PatternDisplay.copyLayer);
		
		wrapper.setWidth(Window.getClientWidth() + "px");
		wrapper.setHeight(Window.getClientHeight() + "px");
		wrapper.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		wrapper.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		lienzoWrapper.add(panel);
        wrapper.add(lienzoWrapper);
	}
}
