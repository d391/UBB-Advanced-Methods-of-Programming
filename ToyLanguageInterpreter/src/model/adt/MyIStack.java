package model.adt;

import java.util.Stack;

public interface MyIStack<T>
{
    public T pop();
    public void push(T v);
    public T top();
    public boolean isEmpty();
    public int size();
    public Stack<T> getStack();
}
