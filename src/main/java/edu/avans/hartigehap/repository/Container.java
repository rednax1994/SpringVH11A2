package edu.avans.hartigehap.repository;

import java.util.List;

import edu.avans.hartigehap.domain.Order;
import iterator.Iterator;

public interface Container {
    @SuppressWarnings("rawtypes")
    public Iterator getIterator(List<Order> list);
}
