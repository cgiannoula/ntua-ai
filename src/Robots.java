import java.io.*;
import java.util.*;
import java.lang.*;

public class Robots{
    public static int RobotAx;
    public static int RobotAy;
    public static int RobotBx;
    public static int RobotBy;
    public static int MeetPointx;
    public static int MeetPointy;
    public static int nodesA;
    public static int nodesB;
    public static String [][] Area;
    public static String [][] AreaA;
    public static String [][] AreaB;	
    public static int n;
    public static int m;
    public static AstarNode MeetPoint;   
    public static List<AstarNode> PathRobotA;
    public static List<AstarNode> PathRobotB;

    public static void main(String[] args){
	
	try{
	    if(args.length == 0){
		throw new IllegalArgumentException("there is not file for input");
	    }
	    BufferedReader br = new BufferedReader(new FileReader(args[0]));

	    /* Reading the dimensions of 2d-array */
	    String line = br.readLine();
	    String[] a = line.split(" ");
	    n = Integer.parseInt(a[0]);
	    m = Integer.parseInt(a[1]);

	    /* Reading the starting position of RobotA */
	    line = br.readLine();
	    a = line.split(" ");
	    RobotAx = Integer.parseInt(a[0]);
	    RobotAy = Integer.parseInt(a[1]);

	    /* Readint the starting position of RobotB */
	    line = br.readLine();
	    a = line.split(" ");
	    RobotBx = Integer.parseInt(a[0]);
	    RobotBy = Integer.parseInt(a[1]);   

	    /* Reading the meeting point */
	    line = br.readLine();
	    a = line.split(" ");
	    MeetPointx = Integer.parseInt(a[0]);
	    MeetPointy = Integer.parseInt(a[1]);
	    IdStruct Meet = new IdStruct(MeetPointx, MeetPointy);
	    MeetPoint = new AstarNode(Meet, 0, 0);	

	    /* Reading the area */
	    Area = new String[n][m];
	    AreaA = new String[n][m];
	    AreaB = new String[n][m];
	    for (int i = 0; i < n; i++){
		line = br.readLine();
		for(int j = 0; j < m; j++){
		    Area[i][j] = String.valueOf(line.charAt(j));
		    AreaA[i][j] = String.valueOf(line.charAt(j));  
		    AreaB[i][j] = String.valueOf(line.charAt(j));
		}
	    }
	 
	    System.out.println();  
	    System.out.println("The input area is :"); 
	    /* Printing the area */
	    for (int i = 0; i < n; i++){
		for(int j = 0; j < m; j++){
		    System.out.print(Area[i][j]);
		    System.out.print(" ");
		}	
	        System.out.println();
	    }

	    System.out.println();
            /* Closing th file */
	    br.close();
            /* initiate the number of nodes */	
	    AstarAlgorithm.totalNodes = 0;

	    /* call A* for RobotA */
	    AstarNode tempr = new AstarNode(new IdStruct(RobotAx, RobotAy), 0, 0);
	    PathRobotA = AstarAlgorithm.search(tempr);
	    int sizeA = PathRobotA.size();
	    int nxtIndA = 1;
	    IdStruct PositionA = new IdStruct(RobotAx, RobotAy);
	    AreaA[PositionA.x][PositionA.y] = "A";
	    nodesA = 1;	    
	
	    /* call A* for RobotB */
	    tempr = new AstarNode(new IdStruct(RobotBx, RobotBy), 0, 0);
	    PathRobotB = AstarAlgorithm.search(tempr);

	    int sizeB = PathRobotB.size();
	    int nxtIndB = 1;
	    IdStruct PositionB = new IdStruct(RobotBx, RobotBy);
	    AreaB[PositionB.x][PositionB.y] = "B";
	    nodesB = 1;		
	
	    while((nxtIndA != sizeA - 1) && (nxtIndB != sizeB - 1)){
		System.out.print("Robot A considering next position (");
		System.out.print(PathRobotA.get(nxtIndA).id.x);
		System.out.print(",");
		System.out.print(PathRobotA.get(nxtIndA).id.y);
		System.out.println(")");
		
		if ((PathRobotA.get(nxtIndA).id.x != PositionB.x) || (PathRobotA.get(nxtIndA).id.y != PositionB.y)){
		    /* if there is not collision, robot moves */
		    System.out.print("Robot A moves from position (");
		    System.out.print(PositionA.x);
		    System.out.print(",");
		    System.out.print(PositionA.y);
		    System.out.print(")");
 		    PositionA.SetPos(PathRobotA.get(nxtIndA).id.x, PathRobotA.get(nxtIndA).id.y);
	  	    nxtIndA += 1;
		    nodesA += 1; 
		    AreaA[PositionA.x][PositionA.y] = "A";
		    System.out.print(" to position (");
		    System.out.print(PositionA.x);
		    System.out.print(",");
		    System.out.print(PositionA.y);
		    System.out.println(")");		
		}else{
		    /* there will be collision, so robot chooses another movement */
	 	    System.out.println("Robot A uderstands the collision with Robot B and changes its direction");
		    Area[PositionB.x][PositionB.y] = "X";
		    PathRobotA = AstarAlgorithm.search(new AstarNode(new IdStruct(PositionA.x, PositionA.y), 0, 0));
		    if(PathRobotA != null){
			sizeA = PathRobotA.size();
	   	    }
		    nxtIndA = 1;
		    Area[PositionB.x][PositionB.y] = "O"; 
		    
		    if(PathRobotA == null){
			/* if there is not path the robot can't move */
			System.out.println("Robot A can't move");
	           	PathRobotA = AstarAlgorithm.search(new AstarNode(new IdStruct(PositionA.x, PositionA.y), 0, 0));
		    	sizeA = PathRobotA.size();
	   	    	nxtIndA = 1; 
		    }else if ((PathRobotA.get(nxtIndA).id.x != PositionB.x) || (PathRobotA.get(nxtIndA).id.y != PositionB.y)){
		    	/* if there is not collision, the robot moves */
			System.out.print("Robot A moves from position (");
		    	System.out.print(PositionA.x);
		    	System.out.print(",");
		    	System.out.print(PositionA.y);
		    	System.out.print(")");	    	
			PositionA.SetPos(PathRobotA.get(nxtIndA).id.x, PathRobotA.get(nxtIndA).id.y);
	  	    	nxtIndA += 1;
			nodesA += 1;
		    	AreaA[PositionA.x][PositionA.y] = "A";
			System.out.print(" to position (");
		    	System.out.print(PositionA.x);
		    	System.out.print(",");
		    	System.out.print(PositionA.y);
		    	System.out.println(")");
		    }
		}

		System.out.print("Robot B considering next position (");
		System.out.print(PathRobotB.get(nxtIndB).id.x);
		System.out.print(",");
		System.out.print(PathRobotB.get(nxtIndB).id.y);
		System.out.println(")");
	
		if ((PathRobotB.get(nxtIndB).id.x != PositionA.x) || (PathRobotB.get(nxtIndB).id.y != PositionA.y)){
		    /* if there is not collision, the robot moves */
		    System.out.print("Robot B moves from position (");
		    System.out.print(PositionB.x);
		    System.out.print(",");
		    System.out.print(PositionB.y);
		    System.out.print(")");
		    PositionB.SetPos(PathRobotB.get(nxtIndB).id.x, PathRobotB.get(nxtIndB).id.y);
	  	    nxtIndB += 1;
	   	    nodesB +=1;
		    AreaB[PositionB.x][PositionB.y] = "B";
		    System.out.print(" to position (");
		    System.out.print(PositionB.x);
		    System.out.print(",");
		    System.out.print(PositionB.y);
		    System.out.println(")");
		}else{
		    /* there will be collision, so robot chooses another movement */
		    System.out.println("Robot B uderstands the collision with Robot A and changes its direction");
		    Area[PositionA.x][PositionA.y] = "X";
		    PathRobotB = AstarAlgorithm.search(new AstarNode(new IdStruct(PositionB.x, PositionB.y), 0, 0));
		    if(PathRobotB != null){
			sizeB = PathRobotB.size();
		    }
	   	    nxtIndB = 1;
		    Area[PositionA.x][PositionA.y] = "O";
		 
		   if(PathRobotB == null){
			/* if there is not path, the robot can't move */
			System.out.println("Robot B can't move");
	           	PathRobotB = AstarAlgorithm.search(new AstarNode(new IdStruct(PositionB.x, PositionB.y), 0, 0));
		    	sizeB = PathRobotB.size();
	   	    	nxtIndB = 1; 
		    }else if ((PathRobotB.get(nxtIndB).id.x != PositionA.x) || (PathRobotB.get(nxtIndB).id.y != PositionA.y)){
		    	/* if there is not collision, the robot moves */
			System.out.print("Robot B moves from position (");
		    	System.out.print(PositionB.x);
		    	System.out.print(",");
		    	System.out.print(PositionB.y);
		    	System.out.print(")");    	
			PositionB.SetPos(PathRobotB.get(nxtIndB).id.x, PathRobotB.get(nxtIndB).id.y);
	  	    	nxtIndB += 1;
		        nodesB += 1;
			AreaB[PositionB.x][PositionB.y] = "B";
		    	System.out.print(" to position (");
			System.out.print(PositionB.x);
		    	System.out.print(",");
		    	System.out.print(PositionB.y);
		    	System.out.println(")");
		    }
		}
	    } 
	    if(nxtIndA == sizeA -1){
		/* when robot reaches an neighboring point, it moves to the meeting point */
		System.out.print("Robot A moves from position (");
		System.out.print(PositionA.x);
		System.out.print(",");
		System.out.print(PositionA.y);
		System.out.print(")");	    	
		PositionA.SetPos(MeetPointx, MeetPointy);
		System.out.print(" to the meeting point (");
		System.out.print(MeetPointx);
		System.out.print(",");
		System.out.print(MeetPointy);
		System.out.println(")");
		AreaA[PositionA.x][PositionA.y] = "A";
		nodesA += 1;

		/* the other robot moves until it reaches a neighboring point to the meeting point */
		while(nxtIndB != sizeB -1){
   		    System.out.print("Robot B moves from position (");
		    System.out.print(PositionB.x);
		    System.out.print(",");
		    System.out.print(PositionB.y);
		    System.out.print(")");
		    PositionB.SetPos(PathRobotB.get(nxtIndB).id.x, PathRobotB.get(nxtIndB).id.y);
		    nxtIndB += 1;
		    nodesB += 1;
		    AreaB[PositionB.x][PositionB.y] = "B";
		    System.out.print(" to position (");
		    System.out.print(PositionB.x);
		    System.out.print(",");
		    System.out.print(PositionB.y);
		    System.out.println(")");
		}
	    }else{
		System.out.print("Robot A moves from position (");
		System.out.print(PositionA.x);
		System.out.print(",");
		System.out.print(PositionA.y);
		System.out.print(")");	    	
		PositionA.SetPos(PathRobotA.get(nxtIndA).id.x, PathRobotA.get(nxtIndA).id.y);
	  	nxtIndA += 1;
		nodesA += 1;
		AreaA[PositionA.x][PositionA.y] = "A";
		System.out.print(" to position (");
		System.out.print(PositionA.x);
		System.out.print(",");
		System.out.print(PositionA.y);
		System.out.println(")");	
		/* when robot reaches an neighboring point, it moves to the meeting point */
		System.out.print("Robot B moves from position (");
		System.out.print(PositionB.x);
		System.out.print(",");
		System.out.print(PositionB.y);
		System.out.print(")");
		PositionB.SetPos(MeetPointx, MeetPointy);
		System.out.print(" to the meeting point (");
		System.out.print(MeetPointx);
		System.out.print(",");
		System.out.print(MeetPointy);
		System.out.println(")");
		AreaB[PositionB.x][PositionB.y] = "B";
		nodesB += 1;

		/* the other robot moves until it reaches a neighboring point to the meeting point */
		while(nxtIndA != sizeA -1){
   		    System.out.print("Robot A moves from position (");
		    System.out.print(PositionA.x);
		    System.out.print(",");
		    System.out.print(PositionA.y);
		    System.out.print(")");  		    
		    PositionA.SetPos(PathRobotA.get(nxtIndA).id.x, PathRobotA.get(nxtIndA).id.y);
		    nxtIndA += 1;
		    nodesA += 1;
		    AreaA[PositionA.x][PositionA.y] = "A";
		    System.out.print(" to position (");
		    System.out.print(PositionA.x);
		    System.out.print(",");
		    System.out.print(PositionA.y);
		    System.out.println(")");
		}
	    }
	   System.out.println();
	   System.out.println("The map of robot A is :");
	   for (int i = 0; i < n; i++){
		for(int j = 0; j < m; j++){
		    System.out.print(AreaA[i][j]);
		    System.out.print(" ");
		}	
	        System.out.println();
	   }

	   System.out.println();
	   System.out.println("The map of robot B is :");
	   for (int i = 0; i < n; i++){
		for(int j = 0; j < m; j++){
		    System.out.print(AreaB[i][j]);
		    System.out.print(" ");
		}	
	        System.out.println();
	   }
	
	   System.out.println();
	   //System.out.print("The solution found in ");
	   //System.out.print(nodesA + nodesB - 2);
	   //System.out.println(" steps"); 		
	   //System.out.print("The number of nodes for robot A is: ");
	   //System.out.println(nodesA);
	   //System.out.print("The number of nodes for robot B is: ");
	   //System.out.println(nodesB);
	   System.out.print("The total number of nodes for robots is: ");
	   System.out.println(AstarAlgorithm.totalNodes);
	   System.out.println();	 

	}catch(Exception e){
	   System.out.println(e.toString());
	}
    }
}
