package com.donkeycode.core.collections;

import java.util.LinkedList;

public class SetList<E> extends LinkedList<E> {

    private static final long serialVersionUID = -3471979908988851747L;


    @Override
    public boolean add(E objct) {
        if (this.modCount == 0) {
            return super.add(objct);
        }
        return contains(objct) ? false : super.add(objct);
    }

}
