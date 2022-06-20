
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;



public class Navigation {
    public static void main(String[] args) {
        String file = "C:\\Users\\Nazif Aqif\\Desktop\\DS-Assignment-2022-main\\tasks\\navigation\\cases\\0.txt";
        
        try {
            UnweightedGraph<String,Integer> graph = new UnweightedGraph<>();
            Scanner sc = new Scanner(new FileInputStream(file));
            String num = sc.nextLine();
            int num_rails = Integer.parseInt(num);
            System.out.println(num_rails);
            
            for(int i=0; i<num_rails; i++){
                String line = sc.nextLine();
                String[] split = line.split("=>");

                    graph.addVertex(split[0]);
                    graph.addVertex(split[1]);
                    graph.addEdge(split[0],  split[1],1);
                    graph.addEdge(split[1], split[0],1);
 
            }
            sc.nextLine(); //queries
            String num1 = sc.nextLine();
            int queries = Integer.parseInt(num1);
            for(int j=0; j<queries; j++){
                String line = sc.nextLine();
                String[] splitt = line.split("->");

                    System.out.println(splitt[0]+" => "+splitt[1]);
                    DFS(graph,splitt[0],splitt[1]);
                    System.out.println("\n");

            }
        

        }catch (Exception e){
            System.out.println("File not found");
        }
    }

    public static void BFS(UnweightedGraph x,String source, String destination){


        String temp = "";
        String p = "";
        ArrayList<String> prev = new ArrayList<>();
        String path;
        int s = x.getSize();
        boolean visited[] = new boolean[s];
        LinkedList<String> que = new LinkedList<>();
        visited[x.getIndex(source)] = true;
        que.add(source);


        while (que.size() != 0){



            path = que.poll();


            if(path.equals(destination)){
                System.out.print(destination);
                break;
            }else{
                for(String y : prev){
                    if((!y.equals(temp)) )
                    System.out.print(temp+" => "+y+" => ");
                }
                System.out.print(temp+" -> ");
                prev.clear();
            }

            temp = path;

                    //System.out.print(path + " -> ");

            for(int i =0;i < x.getNeighbours(path).size();i++){

                p = (String) x.getNeighbours(path).get(i);

                prev.add(p);
                if(!visited[x.getIndex(p)]){
                    visited[x.getIndex(p)] = true;
                    que.add(p);
                }
            }


        }

    }

    public static int DFS(UnweightedGraph x,String source,String destination){
        boolean visited[] = new boolean[x.getSize()];
        if(DFSUtil(x,source,destination,visited)==1)
        return 1;

        return 0;
    }

