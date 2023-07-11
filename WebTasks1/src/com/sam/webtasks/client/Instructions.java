package com.sam.webtasks.client;

import com.sam.webtasks.basictools.Counterbalance;
import com.sam.webtasks.iotask2.IOtask2BlockContext;

public class Instructions {

	public static String Get(int index) {
		String i="";
		 
		switch(index) {
		case 0:
			i = "In this part of the experiment you will have a simple task to do.<br><br>"
					+ "You will see several yellow circles inside a box. Inside each circle will be a number. <br><br>"
					+ "You can move them around with your finger or your mouse. Your task is to drag them to the bottom of the box in sequence.<br><br>"
					+ " Please start by dragging 1 all the way to the bottom. This will make it disappear. <br><br>"
					+ "Then drag 2 to the bottom, then 3, and so on.";
			break;

		case 1:
			i="Now you will continue the task with more circles on the screen. ";
			break;
			
		case 2:
			i="Now you will continue the same task, but sometimes there will be something else to do. <br><br>"
					+ "As well as dragging each circle in turn to the "
					+ "bottom of the screen, you will be instructed to drag one or more "
					+ "of the circles to another part of the box. For instance, you might be told that you should drag number 5 "
					+ "to the left of the box instead of the bottom.<br><br>"
					+ "You will still be able to drag any circle to the bottom of the box, but you should try to "
					+ "remember to drag these special circles to the instructed location. ";
			break;

		case 3:
			i="Now you will do the task again, but this time there will be three special circles to remember.";
			break;
			
		case 4:
			i="Now you will continue with the same task, but sometimes you will be interrupted by "
					+"an arithmetic question. You will need to answer the question in order to continue the other task.";
			break;
			
		case 5:
			i="Some people find it helpful to drag "
					+ "the special circles near to the edge of the box to help them remember.<br><br> "
					+ "For example, if you had to remember to drag 5 to the left of the box, "
					+ "you could drag it near to there at the beginning, before you drag the 1. "
					+ "Then when you eventually got to 5, its location would remind you "
					+ "what to do. You should feel free to use this strategy if you like, but "
					+ "it's up to you.<br><br> "
					+ "Click below to continue.";
			break;
			
		case 6:
			i = "Now the task will start for real.<br><br>"
					+ "You can choose whether to use the strategy of placing the special circles "
					+ "at the edge of the box as reminders or just use your own memory without setting any reminders.<br><br>"
					+ "You should feel free to do the task however you prefer. It's completely up to you.<br><br> "
					+ "Please click below to continue. ";
			break;
			
		case 7:
			i = "You have now completed the experiment. Thank you for taking part.<br><br>"
					+ "Please click on the link below to receive your payment:<br><br>"
					+ "<b><a href=\"https://app.prolific.co/submissions/complete?cc=C1I2CPC5\">"
					+ "CLICK HERE</a></b>";
		}

		return(i);	
	}
	
	public static String InfoText() {
		return ("We would like to invite you to participate in this research project. "
                + "You should only participate if you want to; choosing not to take part "
                + "will not disadvantage you in any way. Before you decide whether you "
                + "want to take part, please read the following information carefully and "
                + "discuss it with others if you wish. Ask us if there is anything that "
                + "is not clear or you would like more information.<br><br>"
                + "We are recruiting volunteers from the Amazon Mechanical Turk website to "
                + "take part in an experiment aiming to improve our understanding of human "
                + "attention and memory. You will see various objects on the screen like coloured, numbered circles, "
                + "and you will be asked to move them with your computer mouse. Sometimes you will be asked to remember "
                + "particular numbers and move the corresponding circle in a particular direction. You will be asked how "
                + "confident you are in your ability to solve the task. "
                + "The experiment "
                + "will last approximately 45 minutes and you will receive a payment of $2 plus an additional bonus via the "
                + "Amazon Mechanical Turk payment system. There are no anticipated risks or "
                + "benefits associated with participation in this study.<br><br>"
                + "It is up to you to decide whether or not to take part. If you choose "
                + "not to participate, you won't incur any penalties or lose any "
                + "benefits to which you might have been entitled. However, if you do "
                + "decide to take part, you can print out this information sheet and "
                + "you will be asked to fill out a consent form on the next page. "
                + "Even after agreeing to take "
                + "part, you can still withdraw at any time and without giving a reason. If you withdraw before the "
                + "end of the experiment, we will not retain your data and it will not be analysed."
                + "<br><br>All data will be collected and stored in accordance with the General Data Protection "
                + "Regulations 2018. Personal information is stored separately from test results, and researchers "
                + "on this project have no access to this data. Your personal information such as name and email "
                + "address is held by Amazon Mechanical Turk but the researchers on this project have no acccess "
                + "to this. Data from this experiment may be made available to the research community, for example by "
                + "posting them on websites such as the Open Science Framework (<a href=\"http://osf.io\">http://osf.io</a>). "
                + "It will not be possible to identify you from these data.<br><br>"
                + "We aim to publish the results of this project in scientific journals and book chapters. Copies of the "
                + "results can either be obtained directly from the scientific journals' websites or from us.<br><br>"
                + "Should you wish to raise a complaint, please contact the Principal Investigator of this project, "
                + "Professor Sam Gilbert (<a href=\"mailto:sam.gilbert@ucl.ac.uk\">sam.gilbert@ucl.ac.uk</a>). However, "
                + "if you feel your complaint has not been handled to your satisfaction, please be aware that you can "
                + "also contact the Chair of the UCL Research Ethics Committee (<a href=\"mailto:ethics@ucl.ac.uk\">ethics@ucl.ac.uk</a>).");
    }

}
