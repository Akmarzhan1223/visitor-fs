package fs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyTests {

    @Test
    void testAllVisitors() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode docs = new DirectoryNode("docs");
        DirectoryNode imgs = new DirectoryNode("imgs");

        docs.add(new FileNode("a.txt", 100));
        docs.add(new FileNode("b.txt", 250));
        imgs.add(new FileNode("pic.png", 5000));

        root.add(docs).add(imgs).add(new FileNode("readme.md", 10));

        TotalSizeVisitor sizeVisitor = new TotalSizeVisitor();
        root.accept(sizeVisitor);
        assertEquals(5010, sizeVisitor.totalSize());

        SearchVisitor searchVisitor = new SearchVisitor(name -> name.endsWith(".txt"), 150);
        assertEquals(java.util.List.of("docs/b.txt"), searchVisitor.search(root));

        StatisticsVisitor statsVisitor = new StatisticsVisitor();
        root.accept(statsVisitor);
        assertEquals(4, statsVisitor.fileCount());
        assertEquals(3, statsVisitor.directoryCount());
        assertEquals(2, statsVisitor.maxDepth());
    }

    @Test
    void testInvalidMutations() {
        assertThrows(IllegalArgumentException.class, () -> new FileNode("bad", -5));

        DirectoryNode dir = new DirectoryNode("dir");
        dir.add(new FileNode("file", 10));
        assertThrows(IllegalArgumentException.class, () -> dir.add(new FileNode("file", 20)));
    }
}