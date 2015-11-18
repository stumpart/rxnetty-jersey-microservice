package org.stumpart;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import com.sun.security.auth.UserPrincipal;
/**
 * Created by barringtonhenry on 11/1/15.
 */
public class DefaultSecurityContextFactory  {

    public SecurityContext create(){
        final String principal = "testUser";

        return new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return new UserPrincipal(principal);
            }

            @Override
            public boolean isUserInRole(String role) {
                return false;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public String getAuthenticationScheme() {
                return null;
            }
        };

    }
}
