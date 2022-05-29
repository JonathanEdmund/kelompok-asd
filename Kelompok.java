import java.util.Scanner;
import java.util.ArrayList;

public class Kelompok {
    static ArrayList<User> userList = new ArrayList<>();
    static ArrayList<Server> serverList = new ArrayList<>();
    static ArrayList<Coordinate> points = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static WeightedGraph G;


    public static void main(String[] args) { while (handle()){} }

    private static class Server{
        String name;
        int capacity, current;

        Server(String n, int c, int a){
            name = n;
            capacity = c;
            current = a;
        }

        public boolean isFull(){
            return current == capacity;
        }

        public String getCurrentCapacity(){
            return "("+ current + "/" + capacity + ")";
        }

    }

    private static class UserCredentials {
        public String username;
        public String password;

        public UserCredentials(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    private static class User {
        UserCredentials credential;
        Coordinate coordinate;
        int serverIndex;

        User(String username, String password, Coordinate coordinate, int serverIndex){
            credential = new UserCredentials(username, password);
            this.coordinate = coordinate;
            this.serverIndex = serverIndex;
        }

        public String getServer(){
            return serverList.get(serverIndex).name + serverList.get(serverIndex).getCurrentCapacity();
        }

        public Coordinate getCoordinate(){
            return coordinate;
        }

    }

    private static class Coordinate {
        public int x;
        public int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public static double distance(Coordinate v1, Coordinate v2){
            return Math.hypot(v1.x - v2.x, v1.y - v2.y);
        }
    }

    private static boolean handle() {
        int code;

        System.out.println("PROGRAM FOR FINDING THE NEAREST SERVER");
        System.out.println("==========================================");
        System.out.println();
        System.out.println("Please select a menu: ");
        System.out.println(" 1. Input server/BTS ");
        System.out.println(" 2. Sign in");
        System.out.println(" 3. Sign out");
        System.out.println(" 4. Exit");

        code = scanner.nextInt();

        switch (code){
            case 1:
                addServer();
                break;
            case 2:
                addUser();
                break;
            case 3:
                removeUser();
                break;
            case 4:
                return false;
            default:
                System.out.println("Error! wrong input, please choose between 1, 2, 3, or 4! ");
        }
      return true;
    }

    static void addServer() {
        int V, E;

        System.out.print("Please add the number of server/BTS: ");
        V = scanner.nextInt(); // V = jumlah server dan BTS

        System.out.print("Please enter the number of connected server/BTS pairs: ");
        E = scanner.nextInt(); // E = Jumlah edge
        System.out.println();

        G = new WeightedGraph(V); // G = graph

        for (int i = 0; i < V; i++) {
            int x, y, c, nu;
            String name;

            System.out.print("Enter server "+ (i+1) + " name: ");
            name = scanner.next();

            System.out.print("Enter server coordinates :" );

            x = scanner.nextInt();


            y = scanner.nextInt();

            points.add(new Coordinate(x, y));

            System.out.println("Enter server capacity + number of users (0 if none): ");
            
            c = scanner.nextInt();

            nu = scanner.nextInt();

            serverList.add(new Server(name, c, nu));

        }


        for (int i = 0; i < E; i++) {
            int u, v;
            System.out.print("Enter the connected server/BTS pair: ");

            G.addEdge(
                u = scanner.nextInt(),
                v = scanner.nextInt(),
                Math.hypot(points.get(u).x - points.get(v).x, points.get(u).y - points.get(v).y)
            );
        }
    }

        
    static void addUser() {
        String username, password;
        int Q, x, y;

        System.out.print("Enter the number of user: ");
        Q = scanner.nextInt();

        for(int i = 0; i < Q; i++) {
            System.out.print("Username: ");

            username = scanner.next();

            System.out.print("Password: ");
        
            password = scanner.next();

            System.out.print("Coordinate: ");

            x = scanner.nextInt();
            y = scanner.nextInt();

            Coordinate src = new Coordinate(x, y);

            // WeightedGraph g = (WeightedGraph)G.clone();
            G.addVertex(new ArrayList<>());
            int v = G.V; // current vertex
            

            // iterasi mencari server terdekat dr user
            double minDistance = Double.MAX_VALUE; int serverIndex = -1;
            for(int k = 0; k < v-1; k++){
                double distance = Coordinate.distance(points.get(k), src);
                if(minDistance > distance){
                    minDistance = distance;
                    serverIndex = k;
                }
            }

            // add edge baru antara user dengan server terdekat
            G.addEdge(v-1, serverIndex, minDistance);


            double[] a = WeightedGraph.dijkstra(v , G.graph, v-1);

            double min = a[0];
            int minIndex = -1; // v = 6

            // mencari server terdekat yang tidak penuh
            ArrayList<Integer> route = new ArrayList<>();
            for(int j = 0; j<v-1; j++){
                //find minimum distance when server capacity is not full
                if(min > a[j]) {
                    if(!serverList.get(j).isFull()){
                        min = a[j]; 
                        minIndex = j;
                    }else {
                        // if server is full, add index to route
                        route.add(j);
                    }
                }
            }   

            route.add(minIndex);

            // add user to userList + increment server current
            userList.add(new User(username, password, src, minIndex));
            serverList.get(minIndex).current++;
            
            // Output
            Server connected = serverList.get(minIndex);
            System.out.println("Current server: " + connected.name 
            + connected.getCurrentCapacity() );

            System.out.print("Route: ");
            System.out.print("USER");
            for(int r: route) System.out.print(" -> "+ serverList.get(r).name);
            System.out.println("");

            // remove vertex user dari graph
            G.graph.remove(v-1);
            G.V--;
        }
        

    }

    static void removeUser() {
        System.out.println("User List: "); // list user dalam servernya
        for (int i = 0; i<userList.size(); i++){
            System.out.println( (i+1) + ". " + userList.get(i).credential.username  + "\t"
            + userList.get(i).getServer());
        }
        System.out.print("Pilih user (angka): ");
        int option = scanner.nextInt() - 1;
        System.out.println(userList.get(option).credential.password);
        System.out.print("Password: ");
        String password = scanner.next();

        if (password.equals(userList.get(option).credential.password)){
            System.out.println("Correct Password");
            User user = userList.get(option);

            // decrement server capacity
            serverList.get(user.serverIndex).current--;

            // remove user from userList
            userList.remove(option);
            
        } else {
            System.out.println("Incorrect Password");
        }

    }

}
        

/*
 * 
 * start, user pilih ()
 * 1. Input server/BTS (banyak)
 * - jumlah server
 * - nama + koordinat +kapasitas masing2
 * - edge yang terhubung (for)
 * 2. Menambah user
 * - input koordinat user
 * - input terhubung ke server mana aja
 * - username & password
 * (store user)
 * 3.Mengeluarkan user
 * - tampil user + server
 * -
 * - keluarkan user dari server
 * 
 */

/*
    

*/