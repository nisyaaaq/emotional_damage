
import java.util.*;

public class Navigation {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        
        // Run for the number of test cases required
        int num_cases = Integer.parseInt(sc.nextLine()); //comment if test in IDE
        for (int caseNo = 0; caseNo < num_cases; caseNo++) { //comment if test in IDE
            //Scan size of location data
            int num_rails = Integer.parseInt(sc.nextLine());

            //Declaration for a place to store data
            String test, Answer = "";
            String place[] = new String[3];

            //Call Class Graph
            Graph<String> graph = new Graph<>(num_rails + num_rails);

            //ArrayList for ShortestRoute 
            ArrayList<ArrayList<Integer>> adj = new ArrayList<>(num_rails + num_rails);

            //add new ArrayList
            for (int i = 0; i < (num_rails + num_rails); i++) {
                adj.add(new ArrayList<>());
            }

//             sc.nextLine();
            //store location and data
            for (int i = 1; i <= num_rails; i++) {

                test = sc.nextLine();
                place = test.split(" => ");   //split num_rails

                //add both location to LinkedList in Graph using method
                graph.addLocation(num_rails, place[0]);
                graph.addLocation(num_rails, place[1]);

                //add Line between d1 & d2 (for LinkedList in Graph , then add edge between them (to find Shortest Route)
                if (graph.addLine(place[1], place[0]) && graph.addLine(place[0], place[1]) == true) {
                    int s = graph.hasLocationNo(place[0]);
                    int d = graph.hasLocationNo(place[1]);
                    addEdge(adj, s, d);
                }
            }
            //sc.nextLine();// comment if test in github
            int queries = sc.nextInt();

            int s, d;
            LinkedList<Integer> jalan;

            sc.nextLine(); //queries
            for (int i = 1; i <= queries; i++) {

                //for user input
                test = sc.nextLine();
                place = test.split(" -> ");   //split the string to get Location name

                graph.hasLocation(place[0]);
                graph.hasLocation(place[1]);
            
                s = graph.hasLocationNo(place[0]);
                d = graph.hasLocationNo(place[1]);

                if (s == -1) {
                    System.out.println("This path doesnt start at the starting station!");
                }
                if (d == -1) {
                    System.out.println("This path doesnt end at the destination!");
                }

                jalan = printShortestDistance(adj, s, d, (num_rails + num_rails));

                if (jalan == null) {
                    System.out.println("There is no train from " + place[0] + " to " + place[1]);
                    continue;
                } else {
                    for (int j = jalan.size() - 1; j >= 0; j--) {
                        if (j == 0) {
                            Answer += graph.getAllLocationObjects().get(jalan.get(j));
                            continue;
                        }
                        Answer += graph.getAllLocationObjects().get(jalan.get(j)) + "->";
                    }
                    System.out.println(Answer);
                    Answer = "";
                }
            }
        }
    }
    
    
    private static void addEdge(ArrayList<ArrayList<Integer>> adj, int i, int j){
        adj.get(i).add(j);
        adj.get(j).add(i);
    }
    
    // find the shortest distance and path between source vertex and destination vertex
    private static LinkedList<Integer> printShortestDistance(ArrayList<ArrayList<Integer>> adj,int s, int dest, int v){
        // predecessor[i] array stores predecessor of
        // i and distance array stores distance of i from s
        int pred[] = new int[v];
        int dist[] = new int[v];
 
        if (BFS(adj, s, dest, v, pred, dist) == false) {
            return null;
        }
 
        // LinkedList to store path
        LinkedList<Integer> path = new LinkedList<>();
        int crawl = dest;
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }
 
        
        return path;

    }
 
    private static boolean BFS(ArrayList<ArrayList<Integer>> adj, int src,int dest, int v, int pred[], int dist[]){
        // a queue to maintain queue of vertices whose adjacency list 
        // using BFS algorithm 
        LinkedList<Integer> queue = new LinkedList<>();
 
        // boolean array visited[] which stores the
        // information whether ith vertex is reached
        // at least once in the Breadth first search
        boolean visited[] = new boolean[v];
 
        // initially all vertices are unvisited
        // so v[i] for all i is false
        // and as no path is yet constructed
        // dist[i] for all i set to infinity
        for (int i = 0; i < v; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }
 
        // now source is first to be visited and
        // distance from source to itself should be 0
        visited[src] = true;
        dist[src] = 0;
        queue.add(src);
 
        //using BFS algorithm
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < adj.get(u).size(); i++) {
                if (visited[adj.get(u).get(i)] == false) {
                    visited[adj.get(u).get(i)] = true;
                    dist[adj.get(u).get(i)] = dist[u] + 1;
                    pred[adj.get(u).get(i)] = u;
                    queue.add(adj.get(u).get(i));
 
                    // stop the condition when destination is found
                    if (adj.get(u).get(i) == dest)
                        return true;
                }
            }
        }
        return false;
    }
}


class Location<T extends Comparable<T>> extends ArrayList<T> {
        
