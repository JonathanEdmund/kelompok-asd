public class Server {
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
