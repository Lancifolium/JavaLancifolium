package core;
import java.util.Arrays;
import java.util.Stack;

public class GnCalculate {
	public int siz;
	public static char[][] ston = new char[26][26];
	public static char[][] tmpbord = new char[26][26];
	
	public int conflict;
	public int confmove;
	
	public GnCalculate() {
		this(19); // to implement default values
	}
	
	public GnCalculate(int siz) {
		if (siz > 26 || siz < 4) this.siz = 19;
		this.siz = siz;
		this.conflict = this.confmove = 0;
		for (int tmpi = 0; tmpi < 26; tmpi++) { // 臨時棋盤初始化
			Arrays.fill(ston[tmpi], (char)(0));
			Arrays.fill(tmpbord[tmpi], (char)(0));
		}
		System.out.println("GnCalculate's Constructor; ");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GnCalculate gnc = new GnCalculate(); // very important
		
	}
	
	public int[] findStoneBlock(int colour, int tmpcolour,
			int pos, int tmpsiz) {
		int tmpmov, breath, stonnum;
		int tmpx, tmpy;
		Stack<Integer> movStack = new Stack<Integer>(); // Stack here
		movStack.push(pos);
		breath = 0;
		stonnum = 1;
		while (!movStack.isEmpty()) {
			tmpmov = movStack.pop();
			tmpx = tmpmov / 100; tmpy = tmpmov % 100;
			if ((tmpx > 0) && (tmpbord[tmpx - 1][tmpy] != tmpcolour)) {
				if (ston[tmpx - 1][tmpy] == colour) { // 同色
					movStack.push(tmpmov - 100); // 入棧
					tmpbord[tmpx - 1][tmpy] = (char)tmpcolour; // 標記
					stonnum++;
				}
				else if (ston[tmpx - 1][tmpy] == 0) breath++; // 無子，異色不用考慮
			}
			if ((tmpx < tmpsiz) && (tmpbord[tmpx + 1][tmpy] != tmpcolour)) {
				if (ston[tmpx + 1][tmpy] == colour) { // 同色
					movStack.push(tmpmov + 100); // 入棧
					tmpbord[tmpx + 1][tmpy] = (char)tmpcolour; // 標記
					stonnum++;
				}
				else if (ston[tmpx + 1][tmpy] == 0) breath++; // 無子，異色不用考慮
			}
			if ((tmpy > 0) && (tmpbord[tmpx][tmpy - 1] != tmpcolour)) {
				if (ston[tmpx][tmpy - 1] == colour) { // 同色
					movStack.push(tmpmov - 1); // 入棧
					tmpbord[tmpx][tmpy - 1] = (char)tmpcolour; // 標記
					stonnum++;
				}
				else if (ston[tmpx][tmpy - 1] == 0) breath++; // 無子，異色不用考慮
			}
			if ((tmpy < tmpsiz) && (tmpbord[tmpx][tmpy + 1] != tmpcolour)) {
				if (ston[tmpx][tmpy + 1] == colour) { // 同色
					movStack.push(tmpmov + 1); // 入棧
					tmpbord[tmpx][tmpy + 1] = (char)tmpcolour; // 標記
					stonnum++;
				}
				else if (ston[tmpx][tmpy + 1] == 0) breath++; // 無子，異色不用考慮
			}
		}
		int tmpval[] = new int[2];
		tmpval[0] = breath; tmpval[1] = stonnum;
		return tmpval;
	} // finished findStoneBlock
	