        T locationInfo;
        int indeg;
        int outdeg;
        int tag;
        int edge;
        Location<T> nextLocation;
        Line<T> firstLine;
        ArrayList<T> neighbour;
        ArrayList<Location> neighbourLocation;
        boolean visited;

        public Location() {
            this.locationInfo = null;
            this.indeg = 0;
            this.outdeg = 0;
            this.tag = 0;
            this.edge = 0;
            this.nextLocation = null;
            this.firstLine = null;
        }

        public Location(int num,T LocationInfo, Location<T> nextLocation) {
            this.locationInfo = LocationInfo;
            this.indeg = 0;
            this.outdeg = 0;
            this.tag = 0;
            this.edge = 0;
            this.nextLocation = nextLocation;
            this.firstLine = null;
            this.neighbour = new ArrayList<>(num);
            this.visited = false;
        }

        void visit() {
            visited = true;
        }

        void unvisit() {
            visited = false;
        }
    }
    
class Line<T extends Comparable<T>> {
        
        Location<T> toLocation;
        Line<T> nextLine;
        boolean visited;

        public Line() {
            this.toLocation = null;
            this.nextLine = null;
        }

        public Line(Location<T> toLocation, Line<T> nextLine) {
            this.toLocation = toLocation;
            this.nextLine = nextLine;
        }
        
        void visit() {
            visited = true;
        }

        void unvisit() {
            visited = false;
        }
    }

class Graph<T extends Comparable<T>> extends ArrayList{
    Location<T> head;
    int size;
    ArrayList<ArrayList<Integer>> adj ;


    public Graph() {
        head = null;
        size = 0;
    }

    //ArrayList for Shortest distance
    public Graph(int size) {
        this.adj = new ArrayList<>(size);
        head = null;
        this.size = 0;
        for (int i = 0; i < size; i++) {
            this.adj.add(new ArrayList<>());
        }
    }

    //method to get the class size
    public int getSize(){
        return this.size;
    }

    //Check if v is exist in the list
    public boolean hasLocation(T v){
        if(head == null)
            return false;

        //start from head
        Location<T> temp = head; 

        //keep repeating until the end of the list
        while(temp != null){
            if(temp.locationInfo.compareTo(v) == 0)//found location v
                return true;
            temp = temp.nextLocation;//keep moving to the next location if v not found
        }
        return false;//return false as there are no location v
    }

    //method to get the index of location in the array
    public int hasLocationNo(T v){
        if(head == null)
            return -1;

        //start from head
        Location<T> temp = head; 

        //keep repeating until the end of the list
        while(temp != null){
            if(temp.locationInfo.compareTo(v) == 0)//found location v
                return temp.tag;
            temp = temp.nextLocation;//keep moving to the next location until found v
        }
        return -1;//return false as there are no location v
    }
    
    //return a list of vertex using ArrayList
    public ArrayList<T> getAllLocationObjects(){
        ArrayList<T> list = new ArrayList<>();
        Location<T> temp = head;//let the cursor start from head of list
        int i=0;

        //the cursor will move to the end of the list
        while (temp != null){
            //add location to ArrayList
            list.add(temp.locationInfo);
            temp = temp.nextLocation;//go to next element in the list;
            i++;
        }
        return list;
    }

    //method to add a location v into the list
    public boolean addLocation(int num,T v){
        int no = 0;
        //heck wether location v exist in the list, if not then continue
        if(hasLocation(v) == false){
            Location<T> temp = head;//let the cursor start from head
            Location<T> newlocation = new Location<>(num,v,null);//set location v as new location (*total number of location, *location name, *ArrayList for line)

            //if there is no element in the list, location v will be the head of the list
            if(head == null){
                head = newlocation;
                head.tag = no;
            }

            else{
                Location<T> previous = head;//set another variable for set the nextLocation of the last element in the list
                //we use temp to move the list until the end of the current list
                while(temp != null){
                    no++;
                    previous = temp;
                    temp = temp.nextLocation;
                }
                //cursor will be at the end of the list
                previous.nextLocation = newlocation; //at the end the new element will be added
                previous.nextLocation.tag = no;
            }
            size++;//size of list increase by 1
            return true;
        }
        else    //location already exist
            return false;
    }

    

    //method to check the edge of locations
    public boolean hasLine(T source, T destination){
        //check wether there are location in the list
        if(head == null) 
            return false;
        //check wether source and destination is exist in the list
        if(!hasLocation(source) || !hasLocation(destination)) 
            return false;

        //set the cursor at the head of the list
        Location<T> sourceLocation = head;

        //run until sourceLocation == source 
        while(sourceLocation != null){
            //source found
            if(sourceLocation.locationInfo.compareTo(source) == 0){
                //set the cursor at the first edge of the location
                Line<T> currentLine = sourceLocation.firstLine;
                //run until last edge of location to check
                while(currentLine != null){
                    //found the destination
                    if(currentLine.toLocation.locationInfo.compareTo(destination) == 0)
                        return true;
                    currentLine = currentLine.nextLine;
                }
            }
            sourceLocation = sourceLocation.nextLocation;
        }
        return false;
    }

