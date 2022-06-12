package Navigation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Navigation {

    public static void main(String[] args) throws FileNotFoundException {
        
        Scanner sc = new Scanner(new FileInputStream("cases.txt"));
        HashMap< String, Integer> list = new HashMap< String, Integer>(); //nodes and edges
        //Scanner sc = new Scanner(System.in);
        int i = 0;
        int k = 0;
        int m = 0;
        int tracksnum = sc.nextInt(); //scan the number of tracks in the cases.txt
        sc.nextLine();

        Graph graph = new Graph(tracksnum); //Create graph
        String[] trains = new String[tracksnum]; //Create string for train 
        for (int count = 0; count < tracksnum; count++) {
            trains[count] = sc.nextLine(); //Read line from cases.txt and put in on array as train
        }

        for (int j = 0; j < trains.length; j++) {

            String[] cityname = trains[j].split(" => ");  //Split the cityname

            if (cityname.length == 2) {

                while(k<2) {

                    if (list.containsKey(cityname[0]) && list.containsKey(cityname[1])) { 
                        graph.addEdge(list.get(cityname[0]), list.get(cityname[1]));
                    } else {
                        if (list.containsKey(cityname[0])) {
                            list.put(cityname[1], i);
                            graph.addEdge(list.get(cityname[0]), list.get(cityname[1]));
                            i++;

                        } else if (list.containsKey(cityname[1])) {
                            list.put(cityname[0], i);
                            graph.addEdge(list.get(cityname[0]), list.get(cityname[1]));
                            i++;
                        } else {
                            list.put(cityname[0], i);
                            list.put(cityname[1], ++i);
                            graph.addEdge(list.get(cityname[0]), list.get(cityname[1]));
                            i++;
                        }
                    }
                    k++;
                }

            }
        }

        System.out.println("Number of queries: ");
        int queries = sc.nextInt(); //Read integer from cases.txt
        sc.nextLine();
        String[] qpath = new String[queries]; //total queries path source-destination that exist
        ArrayList<String> startingpoint = new ArrayList<>(); //Create String for the startingpoint
        ArrayList<String> destination = new ArrayList<>();//Create String for desination

        while(m<queries) { // Read queries line from cases.txt 
            qpath[k] = sc.nextLine();
            m++;
        }

        for (int l = 0; l < qpath.length; l++) {
            String[] station = qpath[l].split(" -> "); //split the startingpoint and destination
            //source.
            startingpoint.add(station[0]);
            destination.add(station[1]);

            graph.BFS(list.get(station[0]), list.get(station[1]));

            for (int n = 0; n < graph.getRoute().size(); n++) {
                for (Map.Entry<String, Integer> set : list.entrySet()) {

                    if (n == graph.getRoute().size() - 1) {
                        if (set.getValue() == graph.getRoute().get(n)) {
                            System.out.print(set.getKey());
                            break;
                        }
                    } else {
                        if (set.getValue() == graph.getRoute().get(n)) {
                            System.out.print(set.getKey() + "->");
                            break;
                        }
                    }
                }
            }
        }

        System.out.println("Enter startingpoint and detination: ");
        String user = sc.nextLine();

        String[] split = user.split(" -> ");
        
        if (!destination.contains(split[1])) { //If there is no destination print 
            System.out.println("Attention! this path will not lead to the destination!");
        }

        if (!startingpoint.contains(split[0])) { //If there is no starting point
            System.out.println("Attention! this path will not start from the starting terminal!");
        }

    }
    public static class Graph {
        
        private int Vertices;
        private LinkedList<Integer> adjacencyList[];
        private List<Integer> route;

        int size = 0;

        // Create a graph
        Graph(int v) {
            Vertices = v;
            route = new ArrayList<Integer>();
            adjacencyList = new LinkedList[v];
            for (int i = 0; i < v; ++i) {
                adjacencyList[i] = new LinkedList();
            }
        }

        public List<Integer> getRoute() {
            return route;
        }

        // Add edges to the graph
        void addEdge(int v, int w) {
            adjacencyList[v].add(w);

        }

        // BFS algorithm
        void BFS(int s, int d) {

            boolean visited[] = new boolean[Vertices];

            LinkedList<Integer> queue = new LinkedList();

            visited[s] = true;
            queue.add(s);

            while (!queue.isEmpty()) {
                s = queue.poll();

                route.add(s);

                if (s == d) {
                    break;
                }

                Iterator<Integer> i = adjacencyList[s].listIterator();
                while (i.hasNext()) {
                    int n = i.next();
                    if (!visited[n]) {
                        visited[n] = true;
                        queue.add(n);
                    }
                }
            }

        }

    }   
}

