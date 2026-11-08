package fs;

public final class TotalSizeVisitor implements Visitor {
    private long total;
    private boolean started;

    @Override
    public void visit(FileNode file) {
        total += file.size();
    }

    @Override
    public void visit(DirectoryNode dir) {
        if (!started) {
            total = 0;
            started = true;
        }
        for (Element child : dir.children()) {
            child.accept(this);
        }
        started = false;
    }

    public long totalSize() { return total; }
}