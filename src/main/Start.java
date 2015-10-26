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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pop.dialog.EditNode;
import tree.node.Info;
import tree.node.LeafMap;
import tree.node.MyTreeNode;
import tree.node.MyTreeNodeListener;

public class Start extends JFrame implements Handler{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTree templateTree;
	private MyTreeNode templateRoot;
	private JTree sourceTree;
	private MyTreeNode sourceRoot;
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
		
		templateRoot = new MyTreeNode(new Info( "Template", false ) );
		templateTree = new JTree(templateRoot);
		templateTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		createTree( "src/templateTree.xml", templateRoot );
		JScrollPane templatePane = new JScrollPane( templateTree );
		
		sourceRoot = new MyTreeNode(new Info( "Source", false ));
		sourceTree = new JTree(sourceRoot);
		sourceTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		sourceTree.addTreeSelectionListener( new MyTreeNodeListener() );
		sourceTree.addMouseListener( new MyMouseListener() );
		createTree("src/sourceTree.xml", sourceRoot );
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

	private void createTree( String path, MyTreeNode root ){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			File source = new File(path);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(source);
			Element book = doc.getDocumentElement();
			iteratorCreateNode( root, book );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void iteratorCreateNode( MyTreeNode root, Node info ){
		MyTreeNode treeNode = null;
		NodeList subInfos = info.getChildNodes();
		for( int i = 0; i < subInfos.getLength(); i++ ){
			Node node = subInfos.item(i);
			if( node.getNodeType() == Node.ELEMENT_NODE ){
				treeNode = createTreeNode(root, node );
				iteratorCreateNode( treeNode, node );
			}	
		}
	}
	private MyTreeNode createTreeNode( MyTreeNode parent, Object info ){
		MyTreeNode child = new MyTreeNode(info);
		parent.add( child );
		return child;
	}
	
	
	public static void main( String[] args ){
		new Start();
		
	}

	@Override
	public void ActionPerformed( Object source) {
		EditNode dialog = (EditNode) source;
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
		MyTreeNode parent = (MyTreeNode) path.getLastPathComponent();
		createTreeNode(parent, info );
	}
	class MyMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			JTree source= (JTree) e.getSource();
			int selRow = source.getRowForLocation( e.getX(), e.getY() );
			TreePath path = source.getPathForLocation(e.getX(), e.getY());
			if( selRow < 0 )return ;
			if( e.getButton() == MouseEvent.BUTTON3 ){
				new EditNode(self);
			}
		}
	}

	class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			MyTreeNode s = null;
			MyTreeNode t = null;
			AbstractButton btn = (AbstractButton) e.getSource();
			System.out.println( btn.getText() );
			switch( btn.getText() ){
			case "=":
				s = (MyTreeNode) sourceTree.getLastSelectedPathComponent();
				t = (MyTreeNode) templateTree.getLastSelectedPathComponent();
				model.addElement( new LeafMap( s,"=", t )) ;
				break;
			case "<":
				s = (MyTreeNode) sourceTree.getLastSelectedPathComponent();
				t = (MyTreeNode) templateTree.getLastSelectedPathComponent();
				model.addElement( new LeafMap( s,"<", t )) ;
				break;
			case ">":
				s = (MyTreeNode) sourceTree.getLastSelectedPathComponent();
				t = (MyTreeNode) templateTree.getLastSelectedPathComponent();
				model.addElement( new LeafMap( s,">", t )) ;
				break;
			case "Remove":
				int index = mapList.getSelectedIndex();
				if( index > 0 ){
					model.remove(index);
				}
				break;
				default:
			}
			
		}
		
	}
}
