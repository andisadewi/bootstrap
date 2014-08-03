package DesignsStu;

import java.sql.SQLException; 
import java.sql.Statement;
import java.util.ArrayList;

import Backend.*;
import Frontend.*;
import Frontend.Broadcaster.BroadcastListener;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;

public class UmfragenStu extends Panel implements BroadcastListener{

	private static final long serialVersionUID = -8078186473846496376L;
	private MainUI main;

	public UmfragenStu(MainUI main) {
		Broadcaster.register(this);
		this.main=main;
		setSizeFull();
		setCaption("Umfragen :");
		setContent(buildMainVertical());				
	}
	
	private VerticalLayout buildMainVertical(){
		if (main.getVeranstaltung()==0){
			VerticalLayout mainVertical = new VerticalLayout();
			mainVertical.setImmediate(false);
			mainVertical.setSizeFull();
			mainVertical.setMargin(false);
			mainVertical.setSpacing(true); 
			mainVertical.addComponent(new Label("Noch keine Veranstaltung"));
			return mainVertical;
		}else {
		  
			Statement stm;
			try {
				stm = Backend.ConnectionManager.Instance.createStatement();

				ArrayList<Survey> surveyList = Survey.selectSurveysFromLecture(stm, main.getVeranstaltung());
				Survey visibleSurvey=getVisible(surveyList);
				
				if(visibleSurvey==null) return buildBild();
				stm = Backend.ConnectionManager.Instance.createStatement();
				if(StudentResponse.isAnswered(stm,main.getLoggedInUser(),visibleSurvey.getId())) return buildSecBild();
			
				stm = Backend.ConnectionManager.Instance.createStatement();
				final ArrayList<Response> responses= Response.selectFromSurvey(stm, visibleSurvey.getId());
				VerticalLayout mainVertical= new VerticalLayout();
				mainVertical.setSizeFull();
				mainVertical.setSpacing(true);
				mainVertical.setImmediate(false);
				mainVertical.setMargin(false);
				
				//Frage
				Panel question = new Panel("Frage :");
				question.setWidth("100%");
				question.setHeight("45px");
				question.setContent(new Label(visibleSurvey.getText()));
				mainVertical.addComponent(question);
		
				//Antwort 1 
				Panel answer1 = new Panel ();
				HorizontalLayout answer1Horizontal = new HorizontalLayout();
				final CheckBox answer1Check = new CheckBox();
				Label answer1Label = new Label(responses.get(0).getText());
				answer1Horizontal.addComponent(answer1Check);
				answer1Horizontal.addComponent(answer1Label);
				answer1.setContent(answer1Horizontal);
				mainVertical.addComponent(answer1);
		
				//Anwort 2 
				Panel answer2 = new Panel ();
				HorizontalLayout answer2Horizontal = new HorizontalLayout();
				final CheckBox answer2Check = new CheckBox();
				Label answer2Label = new Label(responses.get(1).getText());
				answer2Horizontal.addComponent(answer2Check);
				answer2Horizontal.addComponent(answer2Label);
				answer2.setContent(answer2Horizontal);
				mainVertical.addComponent(answer2);
				
		
				//Antwort3 
				Panel answer3 = new Panel ();
				HorizontalLayout answer3Horizontal = new HorizontalLayout();
				final CheckBox answer3Check = new CheckBox();
				Label answer3Label = new Label(responses.get(2).getText());
				answer3Horizontal.addComponent(answer3Check);
				answer3Horizontal.addComponent(answer3Label);
				answer3.setContent(answer3Horizontal);
				mainVertical.addComponent(answer3);
				
		
				//Antwort4 
				Panel answer4 = new Panel ();
				HorizontalLayout answer4Horizontal = new HorizontalLayout();
				final CheckBox answer4Check = new CheckBox();
				Label answer4Label = new Label(responses.get(3).getText());
				answer4Horizontal.addComponent(answer4Check);
				answer4Horizontal.addComponent(answer4Label);
				answer4.setContent(answer4Horizontal);
				mainVertical.addComponent(answer4);
		
				//Button Submit 
				final Button submit = new Button("absenden");
				submit.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 7849163564192483285L;

					@Override
					public void buttonClick(ClickEvent event) {
						if(answer1Check.getValue()){
							StudentResponse sr1= new StudentResponse(main.getLoggedInUser(),responses.get(0).getSurveyID(),1);
							try {
								Statement stm = Backend.ConnectionManager.Instance.createStatement();
								sr1.insert(stm);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} 
						if(answer2Check.getValue()){	
							StudentResponse sr2= new StudentResponse(main.getLoggedInUser(),responses.get(1).getSurveyID(),2);
							try {
								Statement stm = Backend.ConnectionManager.Instance.createStatement();
								sr2.insert(stm);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						if(answer3Check.getValue()){
							StudentResponse sr3= new StudentResponse(main.getLoggedInUser(),responses.get(2).getSurveyID(),3);
							try {
								Statement stm = Backend.ConnectionManager.Instance.createStatement();
								sr3.insert(stm);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						if(answer4Check.getValue()){
							StudentResponse sr4= new StudentResponse(main.getLoggedInUser(),responses.get(3).getSurveyID(),4);
							try {
								Statement stm = Backend.ConnectionManager.Instance.createStatement();
								sr4.insert(stm);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						Notification.show("gesendet");
						setContent(buildMainVertical());
					}
				});
				submit.addStyleName(Reindeer.BUTTON_LINK);
				submit.setIcon(new ThemeResource("Icons/Faenza/actions/22/dialog-ok.png"));
				mainVertical.addComponent(submit);
				mainVertical.setComponentAlignment(submit, Alignment.TOP_RIGHT);
				return mainVertical;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			return new VerticalLayout();
		}
	}
	
	private VerticalLayout buildBild(){
		// common part: create layout
		VerticalLayout verticalPanel = new VerticalLayout();
		verticalPanel.setImmediate(false);
		verticalPanel.setSizeFull();
		verticalPanel.setSpacing(true);
		verticalPanel.setMargin(false);
		 
		//Bild
		Image bild = new Image(null,new ThemeResource("img/umfrage.jpg"));
		
		verticalPanel.addComponent(bild);
		return verticalPanel;
	}
	private Survey getVisible(ArrayList <Survey> surveys){
		
		for (int i=0;i<surveys.size();i++){
			if(surveys.get(i).getVisible()) return (surveys.get(i));	
		}
		return null;
	}
	
	public void receiveBroadcast(final String message) {
		try{
			main.access(new Runnable() {				
				@Override
				public void run() {
					if(message.equals("visibleSurvey")){
									setContent(buildMainVertical());	
					}
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void detach(){
		Broadcaster.unregister(this);
		super.detach();
	}

private VerticalLayout buildSecBild(){
	// common part: create layout
	VerticalLayout verticalPanel = new VerticalLayout();
	verticalPanel.setImmediate(false);
	verticalPanel.setSizeFull();
	verticalPanel.setSpacing(true);
	verticalPanel.setMargin(false);
	 
	//Bild
	Image bild = new Image(null,new ThemeResource("img/responsed.png"));
	bild.setWidth("90%");
	verticalPanel.addComponent(bild);
	return verticalPanel;
}
}
