package model.expressions;

import model.MyException;
import model.adt.Heap;
import model.adt.MyIDictionary;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

public class ArithExp implements Exp
{
    private Exp e1;
    private Exp e2;
    private char op; //1: +, 2: -, 3: *, 4: /
    public ArithExp(char _op, Exp _e1, Exp _e2)
    {
        op = _op;
        e1 = _e1;
        e2 = _e2;
    }


    @Override
    public String toString() {
        return e1.toString() + op + e2.toString();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, Heap<Integer, Value> hp) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl, hp);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, hp);
            System.out.println(v2);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                if (op == '+') return new IntValue(n1 + n2);
                if (op == '-') return new IntValue(n1 - n2);
                if (op == '*') return new IntValue(n1 * n2);
                if (op == '/')
                    if (n2 == 0) throw new MyException("Division by zero!\n");
                    else return new IntValue(n1 / n2);
                throw new MyException("Invalid operand for integer operands!\n");
            } else throw new MyException("Second operand is not an integer!\n");
        } else throw new MyException("First operand is not an integer!\n");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else
            throw new MyException("second operand is not an integer");
        }else
        throw new MyException("first operand is not an integer");
    }
}