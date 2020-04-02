package model.adt;

import model.MyException;
import model.value.StringValue;

import java.util.Set;

public interface IFileTable<T5, T6>
{
    public void add(T5 key, T6 val);
    public void deleteEntry(StringValue id);
    public T6 lookup(StringValue id) throws MyException;
    public boolean verify(T5 id);
    public Set<T5> getFiles();
}
