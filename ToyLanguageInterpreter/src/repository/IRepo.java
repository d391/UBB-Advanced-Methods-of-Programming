package repository;

import model.MyException;
import model.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepo
{
    public void setFilePath(String newFilePath);
    public List<PrgState> getPrgList();
    public void setPrgList(List<PrgState> newList);
    public void logPrgStateExec(PrgState prgState) throws MyException, IOException;
    public void logAllPrgStates() throws MyException;
}
