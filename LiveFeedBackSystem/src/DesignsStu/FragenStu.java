package DesignsStu;

/*
 * Zum Stellen und Voten der Fragen
 * 
 */

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Backend.Question;
import Backend.Voting;
import Frontend.Broadcaster;
import Frontend.Broadcaster.BroadcastListener;
import Frontend.MainUI;

import com.vaadin.annotations.Push;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Push(PushMode.MANUAL)
public class FragenStu extends Panel implements BroadcastListener{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2522961356117204467L;
	private MainUI main;
	private Panel questionsPanel;
	private ArrayList<Question> questionList = new ArrayList<Question>();
	public static VerticalLayout questionPanelVertical;
	
	public FragenStu(MainUI main) {
		Broadcaster.register(this);
		this.main=main;
		setSizeFull(); 
		setCaption("Fragen :");	
		setContent(buildmainVertical());
		
	} 
	 
	private VerticalLayout buildmainVertical(){
		
		if (main.getVeranstaltung()==0){
			VerticalLayout mainVertical = new VerticalLayout();
			mainVertical.setImmediate(false);
			mainVertical.setSizeFull();
			mainVertical.setMargin(false);
			mainVertical.setSpacing(true);
			mainVertical.addComponent(new Label("Noch keine Veranstaltung"));
			return mainVertical;
		}else {
		VerticalLayout mainVertical=new VerticalLayout();
		mainVertical.setSizeFull();
		mainVertical.setSpacing(true);
		System.out.print(main.toString()+"rebuild main");
		final Statement stm;
		try {
			stm = Backend.ConnectionManager.Instance.createStatement();
			final TextField newQuestion = new TextField("Neue Frage: ");
			newQuestion.setWidth("100%");
			newQuestion.setHeight("35px");
			newQuestion.setValue("Write your question here!");
			mainVertical.addComponent(newQuestion);
	
		
			Button submit = new Button(null, new Button.ClickListener() {
			
				/**
				 * 
				 */
				private static final long serialVersionUID = 6177345128590990000L;

				@Override
				public void buttonClick(ClickEvent event) {
					//TODO noetige Catches
					Question question = new Question(main.getVeranstaltung(),newQuestion.getValue(),false);
					try {
						question.insert(stm);
						Broadcaster.broadcast("updateQuestion");

						Notification.show("Erfolgreich gesendet");

					} catch (SQLException e) {
						Notification.show("Fehler beim Absenden:(!");
						e.printStackTrace();
					}
				}
			});
			submit.addStyleName(Reindeer.BUTTON_LINK);
			submit.setIcon(new ThemeResource("Icons/Faenza/actions/22/gtk-new.png"));
			submit.setHeight("35px");
			mainVertical.addComponent(submit);
			mainVertical.setComponentAlignment(submit, Alignment.TOP_RIGHT);
		
			Panel questionsPanel = buildQuestionsPanel();
			mainVertical.addComponent(questionsPanel);
			mainVertical.setExpandRatio(questionsPanel, 1.0f);
		
			return mainVertical;
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new VerticalLayout();
		}
	}
	
	
	
	private Panel buildQuestionsPanel(){
		questionsPanel = new Panel();
		questionsPanel.setWidth("100%");
		questionPanelVertical = new VerticalLayout();
		questionPanelVertical.setSizeFull();
		questionPanelVertical.setSpacing(true);
		questionsPanel.setContent(questionPanelVertical);
		
		
		Panel [] questions = buildQuestionPanels();
		for (int i=0;i<questions.length;i++){
			questionPanelVertical.addComponent(questions[i]);
		}
		return questionsPanel;
	}
	public Panel[] buildQuestionPanels(){
		final Statement stm;
		try {
			stm = Backend.ConnectionManager.Instance.createStatement();
			ArrayList<Question> questionsUnsort = Question.selectAll(stm,main.getVeranstaltung());
			if((questionsUnsort.size())>0){
			int[] votes = new int[questionsUnsort.size()];
            for (int i = 0; i < votes.length; i++) {
				ArrayList<Voting> votings = Voting.selectFromID(stm, questionsUnsort.get(i).getId());
				votes[i]=votings.size();
				}
            int index=0;
            for (int i = 0; i < questionsUnsort.size(); i++) {
            	 int bufferVotings=0;
            	 boolean run=false;
				for (int j = 0; j < votes.length; j++) {
					if(votes[j]==-1){
						continue;
					}
					if (votes[j]>bufferVotings) {
						bufferVotings=votes[j];
						index=j;
						run=true;
					}
				}
				if(run){
				questionList.add(questionsUnsort.get(index));
				votes[index]=-1;
				index=0;
				}
			}
            for (int i = 0; i < votes.length; i++) {
				if(votes[i]!=-1){
					questionList.add(questionsUnsort.get(i));
				}
			}
            }else{questionList=questionsUnsort;}
	
			Panel[] panels=new Panel[questionList.size()];
		//TODO sortieren nach Voting zaehler 
		
		for (int i=0 ; i<panels.length;i++) {
			final Question questionDB = questionList.get(i);
			Panel buffer= new Panel("Frage "+(i+1)+":");
			buffer.setSizeFull();
			HorizontalLayout panelHorizontal = new HorizontalLayout();
			buffer.setContent(panelHorizontal);
			panelHorizontal.setSizeFull();
			panelHorizontal.setSpacing(true);
		
			//Datenabfragen
			Panel question = new Panel("Frage :");
			question.setWidth("100%");
			question.setHeight("100%");
			//question.setContent(new Label("Das hier ist die super coole Frage.Da das erst 35 Zeichen waren, wir aber 160 brauchen, steht hier mehr! Und nun haben wir 125, deswegen schreibe ich noch mehr!"));
			question.setContent(new Label(questionDB.getText()));
			panelHorizontal.addComponent(question);
			//answered
			if (questionDB.getAnswered()){
				Image answered = new Image();
				answered.setSource(new ThemeResource("Icons/Faenza/actions/24/dialog-ok.png"));
				panelHorizontal.addComponent(answered);
				//panelHorizontal.setExpandRatio(answered, 0.5f);
			}else{
			//voting
			
				ArrayList<Voting> votings = Voting.selectFromID(stm,questionDB.getId());
				Panel vote = new Panel("Vote");
				vote.setWidth("50px");
				vote.setHeight("100%");
				vote.setContent(new Label(""+votings.size()));
				panelHorizontal.addComponent(vote);
				//voten
				Button voting = new Button(null,new Button.ClickListener() {
			
					/**
					 * 
					 */
					private static final long serialVersionUID = -6473233262880723736L;

					@Override
					public void buttonClick(ClickEvent event) {
						Voting vote = new Voting(main.getLoggedInUser(),questionDB.getId());
						try {
							vote.insert(stm);
							Broadcaster.broadcast("up");
							Notification.show("gevoted");

						} catch (SQLException e) {
							Notification.show("Fehler!Du hast schon gevoted!");
//							e.printStackTrace();
						}
					}
				});
				voting.addStyleName(Reindeer.BUTTON_LINK);
				voting.setIcon(new ThemeResource("Icons/Faenza/actions/22/add.png"));
				panelHorizontal.addComponent(voting);
				panelHorizontal.setExpandRatio(vote, 1f);
				panelHorizontal.setExpandRatio(voting, 1f);
				panelHorizontal.setComponentAlignment(voting, Alignment.MIDDLE_CENTER);
			}
			panelHorizontal.setExpandRatio(question, 5.0f);
			panels[i]=buffer;
		}
		questionList.clear();
		return panels;
		} catch (SQLException e) {
			e.printStackTrace();
		}return new Panel[0]; 
		
	}
	
	public void receiveBroadcast(final String message) {
		try{
			main.access(new Runnable() {				
				@Override
				public void run() {
					
					if(message.equals("updateQuestion") || message.equals("up") || message.equals("answered") || message.equals("deleteQuestion")){
									setContent(buildmainVertical());
									
									
					}
				}});
			}catch(Exception e){ e.printStackTrace();}
	}
	
	public void detach(){
		Broadcaster.unregister(this);
		super.detach();
	}

	
	public FragenStu get(){
		return this;
	}
}
