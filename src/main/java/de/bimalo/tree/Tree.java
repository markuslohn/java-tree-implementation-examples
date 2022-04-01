package de.bimalo.tree;

import java.io.Serializable;
import java.util.List;

public interface Tree<T extends Serializable> extends Serializable {

    List<T> getRoots();

    T getParent(T node);

    public List<T> getChildren(T node);
}
