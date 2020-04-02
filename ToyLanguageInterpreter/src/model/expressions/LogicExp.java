package model.expressions;

import model.MyException;
import model.adt.Heap;
import model.adt.MyIDictionary;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class LogicExp implements Exp
{
    private Exp e1;
    private Exp e2;
    int op; //1: &, 2: |

    public LogicExp(Exp _e1, Exp _e2, int _op) {e1 = _e1; e2 = _e2; op = _op;}

    @Override
    public String toString() {
        return e1.toString() + op +e2.toString();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, Heap<Integer, Value> hp) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl, hp);
        if(v1.getType().equals(new BoolType()))
        {
            v2 = e2.eval(tbl, hp);
            if(v2.getType().equals(new BoolType()))
            {
                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;
                if(op == 1) return new BoolValue(b1.getValue() && b2.getValue());
                if(op == 2) return new BoolValue(b1.getValue() || b2.getValue());
                throw new MyException("Invalid operand for boolean operands!\n");
            }
            else
                throw new MyException("The second operand is not a boolean!\n");
        }
        else
            throw new MyException("The first operand is not a boolean!\n");

    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            } else
            throw new MyException("second operand is not a boolean");
        }else
        throw new MyException("first operand is not a boolean");
    }
}
