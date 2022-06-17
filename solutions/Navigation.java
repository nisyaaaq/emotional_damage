import java.util.*;

public class Navigation {

    public static void main(String[] args) {
        
        
        int i = 0;
        Scanner sc = new Scanner(System.in);
        int num_rails = sc.nextInt();
        UnweightedGraph<String> graph = new UnweightedGraph<String>();
        //UnweightedGraph<String> graph1 = new UnweightedGraph<String>();
        String[] trains =new String[num_rails];
        
        for(int count=0;count<num_rails;count++){
        trains[count]=sc.nextLine();
        }
        
        for(int l=0; l<trains.length; l++){
            String[] words=trains[l].split(" => ");
            graph.addVertex(words[0]);
            graph.addVertex(words[1]);
        }
        
        for(int j=0; j<trains.length; j++){
            String[] words=trains[j].split(" => ");
            graph.addEdge(words[0], words[1]);        
        }
        
        if(sc.nextLine().equalsIgnoreCase("QUERIES")){
        sc.nextLine(); //queries
        int queries = sc.nextInt(); //Read queries integer from cases.txt
        System.out.println(queries);
        String[] qpath = new String[queries]; //total station that exist
        ArrayList<String> src=new ArrayList<>();
        ArrayList<String> dst=new ArrayList<>();

        for(int m=0; m<queries; m++) { // Read queries line from cases.txt 
            qpath[m] = sc.nextLine();
            m++;
        }
        

        for (int l = 0; l < qpath.length; l++) {
            String[] station = qpath[l].split(" -> "); //split the startingpoint and destination  
            src.set(l, station[0]);
            dst.set(l, station[1]);
            graph.dfs(src.get(l));
        }

        System.out.println("Enter startingpoint and destination: ");
        for(int l=0; l<qpath.length; l++){  
        
        String[] user_path = qpath[l].split(" -> ");
        
        if (!src.contains(user_path[0])) { //If there is no starting point
            System.out.println("This path doesnt start at the starting station!");
        }        
        if (!dst.contains(user_path[1])) { //If there is no destination print 
            System.out.println("This path doesnt end at the destination!");
        }
        if (!src.contains(user_path[0]) && !dst.contains(user_path[1])){
            System.out.println("There is no train from "+ user_path[0] + " to " + user_path[1]);
        }
    }

    }
        
        
        
    }
    
   public static class UnweightedGraph<T extends Comparable<T>> {
   Vertex<T> head;
   int size;
	
   public UnweightedGraph()	{
      head=null;
      size=0;
   }
   
   public void clear() {   
      head=null;
   }

   public int getSize()   {
      return this.size;
   }
   
   public int getIndeg(T v)  {
      if (hasVertex(v))	{
         Vertex<T> temp = head;
         while (temp!=null) {
            if ( temp.vertexInfo.compareTo( v ) == 0 )
               return temp.indeg;
            temp=temp.nextVertex;
         }
      }
      return -1;
   }
         
   public int getOutdeg(T v)  {
      if (hasVertex(v))	{
         Vertex<T> temp = head;
         while (temp!=null) {
            if ( temp.vertexInfo.compareTo( v ) == 0 )
               return temp.outdeg;
            temp=temp.nextVertex;
         }
      }
      return -1;
   }

   public boolean hasVertex(T v)	{
      if (head==null)
         return false;
      Vertex<T> temp = head;
      while (temp!=null)	{
         if ( temp.vertexInfo.compareTo( v ) == 0 )
            return true;
         temp=temp.nextVertex;
      }
      return false;
   }

   public boolean addVertex(T v)	{
      if (hasVertex(v)==false)	{
         Vertex<T> temp=head;
         Vertex<T> newVertex = new Vertex<>(v, null);
         if (head==null)   
            head=newVertex;
         else {
            Vertex<T> previous=head;;
            while (temp!=null)  {
               previous=temp;
               temp=temp.nextVertex;
            }
            previous.nextVertex=newVertex;
         }
         size++;
         return true;
      }
      else
         return false;
   }
   
   public int getIndex(T v) {
      Vertex<T> temp = head;
      int pos=0;
      while (temp!=null)	{
         if ( temp.vertexInfo.compareTo( v ) == 0 )
            return pos;
         temp=temp.nextVertex;
         pos+=1;
      }
      return -1;
   }
   
   public ArrayList<T> getAllVertexObjects() {
      ArrayList<T> list = new ArrayList<>();
      Vertex<T> temp = head;
      while (temp!=null)	{
         list.add(temp.vertexInfo);
         temp=temp.nextVertex;
      }
      return list;
   }

   public ArrayList<Vertex<T>> getAllVertices() {
      ArrayList<Vertex<T>> list = new ArrayList<>();
      Vertex<T> temp = head;
      while (temp!=null)	{
         list.add(temp);
         temp=temp.nextVertex;
      }
      return list;
   }
   
