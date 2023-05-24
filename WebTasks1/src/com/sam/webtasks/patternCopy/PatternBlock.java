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
	public static int block;
	
	//how many correct responses so far?
	public static int nCorrect;
	
	//timestamp of beginning of block
	public static Date blockStart;
	
	//in practice mode, you can see both the template and copy together
	public static Boolean practiceMode = false;
	
	/*-----------initialise / reset all block settings-----------*/
	public static void Init() {
		if (PatternDisplay.isInitialised == false) {
			PatternDisplay.Init();
		} else {
			trial=0;
			practiceMode = false;
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
