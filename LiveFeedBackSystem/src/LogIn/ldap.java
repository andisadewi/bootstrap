package LogIn;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class ldap {

	public boolean authentification(String username,String password){
		try
	    {
	        // Set up the environment for creating the initial context
	        Hashtable<String, String> env = new Hashtable<String, String>();
	        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	        env.put(Context.PROVIDER_URL, "ldaps://ldap.tu-berlin.de:636");
	        // 
	        env.put(Context.SECURITY_AUTHENTICATION, "simple");
	        env.put(Context.SECURITY_PRINCIPAL, "uid="+username+",ou=user,dc=tu-berlin,dc=de"); //we have 2 \\ because it's a escape char
	        env.put(Context.SECURITY_CREDENTIALS, password);

	        // Create the initial context

	        DirContext ctx = new InitialDirContext(env);
	        boolean result = ctx != null;

	        if(ctx != null)
	            ctx.close(); 

	        return result; 
	    }
	    catch (Exception e)
	    {            
	    	return false;	    }

		
		
	}
	public static void main(String[] args) {
		

	}

}
