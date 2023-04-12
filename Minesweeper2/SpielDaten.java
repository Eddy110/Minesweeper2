/** Represents an employee.
 * @author Edmund Willusch
 * @version 1.0.1
 * @since 1.0
*/
/** Erstellt das Spielfeld und ermittelt die Daten (Größe des Feldes und Wert der Felder)
*/
public class SpielDaten {
  
  // Anfang Attribute
  private int[][] spielBrettDaten = new int [5][5];
  private int anzahlMinen;
  // Ende Attribute
  
  // Anfang Methoden
  public int[][] getSpielBrettDaten() {
    return spielBrettDaten;
  }
  
  public void setSpielBrettDaten(int[][] spielBrettDaten) {
    this.spielBrettDaten = spielBrettDaten;
  }
  
  /**
   * initialisiereSpielDaten:
     * Diese Methode verteilt die Bomben auf den Feldern. Bomben werden durch den Wert: -1 symbolisiert.
     * Die Methode erzeugt eine Zufallszahl für x und y zwischen 1 und 5. Nun überprüft Sie ob auf diesem Feld bereits eine Bombe liegt. Falls ja, setzt Sie i zurück und macht hiermit diese Bombe rückgängig.
     * Falls nicht, übergibt Sie "die Bombe" also den Wert -1 an die SpielBrettDaten und speichert ihn damit sozusagen für das Spiel ab. Dies tut Sie fünf mal.
     * Zuerst setzt Sie aber alle auf -2 um Fehler zu vermeiden.
   */
  public void initialisiereSpielDaten(int minenAnzahl) {
    System.out.println (minenAnzahl);
    int x;    
    int y;
    
    this.anzahlMinen=minenAnzahl;
    for (int i = 0; i < spielBrettDaten.length; i++) {
      for (int j = 0; j < spielBrettDaten[i].length; j++) {
        spielBrettDaten[i][j]=-2;
      }
    }
    
    for (int i = 0; i < minenAnzahl; i++) {
      x = (int)(Math.random()*5);
      y = (int)(Math.random()*5);      
      if (spielBrettDaten[x][y]==-1) {
        //Feld gefüllt mit Bombe und auslassen
        i--;
      } else {
        spielBrettDaten[x][y]=-1;
        System.out.println("Vielleicht liegt hier eine Bombe: "+x+"|"+y);
      } // end of if-else
      
    }      
  }
  public int leseAnzahlMinen() {
    return anzahlMinen;
  }
  
  public int leseDatenfeld(int x, int y) {
    return spielBrettDaten[x][y];
  }
  
  public void schreibeFeldDaten(int x, int y, int wert) {
    spielBrettDaten[x][y]=wert;
  }
  
  public int bestimmteAnzAufgedeckteFelder() {
    int zaehler=0;
    for (int i = 0; i < spielBrettDaten.length; i++) {
      for (int j = 0; j < spielBrettDaten[i].length; j++) {
        if (spielBrettDaten[i][j]>=0) {
          zaehler++;
        } // end of if
      }
    }
    return zaehler;
  }
  
  public int getAnzahlMinen() {
    return anzahlMinen;
  }
  
  public void setAnzahlMinen(int anzahlMinen) {
    this.anzahlMinen = anzahlMinen;
  }
  
  // Ende Methoden
} // end of SpielDaten}
