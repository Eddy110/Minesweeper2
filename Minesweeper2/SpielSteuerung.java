import com.sun.org.apache.bcel.internal.generic.RETURN;
/** Represents an employee.
 * @author Edmund Willusch
 * @version 1.0.1
 * @since 1.0
*/
/** Erstellt die Steuerung und sorgt für die Kommunikation und berechnet das Spiel
*/
public class SpielSteuerung {
  
  // Anfang Attribute
  private boolean spielBeendet;
  private SpielDaten dieDaten;
  private SpielOberflaeche dieOberflaeche;
  private int size = 0;
  // Ende Attribute
  
  // Anfang Methoden
  public SpielSteuerung(SpielOberflaeche gui) {
    dieOberflaeche = gui;
    dieDaten = new SpielDaten();
    size = dieOberflaeche.SIZE;
    this.spielBeendet = false;
  }
  
  public boolean getSpielBeendet() {
    return spielBeendet;
  }
  
  public void setSpielBeendet(boolean spielBeendet) {
    this.spielBeendet = spielBeendet;
  }
  
  public void starteNeuesSpiel(int anzMinen) {
    this.spielBeendet = false;
    dieDaten.initialisiereSpielDaten(anzMinen); 
    dieOberflaeche.loescheSpielbrett();
  }
  
  /**
   * spieleZug:
     * Die Methode überprüft zuerst ob das Spiel nicht schon beendet ist. Danach überprüft Sie ob das Feld welches geclickt wurde, mit einer Bombe belegt ist. 
     * Falls dies der Fall ist, beendet Sie das Spiel.
     * Ansonsten schreibt Sie den Wert (Anzahl der Bomben die angrenzen) auf das Feld. Sollte auf dem feld eine Null stehen, überpüft sie die umliegenden Felder auch auf Nullen und übergibt diese Felder an die Methode NullenAuf.
     * Am Ende überprüft Sie ob das Spiel vorbei ist, indemSie die Anzahl der gespielten Felder mit der Anzahl der verfügbaren - die der Bomben vergleicht. Dies alles tut Sie unter anderem durch den Int ergeb, welchen Sie überliefert bekommt.
    */
  public void spieleZug(int x, int y) {
    int erg = this.analysiereSpielzug(x,y);
    int anzAufgedeckt;
    int anzMinen;
    
    if (spielBeendet==false) {
      if (erg==-1) {
        this.aufdeckenMinen();
        spielBeendet=true;
      } // end of if
      else{
        //erg=0;
        dieDaten.schreibeFeldDaten(x,y,erg);
        dieOberflaeche.schreibeAufFeld(x,y,String.valueOf(erg));
        if (erg == 0) {
          for(int i = -1; i<2; i++) {
            for(int j = -1; j<2; j++) {
              if(!(x+i < 0 | x+i > (size-1) | y+j < 0 | y+j > (size-1) | (x+i == x & y+j == y))) {
                nullenAuf(x+i, y+j);
              }         
            }
          }        
        }        
      }
      anzAufgedeckt=dieDaten.bestimmteAnzAufgedeckteFelder();
      anzMinen=dieDaten.leseAnzahlMinen();         
      if ((size*size)-anzMinen==anzAufgedeckt) {
        dieOberflaeche.zeigeMeldung(1);
        spielBeendet=true;
      } // end of if
           
    } // end of if
  }
  
  /**
   * Diese Methode überprüft für spieleZug die Datenfelder. Und liefert so den Ergebnis (ergeb) Wert wieder, welchen spieleZug benutzt. Sie sorgt auch dafür das die umliegenden Felder überprüft und beschriftet werden, indem Sie auch für diese Felder den ergeb Wert an spieleZug sendet.
   * @RETURN ergeb ist der Wert des Feldes
   */ 
  protected int analysiereSpielzug(int x, int y) {
    int ergeb=0;
    if(dieDaten.leseDatenfeld(x, y) == -1) {
      ergeb = -1;
    }else {    
      for(int i = -1; i<2; i++) {
        for(int j = -1; j<2; j++) {
          if(!(x+i < 0 | x+i > (size-1) | y+j < 0 | y+j > (size-1) | (x+i == x & y+j == y))) {
            if(dieDaten.leseDatenfeld(x+i, y+j) == -1) {
              ergeb++;
            }
          }         
        }
      }
    }
    return ergeb;
  }
  
  protected void aufdeckenMinen() {
    int ergeb;
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        ergeb=dieDaten.leseDatenfeld(i,j);
        if (ergeb==-1) {
          dieOberflaeche.schreibeAufFeld(i,j,String.valueOf(ergeb));
        } // end of if
      }
    }
    dieOberflaeche.zeigeMeldung(2);
  }
  
  protected void nullenAuf(int x, int y){
    if (dieOberflaeche.gibButton(x,y).equals("")) {
      spieleZug(x,y);
    } 
  }
  
  // Ende Methoden
} // end of SpielSteuerung