    //method to add edge for a existing location
    public boolean addLine(T source, T destination){
        //check wether there are locations in the list
        if(head == null) 
            return false;
        //check wether source and destination is exist in the list
        if(!hasLocation(source) || !hasLocation(destination)) //false sebab antara source and destination tak wujud
            return false;

        //to check if the is existing edge between these locations
        if(hasLine(source,destination) == true)
            return false;

        //set the cursor at the head of the list
        Location<T> sourceLocation = head;

        //run until the end of list to find the sourceLocation
        while(sourceLocation != null){
            //source found
            if(sourceLocation.locationInfo.compareTo(source) == 0){
                //set the cursor at the head of the location 
                Location<T> destinationLocation = head;

                //run until last location to to find the destinationLocation
                while(destinationLocation != null){
                    //found destination
                    if(destinationLocation.locationInfo.compareTo(destination) == 0){
                        //now set the cursor at the first edge of the sourceLocation
                        Line<T> currentLine = sourceLocation.firstLine;
                        Line<T> newLine = new Line<>(destinationLocation,currentLine);//set new Line
                        sourceLocation.firstLine = newLine;
                        sourceLocation.outdeg++;
                        destinationLocation.indeg++;
                        sourceLocation.neighbour.add(destination);//add location name to arraylist
                        return true;
                    }
                    destinationLocation = destinationLocation.nextLocation;
                }
            }
            sourceLocation = sourceLocation.nextLocation;
        }
        return false;
    }

    //method to find the locations that connected to v
    public ArrayList<T> getNeighbours(T v){
        //check wether v exist in the list
        if(!hasLocation(v))
            return null;

        ArrayList<T> list = new ArrayList<>();
        //set cursor at the head of the list
        Location<T> temp = head;

        //run until the end of list to find v
        while(temp != null){
            //location v found
            if(temp.locationInfo.compareTo(v)== 0 ){
                //set cursor at the first edge of location v
                Line<T> currentLine = temp.firstLine;

                //run until the last edge to collect data
                while(currentLine != null){
                    list.add(currentLine.toLocation.locationInfo);//add to ArrayList
                    currentLine = currentLine.nextLine;
                }
            }
            temp = temp.nextLocation;
        }
        return list;
    }

    //method to print edge for each Location using LinkedList
    public void printLines(){
        //set cursor at the head of the list
        Location<T> temp = head;
        int no = 0;

        //run until teh end of teh list
        while(temp !=null){
            System.out.println("# "+no+" "+temp.locationInfo+" : ");
            Line<T> currentLine = temp.firstLine;//place cursor at the first edge of the temp location

            //run until the last edge of the location
            while(currentLine != null){
                System.out.println("["+temp.locationInfo+" , "+currentLine.toLocation.locationInfo+"] ");
                currentLine = currentLine.nextLine;
            }
            System.out.println();
            temp = temp.nextLocation;
            no++;
        }
    }

    //method to print edge for each location but using ArrayList<E> declared in Location
    public void printLines2(int num){
        //set cursor at the head of the list
        Location<T> temp = head;
        int no = 0;

        //loop till the end of list
        while(temp !=null){
            System.out.println("# "+no+" "+temp.locationInfo+" : ");
            Line<T> currentLine = temp.firstLine;//place cursor at the first edge of the temp location

            //loop till the last edge of the location
            for(int i=0 ; i<temp.neighbour.size() ; i++){
                System.out.println("["+temp.locationInfo+" , "+temp.neighbour.get(i)+"] ");
                currentLine = currentLine.nextLine;
            }
            System.out.println();
            temp = temp.nextLocation;
            no++;
        }
    }

    public ArrayList<T> getNeighboursLocation(T v){
        if(head == null)
            return null;

        //start from head
        Location<T> temp = head; 

        //loop until the end of the list
        while(temp != null){
            if(temp.locationInfo.compareTo(v) == 0)//will return 0 if data == v
                return temp.neighbour;
            temp = temp.nextLocation;//keep moving to the next location
        }
        return null;//return false 
    }

    public Location getLocation(T v){
        //if list == null
        if(head == null)
            return null;

        //start from head
        Location<T> temp = head; 

        //loop until the end of the list
        while(temp != null){
            if(temp.locationInfo.compareTo(v) == 0)//found location v
                return temp;
            temp = temp.nextLocation;//keep moving to the next location if v not found
        }
        return null;
    }

    public ArrayList<Object> getAllLocationObjects2(){
        ArrayList<Object> list = new ArrayList<>();
        Location<T> temp = head;//start from head of list
        int i=0;

        //move to the end of the list
        while (temp != null){
            //add location 
            list.add(temp);
            temp = temp.nextLocation;//next element
            i++;
        }
        return list;
    }
}
