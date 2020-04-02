package model.adt;

import java.util.*;

public class Heap<T7, T8> implements IHeap<T7, T8>
{
    private HashMap<T7, T8> dict;
    private Stack<Integer> freeAdresses;
    private int index;
    public Heap()
    {
        dict = new HashMap<>();
        freeAdresses = new Stack<>();
        index = 1;

    }

    public HashMap<T7, T8> getHeap(){return dict;}

    public int getCurrentAddress() { return index;}

    @Override
    public void put(T7 key, T8 value) {
        dict.put(key , value);
        if(freeAdresses.isEmpty())
            index = dict.size() + 1;
        else
            index = freeAdresses.pop();
    }

    @Override
    public T8 getFromAddress(T7 key) {
        return dict.get(key);
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for(T7 key: dict.keySet())
            text.append(key).append(": ").append(dict.get(key)).append("\n");
        return text.toString();
    }

    @Override
    public void updateHeap(T7 key, T8 newValue) {
        dict.remove(key);
        dict.put(key, newValue);
    }

    public void setHeap(HashMap<T7,T8> newHeap)
    {
        dict = newHeap;
    }

    @Override
    public Collection<T8> values()
    {
        return dict.values();
    }
}
