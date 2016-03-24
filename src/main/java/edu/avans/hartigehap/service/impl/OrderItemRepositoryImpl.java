package edu.avans.hartigehap.service.impl;

import java.util.List;

import edu.avans.hartigehap.domain.Order;
import edu.avans.hartigehap.repository.Container;
import iterator.Iterator;

public class OrderItemRepositoryImpl implements Container {
    
    @SuppressWarnings("rawtypes")
    @Override
    public Iterator getIterator(List<Order> list) {
        return new NameIterator(list);
    }
    
    @SuppressWarnings("rawtypes")
    private class NameIterator implements Iterator {
        
        int index;
        List<Order> list;
        
        public NameIterator(List<Order> list) {
            this.list = list;
        }
        
        @Override
        public boolean hasNext() {
            
            if (index < list.size()) {
                return true;
            }
            return false;
        }
        
        @Override
        public Order next() {
            
            if (hasNext()) {
                return list.get(index++);
            }
            return null;
        }
    }
}