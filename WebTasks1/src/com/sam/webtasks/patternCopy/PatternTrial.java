package com.sam.webtasks.patternCopy;

import java.util.Collections;

import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;

public class PatternTrial {
	public static void Run() {	
		PatternDisplay.gridLayer.setVisible(true);
		PatternDisplay.templateLayer.setVisible(true);
		PatternDisplay.copyLayer.setVisible(false || PatternBlock.practiceMode);
		PatternDisplay.sideSwitchGroup.setVisible(!PatternBlock.practiceMode);
		PatternDisplay.visiblePanel=PatternDisplay.TEMPLATE;
		
		//randomise the full set of squares, to pick a pattern
		for (int i = 0; i < PatternDisplay.allSquares.size(); i++) {
			Collections.swap(PatternDisplay.allSquares, i, Random.nextInt(PatternDisplay.allSquares.size()));
			
			//initialise this square to white initially
			PatternDisplay.templateRectangles[i].setFillColor(ColorName.WHITE);
		}

		//now fill the first N squares
		for (int i = 0; i < PatternDisplay.nFilledSquares; i++) {
			PatternDisplay.templateRectangles[PatternDisplay.allSquares.get(i)].setFillColor(PatternDisplay.palette[i]);
		}
		
		//now initialise the locations for the copy squares
		PatternDisplay.allSquaresPlaced = false;
		PatternDisplay.finishText.setAlpha(0.5);

		//fist randomise the list of all copy squares
		for (int i = 0; i < PatternDisplay.nFilledSquares; i++) {
			Collections.swap(PatternDisplay.allCopySquares,  i,  Random.nextInt(PatternDisplay.allCopySquares.size()));
		}
		
		int rectangleIndex = 0;
		
        for (int x = 0; x < PatternDisplay.copyCols; x++) {
            for (int y = 0; y < PatternDisplay.copyRows; y++) {

            	PatternDisplay.copySquarePlaced[rectangleIndex]=false;

                int xPixel = x * PatternDisplay.gridPixels + 1 + (2 * PatternDisplay.gridPixels * PatternDisplay.gridSize) + 2*PatternDisplay.gridLineWidth;
                int yPixel = y * PatternDisplay.gridPixels + 1 + 2*PatternDisplay.gridLineWidth;

                //place the copy rectangles underneath the grid
                yPixel += 1.1 * PatternDisplay.gridPixels * PatternDisplay.gridSize;

                PatternDisplay.copyRectangles[PatternDisplay.allCopySquares.get(rectangleIndex++)].setX(xPixel).setY(yPixel);
            }
        }
		
    	PatternDisplay.panel.draw();
	}

}