    public static int DFSUtil(UnweightedGraph x,String source,String destination,boolean visited[]){
        visited[x.getIndex(source)] = true;
        System.out.print(source+" -> ");
        for(int i =0;i < x.getNeighbours(source).size();i++){

            String p = (String) x.getNeighbours(source).get(i);
            if(p.equals(destination)){
                System.out.print(destination);
                return 1;
            }

            if(!visited[x.getIndex(p)]){
                if(DFSUtil(x,p,destination,visited)==1){
                    return 1;
                }else continue;

            }
        }

        return 0;
    }
}
class UnweightedGraph<T extends Comparable<T>, N extends Comparable <N>> {
    Vertex<T,N> head;
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
        if (hasVertex(v)==true)	{
            Vertex<T,N> temp = head;
            while (temp!=null) {
                if ( temp.vertexInfo.compareTo( v ) == 0 )
                    return temp.indeg;
                temp=temp.nextVertex;
            }
        }
        return -1;
    }

    public int getOutdeg(T v)  {
        if (hasVertex(v)==true)	{
            Vertex<T,N> temp = head;
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
        Vertex<T,N> temp = head;
        while (temp!=null)	{
            if ( temp.vertexInfo.compareTo( v ) == 0 )
                return true;
            temp=temp.nextVertex;
        }
        return false;
    }

    public boolean addVertex(T v)	{
        if (hasVertex(v)==false)	{
            Vertex<T,N> temp=head;
            Vertex<T,N> newVertex = new Vertex<>(v, null);
            if (head==null)
                head=newVertex;
            else {
                Vertex<T,N> previous=head;;
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
        Vertex<T,N> temp = head;
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
        Vertex<T,N> temp = head;
        while (temp!=null)	{
            list.add(temp.vertexInfo);
            temp=temp.nextVertex;
        }
        return list;
    }

    public ArrayList<Vertex<T,N>> getAllVertices() {
        ArrayList<Vertex<T,N>> list = new ArrayList<>();
        Vertex<T,N> temp = head;
        while (temp!=null)	{
            list.add(temp);
            temp=temp.nextVertex;
        }
        return list;
    }

    public T getVertex(int pos) {
        if (pos>size-1 || pos<0)
            return null;
        Vertex<T,N> temp = head;
        for (int i=0; i<pos; i++)
            temp=temp.nextVertex;
        return temp.vertexInfo;
    }

    public boolean addEdge(T source, T destination,N w)   {
        if (head==null)
            return false;
        if (!hasVertex(source) || !hasVertex(destination))
            return false;
        Vertex<T,N> sourceVertex = head;
        while (sourceVertex!=null)	{
            if ( sourceVertex.vertexInfo.compareTo( source ) == 0 )   {
                // Reached source vertex, look for destination now
                Vertex<T,N> destinationVertex = head;
                while (destinationVertex!=null)	{
                    if ( destinationVertex.vertexInfo.compareTo( destination ) == 0 )   {
                        // Reached destination vertex, add edge here
                        Edge<T,N> currentEdge = sourceVertex.firstEdge;
                        Edge<T,N> newEdge = new Edge<>(destinationVertex,w,  currentEdge);
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

    public boolean hasEdge(T source, T destination) {
        if (head==null)
            return false;
        if (!hasVertex(source) || !hasVertex(destination))
            return false;
        Vertex<T,N> sourceVertex = head;
        while (sourceVertex!=null)	{
            if ( sourceVertex.vertexInfo.compareTo( source ) == 0 )   {
                // Reached source vertex, look for destination now
                Edge<T,N> currentEdge = sourceVertex.firstEdge;
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


    public N getEdgeWeight(T source, T destination) {
        N notFound=null;
        if (head==null)
            return notFound;
        if (!hasVertex(source) || !hasVertex(destination))
            return notFound;
        Vertex<T,N> sourceVertex = head;
        while (sourceVertex!=null)	{
            if ( sourceVertex.vertexInfo.compareTo( source ) == 0 )   {
                // Reached source vertex, look for destination now
                Edge<T,N> currentEdge = sourceVertex.firstEdge;
                while (currentEdge != null) {
                    if (currentEdge.toVertex.vertexInfo.compareTo(destination)==0)
                        // destination vertex found
                        return currentEdge.weight;
                    currentEdge=currentEdge.nextEdge;
                }
            }
            sourceVertex=sourceVertex.nextVertex;
        }
        return notFound;
    }



    public ArrayList<T> getNeighbours (T v)  {
        if (!hasVertex(v))
            return null;
        ArrayList<T> list = new ArrayList<T>();
        Vertex<T,N> temp = head;
        while (temp!=null)	{
            if ( temp.vertexInfo.compareTo( v ) == 0 )   {
                // Reached vertex, look for destination now
                Edge<T,N> currentEdge = temp.firstEdge;
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
        Vertex<T,N> temp=head;
        while (temp!=null) {
            System.out.print("# " + temp.vertexInfo + " : " );
            Edge<T,N> currentEdge = temp.firstEdge;
            while (currentEdge != null) {
                System.out.print("[" + temp.vertexInfo + "," + currentEdge.toVertex.vertexInfo +"] " );
                currentEdge=currentEdge.nextEdge;
            }
            System.out.println();
            temp=temp.nextVertex;
        }
    }

}

class Vertex<T extends Comparable<T>, N extends Comparable <N>> {
    T vertexInfo;
    int indeg;
    int outdeg;
    Vertex<T,N> nextVertex;
    Edge<T,N> firstEdge;

    public Vertex() {
        vertexInfo=null;
        indeg=0;
        outdeg=0;
        nextVertex = null;
        firstEdge = null;
    }

    public Vertex(T vInfo, Vertex<T,N> next) {
        vertexInfo = vInfo;
        indeg=0;
        outdeg=0;
        nextVertex = next;
        firstEdge = null;
    }
}


class Edge<T extends Comparable<T>, N extends Comparable <N>> {
    Vertex<T,N> toVertex;
    N weight;
    Edge<T,N> nextEdge;

    public Edge()	{
        toVertex = null;
        nextEdge = null;
    }

    public Edge(Vertex<T,N> destination,N w, Edge<T,N> a)	{
        toVertex = destination;
        nextEdge = a;
    }

}
