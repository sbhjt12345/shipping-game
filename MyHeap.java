import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/** An instance is a heap of elements of type T. */
public class MyHeap<T> implements MinHeap<T> {
    private ArrayList<T> l;
    private HashMap<T,C> m;
	
    /** Constructor: an empty heap. */
    public MyHeap() {
    	l = new ArrayList<T>();
    	m = new HashMap<T,C>();
    }

    /** Return a representation of this heap. */
    public @Override String toString(){
        //TODO
    	if (l == null || l.size() < 1)
    		return "[]";
    	String str = "[";
    	int i = 0;
    	for (i = 0 ; i < l.size() - 1; i++) {
    		str = str + l.get(i) + ":" + m.get(l.get(i)).priority + ", ";
    	}
    	str = str + l.get(i) + ":" + m.get(l.get(i)).priority + "]";
        return str;
    }

    /** Remove and return the min value in this heap.
     * Precondition: The heap is not empty. */
    public @Override T poll() {
        //TODO 
    	if (size() == 0)
    		throw new IllegalArgumentException("False use");
    	T result = l.get(0);
    	swap(0,l.size() - 1);
    	l.remove(l.size() - 1);
    	bubbleDown(0);
        return result;
    }
    
    
    /** Return the position of the lower priority one*/
    private int min(int i, int j) {
    	if (m.get(l.get(i)).priority > m.get(l.get(j)).priority)
    		return j;
    	else
    		return i;	
    }
    
    private void bubbleDown(int i) {
    	int j = 2 * i + 1;
    	while (j < l.size()) {
    		if (j + 1 < l.size() && m.get(l.get(i)).priority > m.get(l.get(min(j,j+1))).priority) {
    			int temp = min (j, j+1);
    			swap(i, min(j,j+1));
    			i = temp;
    		}
    		else if (j + 1 == l.size() && m.get(l.get(i)).priority > m.get(l.get(j)).priority) {
    			swap(i, j);
    			i = j;
    		}
    		else
    			break;
    		j = 2 * i + 1;
    	}
    }

    /** Add item with priority p to this heap.
     * Throw IllegalArgumentException if an equal item is already in the heap. */
    public @Override void add(T item, double p) throws IllegalArgumentException {
        //TODO 
    	if (m.containsKey(item))
    		throw new IllegalArgumentException ("This value has existed");
    	l.add(item);
    	C c = new C(p, l.size() - 1);
    	m.put(item, c);
    	bubbleUp(l.size() - 1);
    }
    
    private void bubbleUp (int i) {
    	while (m.get(l.get(i)).priority < m.get(l.get((i-1)/2)).priority && i > 0) {
    		swap (i, (i-1)/2);
    		i = (i-1)/2;
    	}
    }
    
    /** Change position i and position j*/
    private void swap(int i, int j) {
    	T ti = l.get(i);
    	T tj = l.get(j);
    	C ci = m.get(l.get(i));
    	C cj = m.get(l.get(j));
    	l.set(i, tj);
    	m.put(ti, new C(ci.priority, cj.position));
    	l.set(j, ti);
    	m.put(tj, new C(cj.priority, ci.position));
    }

    /** Change the priority of item to p. */
    public @Override void updatePriority(T item, double p) {
        //TODO
    	if (!m.containsKey(item))
    		throw new IllegalArgumentException ("This value is not exist");
    	int po = m.get(item).position;
    	m.put(item, new C(p, po));
    	bubbleUp(po);
    	bubbleDown(po);
    }

    /** Return the size of this heap. */
    public @Override int size() {
        //TODO
        return l.size();
    }

    /** Return true iff the heap is empty. */
    public @Override boolean isEmpty() {
        //TODO
        return l.size() == 0;
    }
    
    private static class C{
    	private double priority;
    	private int position;
    	
    	private C(double pr, int po){
    		priority = pr;
    		position = po;
    	}
    }
}