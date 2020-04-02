package model.adt;

import java.util.ArrayList;

public class MyList<T4> implements MyIList<T4> {
    private ArrayList<T4> lst = new ArrayList<T4>();

    @Override
    public void add(T4 v) {
        lst.add(v);
    }

    @Override
    public ArrayList<T4> getList() {
        return lst;
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for(T4 txt: lst)
            text.append(txt).append("\n");
        return text.toString();
    }
}
