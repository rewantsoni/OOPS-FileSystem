import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FileSystem {

    private final String name;
    private final Boolean isDir;
    private FileSystem parent;
    private final List<FileSystem> subFiles;
    private static final Stack<FileSystem> curDir = new Stack<>();
    private static final StringBuilder pwd = new StringBuilder();
    private final StringBuilder data = new StringBuilder();

    FileSystem(String name) {
        this.name = name;
        this.isDir = true;
        this.parent = this;
        subFiles = new ArrayList<>();
        curDir.push(this);
        pwd.append(name);
    }

    private FileSystem(String name, boolean isDir, FileSystem parent) {
        this.name = name;
        this.isDir = isDir;
        subFiles = new ArrayList<>();
        if (isDir) this.parent = parent;
    }

    public List<String> ls() {
        List<String> res = new ArrayList<>();
        if (curDir.peek().subFiles.isEmpty()) return res;

        for (FileSystem child : curDir.peek().subFiles) {
            if (child.isDir) {
                res.add("d-" + child.name);
            } else {
                res.add("f-" + child.name);
            }
        }
        return res;
    }

    public void mkdir(String name) throws IllegalAccessException {
        if (hasFile(name, true) != -1) throw new IllegalAccessException("directory already exist");
        addItem(name, true);
    }

    public void touch(String name) throws IllegalAccessException {
        if (hasFile(name, false) != -1) throw new IllegalAccessException("file already exist");
        addItem(name, false);
    }

    public FileSystem cd(String name) throws IllegalAccessException {
        if (name.equals("..")) {
            if (curDir.size() > 1) {
                pwd.delete(pwd.lastIndexOf("/"), pwd.capacity());
                curDir.pop();
            }
            return parent;
        }
        for (FileSystem child : curDir.peek().subFiles) {
            if (child.name.equals(name) && child.isDir) {
                curDir.push(child);
                pwd.append("/").append(name);
                return child;
            }
        }
        throw new IllegalAccessException("no such directory");
    }

    public String pwd() {
        return pwd.toString();
    }

    public void vi(String name, String data) throws IllegalAccessException {
        int i = hasFile(name, false);
        if (i == -1) {
            touch(name);
            i = subFiles.size() - 1;
        }
        curDir.peek().subFiles.get(i).insert(data);
    }

    public String cat(String name) throws IllegalAccessException {
        int i = hasFile(name, false);
        if (i == -1) throw new IllegalAccessException("no such file");
        return curDir.peek().subFiles.get(i).showContents();
    }

    public void rm(String name) {
        int i = hasFile(name, false);
        if (i != -1) {
            subFiles.remove(i);
        }
    }

    public void rm_rf(String name){
        int i = hasFile(name, true);
        if (i != -1) {
            subFiles.remove(i);
        }
    }

    private void addItem(String name, boolean isDir) {
        FileSystem cur = curDir.pop();
        cur.subFiles.add(new FileSystem(name, isDir, cur));
        curDir.push(cur);
    }

    private void insert(String d) {
        this.data.append(d);
    }

    private String showContents() {
        return this.data.toString();
    }

    private int hasFile(String name, boolean isDirectory) {
        int i = 0;
        for (FileSystem subFile : curDir.peek().subFiles) {
            if (subFile.name.equals(name) && subFile.isDir == isDirectory) {
                return i;
            }
            i++;
        }
        return -1;
    }
}
