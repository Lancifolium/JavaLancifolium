package core;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class JavaLancifolium {
	int siz; // ��PҎ��

	GnNode root; // �����c

	Reader filebuff;
	int reader;

	GnNode curNode; // ��ǰ���cָ�
	GnNode tmpNode; // �R�rָ�
	Stack<GnNode> branchStack; // ��֧��
	
	public JavaLancifolium() {
		root = null;
		filebuff = null;
		curNode = tmpNode = null;
		branchStack = null;
	}
	
	public int iswhite(char tmpc) {
		if (tmpc == ' ' || tmpc == '\n' || tmpc == '\t' || tmpc == 13) return 1;
		return 0;
	}
	
	public int openfile(String filename) throws FileNotFoundException {
		File fil = new File(filename);
		if (!fil.exists()) return 0;
		filebuff = new FileReader(fil);
		return 1;
	}

	public void dealSize() throws IOException {
		reader = filebuff.read();
		int tmpsize;
		tmpsize = reader;
		reader = filebuff.read();
		if (Character.isDigit((char)reader)) {
			tmpsize = tmpsize * 10 + reader;
		}
		if (tmpsize < 4) tmpsize = 4;
		if (tmpsize > 26) tmpsize = 26;
		siz = tmpsize;
		while ((char)reader != ']') {
			reader = filebuff.read();
		}
		reader = filebuff.read();
	}
	
	public int dealAddStones(GnNode tmpnode, int colour) throws Exception {
		int tmpx, tmpy;
		List<Integer> coorbuff = new ArrayList<Integer>(); // ��������

		if (colour == 0) return 0;
		else if (colour != 1) colour = 2;

		while (reader == '[') {
			tmpx = Character.toUpperCase(filebuff.read()) - 'A';
			tmpy = Character.toUpperCase(filebuff.read()) - 'A';
			if ((tmpx >= 0) && (tmpx < siz) && (tmpy >= 0) && (tmpy < siz)) {
				coorbuff.add(tmpx * 100 + tmpy);
			}
			reader = filebuff.read(); // ����']'
			reader = filebuff.read(); // '['
		}
		tmpnode.insertAddStones(coorbuff, colour);
		return 1;
	} // finished dealAddStones
	
	public void dealMove(GnNode tmpnode, int colour) throws Exception {
		int tmpx, tmpy;
		reader = Character.toUpperCase((char)filebuff.read());
		while (!Character.isAlphabetic(reader) && reader != -1) {
			reader = Character.toUpperCase((char)filebuff.read());
		}
		tmpx = reader - 'A';
		reader = Character.toUpperCase((char)filebuff.read());
		tmpy = reader - 'A';
		if (reader == -1) return;
		System.out.println("=============" + tmpx + " " + tmpy);
		tmpnode.mov = tmpx * 100 + tmpy;
		tmpnode.stoneProp = colour;
		reader = filebuff.read();
		reader = filebuff.read();
	}
	
	public void dealCommentNodename(GnNode tmpnode, int tmpkind) throws Exception {
		String buff = "";
		char tmpsave = '\0';
		reader = filebuff.read();
		while (reader != ')' && tmpsave == '\\') {
			if (reader == 'n') buff.replace(buff.charAt(buff.length() - 1), '\n');
			else if (reader == 't') buff.replace(buff.charAt(buff.length() - 1), '\t');
			else if (reader == '[' || reader == ']' || reader == '\\')
				buff.replace(buff.charAt(buff.length() - 1), (char)reader);
			else buff = buff + (char)reader;
			reader = filebuff.read();
		}
		if (tmpkind == 1) tmpnode.insertComment(buff);
		else tmpnode.insertNodename(buff);
	}
	
	public void dealLabels(GnNode tmpnode, int form) throws Exception {
		char tmpform;
		int tmpi, tmpval;
		List<Integer> tmplab = new ArrayList<Integer>();

		while (reader == '[') {
			tmpval = (Character.toUpperCase(filebuff.read()) - 'A') * 100;
			tmpval += Character.toUpperCase(filebuff.read()) - 'A';
			reader = filebuff.read();
			reader = filebuff.read();
			tmplab.add(tmpval);
		}

		switch (form) {
		case 0: // ��ĸ�˻`
			for (tmpi = 0; tmpi < tmplab.size(); tmpi++) {
				tmplab.set(tmpi, tmplab.get(tmpi) + ('A' + tmpi) * 10000);
			}
			break;
		case 1: tmpform = 1;
		case 2: tmpform = 2;
		case 3: tmpform = 3;
		case 4: tmpform = 4;
			for (tmpi = 0; tmpi < tmplab.size(); tmpi++) {
				tmplab.set(tmpi, tmplab.get(tmpi) + tmpform * 10000);
			}
			break;
		default: break;
		}
		tmpnode.insertLabels(tmplab);
	}
	
	public void configNode() throws Exception {
		String operate;
		reader = filebuff.read(); // ����';' ʼ��';'���K��';', '(', ')'

		while ((reader != ';') && (reader != '(') && (reader != ')')) { // ����
			while (iswhite((char)reader) == 1) reader = filebuff.read(); // �հ�
			operate = "";
			while (Character.isAlphabetic(reader)) { // ������ĸ�ĕr��
				operate += Character.toUpperCase((char)reader);
				reader = filebuff.read();
			}
			while (reader != '[' && reader != -1) reader = filebuff.read(); // �ҵ�'['
			if (reader == -1) return; // EOF
			System.out.println(operate); ///////////////

			if (operate.equals("LB")) dealLabels(curNode, 0); // ��ĸ 0
			else if (operate.equals("TR")) dealLabels(curNode, 1); // ���� 1
			else if (operate.equals("SQ")) dealLabels(curNode, 2); // ���K 2
			else if (operate.equals("MA")) dealLabels(curNode, 3); // �� 3
			else if (operate.equals("CR")) dealLabels(curNode, 4); // �A 4
			else if (operate.equals("C")) dealCommentNodename(curNode, 1);
			else if (operate.equals("N")) dealCommentNodename(curNode, 2);
			else if (operate.equals("AB")) dealAddStones(curNode, 1);
			else if (operate.equals("AW")) dealAddStones(curNode, 2);
			else if (operate.equals("SZ")) dealSize();
			else if (operate.equals("B")) dealMove(curNode, 1); // ������
			else if (operate.equals("W")) {
				System.out.println("do ifelse"); dealMove(curNode, 2); // ������
				
			}
			else { // ���Ե��
				System.out.println("no usee");
				while (reader != ']' && reader != -1) reader = filebuff.read();
				reader = filebuff.read(); // ����']'
			} // finished if-else

			while (iswhite((char)reader) == 1) reader = filebuff.read(); // ȥ���հף��ز�����
		} // finished while ';' '('
	}
	
	public void configManual(String filename) throws Exception {
		if (openfile(filename) == 0) return; // �ļ��xȡʧ��
		if (root != null) return; // ���V�䲻����
		siz = 19; // ��ʼ��
		root = new GnNode(null);
		branchStack = new Stack<GnNode>();

		reader = filebuff.read();
		while (reader != '(' && reader != -1) reader = filebuff.read(); // �ҵ���һ������̖
		while (reader != ';' && reader != -1) reader = filebuff.read(); // �ҵ���̖
		curNode = root; //
		configNode();

		while (true) { // ̎�����V
			if (reader == ';') { // ';'
				tmpNode = curNode;
				curNode = new GnNode(tmpNode); // �����cָᘂ���
				tmpNode.next = curNode;
				configNode(); //
			}
			else if (reader == '(') { // '('
				tmpNode = curNode;
				curNode = new GnNode(tmpNode);
				tmpNode.insertNextNode(curNode); // �����֧
				branchStack.push(tmpNode); // ��һ��֧���c�뗣
				reader = filebuff.read();
				while ((reader != ';') && (reader != '(') && (reader != ')')
					&& (reader != -1)) reader = filebuff.read();
				if (reader == ';') configNode(); // ���ǹ��c�ĕr��ֱ���O��
			}
			else if (reader == ')') { // ')'
				if (branchStack.empty()) { // �����c��')'���x�V�Y��
					System.out.println("\nFinished Reading Manual. \n");
					break;
				}
				else {
					curNode = branchStack.pop();
					reader = filebuff.read(); // ����')'
					while ((reader != ';') && (reader != '(') && (reader != ')')
						&& (reader != -1)) reader = filebuff.read();
				}
			} // finished if-else ';', '(', ')'.
			if (reader == -1) break; // EOF
		} // finished while true
	} // finished configManual
	
	public void reverse(int deep, GnNode cur) {
		if (cur == null) return;
		cur.printbase();

		if (cur.nxt != null && cur.nxt.size() > 0) {
			deep++;
		}
		reverse(deep, cur.next);
		if (cur.nxt == null) return;
		for (int tmpi = 0; tmpi < cur.nxt.size(); tmpi++) {
			System.out.print("\n");
			for (int tmpj = 0; tmpj < deep; tmpj++) System.out.print("  ");
			System.out.print("|" + deep + "|");
			reverse(deep, cur.nxt.get(tmpi));
		}
		return;
	}
	
	public void printfManual() {
		int deep = 0;
		//System.out.println("_");
		reverse(0, root);
	}
	
	public static void main(String args[]) throws Exception {
		JavaLancifolium jlac = new JavaLancifolium();
		String filename = "D:/Fierralin/OneDrive/Code/PyLancifolium/ttts.sgf";
		jlac.configManual(filename);
		jlac.printfManual();
	}
}
