package de.bimalo.tree;

import java.io.Serializable;
import java.util.*;
import lombok.NonNull;

public final class DefaultMutableTree<T extends Serializable> implements MutableTree<T> {

    private final Map<T, T> nodeParent = new HashMap<>();
    private final LinkedHashSet<T> nodeList = new LinkedHashSet<>();

    @Override
    public boolean add(@NonNull T parent, @NonNull T node) {
        T current = parent;
        do {
            if (node.equals(current)) {
                throw new IllegalArgumentException(
                        "node must not be the same or an ancestor of the parent");
            }
        } while ((current = getParent(current)) != null);

        boolean added = nodeList.add(node);
        nodeList.add(parent);
        nodeParent.put(node, parent);
        return added;
    }

    @Override
    public boolean remove(T node) {
        if (node != null && nodeList.contains(node)) {
            for (T child : getChildren(node)) {
                remove(child);
            }
            nodeList.remove(node);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<T> getRoots() {
        return getChildren(null);
    }

    @Override
    public T getParent(@NonNull T node) {
        return nodeParent.get(node);
    }

    @Override
    public List<T> getChildren(T node) {
        List<T> children = new LinkedList<>();
        for (T currentNode : nodeList) {
            T parent = nodeParent.get(currentNode);
            if (node == null && parent == null) {
                children.add(currentNode);
            } else if (node != null && parent != null && parent.equals(node)) {
                children.add(currentNode);
            }
        }
        return children;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        dumpNodeStructure(builder, null, "- ");
        return builder.toString();
    }

    private void dumpNodeStructure(StringBuilder builder, T node, String prefix) {
        if (node != null) {
            builder.append(prefix);
            builder.append(node.toString());
            builder.append('\n');
            prefix = "  " + prefix;
        }
        for (T child : getChildren(node)) {
            dumpNodeStructure(builder, child, prefix);
        }
    }
}
