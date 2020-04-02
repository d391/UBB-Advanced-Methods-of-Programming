package model.statements;

import model.MyException;
import model.PrgState;
import model.adt.Heap;
import model.adt.MyIDictionary;
import model.expressions.Exp;
import model.type.IntType;
import model.type.StringType;
import model.type.Type;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class readFile implements IStatement
{
    private Exp exp;
    private StringValue varName;
    public readFile(Exp _exp, StringValue _varName)
    {
        exp = _exp;
        varName = _varName;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        if(state.getSymTable().isDefined(varName.getValue()) && state.getSymTable().lookup(varName.getValue()).getType().equals(new IntType()))
        {
            Value val = exp.eval(state.getSymTable(), (Heap<Integer, Value>) state.getHeap());
            if (val.getType().equals(new StringType()))
            {
                StringValue sval = (StringValue) val;
                BufferedReader bufred = state.getFileTab().lookup(sval);
                String line = bufred.readLine();
                if(line == null)
                {
                    state.getSymTable().put(varName.getValue(), new IntValue(0));
                }
                else
                    state.getSymTable().put(varName.getValue(), new IntValue(Integer.parseInt(line)));
            }
            else
                throw new MyException("Not a string type!");
        }
        else
            throw new MyException("Not an int!\n");
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
        return "read(" + exp.toString() + ");\n";
    }
}
