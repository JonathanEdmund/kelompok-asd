public class Coordinate {
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
