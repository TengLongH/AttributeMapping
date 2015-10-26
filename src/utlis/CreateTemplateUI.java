package utlis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

public class CreateTemplateUI extends JFrame {

	private static final long serialVersionUID = 1L;

	ListTooltip tip = new ListTooltip();
	@SuppressWarnings("unchecked")
	public CreateTemplateUI( ){
		super("datatemplate generator");
		setLayout(new GridBagLayout() );
		JPanel cuisePanel = new JPanel();
		cuisePanel.add(new JLabel("Cruise: "));
		JComboBox<String> curiBox =new JComboBox<String>( new String[]{ "22","25","26","27","30"});
		curiBox.setPreferredSize(new Dimension(150, 25 ));
		cuisePanel.add( curiBox );
		JPanel applicant = new JPanel();
		applicant.add(new JLabel("Applicant: ") );
		JComboBox<String> appBox =new JComboBox<String>(new String[]{"ZuoshengYang"});
		appBox.setPreferredSize(new Dimension(150, 25 ));
		applicant.add(appBox);
		
		JPanel choose = new JPanel();
		choose.setLayout( new BorderLayout() );
		choose.add(new JLabel("Please choose analytical indicators:"), BorderLayout.NORTH);
		JList<String> chooseList = new JList<String>( new String[]{
				"Dissolved oxygen",
				"Dissolved oxygen saturation concentration",
				"PH value",
				"EH value",
				"Total alkalinity",
				"Chlorophyll ¶¡ and primary productivity",
				"Turbidity",
				"Salinity",
				"Organic Pollutants in Water",
				"Suspended particulate matte(SPM)",
				"methane",
				"Biomarkers in water"});
		chooseList.addMouseMotionListener(tip);
		choose.add( new JScrollPane( chooseList ), BorderLayout.CENTER );
		JPanel choosen = new JPanel();
		choosen.setLayout(new BorderLayout() );
		choosen.add(new JLabel("analytical indicators have choosen:"), BorderLayout.NORTH);
		JList<String> choosenList = new JList<String>(new String[]{
				"PH value",
				"EH value",
				"Total alkalinity"});
		choosenList.addMouseMotionListener(tip);
		choosen.add( new JScrollPane( choosenList ),BorderLayout.CENTER );
		
		JPanel btnPanel = new JPanel();
		BoxLayout box = new BoxLayout( btnPanel, BoxLayout.Y_AXIS);
		
		btnPanel.setLayout( box );
		btnPanel.add(new JButton(">>>") );
		btnPanel.add(new JButton("<<<") );
		
		
		add( cuisePanel, new GridBagConstraints( 0,0,50,1,0,0,
				GridBagConstraints.CENTER,
				GridBagConstraints.NONE,
				new Insets(20, 0, 20, 0),
				0,0) );
		add( applicant, new GridBagConstraints( 50,0,50,1,0,0,
				GridBagConstraints.CENTER,
				GridBagConstraints.NONE,
				new Insets(20, 0, 20, 0),
				0,0) );
		
		add( choose, new GridBagConstraints( 0,1,40,4,0,0,
				GridBagConstraints.CENTER,
				GridBagConstraints.NONE,
				new Insets(0, 10, 0, 10 ),
				0,0) );
		add( btnPanel, new GridBagConstraints( 40, 1, 20, 4, 0, 0,
				GridBagConstraints.CENTER,
				GridBagConstraints.NONE,
				new Insets(0, 10, 0, 10),
				0,0) );
		add( choosen, new GridBagConstraints( 60,1,40,4,0,0,
				GridBagConstraints.CENTER,
				GridBagConstraints.NONE,
				new Insets(0, 10, 0, 10),
				0,0) );
	
		
		JPanel btn = new JPanel();
		btn.add( new JButton("Output") );
		add( btn, new GridBagConstraints( 0,5,100,1,0,0,
				GridBagConstraints.CENTER,
				GridBagConstraints.NONE,
				new Insets(20, 0, 20, 0),
				0,0) );
	
		pack();
		setVisible(true);
		setLocationRelativeTo(null);

	}
	class ListTooltip implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {}
		@Override
		public void mouseMoved(MouseEvent e) {
			JList<String> list = (JList<String>) e.getSource();
			int index = list.locationToIndex(e.getPoint());
			if( index > -1 ){
				list.setToolTipText("»‹Ω‚—ı±•∫Õ≈®∂»");
			}
			
		}
		
	}
	public static void main(String[] args){
		new CreateTemplateUI();
	}
}
