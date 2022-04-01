package de.bimalo.tree;

import java.io.Serializable;

public interface MutableTree<T extends Serializable> extends Tree<T> {

    boolean add(T parent, T node);

    boolean remove(T node);
}

