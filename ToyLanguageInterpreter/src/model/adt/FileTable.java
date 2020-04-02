package model.adt;

import model.MyException;
import model.value.StringValue;

import java.util.Hashtable;
import java.util.Set;

public class FileTable<T5, T6> implements IFileTable<T5,T6>
{
    private Hashtable<T5, T6> dict;
    public FileTable()
    {
        dict = new Hashtable<T5, T6>();
    }

    @Override
    public void add(T5 key, T6 val) {
        dict.put(key, val);
    }

    @Override
    public void deleteEntry(StringValue id) {
        dict.remove(id);
    }

    @Override
    public T6 lookup(StringValue id) throws MyException {
        T6 v = dict.get(id);
        if(v == null)
            throw new MyException("Inexistent file!\n");
        return v;
    }

    public boolean verify(T5 id)
    {
        T6 v = dict.get(id);
        return v == null;
    }

    @Override
    public Set<T5> getFiles() {
        return dict.keySet();
    }
}
