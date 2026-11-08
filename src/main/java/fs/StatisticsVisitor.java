package fs;

public final class StatisticsVisitor implements Visitor {
    private int files;
    private int dirs;
    private int maxDepth;
    private int depth = 0;

    @Override
    public void visit(FileNode file) {
        files++;
        maxDepth = Math.max(maxDepth, depth);
    }

    @Override
    public void visit(DirectoryNode dir) {
        dirs++;
        maxDepth = Math.max(maxDepth, depth);
        depth++;
        for (Element child : dir.children()) {
            child.accept(this);
        }
        depth--;
    }

    public int fileCount() { return files; }
    public int directoryCount() { return dirs; }
    public int maxDepth() { return maxDepth; }
}