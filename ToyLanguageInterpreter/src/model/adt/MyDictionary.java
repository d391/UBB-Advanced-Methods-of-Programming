package model.adt;

import model.MyException;

import java.util.Collection;
import java.util.Hashtable;

public class MyDictionary<T1, T2> implements MyIDictionary<T1, T2>
{
    private Hashtable<T1, T2> dict;
    public MyDictionary()
    {
        dict = new Hashtable<T1,T2>();
    }

    @Override
    public void put(T1 key, T2 val)
    {
        dict.put(key, val);
    }
    public T2 lookup(T1 id) throws MyException
    {
        T2 v = dict.get(id);
        if(v == null)
            throw new MyException("Variable " + id + "is not defined!\n");
        return v;
    }

    @Override
    public boolean isDefined(T1 id)
    {
        T2 v = dict.get(id);
        return v != null;
    }

    @Override
    public void update(T1 key, T2 newValue)
    {
        dict.remove(key);
        dict.put(key, newValue);
    }

    @Override
    public boolean isEmpty() {
        return dict.isEmpty();
    }

    @Override
    public String toString()
    {
        StringBuilder text = new StringBuilder();
        for(T1 key: dict.keySet())
            text.append(key).append(": ").append(dict.get(key)).append("\n");
        return text.toString();
    }

    public Hashtable<T1, T2> getContent()
    {
        return dict;
    }

    @Override
    public MyDictionary<T1, T2> copy() {
        MyDictionary<T1, T2> di = new MyDictionary<>();
        for(T1 key : dict.keySet())
            di.put(key, dict.get(key));
        return di;
    }

    @Override
    public Hashtable<T1, T2> getDictionary() {
        return dict;
    }

    public Collection<T2> values()
    {
        return dict.values();
    }
}
