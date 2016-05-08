import java.util.*;

public class AstarNode{
    public IdStruct id;
    public AstarNode parent;
    public int g;
    public int h;
    public int f;

    AstarNode(IdStruct id, int g, int h){
    	this.id = id;
	this.g = g;
	this.h = h;
	this.f = g + h;
    }

    public void SetParent(AstarNode parent){
	this.parent = parent;
    }

    public IdStruct getId(){
	return(this.id);
    }

    public int getG(){
	return(this.g);	
    }

    public void setG(int g_new){
	this.g = g_new;
	return;
    }

    public void setH(int f_new){
	this.f = f_new;
	this.h = this.g + this.f;
	return;
    }

    public AstarNode getNode(){
	return(this);
    }

    public AstarNode getParent(){
	return(this.parent);
    }

    public static AstarNode moveLeft(AstarNode curr){
	int flag = 0;
	if(curr.id.x - 1 >= 0) 
	    flag =1;
	if(flag == 1 && Robots.Area[curr.id.x - 1][curr.id.y].equals("O")){
	    IdStruct newS = new IdStruct(curr.id.x - 1, curr.id.y);	
	    return(new AstarNode(newS, 0, 0));
	}else{
	    return(null);
	}
    }    

    public static AstarNode moveRight(AstarNode curr){
	int flag = 0;
	if(curr.id.x + 1 < Robots.n) 
	    flag =1;
	if(flag == 1 && Robots.Area[curr.id.x + 1][curr.id.y].equals("O")){
	    IdStruct newS = new IdStruct(curr.id.x + 1, curr.id.y);	
	    return(new AstarNode(newS, 0, 0));
	}else{
	    return(null);
	}
    }

    public static AstarNode moveUp(AstarNode curr){
	int flag = 0;
	if(curr.id.y - 1 >= 0) 
	    flag =1;
	if(flag == 1 && Robots.Area[curr.id.x][curr.id.y - 1].equals("O")){
	    IdStruct newS = new IdStruct(curr.id.x, curr.id.y - 1);	
	    return(new AstarNode(newS, 0, 0));
	}else{
	    return(null);
	}
    }

    public static AstarNode moveDown(AstarNode curr){
	int flag = 0;
	if(curr.id.y + 1 < Robots.m) 
	    flag =1;
	if(flag == 1 && Robots.Area[curr.id.x][curr.id.y + 1].equals("O")){
	    IdStruct newS = new IdStruct(curr.id.x, curr.id.y + 1);	
	    return(new AstarNode(newS, 0, 0));
	}else{
	    return(null);
	}
    }
/*
    public static List<AstarNode> neighbors(AstarNode curr){
  	List<AstarNode> neighborSet = new ArrayList<AstarNode>();
	int left = 0, right = 0, up = 0, down = 0;
	if(curr.id.x - 1 >= 0) 
	    left =1;
	if(curr.id.x + 1 < Robots.n)
	    right = 1;
	if(curr.id.y - 1 >= 0)
	    up = 1;
        if(curr.id.y + 1 < Robots.m)
	    down = 1;
	if(left == 1 && Robots.Area[curr.id.x-1][curr.id.y].equals("O")){
	    IdStruct newS = new IdStruct(curr.id.x-1, curr.id.y);	
	    neighborSet.add(new AstarNode(newS, 0, 0));
	}
	if(right == 1 && Robots.Area[curr.id.x+1][curr.id.y].equals("O")){
	    IdStruct newS = new IdStruct(curr.id.x+1, curr.id.y);	
	    neighborSet.add(new AstarNode(newS, 0, 0));
	}
	if(up == 1 && Robots.Area[curr.id.x][curr.id.y-1].equals("O")){
	    IdStruct newS = new IdStruct(curr.id.x, curr.id.y-1);	
	    neighborSet.add(new AstarNode(newS, 0, 0));
	}
	if(down == 1 && Robots.Area[curr.id.x][curr.id.y+1].equals("O")){
	    IdStruct newS = new IdStruct(curr.id.x, curr.id.y+1);	
	    neighborSet.add(new AstarNode(newS, 0, 0));
	}

	return neighborSet;
    }
*/

    public static List<AstarNode> neighbors(AstarNode curr){
  	List<AstarNode> neighborSet = new ArrayList<AstarNode>();
	AstarNode temp;
	temp = moveLeft(curr);
	if (temp != null){
	    neighborSet.add(temp);
	}
	temp = moveRight(curr);
	if (temp != null){
	    neighborSet.add(temp);
	}
	temp = moveUp(curr);
	if (temp != null){
	    neighborSet.add(temp);
	}
	temp = moveDown(curr);
	if (temp != null){
	    neighborSet.add(temp);
	}
	return neighborSet;
    }
}
