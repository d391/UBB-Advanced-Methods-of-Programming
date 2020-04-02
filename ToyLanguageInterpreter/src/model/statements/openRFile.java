package model.statements;

import model.MyException;
import model.PrgState;
import model.adt.Heap;
import model.adt.MyIDictionary;
import model.expressions.Exp;
import model.type.StringType;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class openRFile implements IStatement
{
    private Exp exp;
    public openRFile(Exp _exp)
    {
        exp = _exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException {
        Value val = exp.eval(state.getSymTable(), (Heap<Integer, Value>) state.getHeap());
        if (val.getType().equals(new StringType()))
        {
            StringValue sval = (StringValue) val;
            if(state.getFileTab().verify(sval))
            {
                BufferedReader buffred = new BufferedReader(new FileReader(sval.toString()));
                state.getFileTab().add(sval, buffred);
            }
            else
                throw new MyException("File already added.\n");
        }
        else
            throw new MyException("Not string type!\n");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typecheck(typeEnv);
        if(typeExp.equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("Not a string type.");
    }

    @Override
    public String toString() {
        return "openRFile(" + exp.toString() + ");\n";
    }
}
