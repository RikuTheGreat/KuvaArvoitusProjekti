package yhteydenhallinta;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import kuvaarvoitusprojekti.Kuva;

/**
 *
 * @author s1000780
 */
public class Tietovarasto {
    private final String db = "jdbc:derby:C:\\Tiedostot\\KuvaArvoitusProjekti\\kuvaarvoitus";
    private final String user = "pena";
    private final String pw = "penala";

    private String haeKuvaMaara = "select count(*) from kuvataulu";
    private String haeVihjeMaara = "select count(*) from vihjetaulu";
    private String lisaaKuva = "insert into kuvataulu (kuvaid, nimi, polku) values (?,?,?)";
    private String lisaaVihje = "insert into vihjetaulu (vihjeid, kuvaid, vihje) values (?,?,?)";
    private String haeKaikkiKuvat = "select * from kuvataulu";
    private String haeVihjeetKuvalle = "select * from vihjetaulu where kuvaid = ? order by vihjeid";

    private String veneenHakuSql = "select * from henkilo where sukunimi=? and syntymavuosi > ?";
    private String kaikkienHenkiloidenHakuSql = "select * from henkilo order by henkiloID";
    private String henkilonLisaysSql = "insert into henkilo (henkiloID, etunimi,sukunimi, syntymavuosi) values(?,?,?,?)";
//
//    public boolean poistaHenkilo(int id)
//    {
//          Connection yhteys = null;
//        try {
//            yhteys = YhteydenHallinta.avaaYhteys("org.apache.derby.jdbc.EmbeddedDriver", "jdbc:derby:venekanta", "saku", "salainen");
//
//            PreparedStatement henkilonPoisto = yhteys.prepareStatement(henkilonPoistoSql);
//            henkilonPoisto.setInt(1, id);
//
//            henkilonPoisto.executeUpdate();
//            henkilonPoisto.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            YhteydenHallinta.suljeYhteys();
//        }
//
//        return true;
//    }

    public boolean lisaaKuva(Kuva kuva) {
        Connection yhteys = null;
        try {
            yhteys = YhteydenHallinta.avaaYhteys("org.apache.derby.jdbc.EmbeddedDriver", db, user, pw);

            PreparedStatement veneenLisays = yhteys.prepareStatement(lisaaKuva);
            veneenLisays.setInt(1, kuva.getId());
            veneenLisays.setString(2, kuva.getVastaus());
            veneenLisays.setString(3, kuva.getPolku());

            veneenLisays.executeUpdate();
            veneenLisays.close();

            for(String s:kuva.getVihjeet())
                lisaaVihje(s, kuva.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           YhteydenHallinta.suljeYhteys();
        }

        return true;
    }

        public boolean lisaaVihje(String vihje, int kuvaID) {
        Connection yhteys = null;
        int id = haeVihjeMaara();
        try {
            yhteys = YhteydenHallinta.avaaYhteys("org.apache.derby.jdbc.EmbeddedDriver", db, user, pw);

            PreparedStatement veneenLisays = yhteys.prepareStatement(lisaaVihje);
            veneenLisays.setInt(1, id);
            veneenLisays.setInt(2, kuvaID);
            veneenLisays.setString(3, vihje);

            veneenLisays.executeUpdate();
            veneenLisays.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           YhteydenHallinta.suljeYhteys();
        }

        return true;
    }


    public int haeVihjeMaara() {
        Connection yhteys = null;
        int maara = -1;
        try {
            yhteys = YhteydenHallinta.avaaYhteys("org.apache.derby.jdbc.EmbeddedDriver", db, user, pw);

            PreparedStatement kuvaMaaraHaku = yhteys.prepareStatement(haeVihjeMaara);

            ResultSet tulos = kuvaMaaraHaku.executeQuery();
            while (tulos.next()) {
                maara = tulos.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           YhteydenHallinta.suljeYhteys();
        }

        return maara;
    }

       public int haeKuvaMaara() {
        Connection yhteys = null;
        int maara = -1;
        try {
            yhteys = YhteydenHallinta.avaaYhteys("org.apache.derby.jdbc.EmbeddedDriver", db, user, pw);

            PreparedStatement kuvaMaaraHaku = yhteys.prepareStatement(haeKuvaMaara);


            ResultSet tulos = kuvaMaaraHaku.executeQuery();
            while (tulos.next()) {
                maara = tulos.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           YhteydenHallinta.suljeYhteys();
        }

        return maara;
    }

    public List<String> haeVihjeetKuvalle(Kuva k)
    {
        List<String> apulista = new ArrayList<String>();
        Connection yhteys = null;
        try {
            yhteys = YhteydenHallinta.avaaYhteys("org.apache.derby.jdbc.EmbeddedDriver", db, user, pw);

            PreparedStatement vihjeHaku = yhteys.prepareStatement(haeVihjeetKuvalle);
            vihjeHaku.setInt(1, k.getId());

            ResultSet vihjetulos = vihjeHaku.executeQuery();
            while (vihjetulos.next()) {
                apulista.add(vihjetulos.getString(3));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           YhteydenHallinta.suljeYhteys();
        }

        return apulista;
    }

    public List<Kuva> haeKaikkiKuvat() {
        List<Kuva> apulista = new ArrayList<Kuva>();
        Connection yhteys = null;
        try {
            yhteys = YhteydenHallinta.avaaYhteys("org.apache.derby.jdbc.EmbeddedDriver", db, user, pw);
            
            PreparedStatement kuvaHaku = yhteys.prepareStatement(haeKaikkiKuvat);

            ResultSet kuvatulos = kuvaHaku.executeQuery();
            while (kuvatulos.next()) {
                apulista.add(new Kuva(kuvatulos.getString(2), kuvatulos.getString(3),new ArrayList<String>(), kuvatulos.getInt(1)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           YhteydenHallinta.suljeYhteys();
        }

        return apulista;
    }

//    public List<Henkilo> haeHenkilotSukunimenJaSyntymavuodenMukaan(String sukunimi, int syntymavuosi) {
//        List<Henkilo> apulista = new ArrayList<Henkilo>();
//        Connection yhteys = null;
//        try {
//            yhteys = YhteydenHallinta.avaaYhteys("org.apache.derby.jdbc.EmbeddedDriver", "jdbc:derby:C:\\Users\\s1000780.BC\\Desktop\\EkaJDK\\henkilokanta", "saku", "salainen");
//
//            PreparedStatement henkilonHaku = yhteys.prepareStatement(henkilonHakuSql);
//            henkilonHaku.setString(1, sukunimi);
//            henkilonHaku.setInt(2, syntymavuosi);
//
//            ResultSet henkilotulos = henkilonHaku.executeQuery();
//            while (henkilotulos.next()) {
//                apulista.add(new Henkilo(henkilotulos.getInt(1), henkilotulos.getInt(4), henkilotulos.getString(2), henkilotulos.getString(3)));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//           YhteydenHallinta.suljeYhteys();
//        }
//
//        return apulista;
//    }
}
