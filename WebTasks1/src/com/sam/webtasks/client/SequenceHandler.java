//The SequenceHandler is the piece of code that defines the sequence of events
//that constitute the experiment.
//
//SequenceHandler.Next() will run the next step in the sequence.
//
//We can also switch between the main sequence of events and a subsequence
//using the SequenceHandler.SetLoop command. This takes two inputs:
//The first sets which loop we are in. 0 is the main loop. 1 is the first
//subloop. 2 is the second subloop, and so on.
//
//The second input is a Boolean. If this is set to true we initialise the 
//position so that the sequence will start from the beginning. If it is
//set to false, we will continue from whichever position we were currently in.
//
//So SequenceHandler.SetLoop(1,true) will switch to the first subloop,
//starting from the beginning.
//
//SequenceHandler.SetLoop(0,false) will switch to the main loop,
//continuing from where we left off.

//TODO:
//scroll
//data output
//resume where you left off

package com.sam.webtasks.client;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.sam.webtasks.basictools.CheckIdExists;
import com.sam.webtasks.basictools.CheckScreenSize;
import com.sam.webtasks.basictools.ClickPage;
import com.sam.webtasks.basictools.Consent;
import com.sam.webtasks.basictools.Counterbalance;
import com.sam.webtasks.basictools.InfoSheet;
import com.sam.webtasks.basictools.Initialise;
import com.sam.webtasks.basictools.Names;
import com.sam.webtasks.basictools.PHP;
import com.sam.webtasks.basictools.ProgressBar;
import com.sam.webtasks.basictools.Slider;
import com.sam.webtasks.basictools.TimeStamp;
import com.sam.webtasks.iotask1.IOtask1Block;
import com.sam.webtasks.iotask1.IOtask1BlockContext;
import com.sam.webtasks.iotask1.IOtask1InitialiseTrial;
import com.sam.webtasks.iotask1.IOtask1RunTrial;
import com.sam.webtasks.iotask2.IOtask2Block;
import com.sam.webtasks.iotask2.IOtask2BlockContext;
import com.sam.webtasks.iotask2.IOtask2RunTrial;
import com.sam.webtasks.patternCopy.PatternBlock;
import com.sam.webtasks.patternCopy.PatternDisplay;
import com.sam.webtasks.patternCopy.PatternTrial;
import com.sam.webtasks.perceptualTask.PerceptBlock;
import com.sam.webtasks.timeBasedOffloading.TimeBlock;
import com.sam.webtasks.iotask2.IOtask2InitialiseTrial;
import com.sam.webtasks.iotask2.IOtask2PreTrial;

