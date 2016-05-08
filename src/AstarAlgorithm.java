import java.util.*;
import java.io.*;

public class AstarAlgorithm{
public static ArrayList<AstarNode> openList = new ArrayList<AstarNode>();
public static ArrayList<AstarNode> closeList = new ArrayList<AstarNode>();
public static int totalNodes;

    public static int calcManhattanDistance(AstarNode start, AstarNode finish){
	int calc = Math.abs(start.id.x - finish.id.x) + Math.abs(start.id.y - finish.id.y);
 	return(calc);	  
    }

    public static int calcHyperDistance(AstarNode start, AstarNode finish){
	int calc = Math.abs(start.id.x - finish.id.x) * Math.abs(start.id.y - finish.id.y);
 	return(calc);	  
    }

    public static List<AstarNode> search(AstarNode source){
	openList.clear();
	closeList.clear();
	AstarNode start = new AstarNode(source.getId(), 0, calcManhattanDistance(source, Robots.MeetPoint));
	openList.add(start);

	AstarNode goal = null;
	while(openList.size() > 0){
	    AstarNode min, aux;
	    int i, ind = 0;
	    min = (AstarNode) openList.get(0); // find the element with the minimum f
	    for(i = 1; i < openList.size(); i++){
		aux = (AstarNode) openList.get(i);
		if (min.f > aux.f){
		    min = aux;
		    ind = i;
		}
	    }
	    openList.remove(ind); // delete from open list
	    if (min.getId().x == Robots.MeetPointx && min.getId().y == Robots.MeetPointy){
		//System.out.println("ok"); // found target 
		goal = min;
		break;	    
	    }else{
		totalNodes++;
		closeList.add(min);
		List<AstarNode> neighbors = AstarNode.neighbors(min);
		for(AstarNode neighbor : neighbors){
		    /* if the node has already been visited, it will be in the close list */
		    AstarNode n2, visited = null; 
		    for(i = 0; i < closeList.size(); i++){ 
			n2 = (AstarNode) closeList.get(i);
			if (n2.id.x == neighbor.id.x && n2.id.y == neighbor.id.y){
			    visited = n2;
			}
		    }

		    if (visited == null){
			int g = min.g + calcManhattanDistance(min, neighbor);		
			AstarNode n1, n = null;
			int flag = -1;
			/* check if the node is in the open list */
			for(i = 0; i < openList.size(); i++){ 
			    n1 = (AstarNode) openList.get(i);
			    if (n1.id.x == neighbor.id.x && n1.id.y == neighbor.id.y){
				flag = i;
				n = n1;
			    }
			}
			
			if (flag == -1){ // not in the open set
			    n = new AstarNode(neighbor.getId(), g,calcManhattanDistance(neighbor, Robots.MeetPoint));
			    n.SetParent(min);
			    openList.add(n);
			}else if(g < n.getG()){ // have a better route to the current node, change its parent
			    openList.remove(flag);
			    n = new AstarNode(neighbor.getId(), g, calcManhattanDistance(neighbor, Robots.MeetPoint));
			    n.SetParent(min);
			    openList.add(n);
			}			
		    }
		} 
	    }
	}

	/* after found the target, start to construct the path */
	if(goal != null){
	    Stack<AstarNode> stack = new Stack<AstarNode>();
	    List<AstarNode> list = new ArrayList<AstarNode>();
	    stack.push(goal.getNode());
	   
	    AstarNode parent = goal.getParent();
	    while(parent != null){
		stack.push(parent.getNode());
		parent = parent.getParent();
	    }
	    while(stack.size() > 0){
	  	list.add(stack.pop());
	    } 
	    return list;
	}
	return null;
    }
}