	public int configDropStone(int colour, int mov) {
		int tmpx, tmpy;
		tmpx = mov / 100; tmpy = mov % 100; // 落子坐標
		
		if (mov < 0) return 0;
		if (ston[tmpx][tmpy] > 0) return 0;
		if (conflict == 1 && confmove == mov) return 0;
		
		int opcolour;
		int tmpi, tmpj;
		int tmpsiz, lift; // 
		int tmpfind[];
		
		tmpsiz = siz - 1; // 棋盤規則減一
		opcolour = (colour == 1) ? 2 : 1; // 異色棋子顏色
		for (tmpi = 0; tmpi < 26; tmpi++) { // 臨時棋盤初始化
			Arrays.fill(tmpbord[tmpi], (char)(0));
		}
		tmpbord[tmpx][tmpy] = 3; // 置子
		ston[tmpx][tmpy] = (char)colour; // 落子

		;// 必須先行判斷異色子有否可被提者
		lift = 0; // 提子判斷
		if ((tmpx > 0) && (ston[tmpx - 1][tmpy] == opcolour)) { // 上
			tmpbord[tmpx - 1][tmpy] = 4;
			tmpfind = findStoneBlock(opcolour, 4, mov - 100, tmpsiz);
			if (tmpfind[0] == 0) { // 無氣，需要提子
				lift += tmpfind[1];
				for (tmpi = 0; tmpi <= tmpsiz; tmpi++) {
					for (tmpj = 0; tmpj <= tmpsiz; tmpj++) {
						if (tmpbord[tmpi][tmpj] == 4) tmpbord[tmpi][tmpj] = 5;
					}
				}
			}
			else { // 有氣，無需提子
				for (tmpi = 0; tmpi <= tmpsiz; tmpi++) {
					for (tmpj = 0; tmpj <= tmpsiz; tmpj++) {
						if (tmpbord[tmpi][tmpj] == 4) tmpbord[tmpi][tmpj] = 6;
					}
				}
			}
		}
		if ((tmpx < tmpsiz) && (ston[tmpx + 1][tmpy] == opcolour) &&
			(tmpbord[tmpx + 1][tmpy] != 5) && (tmpbord[tmpx + 1][tmpy] != 6)) { // 下
			tmpbord[tmpx + 1][tmpy] = 4;
			tmpfind = findStoneBlock(opcolour, 4, mov + 100, tmpsiz);
			if (tmpfind[0] == 0) { // 無氣，需要提子
				lift += tmpfind[1];
				for (tmpi = 0; tmpi <= tmpsiz; tmpi++) {
					for (tmpj = 0; tmpj <= tmpsiz; tmpj++) {
						if (tmpbord[tmpi][tmpj] == 4) tmpbord[tmpi][tmpj] = 5;
					}
				}
			}
			else { // 有氣，無需提子
				for (tmpi = 0; tmpi <= tmpsiz; tmpi++) {
					for (tmpj = 0; tmpj <= tmpsiz; tmpj++) {
						if (tmpbord[tmpi][tmpj] == 4) tmpbord[tmpi][tmpj] = 6;
					}
				}
			}
		}
		if ((tmpy > 0) && (ston[tmpx][tmpy - 1] == opcolour) &&
			(tmpbord[tmpx][tmpy - 1] != 5) && (tmpbord[tmpx][tmpy - 1] != 6)) { // 左
			tmpbord[tmpx][tmpy - 1] = 4;
			tmpfind = findStoneBlock(opcolour, 4, mov - 1, tmpsiz);
			if (tmpfind[0] == 0) { // 無氣，需要提子
				lift += tmpfind[1];
				for (tmpi = 0; tmpi <= tmpsiz; tmpi++) {
					for (tmpj = 0; tmpj <= tmpsiz; tmpj++) {
						if (tmpbord[tmpi][tmpj] == 4) tmpbord[tmpi][tmpj] = 5;
					}
				}
			}
			else { // 有氣，無需提子
				for (tmpi = 0; tmpi <= tmpsiz; tmpi++) {
					for (tmpj = 0; tmpj <= tmpsiz; tmpj++) {
						if (tmpbord[tmpi][tmpj] == 4) tmpbord[tmpi][tmpj] = 6;
					}
				}
			}
		}
		if ((tmpy < tmpsiz) && (ston[tmpx][tmpy + 1] == opcolour) &&
			(tmpbord[tmpx][tmpy + 1] != 5) && (tmpbord[tmpx][tmpy + 1] != 6)) { // 右
			tmpbord[tmpx][tmpy + 1] = 4;
			tmpfind = findStoneBlock(opcolour, 4, mov + 1, tmpsiz);
			if (tmpfind[0] == 0) { // 無氣，需要提子
				lift += tmpfind[1];
				for (tmpi = 0; tmpi <= tmpsiz; tmpi++) {
					for (tmpj = 0; tmpj <= tmpsiz; tmpj++) {
						if (tmpbord[tmpi][tmpj] == 4) tmpbord[tmpi][tmpj] = 5;
					}
				}
			}
			else { // 有氣，無需提子
				for (tmpi = 0; tmpi <= tmpsiz; tmpi++) {
					for (tmpj = 0; tmpj <= tmpsiz; tmpj++) {
						if (tmpbord[tmpi][tmpj] == 4) tmpbord[tmpi][tmpj] = 6;
					}
				}
			}
		}
		// 異色提子判斷完畢

		tmpfind = findStoneBlock(colour, 3, mov, tmpsiz);
		if (tmpfind[0] == 0 && lift == 0) {
			ston[tmpx][tmpy] = 0;
			return 0;
		}
		if (lift == 1 && tmpfind[1] == 1) {
			if (tmpx > 0 && tmpbord[tmpx - 1][tmpy] == 5) {
				ston[tmpx - 1][tmpy] = 0;
				confmove = (tmpx - 1) * 100 + tmpy;
			}
			if (tmpx < tmpsiz && tmpbord[tmpx + 1][tmpy] == 5) {
				ston[tmpx + 1][tmpy] = 0;
				confmove = (tmpx + 1) * 100 + tmpy;
			}
			if (tmpy > 0 && tmpbord[tmpx][tmpy - 1] == 5) {
				ston[tmpx][tmpy - 1] = 0;
				confmove = tmpx * 100 + tmpy - 1;
			}
			if (tmpy < tmpsiz && tmpbord[tmpx][tmpy + 1] == 5) {
				ston[tmpx][tmpy + 1] = 0;
				confmove = tmpx * 100 + tmpy + 1;
			}
			conflict = 1;
			System.out.println("New conflict: " + conflict + ", " + confmove);
			return 1;
		}
		if (lift > 0) { // 提子
			conflict = 0; // 撤銷劫爭
			for (tmpi = 0; tmpi < siz; tmpi++) { // 提子操作
				for (tmpj = 0; tmpj < siz; tmpj++) {
					if (tmpbord[tmpi][tmpj] == 5) ston[tmpi][tmpj] = 0;
				}
			}
			return 2; // 需要提子
		}
		conflict = 0; // 撤銷劫爭
		return 3; // 無需提子
	} // finished configDropStone
}
