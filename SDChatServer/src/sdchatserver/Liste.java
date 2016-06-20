/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdchatserver;

/**
 *
 * @author Klauke.Sem
 */
public class Liste
    {
      private Knoten hatBug, kenntAktuelles, hatHeck;
    
      private int zLaenge;
    
      private class Knoten
      { 
        private Object kenntInhalt;
        private Knoten kenntVorgaenger, kenntNachfolger;
    
        public Knoten (Object pInhalt, Knoten pVorgaenger, Knoten pNachfolger)
        {
          kenntInhalt     = pInhalt;
          kenntVorgaenger = pVorgaenger;
          kenntNachfolger = pNachfolger;
        }
    
        public void setzeInhalt (Object pInhalt)
        {
          kenntInhalt = pInhalt;
        }
    
          public Object inhalt()
          {
            return kenntInhalt;
          }
    
          public void setzeNachfolger (Knoten pNachfolger)
          {
            kenntNachfolger = pNachfolger;
          }
    
          public Knoten nachfolger()
          {
            return kenntNachfolger;
          }
    
        public void setzeVorgaenger (Knoten pVorgaenger)
        {
          kenntVorgaenger = pVorgaenger;
        }
    
        public Knoten vorgaenger()
        {
          return kenntVorgaenger;
        }
      }
    
      public Liste ()
      {
        hatBug         = new Knoten(null,null,null);
        hatHeck        = new Knoten(null,hatBug,null);
        kenntAktuelles = hatBug;
        hatBug.setzeNachfolger(hatHeck);  
        zLaenge        = 0;
      }
    
      public boolean istLeer()
      { 
        return hatBug.nachfolger() == hatHeck;
      }
    
      public boolean istDahinter()
      {
        return kenntAktuelles == hatHeck;
      }
          
      public void vor()
      {
        if (!this.istDahinter())
          kenntAktuelles = kenntAktuelles.nachfolger();
      }
    
      public void zumAnfang()
      {
        kenntAktuelles = hatBug.nachfolger();
      }
    
      public Object aktuelles()
      {
        return kenntAktuelles.inhalt();
      }
    
    
      public void haengeAn (Object pInhalt)
      {
        Knoten lNeuknoten = new Knoten(pInhalt,hatHeck.vorgaenger(),hatHeck);   
        hatHeck.vorgaenger().setzeNachfolger(lNeuknoten);       
        hatHeck.setzeVorgaenger(lNeuknoten);           
        zLaenge++;
      }
       
      public void entferneAktuelles()
      {
        if (!(this.istDahinter()))
        {
          this.verketteKnoten(kenntAktuelles.vorgaenger(), kenntAktuelles.nachfolger());
          kenntAktuelles = kenntAktuelles.nachfolger();
          zLaenge--;
        }
      }
      
      private void verketteKnoten (Knoten pLinksknoten, Knoten pRechtsknoten)
      {
        pLinksknoten.setzeNachfolger(pRechtsknoten);
          pRechtsknoten.setzeVorgaenger(pLinksknoten);
        }
    
    }
