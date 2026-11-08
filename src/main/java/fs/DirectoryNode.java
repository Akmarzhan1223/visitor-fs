package fs;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class DirectoryNode implements Element {
    private final String name;
    private final Map<String, Element> children = new LinkedHashMap<>();
    private DirectoryNode parent;

    public DirectoryNode(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be empty");
        if (name.contains("/")) throw new IllegalArgumentException("Name cannot contain /");
        this.name = name;
    }

    @Override
    public String name() { return name; }

    public List<Element> children() {
        return List.copyOf(children.values());
    }

    public DirectoryNode add(Element child) {
        if (child == null) throw new IllegalArgumentException("Child cannot be null");
        if (children.containsKey(child.name())) {
            throw new IllegalArgumentException("Duplicate name: " + child.name());
        }
        child.onAttachTo(this);
        children.put(child.name(), child);
        return this;
    }

    @Override
    public void onAttachTo(DirectoryNode newParent) {
        if (this.parent != null) {
            throw new IllegalArgumentException("Element already has a parent");
        }
        for (DirectoryNode cur = newParent; cur != null; cur = cur.parent) {
            if (cur == this) {
                throw new IllegalArgumentException("Cycle detected!");
            }
        }
        this.parent = newParent;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}