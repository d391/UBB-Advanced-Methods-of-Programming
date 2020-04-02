package model.expressions;

import model.MyException;
import model.adt.Heap;
import model.adt.MyIDictionary;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class HeapReading implements Exp
{
    private Exp exp;
    public HeapReading(Exp _exp)
    {
        exp = _exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, Heap<Integer, Value> hp) throws MyException {
        Value val = exp.eval(tbl, hp);
        if(val instanceof RefValue)
        {
            RefValue refValue = (RefValue) val;
            Value asocValue = hp.getFromAddress(refValue.getAddress());

            if(asocValue != null)
                return asocValue;
            else
                throw new MyException("There is nothing at this address\n");
        }
        else
            throw new MyException("Not a ref value!\n");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ=exp.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }

    @Override
    public String toString() {
        return "rH(" + exp.toString() + ")";
    }
}
