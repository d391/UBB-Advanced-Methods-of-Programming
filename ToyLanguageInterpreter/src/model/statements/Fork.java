package model.statements;

import model.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyStack;
import model.type.Type;

import java.io.IOException;

public class Fork implements IStatement
{
    private IStatement stmt;
    public Fork(IStatement _stmt)
    {
        stmt = _stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        return new PrgState(new MyStack<>(), state.getSymTable().copy(), state.getOut(), state.getFileTab(), stmt, state.getHeap());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + stmt.toString() + ")";
    }
}
