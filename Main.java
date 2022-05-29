import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    static ArrayList<UserCredentials> list = new ArrayList<>();
    static ArrayList<Coordinate> points = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static WeightedGraph G;

    private static class UserCredentials {
        public String username;
        public String password;

        public UserCredentials(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    private static class Coordinate {
        public int x;
        public int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) { while (handle()) {} }

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
                addUser(list);
                break;
            case 3:
                removeUser(list);
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

        System.out.print("Please add the number off server/BTS: ");
        V = scanner.nextInt(); // V = jumlah server dan BTS

        System.out.print("Please enter the number of connected server/BTS pairs: ");
        E = scanner.nextInt(); // E = Jumlah edge
        System.out.println();

        G = new WeightedGraph(V); // G = graph

        for (int i = 0; i < V; i++) {
            int x, y;

            System.out.print("Enter server coordinates/BTS " + (i+1) + ": ");

            x = scanner.nextInt();

            y = scanner.nextInt();

            points.add(new Coordinate(x, y));
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

        
    static void addUser(ArrayList<UserCredentials> list) {
        String username, password;
        int Q;

        System.out.print("Enter the number of user: ");
        Q = scanner.nextInt();

        for(int i = 0; i < Q; i++){
            System.out.print("Username: ");

            username = scanner.next();

            System.out.print("Password: ");
        
            password = scanner.next();

            list.add(new UserCredentials(username, password));
        }
        

    }

    static void removeUser(ArrayList<UserCredentials> list) {
        System.out.println("list user //edit"); // list user dalam servernya
        for (int i = 0; i<list.size(); i++){
            System.out.println( (i+1) + ". " + list.get(i).username);
        }
        System.out.print("Pilih user (angka): ");
        int option = scanner.nextInt() - 1;

        System.out.print("Password: ");
        String password = scanner.next();

        if (list.get(option).password == password){
            list.remove(list.get(option));
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