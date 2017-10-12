import java.util.*;
class node{
	public String state;
	public int val;
	public String prevState;
	public node(String s, int v,String p) {
		state=s;
		val=v;
		prevState=p;
	}
	
}
public class Astar {
	public static boolean MANHATTAN = true;   //TRUE MANHATTAN     FALSE MISPLACED
	public static LinkedList<node> llist;
	public static Hashtable<String,Integer> visited = new Hashtable<String,Integer>();
	public static Hashtable<String,Integer> finished = new Hashtable<String,Integer>();
	private static void generate(String state) {
		int index=state.indexOf('0');
		String state1,state2,state3,state4;
		int dist;
		char temp;
		if(!(index>=0 && index<=3)) { //Top row
			char[] S=state.toCharArray();
			temp=S[index-4];
			S[index-4] = S[index];    S[index]= temp;
			state1 = new String(S);
			if(!visited.containsKey(state1)) { 
					if(MANHATTAN) dist = Manhattan(state1);
					else dist = Misplaced(state1);		
					addState(state1,dist,state);
					visited.put(state1, index);
					//System.out.println(state1 + " " +dist);
					
			}
			//else  System.out.println("CONTAIN: "+state1);
		}
		if( !(index>=12 && index <= 15)) { //Bottom row
			char [] S=state.toCharArray();
			temp= S[index+4];
			S[index+4]=S[index];    S[index]=temp;
			state2 = new String(S);
			if(!visited.containsKey(state2)) {
					if(MANHATTAN) dist = Manhattan(state2);
					else dist = Misplaced(state2);				
						addState(state2,dist,state);
						visited.put(state2, index);
					//System.out.println(state2 + " " + dist);
				}
			//else  System.out.println("CONTAIN: "+state2);
		}
		if( !(index%4 == 0)) { //Left col
			char [] S=state.toCharArray();
			temp= S[index-1];
			S[index-1]=S[index];    S[index]=temp;
			state3 = new String(S);
			if(!visited.containsKey(state3)) {
				if(MANHATTAN) dist = Manhattan(state3);
				else dist = Misplaced(state3);				
				addState(state3,dist,state);
				visited.put(state3, index);

				//System.out.println(state3 + " " + dist);
			}
			//else  System.out.println("CONTAIN: "+state3);
			//System.out.println(state3);
		}
		if( !(index%4 ==  3)) { //Right col
			char [] S=state.toCharArray();
			temp= S[index+1];
			S[index+1]=S[index];    S[index]=temp;
			state4 = new String(S);
			if(!visited.containsKey(state4)) {
				if(MANHATTAN) dist = Manhattan(state4);
				else dist = Misplaced(state4);				
				addState(state4,dist,state);
				visited.put(state4, index);
				//System.out.println(state4 + " " + dist);
			}
			//else  System.out.println("CONTAIN: "+state4);
		}
		
		
		
		
	}
	private static int Manhattan(String state) {
		int row,col;
		int totalMan=0;
		//String correct = "123456789ABCDEF0";
		//System.out.println("STATE IS " +state);
		char []S = state.toCharArray();
		for(int i=0; i<16;i++) {
			row = i/4;
			col = i%4;
			
			switch(S[i]) {
			case '1': totalMan = totalMan + (row+col); break;
			case '2': totalMan = totalMan + row + Math.abs((col-1)); break;
			case '3': totalMan = totalMan + row + Math.abs((col-2)); break;
			case '4': totalMan = totalMan + row + Math.abs((col-3)); break;
			case '5': totalMan = totalMan + Math.abs((row-1)) + Math.abs((col)); break;
			case '6': totalMan = totalMan + Math.abs((row-1)) + Math.abs((col-1)); break;
			case '7': totalMan = totalMan + Math.abs(row-1) + Math.abs((col-2)); break;
			case '8': totalMan = totalMan + Math.abs(row-1) + Math.abs((col-3)); break;
			case '9': totalMan = totalMan + Math.abs(row-2) + (col); break;
			case 'A': totalMan = totalMan + Math.abs(row-2) + Math.abs((col-1)); break;
			case 'B': totalMan = totalMan + Math.abs(row-2) + Math.abs((col-2)); break;
			case 'C': totalMan = totalMan + Math.abs(row-2) + Math.abs((col-3)); break;
			case 'D': totalMan = totalMan + Math.abs(row-3) + Math.abs((col)); break;
			case 'E': totalMan = totalMan + Math.abs(row-3) + Math.abs((col-1)); break;
			case 'F': totalMan = totalMan + Math.abs(row-3) + Math.abs((col-2)); break;
			case '0': totalMan = totalMan + Math.abs(row-3) + Math.abs((col-3)); break;
			default: totalMan=0;			
			}
			//System.out.println(" " + S[i]+" row " + row +" col "+col+ " Manhattan" + totalMan);
		}
		return totalMan;
		
		
	}
	private static int Misplaced(String state) {
		int missed=0;
		String correct = "123456789ABCDEF0";
		char []S = state.toCharArray();
		char []C = correct.toCharArray();
		for (int i =0 ; i < 16; i++) {
			if( S[i] != C[i]) { missed++;}
		}
		return missed;
	}
	private static void addState(String s,  int val,String p) {
		node temp = new node(s,val,p);
		if( llist.size() ==0) {
			llist.add(temp);
		}
		else if (llist.get(0).val > val) {
			llist.add(0,temp);
		}
		else if (llist.get(llist.size()-1).val < val) {
			llist.add(llist.size(),temp);
		}
		else {
			int i =0;
			while( llist.get(i).val <val) {
				i++;
			}llist.add(i,temp);
		}	
	}
	public static void main(String[] args) {
		llist = new LinkedList<node>();
		boolean FOUND=false;
		String correct = "123456789ABCDEF0";
		String init_state = "E2547CF80B69A3D1";
		int prev_index=init_state.indexOf('0');
		MANHATTAN = true;
		//23401567ABC89DEF   SELFMADE
		//69E1BA0CF5237D84
		//F21C850B49A73ED6
		//123456789ABCD0EF
		//6C7A89B0F2C5E314
		//123456789EBFDA0C		SOLVEABLE IN 2SECONDS
		//E172A48D6C9B5F30
		//120F9C457B683EDA
		//E2547CF80B69A3D1
		System.out.println("Initial state = "+init_state);
		node temp1;
		generate(init_state);
		finished.put(init_state,prev_index);
		
		while(llist.size() != 0) {
			System.out.println();
			System.out.println("Llist size: " + llist.size());
			temp1 = llist.remove();
			if( Objects.equals(temp1.state,correct)) {
				System.out.println("FOUND SOLUTION");
				break;
			}
			System.out.println( "Chosen state: " + temp1.state +" "+ temp1.val);
			if(!finished.containsKey(temp1.state)) {
				
				generate(temp1.state);			
				finished.put(temp1.state, prev_index);
				//System.out.println("Finished state: " + temp1.state+" "+ prev_index);
				System.out.println(llist.peekLast().val);
			}
			
			prev_index = temp1.state.indexOf('0');
		
			
		}
		
	}
}