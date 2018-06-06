/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldh.wauthentication.pkg834;

/**
 *
 * @author Daniel Mensche
 */
import java.util.*;
import javax.naming.*;
import javax.naming.directory.*;

public class LdapAuth {
  	private final static String ldapURI = "ldaps://ldap.server.com/dc=ldap,dc=server,dc=com";
	private final static String contextFactory = "com.sun.jndi.ldap.LdapCtxFactory";

        public LdapAuth(){
            
        }
        
	private static DirContext ldapContext () throws Exception {
		Hashtable<String,String> env = new Hashtable <String,String>();
		return ldapContext(env);
	}

	private static DirContext ldapContext (Hashtable <String,String>env) throws Exception {
		env.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
		env.put(Context.PROVIDER_URL, ldapURI);
		DirContext ctx = new InitialDirContext(env);
		return ctx;
	}

	public static String getUid (String user) throws Exception {
		DirContext ctx = ldapContext();

		String filter = "(uid=" + user + ")";
		SearchControls ctrl = new SearchControls();
		ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
		NamingEnumeration answer = ctx.search("", filter, ctrl);

		String dn;
		if (answer.hasMore()) {
			SearchResult result = (SearchResult) answer.next();
			dn = result.getNameInNamespace();
		}
		else {
			dn = null;
		}
		answer.close();
		return dn;
	}

	public static boolean testBind (String dn, String password) throws Exception {
		Hashtable<String,String> env = new Hashtable <String,String>();
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, dn);
		env.put(Context.SECURITY_CREDENTIALS, password);

		try {
			ldapContext(env);
		}
		catch (javax.naming.AuthenticationException e) {
			return false;
		}
		return true;  
}
}
