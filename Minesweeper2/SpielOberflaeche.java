import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/** Represents an employee.
 * @author Edmund Willusch
 * @version 1.0.1
 * @since 1.0
*/
/** Erstellt die Oberfläche und verändert diese bei Bedarf
*/
public class SpielOberflaeche extends JFrame {
  // Anfang Attribute
  private SpielSteuerung dieSteuerung;
  private JPanel spielBrett = new JPanel(null,true);
  private Button but_SpielStart = new Button();
  private Button but_SpielBeenden = new Button();
  private Button but_Hilfe = new Button();
  private javax.swing.JButton[][] jButton = new javax.swing.JButton[5] [5];
  protected ImageIcon hilfe = new ImageIcon(this.getClass().getResource("./hilfe.png"));
  protected ImageIcon winning = new ImageIcon(this.getClass().getResource("./winning.gif"));
  protected ImageIcon lost = new ImageIcon(this.getClass().getResource("./lost.gif"));
  protected ImageIcon imgBomb = new ImageIcon(this.getClass().getResource("./bombe.png"));
  //icon alegen
  protected static final int SIZE = 5;
  
  //private JPanel spielBrett = new JPanel(null, true);
  // Ende Attribute
  
  public SpielOberflaeche(String title) { 
    // Frame-Initialisierung
    super(title);
    dieSteuerung=new SpielSteuerung(this);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 662; 
    int frameHeight = 597;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    spielBrett.setLayout( new java.awt.GridLayout( 5, 5 ) );
    
    // Anfang Komponenten
    
    but_SpielStart.setBounds(152, 464, 75, 25);
    but_SpielStart.setLabel("Neues Spiel");
    but_SpielStart.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        but_SpielStart_ActionPerformed(evt);
      }
    });
    cp.add(but_SpielStart);
    but_SpielBeenden.setBounds(440, 464, 75, 25);
    but_SpielBeenden.setLabel("Beenden");
    but_SpielBeenden.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        but_SpielBeenden_ActionPerformed(evt);
      }
    });
    cp.add(but_SpielBeenden);
    but_Hilfe.setBounds(294, 464, 75, 25);
    but_Hilfe.setLabel("Tutorial");
    but_Hilfe.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        but_Hilfe_ActionPerformed(evt);
      }
    });
    cp.add(but_Hilfe);
    ButtonListener bl = new ButtonListener();
    for ( int i = 0; i<jButton.length; i++ ) {
      for (int j=0;j<jButton[i].length;j++) {
        jButton[i][j] = new javax.swing.JButton ();
        jButton[i][j].addActionListener ( bl );
        spielBrett.add ( jButton[i][j] );
      } // end of for
    }
    
    
    spielBrett.setBounds(16, 16, 612, 428);
    //spielBrett.setOpaque(false);
    cp.add(spielBrett);
    // Ende Komponenten
    
    setVisible(true);
    this.erzeugeSpiel();
  } // end of public SpielOberflaeche
  
  // Anfang Methoden
  public void erzeugeSpiel() {
    dieSteuerung.starteNeuesSpiel(5);
  }
  
  public void gedruecktNeuesSpiel() {
    loescheSpielbrett();
    erzeugeSpiel();
  }
  
  public void gedruecktBeendeSpiel() {
    System.exit (0);
  }
  
  public void gedruecktHilfe() {
    JOptionPane.showMessageDialog(null,
      "Du musst alle Bomben finden. Felder die an Bomben angrenzen, zeigen dir mit ihrer Zahl, an wie viele Bomben Sie angrenzen. Viel Glück",
      "Tutorial",             
      JOptionPane.WARNING_MESSAGE,hilfe);
  }
  
  public void mausClickAufFeld(int x, int y) {
    dieSteuerung.spieleZug(x,y);
  }
  
  public void loescheSpielbrett() {
    for (int i = 0; i < jButton.length; i++) {
      for (int j = 0; j < jButton[i].length; j++) {
        jButton[i][j].setText("");
        jButton[i][j].setIcon(null);
      }
    }
  }
  
  public void schreibeAufFeld(int x, int y, String zeichen) {    
    if (Integer.parseInt(zeichen)==-1) {
      System.out.println("Verloren");
      jButton[x][y].setIcon(imgBomb);
      //loescheSpielbrett();
    } else {
      jButton[x][y].setText(zeichen);
    }                                   
  }
  
  public void zeigeMeldung(int nr) {
    if (nr==1) {
      JOptionPane.showMessageDialog(null,
      "Cool du hast gewonnen, fühlst dich jetzt etwa geil ?",
      "Sieg",               
      JOptionPane.WARNING_MESSAGE,winning);
    } 
    else if (nr==2) {
      JOptionPane.showMessageDialog(null,
      "Verloren, für die Leistung zieht sich niemand aus",
      "Verloren",             
      JOptionPane.WARNING_MESSAGE,lost);
    }
    else if (nr==3) {
      /*JOptionPane.showMessageDialog(null,
      "Das Spiel ist beendet",
      "Exit",               
      JOptionPane.WARNING_MESSAGE);*/
    }
    else if (nr>=4) {
      JOptionPane.showMessageDialog(null,
      "Spiel mit "+nr+" Minen gestartet",
      "Minen",               
      JOptionPane.WARNING_MESSAGE);
    }// end of if-else    
  }
  
  public void but_SpielStart_ActionPerformed(ActionEvent evt) {
    gedruecktNeuesSpiel();
  } // end of but_SpielStart_ActionPerformed
  
  public void but_SpielBeenden_ActionPerformed(ActionEvent evt) {
    gedruecktBeendeSpiel();
  } // end of but_SpielBeenden_ActionPerformed
  
  public void but_Hilfe_ActionPerformed(ActionEvent evt) {
    gedruecktHilfe();
  } // end of but_SpielBeenden_ActionPerformed
  
  public static void main(String[] args) {
    new SpielOberflaeche("SpielOberflaeche");
  } // end of main
  ////////////////////////////////////////////
  
  class ButtonListener implements java.awt.event.ActionListener {
    public void actionPerformed(java.awt.event.ActionEvent e) {
      for (int i=0; i<jButton.length; i++) {
        for (int j=0;j<jButton[i].length;j++ ) {
          if( e.getSource() == jButton[i][j] ){
            System.out.println("JButton" + (i+"|"+j) + " wurde geklickt.");
            mausClickAufFeld(i,j);
          }
        } // end of for
      }
    }
  }
  
  public String gibButton(int x, int y){
    return jButton[x][y].getText(); 
  }
  
} // end of class SpielOberflaeche
