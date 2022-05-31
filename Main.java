import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<User> userList = new ArrayList<>();
    static ArrayList<Server> serverList = new ArrayList<>();
    static ArrayList<Coordinate> points = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static Graph graph;


    public static void main(String[] args) { while (handle()) { } }

    private static boolean handle() {
        int code;

        ClearConsole();
        System.out.println("PROGRAM FOR FINDING THE NEAREST SERVER");
        System.out.println("==========================================");
        System.out.println();
        System.out.println("Please select a menu: ");
        System.out.println(" 1. Input server/BTS ");
        System.out.println(" 2. Add user");
        System.out.println(" 3. Remove user");
        System.out.println(" 4. Exit");

        code = scanner.nextInt();

        switch (code){
            case 1:
                ClearConsole();
                addServer();

                break;
            case 2:
                ClearConsole();

                if(graph == null) {
                    System.out.println("Graph is empty. Please add a server.");

                    scanner.nextLine();
                    waitForEnter();

                    break;
                }

                addUser();

                break;
            case 3:
                ClearConsole();

                if(userList.isEmpty()) {
                    System.out.println("User not found. Please add a user.");
    
                    scanner.nextLine();
                    waitForEnter();

                    break;
                }

                removeUser();

                break;
            case 4:
                return false;
            default:
                ClearConsole();

                System.out.println("Error! Wrong input, please choose between 1, 2, 3, or 4! ");
        }

        return true;
    }

    static void addServer() {
        int V, E;

        System.out.print("Please add the number of server/BTS: ");

        V = scanner.nextInt(); // V = jumlah server dan BTS

        System.out.print("Please enter the number of connected server/BTS pairs: ");

        E = scanner.nextInt(); // E = Jumlah edge

        scanner.nextLine();

        System.out.println();

        graph = new Graph(V); // G = graph

        for (int i = 0; i < V; i++) {
            int x, y, c, nu;
            String name;

            System.out.print("Enter server "+ (i+1) + " name: ");
            name = scanner.nextLine();

            System.out.print("Enter server coordinates: " );

            x = scanner.nextInt();

            y = scanner.nextInt();

            points.add(new Coordinate(x, y));

            System.out.print("Enter server capacity and number of users: ");
            
            c = scanner.nextInt();

            nu = scanner.nextInt();

            scanner.nextLine();

            serverList.add(new Server(name, c, nu));

            System.out.println();

        }


        for (int i = 0; i < E; i++) {
            int u, v;

            System.out.print("Enter the connected server/BTS pair: ");

            graph.addEdge(
                u = scanner.nextInt()-1,
                v = scanner.nextInt()-1,
                points.get(u).distance(points.get(v))
            );
        }
        
        scanner.nextLine();
        waitForEnter();
    }

        
    static void addUser() {
        String username, password;
        int Q, x, y;

        System.out.print("Enter the number of user: ");

        Q = scanner.nextInt();

        scanner.nextLine();

        for(int i = 0; i < Q; i++) {

            System.out.print("Username: ");

            username = scanner.nextLine();

            System.out.print("Password: ");
        
            password = scanner.nextLine();

            System.out.print("Coordinate: ");

            x = scanner.nextInt();
            y = scanner.nextInt();
            
            Coordinate src = new Coordinate(x, y);

            graph.addVertex(new ArrayList<>());
            int v = graph.V; // vertex size
            

            // Iterasi mencari server terdekat dari user
            double minDistance = Double.MAX_VALUE;
            int serverIndex = -1;

            for(int k = 0; k < v-1; k++){
                double distance = src.distance(points.get(k));
    
                if(minDistance > distance){
                    minDistance = distance;
                    serverIndex = k;
                }
            }

            // add edge baru antara user dengan server terdekat
            graph.addEdge(v-1, serverIndex, minDistance);

            // store dijkstra graph
            double[] a = Graph.dijkstra(v , graph.graph, v-1);

            double min = a[0];
            int minIndex = 0; // v = 6

            // mencari server terdekat yang tidak penuh
            ArrayList<Integer> route = new ArrayList<>();

            for(int j = 0; j<v-1; j++) {
                // find minimum distance when server capacity is not full
                if(min > a[j]) {
                    if(!serverList.get(j).isFull()){
                        min = a[j]; 
                        minIndex = j;
                    } else {
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
            System.out.println();
            Server connected = serverList.get(minIndex);
            System.out.println("Current server: " + connected.name 
                                                  + connected.getCurrentCapacity());

            System.out.print("Route: ");
            System.out.print("USER");
            for(int r: route) System.out.print(" -> "+ serverList.get(r).name);
            System.out.println("");

            // remove vertex user dari graph
            graph.graph.remove(v-1);
            graph.V--;

            System.out.println();
            scanner.nextLine();
        }

        waitForEnter();

    }

    static void removeUser() {

        // Print user list
        System.out.println("User List: "); 

        for (int i = 0; i<userList.size(); i++){
            System.out.println( (i+1) + ". " + userList.get(i).credential.username  + "\t"
            + userList.get(i).getServer(serverList));
        }
        
        System.out.print("Select user : ");
        int option = scanner.nextInt() - 1;

        scanner.nextLine();

        System.out.println(userList.get(option).credential.password);
        System.out.print("Password: ");

        String password = scanner.nextLine();

        if (password.equals(userList.get(option).credential.password)) {
            User user = userList.get(option);

            System.out.println("Correct Password");

            // Decrement server capacity
            serverList.get(user.serverIndex).current--;

            // Remove user from userList
            userList.remove(option);
            
            waitForEnter();

        } else {

            System.out.println("Incorrect Password");

            waitForEnter();
        }

    }

    public static void waitForEnter() {

        System.out.print("Press Enter to continue . . .");

        scanner.nextLine();
    }

    public static void ClearConsole() {
        try {
            // Check the current operating system.
            String operatingSystem = System.getProperty("os.name"); 
              
            if(operatingSystem.contains("Windows")){        
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
    
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            } 
        } catch(Exception e) {
            System.out.println(e);
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