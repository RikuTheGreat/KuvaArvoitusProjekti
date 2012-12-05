/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kuvaarvoitusprojekti;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author s1000780
 */
public class Kuva {
    private List<String> vihjeet = new ArrayList<String>();
    private String vastaus, polku;
    private int id;

    public Kuva(String vastaus, String polku, List<String> vihjeet, int id) {
        this.vastaus = vastaus;
        this.polku = polku;
        this.id = id;
        this.vihjeet = vihjeet;
    }

    Kuva() {
        
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPolku(String polku) {
        this.polku = polku;
    }

    public void setVastaus(String vastaus) {
        this.vastaus = vastaus;
    }

    public void setVihjeet(List<String> vihjeet) {
        this.vihjeet = vihjeet;
    }
   

    public int getId() {
        return id;
    }

    public String getPolku() {
        return polku;
    }

    public String getVastaus() {
        return vastaus;
    }

    public List<String> getVihjeet() {
        return vihjeet;
    }
}
