
import java.io.*;
import java.text.*;
import javax.swing.*;
import java.util.*; 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import static java.lang.Character.isLetter;

// OBS du skall ta bort fyra "//" och lägga till två "//" nedan
/**
 * Detta är en frame varmed man kan testa olika sorterade samlingar.
 * Via framen kan man välja en textfil, för vilken man kan skapa
 * en referenslista. Referenslistan beräknas i form av en map,
 * som har utökats med en räknare för antalet jämförelser som
 * behövs av de ord som ingår i texten. Efter beräkningen
 * presenteras antalet jämförelser av nycklar som behövdes, den tid
 * det tog och hela referenslistan.
 * @author EH
 * @version (2017) bla findRefs flyttad hit
 */
public class TestFrame extends JFrame implements ActionListener {

	Scanner textfil = new Scanner( System.in );
	File indata = null;
	JFileChooser texten = new JFileChooser(".");

  
	CollectionWithGet<TestMapWithCounter.TestMapEntry<String,List<Integer>>>
/* ########## ########## ########## ########## ##########  */ 
/* ## TAG BORT kommentarna på de 2 följande raderna när ni skrivit era samlingar */
		containerSLC   = new SLCWithGet<TestMapWithCounter.TestMapEntry<String, List<Integer>>>(),
		containerSplay = new SplayWithGet<TestMapWithCounter.TestMapEntry<String, List<Integer>>>(),
		containerBST   = new BSTwithGet<TestMapWithCounter.TestMapEntry<String, List<Integer>>>(),
		containerAVL   = new AVLwithGet<TestMapWithCounter.TestMapEntry<String, List<Integer>>>();


/*  ########## ########## ########## ########## ##########  */  
/* ###### LÄGG TILL kommentarer på de 2 följande raderna när ni skrivit era samlingar */
	//TestMapWithCounter<String,List<Integer>> slcMap = null;
	//TestMapWithCounter<String,List<Integer>> splayMap = null;

	TestMapWithCounter<String,List<Integer>>
/*  ########## ########## ########## ########## ##########  */  
/* ###### TAG BORT kommentarna på de 2 följande raderna när ni skrivit era samlingar */
		slcMap = new TestMapWithCounter<String,List<Integer>>(containerSLC),
		splayMap = new TestMapWithCounter<String,List<Integer>>(containerSplay),
	
		bstMap = new TestMapWithCounter<String,List<Integer>>(containerBST),
		avlMap = new TestMapWithCounter<String,List<Integer>>(containerAVL);

	TestMapWithCounter<String,List<Integer>>  map =  bstMap;

	JRadioButton bst   = new JRadioButton("BST",true);   // preselect see line 56
	JRadioButton avl   = new JRadioButton("AVL",false);
	JRadioButton list  = new JRadioButton("SLC",false); 
	JRadioButton splay = new JRadioButton("Splay",false);
  

	JButton test     = new JButton(" Choose file to test");
	JButton berakna  = new JButton(" Compute refs "); 
	JLabel  filename = new JLabel(" No file choosen "); //
	JLabel  antjfr   = new JLabel("--", JLabel.CENTER);
	JLabel  millisec = new JLabel("--", JLabel.CENTER);

	DefaultListModel listModel = new DefaultListModel();
	JList            ordlistan = new JList(listModel);
	JScrollPane      delList   = new JScrollPane(ordlistan);
	NumberFormat     nf        = NumberFormat.getInstance(Locale.UK);

