import java.util.ArrayList;

public class User {
    UserCredentials credential;
    Coordinate coordinate;
    int serverIndex;

    User(String username, String password, Coordinate coordinate, int serverIndex){
        credential = new UserCredentials(username, password);
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

class UserCredentials {
    public String username;
    public String password;

    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
