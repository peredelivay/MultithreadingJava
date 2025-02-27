public class MyFile {
    String type;
    int size;

    public MyFile(String type, int size) {
        this.type = type;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "File:" + "{type=" + type + ", size=" + size + "}";
    }
}
