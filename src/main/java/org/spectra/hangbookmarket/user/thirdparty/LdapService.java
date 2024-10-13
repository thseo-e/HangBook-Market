package org.spectra.hangbookmarket.user.thirdparty;

import java.util.Hashtable;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class LdapService
{
    private DirContext dirContext;
    private String userId;

    public void connect()
        throws NamingException
    {
    }

    public void connect(String userId, String passwd)
        throws NamingException
    {
        connect("dc1.spectra.co.kr", "389", "p=Spectra", userId + "@spectra.co.kr", passwd);
        this.userId = userId;
    }

    public void connect(String host, String port, String rootdn, String username, String password)
        throws NamingException
    {
        Hashtable env = new Hashtable();
        env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
        env.put("java.naming.security.authentication", "simple");
        env.put("java.naming.provider.url", "ldap://" + host + ":" + port);
        env.put("java.naming.security.credentials", password);
        env.put("java.naming.security.principal", username);
        dirContext = new InitialDirContext(env);
    }

    public void disconnect()
    {
        try
        {
            if(dirContext != null)
            {
                dirContext.close();
                dirContext = null;
            }
        }
        catch(NamingException e)
        {
            //e.printStackTrace();
        }
    }

    public Hashtable search()
        throws Exception
    {
        Hashtable htResult = null;
        SearchControls searchCtls = new SearchControls();
        searchCtls.setSearchScope(2);
        String searchFilter = "(&(objectClass=*)(mail=" + userId + "@spectra.co.kr))";
        String searchBase = "DC=spectra,DC=co,DC=kr";
        int totalResults = 0;
        String returnedAtts[] = {
            "*"
        };
        searchCtls.setReturningAttributes(returnedAtts);
        for(NamingEnumeration answer = dirContext.search(searchBase, searchFilter, searchCtls); answer.hasMoreElements();)
        {
            SearchResult sr = (SearchResult)answer.next();
            Attributes attrs = sr.getAttributes();
            if(attrs != null)
            {
                htResult = new Hashtable();
                String value = null;
                for(NamingEnumeration ae = attrs.getAll(); ae.hasMore();)
                {
                    value = "";
                    Attribute attr = (Attribute)ae.next();
                    for(NamingEnumeration e = attr.getAll(); e.hasMore();)
                    {
                        value = value + e.next();
                        htResult.put(attr.getID(), value);
                        totalResults++;
                    }

                }

            }
        }

        return htResult;
    }
}
