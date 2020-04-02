package model.adt;

import model.MyException;

import java.util.Collection;
import java.util.Hashtable;

public interface MyIDictionary<T1, T2>
{
    public void put(T1 key, T2 val);
    public T2 lookup(T1 key) throws MyException;
    public boolean isDefined(T1 id);
    public void update(T1 key, T2 newValue);
    public boolean isEmpty();
    public Hashtable<T1, T2>  getContent();
    public MyDictionary<T1, T2>  copy();
    public Hashtable<T1, T2> getDictionary();

    public  Collection<T2> values();
}
