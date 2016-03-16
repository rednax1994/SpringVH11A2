package iterator;

import java.util.List;

public class MyIterator<E> implements Iterator<E> {
    
    int index;
    List<E> list;
    
    public MyIterator(List<E> list) {
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
    public E next() {
        
        if (this.hasNext()) {
            return list.get(index++);
        }
        return null;
    }
}
