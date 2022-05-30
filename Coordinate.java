public class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Coordinate other){
        return Math.hypot(other.x - x, other.y - y);
    }
}
