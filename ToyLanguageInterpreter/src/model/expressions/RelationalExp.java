package model.expressions;

import model.MyException;
import model.adt.Heap;
import model.adt.MyIDictionary;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public class RelationalExp implements Exp
{
    private Exp exp1;
    private Exp exp2;
    private String op;

    public RelationalExp(Exp _exp1, Exp _exp2, String _op)
    {
        exp1 = _exp1;
        exp2 = _exp2;
        op = _op;
    }

    @Override
    public String toString() {
        return exp1.toString() + op + exp2.toString();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, Heap<Integer, Value> hp) throws MyException {
        Value v1 = exp1.eval(tbl, hp);
        Value v2 = exp2.eval(tbl, hp);
        if(v1.getType().equals(new IntType()) && v2.getType().equals(new IntType()))
        {
            IntValue i1 = (IntValue) v1;
            IntValue i2 = (IntValue) v2;
            if (op.equals("<") && i1.getValue() < i2.getValue()) return new BoolValue(true);
            else if (op.equals("<=") && i1.getValue() <= i2.getValue()) return new BoolValue(true);
            else if (op.equals(">") && i1.getValue() > i2.getValue()) return new BoolValue(true);
            else if (op.equals(">=") && i1.getValue() >= i2.getValue()) return new BoolValue(true);
            else if (op.equals("!=") && i1.getValue() != i2.getValue()) return new BoolValue(true);
            else if (op.equals("==") && i1.getValue() == i2.getValue()) return new BoolValue(true);
        }
        else
            throw new MyException("Int values for relational expression are needed!\n");
        return new BoolValue(false);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1=exp1.typecheck(typeEnv);
        typ2=exp2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else
            throw new MyException("second operand is not an integer");
        }else
        throw new MyException("first operand is not an integer");
    }
}
