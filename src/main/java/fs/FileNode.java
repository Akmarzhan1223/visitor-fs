package fs;

public final class FileNode implements Element {
    private final String name;
    private final long size;

    public FileNode(String name, long size) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be empty");
        if (name.contains("/")) throw new IllegalArgumentException("Name cannot contain /");
        if (size < 0) throw new IllegalArgumentException("Size cannot be negative");
        this.name = name;
        this.size = size;
    }

    @Override
    public String name() { return name; }

    public long size() { return size; }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}