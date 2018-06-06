/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldh.wauthentication.pkg834;

/**
 * @author Daniel Mensche
 */
import java.util.regex.*;
public class LDHWAuthentication834 {

    /**
     * @param args the command line arguments
     */
   static LdapAuth wAuth;
    public static void main(String[] args) throws Exception {
       
        wAuth = new LdapAuth(); 
       if (args.length != 2) {
			System.out.println( "missing requried username and password" );
			System.exit(1);
		}

		String user = args[0];
		String password = args[1];
		String dn = wAuth.getUid(user);

		if (dn != null) {
			/* Found user - test password */
			if ( wAuth.testBind( dn, password ) ) {
				System.out.println( "user '" + user + "' authentication succeeded" );
				System.exit(0);
			}
			else {
				System.out.println( "user '" + user + "' authentication failed" );
				System.exit(1);
			}
		}
		else {
			System.out.println( "user '" + user + "' not found" );
			System.exit(1);
		}
	}
    }
    

