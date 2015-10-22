package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import pop.dialog.NewNode;
import tree.node.Info;
import tree.node.LeafMap;

public class Start extends JFrame implements Handler{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTree templateTree;
	private DefaultMutableTreeNode templateRoot;
	private JTree sourceTree;
	private DefaultMutableTreeNode sourceRoot;
	private JPanel btnPanel;
	private JButton equalBtn;
	
	private JTree clicked;
	
	private Handler self;
	
	private ButtonHandler btnHandler;
	private DefaultListModel<LeafMap> model;
	private JList<LeafMap> mapList;
	private JButton removeBtn;
	private JButton saveBtn;
	private JButton bigBtn;
	private JButton smallBtn;
	public Start(){
		super("metadata mapping");
		this.self = this;
		btnHandler = new ButtonHandler();
		this.setLayout( new GridBagLayout() );
		
		templateRoot = new DefaultMutableTreeNode(new Info( "Template", false ) );
		templateTree = new JTree(templateRoot);
		templateTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		createTree( "src/templateTree.txt", templateRoot );
		JScrollPane templatePane = new JScrollPane( templateTree );
		
		sourceRoot = new DefaultMutableTreeNode(new Info( "Source", false ));
		sourceTree = new JTree(sourceRoot);
		sourceTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		createTree("src/sourceTree.txt", sourceRoot );
		JScrollPane sourcePane = new JScrollPane(sourceTree);
		
		btnPanel = new JPanel();
		btnPanel.setLayout(new GridBagLayout() );
		equalBtn = new JButton("=");
		equalBtn.addActionListener(btnHandler);
		bigBtn = new JButton(">");
		bigBtn.addActionListener(btnHandler);
		smallBtn = new JButton("<");
		smallBtn.addActionListener(btnHandler);
		btnPanel.add( new JPanel(), new GridBagConstraints( 0, 0, 100, 40, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0),
				0 , 0 ));
		btnPanel.add( equalBtn,     new GridBagConstraints( 0, 40, 100, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0),
				0 , 0 ));
		btnPanel.add( smallBtn,     new GridBagConstraints( 0, 50, 100, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0),
				0 , 0 ));
		btnPanel.add( bigBtn,     new GridBagConstraints( 0, 60, 100, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0),
				0 , 0 ));
		btnPanel.add( new JPanel(), new GridBagConstraints( 0, 70, 100, 30, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0),
				0 , 0 ));
		
		JPanel topPane = new JPanel();
		topPane.setLayout(new GridLayout(1, 2));
		//--------change topPane layout
		topPane.setLayout(new GridBagLayout() );
		topPane.add( sourcePane, new GridBagConstraints( 0,0,40,1,0.5,0.5,
				GridBagConstraints.CENTER,
				GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0),
				0,0));
		topPane.add( btnPanel, new GridBagConstraints( 40,0,20,1,0,0,
				GridBagConstraints.CENTER,
				GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0),
				0,0));
		topPane.add( templatePane, new GridBagConstraints( 60,0,40,1,0.5,0.5,
				GridBagConstraints.CENTER,
				GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0),
				0,0));
		
		add(topPane, new GridBagConstraints( 0, 0, 100, 70, 1, 0.7,
				GridBagConstraints.CENTER,
				GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0),
				0, 0 ) );
		
		model = new DefaultListModel<>();
		mapList = new JList<LeafMap>(model);
		JScrollPane mapPane = new JScrollPane(mapList);
		removeBtn = new JButton("Remove");
		removeBtn.addActionListener(btnHandler);
		saveBtn = new JButton("Save");
		JMenuBar btnsBar = new JMenuBar();
		FlowLayout btnsBarlayout = new FlowLayout();
		btnsBarlayout.setAlignment(FlowLayout.CENTER);
		btnsBar.setLayout(btnsBarlayout);
		btnsBar.add(saveBtn);
		btnsBar.add(removeBtn);
		JPanel bottom = new JPanel();
		bottom.setLayout( new BorderLayout() );
		bottom.add(mapPane, BorderLayout.CENTER);
		bottom.add( btnsBar, BorderLayout.SOUTH );
		add(bottom, new GridBagConstraints(0, 70, 100, 30, 1, 0.3, 
				GridBagConstraints.CENTER, 
				GridBagConstraints.BOTH, 
				new Insets(0, 0, 0, 0),
				0, 0 ) );
		
		setSize( 600, 600 );
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private DefaultMutableTreeNode getTreeNode( DefaultMutableTreeNode root, String name ){
		Info info = (Info) root.getUserObject();
		if( info.getName().equals(name) )return root;
		DefaultMutableTreeNode child = null, value = null;
		Enumeration<DefaultMutableTreeNode> children = root.children(); 
		while( children.hasMoreElements() ){
			child = children.nextElement();
			value = getTreeNode(child, name);
			if( value != null )return value;
		}
		return null;
	}
	private void createTree( String path, DefaultMutableTreeNode root ){
		
		int index = 0;
		Info info = null;
		String value = null;
		String[] childs = null;
		DefaultMutableTreeNode parent;
		try {
			File tree = new File(path);
			Reader reader = new FileReader( tree );
			@SuppressWarnings("resource")
			BufferedReader buffered = new BufferedReader( reader );
			while( (value = buffered.readLine()) != null ){
				index = value.indexOf(':');
				if( index <= 0 )continue;
				parent = getTreeNode(root, value.substring(0, index ) );
				if( parent == null )break;
				info = (Info) parent.getUserObject();
				info.setLeaf(true);
				value = value.substring(index + 1 ).trim();
				childs = value.split(" ");
				for( int i = 0; i < childs.length; i++ ){
					createTreeNode(parent, new Info( childs[i], i, 0,  true ) );
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	private DefaultMutableTreeNode createTreeNode( DefaultMutableTreeNode parent, Info info ){
		DefaultMutableTreeNode child = new DefaultMutableTreeNode(info);
		parent.add( child );
		return child;
	}
	
	
	public static void main( String[] args ){
		new Start();
		
	}

	@Override
	public void ActionPerformed( Object source) {
		NewNode dialog = (NewNode) source;
		String name = dialog.getName();
		if( name.equals("") ){
			JOptionPane.showMessageDialog(null, "Name is blank");
			return ;
		}
		int colum = dialog.getColum();
		if( colum == -2 ){
			JOptionPane.showMessageDialog(null, "Colum is blank");
			return ;
		}
		
		Info info = new Info(name, colum, dialog.getRow(), dialog.isLeaf() );

		TreePath path = clicked.getSelectionPath();
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) path.getLastPathComponent();
		createTreeNode(parent, info );
	}
	
	class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			DefaultMutableTreeNode s = null;
			DefaultMutableTreeNode t = null;
			AbstractButton btn = (AbstractButton) e.getSource();
			System.out.println( btn.getText() );
			switch( btn.getText() ){
			case "=":
				s = (DefaultMutableTreeNode) sourceTree.getLastSelectedPathComponent();
				t = (DefaultMutableTreeNode) templateTree.getLastSelectedPathComponent();
				model.addElement( new LeafMap( s,"=", t )) ;
				break;
			case "<":
				s = (DefaultMutableTreeNode) sourceTree.getLastSelectedPathComponent();
				t = (DefaultMutableTreeNode) templateTree.getLastSelectedPathComponent();
				model.addElement( new LeafMap( s,"<", t )) ;
				break;
			case ">":
				s = (DefaultMutableTreeNode) sourceTree.getLastSelectedPathComponent();
				t = (DefaultMutableTreeNode) templateTree.getLastSelectedPathComponent();
				model.addElement( new LeafMap( s,">", t )) ;
				break;
			case "Remove":
				int index = mapList.getSelectedIndex();
				model.remove(index);
				break;
				default:
			}
			
		}
		
	}
}
