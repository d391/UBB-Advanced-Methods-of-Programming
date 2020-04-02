package model.statements;

import model.MyException;
import model.PrgState;
import model.adt.Heap;
import model.adt.MyIDictionary;
import model.expressions.Exp;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

import java.io.IOException;

public class WhileStatement implements IStatement
{
    private Exp exp;
    private IStatement stmt;
    public WhileStatement(Exp _exp, IStatement _stmt)
    {
        exp = _exp;
        stmt = _stmt;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, IOException
    {
        Value val = exp.eval(state.getSymTable(), (Heap<Integer, Value>) state.getHeap());
        if(val.getType().equals(new BoolType()))
        {
            BoolValue bval = (BoolValue) val;
            if(bval.getValue())
            {
                state.getExeStack().push(this);
                state.getExeStack().push(stmt);
            }
        }
        else
            throw new MyException("While expression is not a boolean!\n");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new IntType()) ) {
            return typeEnv;
        }
        else
            throw new MyException("The condition of WHILE has not the type bool");
    }

    @Override
    public String toString() {
        return "while( " + exp.toString() + ")\n{" + stmt.toString() + "}\n";
    }
}
