package com.sam.webtasks.patternCopy;

import java.util.ArrayList;
import java.util.List;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Line;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.shared.core.types.Color;
import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class PatternDisplay {
	//we can use this to check whether the display has been initialised, if not it needs to be done
	public static boolean isInitialised = false;
		
	/*------------display parameters------------*/	
	public static int gridSize = 5; 	  //how many squares?
	public static int gridPixels = 40;    //size of each square in pixels
	public static int gridLineWidth = 4;
	public static int nFilledSquares = 5; //how many filled squares?
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
	
	public static final Rectangle[] templateRectangles = new Rectangle[gridSize * gridSize];
    public static final Rectangle[] copyRectangles = new Rectangle[gridSize * gridSize];
	
    public static List<Integer> filledSquares = new ArrayList<Integer>();
    
    public static Color[] palette = new Color[nFilledSquares];  

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
        for (int i = 0; i <= gridSize; i++) {
            leftHorizontalLines[i] = new Line(gridLineWidth/2,i*gridPixels+gridLineWidth,gridPixels*gridSize+(1.5*gridLineWidth),i*gridPixels+gridLineWidth).setStrokeColor(ColorName.BLACK).setStrokeWidth(gridLineWidth);
            leftVerticalLines[i] = new Line(i*gridPixels+gridLineWidth,gridLineWidth,i*gridPixels+gridLineWidth,gridPixels*gridSize+gridLineWidth).setStrokeColor(ColorName.BLACK).setStrokeWidth(gridLineWidth);

            int rightOffset = 2*gridPixels*gridSize;
            
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

        /*----------set up stimulus squares------------*/
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

        rectangleIndex = 0;

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                int xPixel = x * gridPixels + 1 + (2 * gridPixels * gridSize);
                int yPixel = y * gridPixels + 1;

                copyRectangles[rectangleIndex] = new Rectangle(gridPixels - (2*gridLineWidth), gridPixels - (2*gridLineWidth));

                copyRectangles[rectangleIndex].setX(xPixel).setY(yPixel);
                copyLayer.add(copyRectangles[rectangleIndex++]);
            }
        }
	}
}
