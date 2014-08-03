package Frontend;
import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("prototyplayout")
@PreserveOnRefresh
@Push
public class MainUI extends UI 
{
	private int veranstaltung;
	private String user;
	private String role;
	public Navigator navigator;
	public static final String LOGIN = "login";
	public static final String FIRSTVIEW = "Veranstaltungen";
	public static final String ACTIVE ="LiveFeedback";
	public static final String ADMIN ="admin";
	private ActiveView activeView = new ActiveView(this);
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MainUI.class)
	public static class Servlet extends VaadinServlet {
	}
  
	@Override
	protected void init(VaadinRequest request) {
		String userAgent = request.getHeader("user-agent").toLowerCase();
        if(userAgent.contains("Mobile") || userAgent.contains("mobile")) { 
        	//TODO richtige Seite
        	getPage().setLocation("https://192.168.178.61:7443/Live-Feedback/");
        	getSession().close(); 
}
		 navigator = new Navigator(this,this);
	     //Add some Views
	     navigator.addView("",new Start(this)); 
	     navigator.addView(LOGIN, new Start(this));
	     navigator.addView(FIRSTVIEW, new VeranstaltungsView(this));
	     navigator.addView(ACTIVE, activeView);
	     navigator.addView(ADMIN, new Admin(this));
	     
		 
		
	} 
	public String getLoggedInUser(){
        return user;
	}  

	public void setLoggedInUser(String user){
        this.user = user;
	}
	
	public String getRole(){
		return role;
	}
	
	public void setRole(String role){
		this.role=role;
	}
	public int getVeranstaltung(){
		return veranstaltung;
	}
	public void setVeranstaltung(int veranstaltung){
		this.veranstaltung=veranstaltung;
	}


}
	

