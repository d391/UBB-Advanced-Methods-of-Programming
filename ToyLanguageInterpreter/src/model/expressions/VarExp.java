package model.expressions;

import model.adt.Heap;
import model.type.Type;
import model.value.Value;
import model.MyException;
import model.adt.MyIDictionary;


public class VarExp implements Exp
{
    private String id;
    public VarExp(String _id) {id = _id;}

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, Heap<Integer, Value> hp) throws MyException {
        return tbl.lookup(id);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }
}
