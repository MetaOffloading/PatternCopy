package com.sam.webtasks.basictools;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sam.webtasks.client.SessionInfo;
import com.sam.webtasks.client.SequenceHandler;

public class Consent {
	public static void Run() {
        final CheckBox box1 = new CheckBox("I have read the information page");
        final CheckBox box2 = new CheckBox("I have had the opportunity to contact the "
                + "researcher to ask questions and discuss the study");
        final CheckBox box3 = new CheckBox("I have received satisfactory answers "
                + "to my questions or have been advised of an individual to "
                + "contact for answers to pertinent questions about the research "
                + "and my rights as a participant");
        final CheckBox box4 = new CheckBox("I understand that I am free to "
                + "withdraw at any time, without giving a reason, and without "
                + "incurring any penalty");
        final CheckBox box5 = new CheckBox("I consent to the processing of my personal information "
        		+ "for the purposes explained to me. I understand that such information will be "
        		+ " handled in accordance with all applicable data protection legislation");        
        final CheckBox box6 = new CheckBox("I agree that my anonymised research data may be used "
                        + "by others for future research. I understand that no one will be able "
                        + " to identify me when this data is shared");

        final VerticalPanel mainPanel = new VerticalPanel(); //contains all page elements
        final VerticalPanel screenPanel = new VerticalPanel();   //used to align elements to centre of screen
        final VerticalPanel checkBoxPanel = new VerticalPanel();
        final VerticalPanel namePanel = new VerticalPanel();
//        final VerticalPanel emailPanel = new VerticalPanel();
        final VerticalPanel agePanel = new VerticalPanel();
        final VerticalPanel eduPanel = new VerticalPanel();
        final VerticalPanel genderPanel = new VerticalPanel();
        final HorizontalPanel buttonPanel = new HorizontalPanel();
        final Button backButton = new Button("Go back to information page");
        final Button agreeButton = new Button("I confirm that I wish to continue");
        final TextBox ageBox = new TextBox();
        final Label ageBoxLabel = new Label("Please enter your age in years: ");
        final Label genderLabel = new Label("Are you: ");
        final RadioButton maleRadioButton = new RadioButton("gender", "male");
        final RadioButton femaleRadioButton = new RadioButton("gender", "female");
        final RadioButton otherRadioButton = new RadioButton("gender", "prefer to self-describe:");
          		
        final TextBox genderBox = new TextBox();
        final Label projectTitleText = new Label();
        final HTML approvalHTML = new HTML();
        final HTML bodyHTML = new HTML();
        final Label title = new Label();
        final Label pleaseConfirmText = new Label();
        final TextBox emailTextBox = new TextBox();
        final Button emailSubmitButton = new Button("Submit");
        final VerticalPanel emailPanel1 = new VerticalPanel();
        final HorizontalPanel emailSubmit = new HorizontalPanel();
        final HTML emailHTML = new HTML();
        final Label printText = new Label();
        final HorizontalPanel printPanel = new HorizontalPanel();
        final Button printButton = new Button("Print");

        title.setText("Consent form for participants in research studies");
        title.setStyleName("titleText");
        title.setStyleName("bottomMarginSmall", true);

        projectTitleText.setStyleName("bottomMarginSmall", true);
        projectTitleText.setText("Title of project: Online response time studies of attention and memory");

        approvalHTML.setStyleName("bottomMarginSmall", true);
        approvalHTML.setHTML("This study has been approved by the UCL Research Ethics Committee "
                + "as Project ID Number: 1584/003");

        bodyHTML.setStyleName("bottomMarginSmall", true);
        bodyHTML.setHTML("Thank you for your interest in taking part in this research. If "
                + "you have any questions arising from the Information Page that you have "
                + "already seen, please contact the experimenter before you decide whether "
                + "to continue. You can go back to "
                + "the Information Page by clicking the 'Go back to information page' button below.");

        emailHTML.setHTML("If you would like to receive a copy of this consent form by email, please enter your email address here:");

        emailTextBox.setStyleName("rightMarginSmall", true);
        emailTextBox.addStyleName("bottomMarginSmall");

        emailSubmit.add(emailTextBox);
        emailSubmit.add(emailSubmitButton);

        emailSubmitButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String phpString = "send_consent.php?to=" + emailTextBox.getText();
                PHP.Call(phpString, false);

                mainPanel.remove(emailPanel1);
            }
        });

        emailPanel1.add(emailHTML);
        emailPanel1.add(emailSubmit);

        printPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        printText.setText("Click here to print this page:");
        printText.setStyleName("rightMarginSmall", true);
        printPanel.add(printText);
        printPanel.add(printButton);

        printPanel.addStyleName("bottomMarginSmall");

        pleaseConfirmText.setText("Please confirm the following: ");

        checkBoxPanel.setSpacing(10);
        checkBoxPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        checkBoxPanel.add(box1);
        checkBoxPanel.add(box2);
        checkBoxPanel.add(box3);
        checkBoxPanel.add(box4);
        checkBoxPanel.add(box5);
        checkBoxPanel.add(box6);

        agePanel.add(ageBoxLabel);
        agePanel.add(ageBox);

        genderPanel.add(genderLabel);
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        genderPanel.add(otherRadioButton);
        genderPanel.add(genderBox);

        mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        checkBoxPanel.setStyleName("bottomMarginSmall");
        ageBox.setStyleName("bottomMarginSmall");
        genderBox.setStyleName("bottomMarginSmall");

        buttonPanel.add(backButton);
        buttonPanel.add(agreeButton);

        mainPanel.setWidth("75%");

        mainPanel.add(title);
        mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        mainPanel.add(printPanel);
        //mainPanel.add(emailPanel1);
        mainPanel.add(projectTitleText);
        mainPanel.add(approvalHTML);
        mainPanel.add(bodyHTML);
        mainPanel.add(pleaseConfirmText);
        mainPanel.add(checkBoxPanel);
        mainPanel.add(namePanel);
        //mainPanel.add(emailPanel);
        mainPanel.add(agePanel);
        mainPanel.add(genderPanel);
        mainPanel.add(eduPanel);
        mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        mainPanel.add(buttonPanel);

        screenPanel.setHeight(Window.getClientHeight() + "px");
        screenPanel.setWidth("100%");
        screenPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        screenPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        screenPanel.addStyleName("bottomMarginSmall");

        screenPanel.add(mainPanel);
        
        final ScrollPanel scrollPanel = new ScrollPanel();

        backButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RootPanel.get().remove(scrollPanel);
                SequenceHandler.SetPosition(SequenceHandler.GetPosition()-2);
                SequenceHandler.Next();
            }
        });

        agreeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                int boxesTicked = 0, validAge = 1, validGender = 0, validEdu = 0;
                String alertString = "";

                if (ageBox.getText().length() < 2) {
                    validAge = 0;
                }

                if (maleRadioButton.getValue()) {
                    validGender++;
                }
                if (femaleRadioButton.getValue()) {
                    validGender++;
                }
                if (otherRadioButton.getValue()) {
                    validGender++;
                }
                       

                if (box1.getValue()) {
                    boxesTicked++;
                }
                if (box2.getValue()) {
                    boxesTicked++;
                }
                if (box3.getValue()) {
                    boxesTicked++;
                }
                if (box4.getValue()) {
                    boxesTicked++;
                }
                if (box5.getValue()) {
                    boxesTicked++;
                }
                if (box6.getValue()) {
                    boxesTicked++;
                }

                if (boxesTicked + validAge + validGender == 8) {
                	if (maleRadioButton.getValue()) {
                		SessionInfo.gender=Names.GENDER_MALE;
                	}
                	
                	if (femaleRadioButton.getValue()) {
                		SessionInfo.gender=Names.GENDER_FEMALE;
                	}
                	
                	if (otherRadioButton.getValue()) {
                		SessionInfo.gender=Names.GENDER_OTHER;
                	}
                	 	
                	
                    SessionInfo.age = Integer.parseInt(ageBox.getText());
                    
                    String data=""+SessionInfo.gender+","+SessionInfo.age+","+Counterbalance.getCounterbalancingCell();
             
                    RootPanel.get().remove(scrollPanel);

                    if (Integer.parseInt(ageBox.getText()) < 18) {
                        under18();
                    } else {
                    	PHP.logData("participantInfo", data, true);
                    }
                } else {
                    if (boxesTicked < 6) {
                        alertString = alertString + "You must tick all boxes to continue\n";
                    }

                    if (validAge == 0) {
                        alertString = alertString + "Please enter your age\n";
                    }

                    if (validGender == 0) {
                        alertString = alertString + "Please indicate your gender\n";
                    }
                    
                    Window.alert(alertString);
                }
            }
        });
 
        scrollPanel.add(screenPanel);
        scrollPanel.setHeight(Window.getClientHeight() + "px");
        RootPanel.get().add(scrollPanel);
        
        ageBox.setWidth(ageBoxLabel.getOffsetWidth() + "px");
        genderBox.setWidth(ageBoxLabel.getOffsetWidth() + "px");
        emailTextBox.setWidth((printText.getOffsetWidth() - 5) + "px");
    }

    public static void under18() {
        Label textLabel = new Label("Sorry but you must be over 18 to take part.");

        RootPanel.get().add(textLabel);
    }

}
