import java.util.*;
public class whatever {
                public static Queue<String> myQ = new LinkedList<String>();
                public static Hashtable<String,Integer> visit = new Hashtable<String,Integer>(); // creates Hash table
                public static int Qsize=0;
                public static void generate(String state){
                                int index2 = state.indexOf('0');
                                //System.out.print(index);
                                int row = index2/4;// System.out.println("Row is " + Row);
                                int col = index2%4;//System.out.println("Col is " + Col);
                                //System.out.print(row);
                                String state1,state2,state3,state4;
                                int index = row*4+col;
                                //System.out.println("Original  "+state);
                                if( row != 0) {
                                                char [] S=state.toCharArray();
                                                char temp = S[index -4];
                                                S[index -4]=S[index];    S[index]=temp;
                                                state1 = new String(S);
                                                                if (!whatever.visit.containsKey(state1) ){
                                                whatever.visit.put(state1,index);
                                                //System.out.println("New state " + state1);
                                                                if(whatever.myQ.offer(state1)) { Qsize++;
                                                                //System.out.println("Added to Queue "+state1 +" " +index);
                                                                }
                                                }
                                } 
                                if (row!=3) {
                                                char [] S=state.toCharArray();
                                                char temp = S[index +4];
                                                S[index +4]=S[index];    S[index]=temp;
                                                state2 = new String(S);
                                                if (!whatever.visit.containsKey(state2) ){
                                                whatever.visit.put(state2,index);
                                                //System.out.println("New state " + state2);
                                                if(whatever.myQ.offer(state2)) {Qsize++;
                                                //System.out.println("Added to Queue "+state2 +" " +index);
                                                }}
                                }
                                if (col!=0) {
                                                char [] S=state.toCharArray();
                                                char temp = S[index -1];
                                                S[index -1]=S[index];    S[index]=temp;
                                                state3 = new String(S);
 
                                                if (!whatever.visit.containsKey(state3) ){whatever.visit.put(state3,index);
                                                //System.out.println("New state " + state3);
                                                if(whatever.myQ.offer(state3)) {Qsize++;
                                                //System.out.println("Added to Queue "+state3 +" " +index);
                                                }}
                                }
                                if (col!=3) {
                                                char [] S=state.toCharArray();
                                                char temp = S[index +1];
                                                S[index +1]=S[index];    S[index]=temp;
                                                state4 = new String(S);
 
                                                if (!whatever.visit.containsKey(state4) ){whatever.visit.put(state4,index);
                                                //System.out.println("New state " + state4);
                                                if(whatever.myQ.offer(state4)) {Qsize++;
                                                //System.out.println("Added to Queue "+state4 +" " +index);
                                                }}
                                }             
                                int temp = whatever.visit.get(state) +100;
                                whatever.visit.remove(state);
                                whatever.visit.put(state, temp);
                                //System.out.println("Visited " + state+ " and index: " + temp);
                }
                public static int path[] = new int[80];
                public static void getPath(String inti){
                                int index ;
                                int count=0;
                                String newState,prevState;
                                newState = "123";
                                prevState=whatever.correct_answer;
                                int index2;
                                char[] S;
                                char temp;
                               
                                                while (! (Objects.equals(prevState , inti))){
                                                                index=whatever.visit.get(prevState) ;
                                                                                if (index>100)   index=index-100;
                                                                //System.out.println("index is: " +index);
                                                                S=prevState.toCharArray();
                                                                index2 = prevState.indexOf('0');
                                                                temp =S[index];
                                                                S[index] = S[index2];
                                                                S[index2] = temp;
                                                                newState= new String(S);
                                                                path[count]=index;
                                                                count++;
                                                                prevState=newState;
                                                                //System.out.println(prevState);
                                }
                                System.out.println("Path is: ");
                                while( count!= -1){
                                                System.out.print(" "+path[count]);
                                                count--;
                                }
                }
               
                public static String correct_answer = "123456789ABCDEF0";
                public static void main(String[] args) {
                                String finalout="not found";
                                System.out.println("Initial State is : \n");
                                String intial_state = "120F9C457B683EDA";
                                //69E1BA0CF5237D84
                                //F21C850B49A73ED6
                                //123456789ABCD0EF
                                //6C7A89B0F2C5E314
                                //123456789EBFDA0C                     SOLVEABLE IN 2SECONDS
                                //E172A48D6C9B5F30
                                //120F9C457B683EDA
                                //E2547CF80B69A3D1
                                //23A417DBEF6059C8
                                System.out.println(intial_state);
                                whatever.visit.put(intial_state, 200);
                                Runtime rt = Runtime.getRuntime();
                                long startTime = System.currentTimeMillis();
                                long preTotal=0;
                                long prevFree=rt.freeMemory();
                                boolean FOUND=false;
                                generate(intial_state);
                                String temp;
                                int attempt=0;
                                long temp2,maximum = 0,curFree,endTime;
                                //whatever.visit.put(intial_state, intial_state.indexOf('0'));
                                try{
                                	for( int i=0;i<50_000_000;i++){
                                				attempt++;
                                                temp = whatever.myQ.poll();
                                                //System.out.println(temp+ " with value " + whatever.visit.get(temp));
                                                if (Objects.equals(temp , whatever.correct_answer)) {FOUND=true;break; };
                                                boolean succeed = whatever.visit.get(temp) <=100;
                                                //System.out.println("\n"+whatever.visit.get(temp));
                                                if(succeed) generate(temp);
                                		}
                                }
                                catch (OutOfMemoryError E){
                                //System.out.println("value: " +whatever.visit.get(whatever.correct_answer));
                                curFree=rt.freeMemory();
                                endTime   = System.currentTimeMillis();
                                temp2 = (prevFree-curFree) /1_000_000;
                                long temp1= (endTime - startTime)/1000;
                                System.out.println("System ran out of Memory........");
                                System.out.println("Total nodes searched: "+attempt);
                                System.out.println("Queue size: "+Qsize);
                                System.out.println("Hashtable size: "+whatever.visit.size());
                                System.out.println("Memory used: " +temp2+"MB");
                                System.out.println("Time CPU used: " + temp1+ "s");
                                FOUND=false;
                                }
                                
                                                if (FOUND) {whatever.getPath(intial_state);
                                                //System.out.println("value: " +whatever.visit.get(whatever.correct_answer));
                                                curFree=rt.freeMemory();
                                                endTime   = System.currentTimeMillis();
                                                temp2 = (prevFree-curFree) /1000000;
                                                long temp1= (endTime - startTime)/1000;
                                                System.out.println(" ");
                                                System.out.println("Total nodes searched: "+attempt);
                                                System.out.println("Queue size: "+Qsize);
                                                System.out.println("Hashtable size: "+whatever.visit.size());
                                                System.out.println("Memory used: " +temp2+"MB");
                                                System.out.println("Time CPU used: " + temp1+ "s");                                             
                                                }
                }                             
}