public class SequenceHandler {
	public static void Next() {	
		// move forward one step in whichever loop we are now in
		sequencePosition.set(whichLoop, sequencePosition.get(whichLoop) + 1);

		switch (whichLoop) {
		case 0: // MAIN LOOP
			switch (sequencePosition.get(0)) {
			/***********************************************************************
			 * The code here defines the main sequence of events in the experiment *
			 **********************************************************************/
			case 1:
				ClickPage.Run("Thank you for taking part. This experiment is divided into two halves.<br><br>"
						+ "Click below to start the first part of the experiment.", "Next");
				break;
			case 2:
				if (Counterbalance.getFactorLevel("FirstTask") == ExtraNames.INTENTION_OFFLOADING) {
					SequenceHandler.SetLoop(4, true); // switch to intention offloading loop
					SequenceHandler.Next();
				} else {
					SequenceHandler.SetLoop(5, true); // switch to pattern copy loop
					SequenceHandler.Next();
				}
				break;
			case 3:
				ClickPage.Run("Thank you, you have now finished the first half of the experiment.<br><br>"
						+ "Click below to continue.", "Next");
				break;
			case 4:
				if (Counterbalance.getFactorLevel("FirstTask") == ExtraNames.PATTERN_COPY) {
					SequenceHandler.SetLoop(4, true); // switch to intention offloading loop
					SequenceHandler.Next();
				} else {
					SequenceHandler.SetLoop(5, true); // switch to pattern copy loop
					SequenceHandler.Next();
				}
				break;
			case 5:
				ProgressBar.Hide();
				// log data and check that it saves
				String data = TimeStamp.Now() + ",";
				data = data + SessionInfo.participantID + ",";
				data = data + Counterbalance.getFactorLevel("FirstTask") + ",";
				data = data + SessionInfo.gender + ",";
				data = data + SessionInfo.age;

				PHP.UpdateStatus("finished");
				PHP.logData("finish", data, true);
				break;			
			case 6:
				ClickPage.Run("You have now completed the experiment, thank you.", "nobutton");
				break;
			}
			break;

		//here we specify the intention offloading subloop
		case 4:
			switch (sequencePosition.get(4)) {
			case 1:
				ClickPage.Run(Instructions.Get(0), "Next");
				break;
			case 2:
				//practice 1: 6 circles, no targets
				IOtask1Block block1 = new IOtask1Block();
				block1.blockNum = 1;
				block1.nCircles = 6;
				block1.nTrials = 1;
				block1.nTargets = 0;
				block1.askArithmetic = false;
				block1.offloadCondition = Names.REMINDERS_NOTALLOWED;		
				block1.Run();
				break;
			case 3:
				ClickPage.Run(Instructions.Get(1), "Next");
				break;
			case 4:
				//practice 2: 10 circles, no targets
				IOtask1Block block2 = new IOtask1Block();
				block2.blockNum = 2;
				block2.nTrials = 1;
				block2.nTargets = 0;
				block2.askArithmetic = false;
				block2.offloadCondition = Names.REMINDERS_NOTALLOWED;		
				block2.Run();
				break;
			case 5:
				ClickPage.Run(Instructions.Get(2), "Next");
				break;
			case 6:
				//practice 3: 10 circles, 1 target
				IOtask1Block block3 = new IOtask1Block();
				block3.blockNum = 3;
				block3.nTrials = 1;
				block3.nTargets = 1;
				block3.askArithmetic = false;
				block3.offloadCondition = Names.REMINDERS_NOTALLOWED;		
				block3.Run();				
				break;
			case 7:
				ClickPage.Run(Instructions.Get(3), "Next");
				break;
			case 8:
				//practice 4: 10 circles, 3 targets
				IOtask1Block block4 = new IOtask1Block();
				block4.blockNum = 4;
				block4.nTrials = 1;
				block4.nTargets = 3;
				block4.askArithmetic = false;
				block4.offloadCondition = Names.REMINDERS_NOTALLOWED;		
				block4.Run();
				break;		
			case 9:
				// Metacognitive Judgement
				Slider.Run("Now that you have had some practice, we would like you to tell us "
						+ "how accurately you can perform the task, when it is the same as the version you "
						+ "just practiced with three special circles to remember. Please use the scale below to indicate what percentage "
						+ "of the special circles you can correctly remember to drag to the instructed side of the square, on average. "
						+ "100% would mean that you always get every single one correct. 0% would mean that you can "
						+ "never get any of them correct. ", "Never", "Always");
				break;
			case 10:
				//save the selected slider value to the database
				PHP.logData("IOprediction",  "" + Slider.getSliderValue(), true);
				break;
			case 11:
				ClickPage.Run(Instructions.Get(5), "Next");
				break;
			case 12:
				//practice 6: 10 circles, 3 targets, offloading allowed
				IOtask1Block block6 = new IOtask1Block();
				block6.blockNum = 6;
				block6.nTrials = 1;
				block6.nTargets = 3;
				block6.askArithmetic = false;
				block6.offloadCondition = Names.REMINDERS_OPTIONAL;		
				block6.Run();
				break;
			case 15:
				ClickPage.Run(Instructions.Get(6), "Next");
				break;
			case 16:
				//experimental block
				IOtask1Block block7 = new IOtask1Block();
				block7.blockNum = 7;
				block7.nTrials = 10;
				block7.nTargets = 3;
				block7.askArithmetic = false;
				block7.offloadCondition = Names.REMINDERS_OPTIONAL;		
				block7.Run();
				break;
			case 17:
				Slider.Run("Thank you. You have now finished this task. We would now like you to estimate how often you "
						+ "used the strategy of placing circles at the edge of the box at the beginning of the "
						+ "trial, as reminders. 0% "
						+ "would mean that you never used this strategy. 100% would mean that you used this strategy "
						+ "for all of the special circles", "Never", "Always");
				break;
			case 18:
				PHP.logData("IOpostdiction",  "" + Slider.getSliderValue(), true);
				break;
			case 19:
				Slider.Run("Please tell us how confident you are about the estimate you gave on the last"
						+ " screen. 0% would mean that it was a completely random guess. "
						+ "100% would mean that you are absolutely certain that you were exactly correct.", "Guess", "Exactly correct");
				break;
			case 20:
				PHP.logData("IOpostdictionConfidence", "" + Slider.getSliderValue(), true);
				break;
			case 21:
				SequenceHandler.SetLoop(0, false); // switch to main loop
				SequenceHandler.Next(); // start the loop
			}
			break;

		//here we specify the pattern copy subloop
		case 5:
			switch (sequencePosition.get(5)) {
			case 1:
				ClickPage.Run("In this part of the experiment you will do a pattern copying task.<br><br>"
						+ "On the left you will see a grid with some segments filled with different colours. "
						+ "This is called the template grid.<br><br>"
						+ "On the right will be an "
						+ "empty grid with some colour blocks below. This is called the copy grid.<br><br>"
						+ "Please use your mouse to drag the colour blocks "
						+ "into the right-hand grid so that it matches the one on the left.<br><br>"
						+ "When you have done this, please click the Finished button.", "Next");
				break;
			case 2:
				// initialise the block
				PatternBlock.Init();
				PatternBlock.block = -1;
				PatternBlock.practiceMode=true;
				PatternBlock.Run();
				break;
			case 3:
				if (PatternBlock.lastRespCorrect) {
					ClickPage.Run("That's correct, well done.<br><br>"
							+ "Next you will do the same task again, but you will no longer be able to see both the "
							+ "template grid and the copy grid at the same time. You will only see one or the other.<br><br>"
							+ "When you are looking at the template grid, click Show Copy to look at the copy grid. "
							+ "When you are looking at the copy grid, click Show Template to look at the template grid. "
							+ "You can switch back and forth between the two grids as many times as you like while you copy the "
							+ "pattern, it is totally up to you how many times to switch.<br><br>"
							+ "When you have finished copying the pattern, click Finished as you did before.",  "Next");
				} else {
					SequenceHandler.SetPosition(SequenceHandler.GetPosition()-2);
					ClickPage.Run("You did not perfectly copy the grid. Please try again.", "Next");
				}

				break;
			case 4:
				PatternBlock.Init();
				PatternBlock.block = -2;
				PatternBlock.Run();
				break;
			case 5:
				if (PatternBlock.lastRespCorrect) {
					Slider.Run("That's correct, well done.<br><br>"
							+ "Now that you have had some practice, we would like you to tell us "
							+ "how accurately you copy the patterns. Please use the scale below to indicate what percentage "
							+ "of the patterns you will copy accurately."
							+ "100% would mean that you always get every single one correct. 0% would mean that you make "
							+ "a mistake every time. ", "None correct", "All correct");
				} else {
					SequenceHandler.SetPosition(SequenceHandler.GetPosition()-2);
					ClickPage.Run("You did not perfectly copy the grid. Please try again.", "Next");
				}

				break;
			case 6:
				PHP.logData("PCprediction",  "" + Slider.getSliderValue(), true);
				break;
			case 7:
				ClickPage.Run("Now the task will begin for real. Click below to continue.", "Next");
				break;
			case 8:
				PatternBlock.Init();
				PatternBlock.block=1;
				PatternBlock.nTrials=10;
				PatternBlock.Run();
				break;
			case 9:
				if (PatternBlock.trial != PatternBlock.nTrials) {
					SequenceHandler.SetPosition(SequenceHandler.GetPosition() - 1);
					PatternTrial.Run();
				} else {		
					SequenceHandler.Next();
				}
				break;
			case 10:
				String resp = Window.prompt("Thank you. Now that you have finished this task, we would like you to "
						+ "estimate the average number of times you clicked on the button to switch "
						+ "between the two grids, when you copied a pattern. Note that a switch in "
						+ "either direction would count, so if you moved from the template to the copy, then back "
						+ "again to the template, this would be two clicks. Please type in the average number "
						+ "of times you clicked the button each time you copied a pattern.", "");
				PHP.logData("PCpostdiction",  "" + resp, true);
				break;			
			case 11:
				Slider.Run("Please tell us how confident you are about the estimate you gave on the last"
						+ " screen. 0% would mean that it was a completely random guess. "
						+ "100% would mean that you are absolutely certain that you were exactly correct.", "Guess", "Exactly correct");
				break;
			case 12:
				PHP.logData("PCpostdictionConfidence", "" + Slider.getSliderValue(), true);
				break;
			case 13:
				SequenceHandler.SetLoop(0, false); // switch to main loop
				SequenceHandler.Next(); // start the loop
			}
			break;

			/********************************************/
			/* no need to edit the code below this line */
			/********************************************/

		case 1: // initialisation loop
			switch (sequencePosition.get(1)) {
			case 1:
				// initialise experiment settings
				Initialise.Run();
				break;
			case 2:
				// make sure that a participant ID has been registered.
				// If not, the participant may not have accepted the HIT
				CheckIdExists.Run();
				break;
			case 3:
				// check the status of this participant ID.
				// have they already accessed or completed the experiment? if so,
				// we may want to block them, depending on the setting of
				// SessionInfo.eligibility
				PHP.CheckStatus();
				break;
			case 4:
				// check whether this participant ID has been used to access a previous experiment
				PHP.CheckStatusPrevExp();
				break;
			case 5:
				// clear screen, now that initial checks have been done
				RootPanel.get().clear();

				// make sure the browser window is big enough
				CheckScreenSize.Run(SessionInfo.minScreenSize, SessionInfo.minScreenSize);
				break;
			case 6:
				if (SessionInfo.runInfoConsentPages) { 
					InfoSheet.Run(Instructions.InfoText());
				} else {
					SequenceHandler.Next();
				}
				break;
			case 7:
				if (SessionInfo.runInfoConsentPages) { 
					Consent.Run();
				} else {
					SequenceHandler.Next();
				}
				break;
			case 8:
				//record the participant's counterbalancing condition in the status table				
				if (!SessionInfo.resume) {
					PHP.UpdateStatus("" + Counterbalance.getCounterbalancingCell() + ",1,0,0,0,0");
				} else {
					SequenceHandler.Next();
				}
				break;
			case 9:
				SequenceHandler.SetLoop(0, true); // switch to and initialise the main loop
				SequenceHandler.Next(); // start the loop
				break;
			}
			break;
		case 2: // IOtask1 loop
			switch (sequencePosition.get(2)) {
			/*************************************************************
			 * The code here defines the sequence of events in subloop 2 *
			 * This runs a single trial of IOtask1                       *
			 *************************************************************/
			case 1:
				// first check if the block has ended. If so return control to the main sequence
				// handler
				IOtask1Block block = IOtask1BlockContext.getContext();

				if (block.currentTrial == block.nTrials) {
					SequenceHandler.SetLoop(4, false);
				}

				SequenceHandler.Next();
				break;
			case 2:
				// now initialise trial and present instructions
				IOtask1InitialiseTrial.Run();
				break;
			case 3:
				// now run the trial
				IOtask1RunTrial.Run();
				break;
			case 4:
				// we have reached the end, so we need to restart the loop
				SequenceHandler.SetLoop(2, true);
				SequenceHandler.Next();
				break;
			}
			break;
		case 3: //IOtask2 loop
			switch (sequencePosition.get(3)) {
			/*************************************************************
			 * The code here defines the sequence of events in subloop 3 *
			 * This runs a single trial of IOtask2                       *
			 *************************************************************/
			case 1:
				// first check if the block has ended. If so return control to the main sequence
				// handler
				IOtask2Block block = IOtask2BlockContext.getContext();

				if (block.currentTrial == block.nTrials) {
					SequenceHandler.SetLoop(0,  false);
				}

				SequenceHandler.Next();
				break;
			case 2:
				IOtask2InitialiseTrial.Run();
				break;
			case 3:;
			//present the pre-trial choice if appropriate
			if (IOtask2BlockContext.currentTargetValue() > -1) {
				IOtask2PreTrial.Run();
			} else { //otherwise just skip to the start of the block
				if ((IOtask2BlockContext.getTrialNum() > 0)&&(IOtask2BlockContext.countdownTimer())) {
					//if we're past the first trial and there's a timer, click to begin
					ClickPage.Run("Ready?", "Continue");
				} else {
					SequenceHandler.Next();
				}
			}
			break;
			case 4:
				if (IOtask2BlockContext.getNTrials() == -1) { //if nTrials has been set to -1, we quit before running
					SequenceHandler.SetLoop(0,  false);
					SequenceHandler.Next();
				} else {
					//otherwise, run the trial
					IOtask2RunTrial.Run();
				}
				break;
			case 5:
				IOtask2PostTrial.Run();
				break;
			case 6:
				//we have reached the end, so we need to restart the loop
				SequenceHandler.SetLoop(3,  true);
				SequenceHandler.Next();
				break;
			}
		}
	}

	private static ArrayList<Integer> sequencePosition = new ArrayList<Integer>();
	private static int whichLoop;

	public static void SetLoop(int loop, Boolean init) {
		whichLoop = loop;

		while (whichLoop + 1 > sequencePosition.size()) { // is this a new loop?
			// if so, initialise the position in this loop to zero
			sequencePosition.add(0);
		}

		if (init) { // go the beginning of the sequence if init is true
			sequencePosition.set(whichLoop, 0);
		}
	}

	// get current loop
	public static int GetLoop() {
		return (whichLoop);
	}

	// set a new position
	public static void SetPosition(int newPosition) {
		sequencePosition.set(whichLoop, newPosition);
	}

	// get current position
	public static int GetPosition() {
		return (sequencePosition.get(whichLoop));
	}

	// get current position from particular loop
	public static int GetPosition(int nLoop) {
		return (sequencePosition.get(nLoop));
	}
}
