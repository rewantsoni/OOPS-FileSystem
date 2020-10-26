public class Runner {

    public static void main(String[] args) {

        FileSystem fs = new FileSystem("rey");

        try {
            fs.mkdir("abd");
            fs.mkdir("re");
            fs.touch("abc");
            fs.vi("ab","New afkdvdvdv");
            System.out.println(fs.cat("ab"));
            System.out.println(fs.ls());
            fs.rm("abc");
            fs.rm_rf("re");
            System.out.println(fs.ls());
            System.out.println(fs.pwd());
            fs = fs.cd("abd");
            System.out.println(fs.pwd());
            System.out.println(fs.ls());
            fs.mkdir("apple");
            fs.mkdir("orange");
            System.out.println(fs.ls());
            fs = fs.cd("..");
            System.out.println(fs.pwd());
            fs = fs.cd("..");
            System.out.println(fs.pwd());
            fs.touch("hey");
            System.out.println(fs.ls());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
