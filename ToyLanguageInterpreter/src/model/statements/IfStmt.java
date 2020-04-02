package model.statements;

import model.MyException;
import model.PrgState;
import model.adt.Heap;
import model.adt.MyIDictionary;
import model.expressions.Exp;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;


public class IfStmt implements IStatement
{
    private Exp exp;
    private IStatement thenS;
    private IStatement elseS;

    public IfStmt(Exp e, IStatement t, IStatement el)
    {
        exp = e;
        thenS = t;
        elseS = el;
    }

    public String toString()
    {
        return "IF(" + exp.toString() + ") THEN(" + thenS.toString() + ") ELSE(" + elseS.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        Value cond = exp.eval(state.getSymTable(), (Heap<Integer, Value>) state.getHeap());
        if(cond.getType() instanceof BoolType)
        {
            BoolValue b = (BoolValue) cond;
            if(b.getValue())
                state.getExeStack().push(thenS);
            else
                state.getExeStack().push(elseS);
        }
        else throw new MyException("Conditional expression is not a boolean.\n");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.copy());
            elseS.typecheck(typeEnv.copy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }
}
