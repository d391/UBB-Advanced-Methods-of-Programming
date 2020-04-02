package model.expressions;

import model.MyException;
import model.adt.Heap;
import model.adt.MyIDictionary;
import model.type.Type;
import model.value.Value;

public interface Exp
{
    Value eval(MyIDictionary<String, Value> tbl, Heap<Integer, Value> hp) throws MyException;
    Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;
}