   public T getVertex(int pos) {
      if (pos>size-1 || pos<0) 
         return null;
      Vertex<T> temp = head;
      for (int i=0; i<pos; i++)
         temp=temp.nextVertex;
      return temp.vertexInfo;
   }

   public boolean addEdge(T source, T destination)   {
      if (head==null)
         return false;
      if (!hasVertex(source) || !hasVertex(destination)) 
         return false;
      Vertex<T> sourceVertex = head;
      while (sourceVertex!=null)	{
         if ( sourceVertex.vertexInfo.compareTo( source ) == 0 )   {
            // Reached source vertex, look for destination now
            Vertex<T> destinationVertex = head;
            while (destinationVertex!=null)	{
               if ( destinationVertex.vertexInfo.compareTo( destination ) == 0 )   {
                  // Reached destination vertex, add edge here
                  Edge<T> currentEdge = sourceVertex.firstEdge;
                  Edge<T> newEdge = new Edge<>(destinationVertex, currentEdge);
                  sourceVertex.firstEdge=newEdge;
                  sourceVertex.outdeg++;
                  destinationVertex.indeg++;
                  return true;
               }
               destinationVertex=destinationVertex.nextVertex;
            }
         }
         sourceVertex=sourceVertex.nextVertex;
      }
      return false;
   }

   public boolean addUndirectedEdge(T source, T destination){
      return addEdge(source, destination) && addEdge(destination, source);
   }
   
   public boolean hasEdge(T source, T destination) {
      if (head==null)
         return false;
      if (!hasVertex(source) || !hasVertex(destination)) 
         return false;
      Vertex<T> sourceVertex = head;
      while (sourceVertex!=null)	{
         if ( sourceVertex.vertexInfo.compareTo( source ) == 0 )   {
            // Reached source vertex, look for destination now 
            Edge<T> currentEdge = sourceVertex.firstEdge;
            while (currentEdge != null) {
               if (currentEdge.toVertex.vertexInfo.compareTo(destination)==0) 
               // destination vertex found 
                  return true;
               currentEdge=currentEdge.nextEdge;
            }
         }
         sourceVertex=sourceVertex.nextVertex;
      }
      return false;
   }

   
   public ArrayList<T> getNeighbours (T v)  {
      if (!hasVertex(v))
         return null;
      ArrayList<T> list = new ArrayList<T>();
      Vertex<T> temp = head;
      while (temp!=null)	{
         if ( temp.vertexInfo.compareTo( v ) == 0 )   {
            // Reached vertex, look for destination now
            Edge<T> currentEdge = temp.firstEdge;
            while (currentEdge != null) {
               list.add(currentEdge.toVertex.vertexInfo);
               currentEdge=currentEdge.nextEdge;
            }
         }
         temp=temp.nextVertex;
      }
      return list;   
   }
   
   public void printEdges()   {
      Vertex<T> temp=head;
      while (temp!=null) {
         System.out.print("# " + temp.vertexInfo + " : " );
         Edge<T> currentEdge = temp.firstEdge;
         while (currentEdge != null) {
            System.out.print("[" + temp.vertexInfo + "," + currentEdge.toVertex.vertexInfo +"] " );
            currentEdge=currentEdge.nextEdge;
         }
         System.out.println();
         temp=temp.nextVertex;
      }  
   }
   Set<T> visited = new HashSet<>();


   public boolean dfs(T source){
      ArrayList<T> neighbourVertex = this.getNeighbours(source); // get next nodes

      if(neighbourVertex==null){ // if no next node
         return false;
      }

      if(source==null){
         visited.clear();
         return true;
      }

      visited.add(source);
      System.out.print(source + " -> "); // node

      for(T neighbour : neighbourVertex){
         if(dfs(neighbour)){
            visited.clear();
            return true;
         }else {
            System.out.println("end");// display end at the end
         }
      }
      return false;
   }

}
    public static class Vertex<T extends Comparable<T>> {
   T vertexInfo;
   int indeg;
   int outdeg;
   Vertex<T> nextVertex;
   Edge<T> firstEdge;
    
   public Vertex() {
      vertexInfo=null;
      indeg=0;
      outdeg=0;
      nextVertex = null;
      firstEdge = null;
   }
	
   public Vertex(T vInfo, Vertex<T> next) {
      vertexInfo = vInfo;
      indeg=0;
      outdeg=0;
      nextVertex = next;
      firstEdge = null;
   }	
}
    
    public static class Edge<T extends Comparable<T>> {
	Vertex<T> toVertex;

	Edge<T> nextEdge;
	
	public Edge()	{
		toVertex = null;
		nextEdge = null;
	}
	
	public Edge(Vertex<T> destination, Edge<T> a)	{
		toVertex = destination;
		nextEdge = a;
	}

}
}
