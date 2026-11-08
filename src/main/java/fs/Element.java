package fs;

public interface Element {
    String name();
    void accept(Visitor visitor);
    default void onAttachTo(DirectoryNode parent) {}
}