package fs;

import java.util.*;
import java.util.function.Predicate;

public final class SearchVisitor implements Visitor {
    private final Predicate<String> nameCriterion;
    private final long minSizeExclusive;
    private final List<String> matches = new ArrayList<>();
    private final Deque<String> path = new ArrayDeque<>();

    public SearchVisitor(Predicate<String> nameCriterion, long minSizeExclusive) {
        this.nameCriterion = nameCriterion;
        this.minSizeExclusive = minSizeExclusive;
    }

    public List<String> search(DirectoryNode root) {
        reset();
        for (Element child : root.children()) {
            child.accept(this);
        }
        return results();
    }

    @Override
    public void visit(FileNode file) {
        if (nameCriterion.test(file.name()) && file.size() > minSizeExclusive) {
            String relPath = String.join("/", path) + (path.isEmpty() ? "" : "/") + file.name();
            matches.add(relPath);
        }
    }

    @Override
    public void visit(DirectoryNode dir) {
        path.addLast(dir.name());
        for (Element child : dir.children()) {
            child.accept(this);
        }
        path.removeLast();
    }

    public List<String> results() {
        List<String> copy = new ArrayList<>(matches);
        Collections.sort(copy);
        return copy;
    }

    public void reset() {
        matches.clear();
        path.clear();
    }
}