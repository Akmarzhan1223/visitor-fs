package fs;

public final class Demo {
    public static void main(String[] args) {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode docs = new DirectoryNode("docs");
        DirectoryNode imgs = new DirectoryNode("imgs");

        docs.add(new FileNode("a.txt", 100));
        docs.add(new FileNode("b.txt", 250));
        imgs.add(new FileNode("pic.png", 5000));

        root.add(docs).add(imgs).add(new FileNode("readme.md", 10));

        System.out.println("=== FILE SYSTEM DEMO ===");

        TotalSizeVisitor sizeVisitor = new TotalSizeVisitor();
        root.accept(sizeVisitor);
        System.out.println("1. Total Size: " + sizeVisitor.totalSize() + " bytes");

        SearchVisitor searchVisitor = new SearchVisitor(name -> name.endsWith(".txt"), 150);
        System.out.println("2. Search results: " + searchVisitor.search(root));

        StatisticsVisitor statsVisitor = new StatisticsVisitor();
        root.accept(statsVisitor);
        System.out.println("3. Statistics: Files=" + statsVisitor.fileCount() +
                ", Dirs=" + statsVisitor.directoryCount() +
                ", MaxDepth=" + statsVisitor.maxDepth());

        System.out.println("========================");
    }
}