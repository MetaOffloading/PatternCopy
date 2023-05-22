package com.sam.webtasks.patternCopy;

import java.util.Date;

import com.google.gwt.user.client.ui.RootPanel;
import com.sam.webtasks.perceptualTask.PerceptDisplay;
import com.sam.webtasks.perceptualTask.PerceptTrial;

public class PatternBlock {
	//how many trials?
	public static int nTrials;

	//which trial number is this?
	public static int trial;
		
	//which block number is this?
	public static int block;
	
	//how many correct responses so far?
	public static int nCorrect;
	
	//timestamp of beginning of block
	public static Date blockStart;
	
	/*-----------initialise / reset all block settings-----------*/
	public static void Init() {
		if (PatternDisplay.isInitialised == false) {
			PatternDisplay.Init();
		}
	}
	
	/*-----------run a block-----------*/
	public static void Run() {
		RootPanel.get().add(PatternDisplay.wrapper);
		
		//set timestamp for beginning of block
		blockStart = new Date();
		
		PatternTrial.Run();
	}
}
