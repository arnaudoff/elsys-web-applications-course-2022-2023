public class Startup {
    public static void killPolician(Politician a) {
        a.talk();
        // kill
    }

    public static void main() {
        killPolician(new LeftPolitician());
    }
}
