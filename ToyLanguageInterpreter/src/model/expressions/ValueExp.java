package model.expressions;

import model.adt.Heap;
import model.type.Type;
import model.value.Value;
import model.MyException;
import model.adt.MyIDictionary;

public class ValueExp implements Exp
{
    private Value e;
    public ValueExp(Value _e) {e = _e;}

    @Override
    public String toString() {
        return e.toString();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, Heap<Integer, Value> hp) throws MyException {
        return e;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

}
