import java.util.ArrayList;


public class User {
    AuthCredentials credential;
    Coordinate coordinate;
    int serverIndex;

    public static class AuthCredentials {
        public String username;
        public String password;

        public AuthCredentials(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    User(String username, String password, Coordinate coordinate, int serverIndex){
        credential = new AuthCredentials(username, password);
        this.coordinate = coordinate;
        this.serverIndex = serverIndex;
    }

    public String getServer(ArrayList<Server> serverList ){
        return serverList.get(serverIndex).name + serverList.get(serverIndex).getCurrentCapacity();
    }

        public Coordinate getCoordinate(){
            return coordinate;
        }
}
