package model.statements;


import model.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.type.Type;

public class CompStmt implements IStatement
{
    private IStatement first;
    private IStatement second;
    public CompStmt(IStatement _first, IStatement _second){first = _first; second = _second;}

    public String toString()
    {
        return first.toString() + "; " + second.toString() ;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        MyIStack<IStatement> stk=state.getExeStack();
        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv));
    }
}
