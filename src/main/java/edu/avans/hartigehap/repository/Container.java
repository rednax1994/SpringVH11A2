package edu.avans.hartigehap.repository;

import java.util.List;

import edu.avans.hartigehap.domain.Order;

public interface Container {
    public Iterator getIterator(List<Order> list);
}