	class RadioLyssnare implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource() == list )
				map = slcMap;
			else if ( e.getSource() == bst )
				map = bstMap;
			else if ( e.getSource() == avl )
				map = avlMap;
			else  
				map = splayMap;
		}
	}
	// The only Constructor
	public TestFrame() {
		
		setLocation(50,50);
		nf.setGroupingUsed(true);
		nf.setMaximumFractionDigits(2);
		setDefaultCloseOperation( EXIT_ON_CLOSE );

		// Create a panel with a multiple-exclusion scope for a set of 4 buttons.
		ButtonGroup radiogrupp = new ButtonGroup();
		radiogrupp.add(bst);
		radiogrupp.add(avl);
		radiogrupp.add(list);
		radiogrupp.add(splay);
		RadioLyssnare rl = new RadioLyssnare();
		list.addActionListener(rl);
		bst.addActionListener(rl);
		avl.addActionListener(rl);
		splay.addActionListener(rl);
		JPanel radioknapparna = new JPanel(new GridLayout(1,4));
		radioknapparna.setPreferredSize(new Dimension(450,50)); // min size on height
		radioknapparna.add(bst);
		radioknapparna.add(avl);
		radioknapparna.add(list);
		radioknapparna.add(splay);
		
		JPanel knapparna  = new JPanel(new GridLayout(2,1)); // panel for choose file & compute buttons
		JPanel resultat   = new JPanel(new GridLayout(3,2)); // panel for rsults
		JPanel both       = new JPanel(new BorderLayout());  // panel for both above
		JPanel ioResultat = new JPanel(new BorderLayout());  // panel for above + radioknapparna
		ioResultat.add( radioknapparna, BorderLayout.NORTH); // a mess because we want listModel to resize
		ioResultat.add( both, BorderLayout.CENTER );
		add(ioResultat, BorderLayout.NORTH);
		both.add( knapparna, BorderLayout.WEST );
		both.add( resultat, BorderLayout.EAST );
		knapparna.add(test);
		knapparna.add(berakna);
		resultat.add(filename);
		resultat.add(new JLabel("    ", JLabel.CENTER));
		resultat.add(new JLabel("Nmb of comparisons", JLabel.CENTER));
		resultat.add(new JLabel("Nbr of millisek", JLabel.CENTER));
		resultat.add(antjfr);
		resultat.add(millisec);
		
		test.addActionListener(this);
		test.setForeground(Color.red);  // pen Color
		test.setPreferredSize(new Dimension(100,50)); // min size on buttons
		berakna.addActionListener(this);
		berakna.setForeground(Color.red);

		listModel.addElement(" Välj någon av filerna aTEXTx.txt 1<=x<=5, genom att " );
		listModel.addElement(" klicka ovan och klicka sedan på \"Compute refs.\" " );
		listModel.addElement("     " );
		listModel.addElement(" Eller klicka på Beräkna skriv sedan in " );
		listModel.addElement(" text via teminalrutan och avsluta med " );
		listModel.addElement(" <CTRL>-d på ny rad" );
    	delList.setPreferredSize(new Dimension(30,300)); // 300 min size on height
		add(delList, BorderLayout.CENTER ); // make this one resize on resize drag
		pack(); //
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == test) {
			if (textfil != null) {
				textfil.close(); // close std.input
			}
			int retVal = texten.showOpenDialog(this);
			if (retVal == JFileChooser.APPROVE_OPTION ) {
				indata = texten.getSelectedFile();  // file to read
				filename.setText(" Testfilen: " + indata.getName());
				antjfr.setText( "--" );
				millisec.setText( "--" );
			} else {
				System.err.println("retVal = " + retVal);
			}
		} else if ( e.getSource() == berakna ) {
			if (map==null) {
				listModel.clear();
				listModel.addElement("NO Collection implemented!");
				return;
			}
			map.clear();
			try{ 
				if (indata != null )
					textfil = new Scanner(indata);
				map.clear();
				map.resetCounter();
				
				// measure time
				long millis = System.currentTimeMillis();
				//Referenslista.findRefs( textfil, map );
				findRefs( textfil, map );
				millis = System.currentTimeMillis() -millis;
				
				millisec.setText( nf.format(millis) + " ms." );
				listModel.clear();
				antjfr.setText( nf.format(map.getCounter()) + " st." );
				for( Map.Entry<String,List<Integer>> me : map.entrySet() )
					listModel.addElement( me.getKey() + "  " + me.getValue() );
				textfil.close();
			}
			catch( FileNotFoundException fnfe) { 
				listModel.addElement( 
					"File " + (indata != null ? indata.getName() : "System.in" ) 
						+ " could not be opened !!" ); 
			} 
			catch( NullPointerException ex) { 
				listModel.addElement("NO Collection implemented");
			}
		}
	}
	// ========== ========== ========== ==========
	/**
	*  En metod som givet en text konstruerar en ordreferenslista.
	*  För varje ord konstrueras en lista med radnummer för förekomster
	*  av ordet i den givna texten.
	*
	*  @param text Texten skall ges i form av en  </tt Scanner>.
	*  @param map En  </tt Map> som fylls i av metoden.
	*/
	public static void findRefs( Scanner text, Map<String, List<Integer>> map) {
		int radNo = 0;
		while ( text.hasNextLine()) {
			String rad = text.nextLine();
			radNo++;
			int pos = 0;
			while ( pos < rad.length() ) {
				char c = rad.charAt(pos);
				if (isLetter(c)) {
					int start = pos;
					while (pos < rad.length() && isLetter(rad.charAt(pos)))
						pos++;
					String ord = rad.substring(start,pos);
					List<Integer> li = map.get(ord);
					if ( li == null ) {
						li = new ArrayList<Integer>();
						li.add(radNo);
						map.put(ord,li);
					}
					else
						li.add(radNo);
				}
				pos++;
			}
		}
	}
	// ========== ========== ========== ==========

	public static void main(String[] args) {
		new TestFrame();
	}
}
