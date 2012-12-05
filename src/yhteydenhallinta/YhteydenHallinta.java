package yhteydenhallinta;


import java.sql.Connection;
import java.sql.DriverManager;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author s1000780
 */
public class YhteydenHallinta
{
    private static Connection yhteys = null;

    public static Connection avaaYhteys(String ajuri, String url, String kayttaja, String salasana)
    {
        try
        {
            Class.forName(ajuri).newInstance();
            yhteys = DriverManager.getConnection(url, kayttaja, salasana);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return yhteys;
    }

    public static void suljeYhteys()
    {
        try{
            if(yhteys != null)
                yhteys.close();
        }
        catch(Exception e)
        {

        }
    }

}
