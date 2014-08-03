package DesignsStu;
/*
 * Um sich in Veranstaltungen als Student einzuloggen
 * 
 */

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Backend.Lecture;
import Frontend.Broadcaster;
import Frontend.Broadcaster.BroadcastListener;
import Frontend.MainUI;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

public class VeranstaltungsOverViewStu extends Panel implements BroadcastListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3385893011788395604L;
	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */
	@AutoGenerated
	private VerticalLayout mainVerticalLayout;
	private MainUI main;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public VeranstaltungsOverViewStu(MainUI main) {
		Broadcaster.register(this);
		this.main=main; 
		setSizeFull();
		mainVerticalLayout=buildMainVerticalLayout();
		setContent(mainVerticalLayout);
 
		 
	}

	private VerticalLayout buildMainVerticalLayout(){
		mainVerticalLayout = new VerticalLayout();
		mainVerticalLayout.setSizeFull();
		
		Panel veranstaltungen= buildVeranstaltungsPanel();
		mainVerticalLayout.addComponent(veranstaltungen);
		mainVerticalLayout.setExpandRatio(veranstaltungen, 1.0f);
		return mainVerticalLayout;
	}
	
	private Panel buildVeranstaltungsPanel(){
		Panel veranstaltungen = new Panel();
		veranstaltungen.setWidth("100%");
		
		VerticalLayout panelLayout= new VerticalLayout();
		panelLayout.setSizeFull();
		panelLayout.setSpacing(true);
		veranstaltungen.setContent(panelLayout);
		
		Panel [] panels = buildPanels();
		for (int i=0;i<panels.length;i++){
			panelLayout.addComponent(panels[i]);
		}
		
		return veranstaltungen;
	}
	
	private Panel[] buildPanels(){
		Statement stm;
		try {
			stm = Backend.ConnectionManager.Instance.createStatement();

			ArrayList<Lecture> lectureList = Lecture.select(stm);
			lectureList=lecturesFromToday(lectureList);
			Panel[] panels = new Panel[lectureList.size()];

		
		for(int i=0;i<panels.length;i++){
			final Lecture lecture = lectureList.get(i);
			panels[i]=new Panel(lecture.getId()+" : "+lecture.getName() );
			panels[i].setWidth("100%");
			panels[i].setHeight("70px");
			//Layout des Panels 
			HorizontalLayout panelLayout = new HorizontalLayout();
			panelLayout.setSpacing(true);

			//Button zum einloggen um Vorlesung zu halten
			Button einloggen = new Button(null, new Button.ClickListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 4490212647025612357L;

				@Override
				public void buttonClick(ClickEvent event) {
					main.addWindow(buildLogInWindow(lecture.getId()));
					
				}
			});
			einloggen.addStyleName(Reindeer.BUTTON_LINK);
			einloggen.setIcon(new ThemeResource("Icons/Faenza/actions/22/forward.png"));
		
		
			//Informationen zur Veranstaltung
			Panel doName = new Panel("Dozent :");
			doName.setContent(new Label(lecture.getProfessorID()));
			doName.setWidth("250px");
			Panel raum = new Panel("RaumNr. :");
			raum.setContent(new Label(lecture.getRoom()));
			raum.setWidth("250px");
			
			//zum Panel hinzufuegen
			panelLayout.addComponents(doName);
			panelLayout.addComponent(raum);
			panelLayout.addComponent(einloggen);
			einloggen.setWidth("50px");
			panelLayout.setComponentAlignment(einloggen, Alignment.BOTTOM_RIGHT);
			panels[i].setContent(panelLayout);
	
		}
		
		return panels;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Panel[0];
	}
	private Window buildLogInWindow(final int i){
		Statement stm;
		try {
		stm = Backend.ConnectionManager.Instance.createStatement();
		final Lecture lecture = Lecture.selectLecture(stm, i);
		
		final Window loginWindow = new Window("Login");
		loginWindow.setModal(true);
		loginWindow.setClosable(false);
		loginWindow.setWidth("220px");
		loginWindow.addStyleName(Reindeer.WINDOW_LIGHT);
		VerticalLayout vertLay = new VerticalLayout();
		vertLay.setSpacing(true);

		final PasswordField passw = new PasswordField("password");
		passw.focus();
		Button buttonLogin = new Button("OK!");
		buttonLogin.setClickShortcut(KeyCode.ENTER);
		buttonLogin.addClickListener(new Button.ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1468851816458051212L;

			@Override
			public void buttonClick(ClickEvent event) {
				loginWindow.close();
				String pw = passw.getValue();
				if (pw.equals(lecture.getPassword())){
					main.setVeranstaltung(i);
					main.getNavigator().navigateTo(MainUI.ACTIVE);
					Notification.show("Erfolgreich!");
					
				}else {
					Notification.show("Fehlerhafter Login");
				}
			}
		});
		
		loginWindow.setContent(vertLay);
		vertLay.addComponent(passw);
		vertLay.setComponentAlignment(passw, Alignment.TOP_CENTER);
		vertLay.addComponent(buttonLogin);
		vertLay.setComponentAlignment(buttonLogin, Alignment.TOP_CENTER);
		loginWindow.center();
		return loginWindow;		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Window("Fehler");
	}
	private ArrayList<Lecture> lecturesFromToday(ArrayList <Lecture> lectures){
		ArrayList<Lecture> todayLecture = new ArrayList<Lecture>();
		for (int i=0;i<lectures.size();i++){
			if(isToday(lectures.get(i).getDate())) todayLecture.add(lectures.get(i));
		}
		return todayLecture;
	}
	private boolean isToday(Date date){
		 Calendar cal1 = Calendar.getInstance();
	        cal1.setTime(date);
	    Calendar cal2 = Calendar.getInstance();
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
		
		
	}
	public void receiveBroadcast(final String message) {
		try{
			main.access(new Runnable() {				
				@Override
				public void run() {
					
					if( message.equals("LectureToday")){
									setContent(buildMainVerticalLayout());
									
									
					}
				}});
			}catch(Exception e){ e.printStackTrace();}
	}
	
	public void detach(){
		Broadcaster.unregister(this);
		super.detach();
	}
	
}