package com.sam.webtasks.patternCopy;

import java.util.Date;

import com.google.gwt.user.client.ui.RootPanel;
import com.sam.webtasks.perceptualTask.PerceptDisplay;
import com.sam.webtasks.perceptualTask.PerceptTrial;

public class PatternBlock {
	//how many trials?
	public static int nTrials=1;

	//which trial number is this?
	public static int trial;
		
	//which block number is this?
	public static int block=0;
	
	//how many correct responses so far?
	public static int nCorrect;
	
	//was the last response correct or incorrect?
	public static Boolean lastRespCorrect;
	
	//timestamp of beginning of block
	public static Date blockStart;
	
	//in practice mode, you can see both the template and copy together
	public static Boolean practiceMode = false;
	
	//increment the progress bar after each trial?
	public static Boolean incrementProgress = false;
	
	/*-----------initialise / reset all block settings-----------*/
	public static void Init() {
		if (!PatternDisplay.isInitialised) {
			PatternDisplay.Init();
		}
		
		trial=0;
		nCorrect=0;
		practiceMode = false;
	}
	
	/*-----------run a block-----------*/
	public static void Run() {
		RootPanel.get().add(PatternDisplay.wrapper);
		
		//set timestamp for beginning of block
		blockStart = new Date();
		
		PatternTrial.Run();
	}
}
