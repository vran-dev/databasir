package com.databasir.core.doc.factory;

public interface Sortable<T extends Sortable<?>> extends Comparable<T> {

    /**
     * @return priority, min -> max means low -> high
     */
    default int order() {
        return Integer.MIN_VALUE;
    }

    @Override
    default int compareTo(T o) {
        return Integer.compare(this.order(), o.order());
    }
}
