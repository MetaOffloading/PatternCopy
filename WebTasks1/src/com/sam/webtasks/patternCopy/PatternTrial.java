package com.sam.webtasks.patternCopy;

import com.sam.webtasks.perceptualTask.PerceptDisplay;

public class PatternTrial {
	public static void Run() {
		PatternDisplay.panel.add(PatternDisplay.gridLayer);
		PatternDisplay.panel.add(PatternDisplay.templateLayer);
		PatternDisplay.templateRectangles[0].setFillColor(PatternDisplay.palette[0]);
    	PatternDisplay.panel.draw();
	}

}
