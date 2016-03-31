package core;
import java.util.ArrayList;
import java.util.List;


public class GnNode {
	GnNode parent;
	
	GnNode next;
	List<GnNode> nxt;       
	
	int stoneProp;
	int mov;
	
	List<Integer> addblacks;
	List<Integer> addwhites;
	
	List<Integer> labels;
	
	String comment;
	String nodename;
	
	private void init(GnNode par) {
		parent = par;
		stoneProp = 0;
		mov = -1;
		nxt = null;
		addblacks = addwhites = null;
		labels = null;
	}
	
	public GnNode(GnNode par) {
		init(par);
	}
	
	public void insertNextNode(GnNode tmpnxt) {
		tmpnxt.parent = this;
		if (nxt == null) {
			nxt = new ArrayList<GnNode>();
		}
		nxt.add(tmpnxt);
	}
	
	public void insertAddStones(List<Integer> tmpcoors, int colour) {
		if (colour == 1) { // black stones
			if (addblacks == null) {
				addblacks = new ArrayList<Integer>();
			}
			addblacks.addAll(tmpcoors);
		}
		else {
			if (addwhites == null) {
				addwhites = new ArrayList<Integer>();
			}
			addwhites.addAll(tmpcoors);
		}
	}
	
	public void insertLabels(List<Integer> tmplab) {
		if (labels == null) {
			labels = new ArrayList<Integer>();
		}
		labels.addAll(tmplab);
	}
	
	public void insertComment(String tmpcomment) {
		comment = tmpcomment;
	}
	
	public void insertNodename(String tmpnodename) {
		nodename = tmpnodename;
	}
	
	public static void main(String args[]) {
		System.out.println("Hello, world! ");
	}
	
	public void printbase() {
		System.out.print("[" + mov + ", " + stoneProp + "]");
	}
};