package model.adt;

import java.util.Collection;
import java.util.HashMap;

public interface IHeap<T7, T8>
{
    public HashMap<T7, T8> getHeap();
    public void put(T7 key, T8 value);
    public int getCurrentAddress();
    public T8 getFromAddress(T7 key);
    public void updateHeap(T7 key, T8 newValue);
    public void setHeap(HashMap<T7,T8> newHeap);

    public Collection<T8> values();
}
