package pop.dialog;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import main.Handler;
import main.Start;

public class NewNode extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean leaf = true;
	private JPanel namePanel;
	JTextField nameField;
	private JPanel columPanel;
	private JTextField columField;
	private ActionListener actionHandler;
	private JTextField rowField;
	private JPanel rowPanel;
	private Handler handle;
	private JDialog self ;
	public NewNode( Handler h ){
		super( (Frame)null, "CreateNewNode");
		handle = h;
		self = this;
		setLayout( new GridLayout(5, 1 ) );
		actionHandler = new ActionHandler();
		
		JRadioButton branchRB = new JRadioButton("Branch");
		branchRB.setSelected(true);
		branchRB.addActionListener(actionHandler);
		JRadioButton leafRB = new JRadioButton("Leaf");
		leafRB.addActionListener(actionHandler);
		ButtonGroup group = new ButtonGroup();
		group.add(branchRB);
		group.add(leafRB);
		JPanel type = new JPanel();
		type.add(branchRB);
		type.add(leafRB);
		add( type );
		
		JLabel nameLabel = new JLabel("Name");
		nameField = new JTextField(20);
		namePanel = new JPanel();
		namePanel.add( nameLabel );
		namePanel.add( nameField );
		add( namePanel );
		
		JLabel columLabel = new JLabel("Colum");
		columField = new JTextField(20);
		columPanel = new JPanel();
		columPanel.add(columLabel);
		columPanel.add(columField);
		add( columPanel );
		
		JLabel rowLabel = new JLabel("Rows");
		rowField = new JTextField(20);
		rowPanel = new JPanel();
		rowPanel.add(rowLabel);
		rowPanel.add(rowField);
		add( rowPanel );
		
		JButton yesBtn = new JButton("Yes");
		JButton cancelBtn = new JButton("Cancel");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(yesBtn);
		buttonPanel.add(cancelBtn);
		add( buttonPanel );
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public boolean isLeaf(){
		return leaf;
	}
	public String getName(){
		return nameField.getText().trim();
	}

	public int getColum(){
		if( !columField.isEditable() )return -1 ;
		String value = nameField.getText().trim();
		int colum = 0;
		try{
			colum = Integer.parseInt(value);
		}catch( Exception e){
			return -2;
		}
		return colum;
	}
	public int getRow(){
		if( !columField.isEditable() )return -1 ;
		String value = nameField.getText().trim();
		int colum = 0;
		try{
			colum = Integer.parseInt(value);
		}catch( Exception e){
			return -2;
		}
		return colum;
	}
	class ActionHandler implements ActionListener{


		public void actionPerformed(ActionEvent e) {
			AbstractButton source = (AbstractButton) e.getSource();
			switch( source.getText() ){
			case "Branch":
				leaf =false;
				columField.setEditable(leaf);
				rowField.setEnabled(leaf);
				break;
			case "Leaf":
				leaf = true;
				columField.setEditable(leaf);
				rowField.setEnabled(leaf);
				break;
			case "Yes":
				handle.ActionPerformed(source);
				self.dispose();
				break;
			case "Cancel":
				self.dispose();
				break;
				default:
			}
		}
		
	}
}

