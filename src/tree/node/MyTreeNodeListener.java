package tree.node;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

public class MyTreeNodeListener implements TreeSelectionListener{

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		System.out.println( e );
	}

}
