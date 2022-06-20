package test;

import java.io.FileInputStream;
import java.util.*;

public class Navigation {
    public static void main(String[] args) {
        String file = "C:\\Users\\Nazif Aqif\\Desktop\\DS-Assignment-2022-main\\tasks\\navigation\\cases\\0.txt";
        
        try {
            UnweightedGraph<String,Integer> graph = new UnweightedGraph<>();
            Scanner in = new Scanner(new FileInputStream(file));
            while (in.hasNextLine()){
                String line = in.nextLine();
                String[] split = line.split(" => ");
                String[] splitt = line.split(" -> ");

                if(split.length > 1) {
                    graph.addVertex(split[0]);
                    graph.addVertex(split[1]);
                    graph.addEdge(split[0],  split[1],1);
                    graph.addEdge(split[1], split[0],1);
                }

                if(splitt.length>1){
                    System.out.println(splitt[0]+" => "+splitt[1]);
                    System.out.println("Pathway :");
                    DFS(graph,splitt[0],splitt[1]);
                    System.out.println("\n");

                }

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
