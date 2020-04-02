package model.statements;

import model.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.type.Type;

import java.io.IOException;

public interface IStatement
{
   public PrgState execute(PrgState state) throws MyException, IOException;
   MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws
           MyException;
}
