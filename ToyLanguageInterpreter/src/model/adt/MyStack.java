package model.adt;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T>
{
    private Stack<T> stack;
    public MyStack()
    {
        stack = new Stack<T>();
    }

    @Override
    public T pop()
    {
        return stack.pop();
    }

    public void push(T v)
    {
        stack.push(v);
    }

    public T top()
    {
        return stack.firstElement();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public Stack<T> getStack() {
        return stack;
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for(T txt: stack)
            text.append(txt).append("\n");
        return text.toString();
    }
}
