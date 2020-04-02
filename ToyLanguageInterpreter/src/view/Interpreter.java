package view;

import controller.Controller;
import model.MyException;
import model.PrgState;
import model.adt.*;
import model.expressions.*;
import model.statements.*;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import repository.IRepo;
import repository.Repo;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;

import java.io.BufferedReader;
import java.util.LinkedList;

public class Interpreter {
    public static LinkedList<IStatement> getStatements()
    {
        LinkedList<IStatement> lst = new LinkedList<IStatement>();
        IStatement stmt1 =
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new CompStmt(
                                        new AssignStmt("v", new ValueExp(new IntValue(1))),
                                        new PrintStmt(new VarExp("v"))
                                ),
                                new NopStmt()
                                ));
        IStatement stmt2 =
                new CompStmt(
                        new VarDeclStmt("a", new IntType()),
                        new CompStmt(
                                new VarDeclStmt("b", new IntType()),
                                new CompStmt(
                                        new AssignStmt("a",
                                                new ArithExp('+', new ValueExp(new IntValue(2)),
                                                        new ArithExp('*',
                                                                new ValueExp(new IntValue(3)),
                                                                new ValueExp(new IntValue(5))))),
                                        new CompStmt(
                                                new CompStmt(
                                                        new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))),
                                                        new PrintStmt(new VarExp("b"))),
                                                new NopStmt()))

                        ));

        IStatement stmt3 =
                new CompStmt(
                        new VarDeclStmt("a", new BoolType()),
                        new CompStmt(
                                new VarDeclStmt("v", new IntType()),
                                new CompStmt(
                                        new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                        new CompStmt(
                                                new CompStmt(
                                                        new IfStmt(
                                                                new VarExp("a"),
                                                                new AssignStmt("v", new ValueExp(new IntValue(2))),
                                                                new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                                        new PrintStmt(new VarExp("v"))),
                                                new NopStmt())
        )));

        IStatement stmt4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("D:\\ubb\\map\\labs\\lab5\\test.in"))),
                        new CompStmt(new openRFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(
                                                new CompStmt(
                                                        new CompStmt(
                                                                new CompStmt(new readFile(new VarExp("varf"), new StringValue("varc")), new PrintStmt(new VarExp("varc"))),
                                                                new CompStmt(new readFile(new VarExp("varf"), new StringValue("varc")), new PrintStmt(new VarExp("varc")))),
                                                        new closeRFile(new VarExp("varf"))),
                                                new NopStmt()
                                                )

                                )
                        )));

        IStatement stmt5 =
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(4))),
                                new CompStmt(
                                        new WhileStatement(
                                                new RelationalExp(
                                                        new VarExp("v"),
                                                        new ValueExp(new IntValue(0)),
                                                        ">"),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new AssignStmt("v",
                                                                new ArithExp('-',
                                                                        new VarExp("v"),
                                                                        new ValueExp(new IntValue(1))
                                                                )
                                                        )
                                                )
                                        ),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new NopStmt()
                                        )

                                )
                        )
                );

        IStatement stmt6 =
                new CompStmt(
                        new VarDeclStmt("v", new RefType(new IntType())),
                        new CompStmt(
                                new HeapAllocation("v", new ValueExp(new IntValue(20))),
                                new CompStmt(
                                        new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                        new CompStmt(
                                                new HeapAllocation("a", new VarExp("v")),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new CompStmt(
                                                                new PrintStmt(new VarExp("a")),
                                                                new NopStmt()
                                                        )

                                                )
                                        )
                                )
                        )
                );

        IStatement stmt7 =
                new CompStmt(
                        new VarDeclStmt("v", new RefType(new IntType())),
                        new CompStmt(
                                new HeapAllocation("v", new ValueExp(new IntValue(20))),
                                new CompStmt(
                                        new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                        new CompStmt(
                                                new HeapAllocation("a", new VarExp("v")),
                                                new CompStmt(
                                                        new PrintStmt(new HeapReading(new VarExp("v"))),
                                                        new CompStmt(
                                                                new PrintStmt(
                                                                        new ArithExp('+',
                                                                                new ValueExp(new IntValue(5)),
                                                                                new HeapReading(new HeapReading(new VarExp("a"))))
                                                                ),
                                                                new NopStmt()
                                                        )

                                                )
                                        )
                                )
                        )
                );

        IStatement stmt8 =
                new CompStmt(
                        new VarDeclStmt("v", new RefType(new IntType())),
                        new CompStmt(
                                new HeapAllocation("v", new ValueExp(new IntValue(20))),
                                new CompStmt(
                                        new PrintStmt(new HeapReading(new VarExp("v"))),
                                        new CompStmt(
                                                new HeapWriting("v", new ValueExp(new IntValue(30))),
                                                new CompStmt(
                                                        new PrintStmt(
                                                                new ArithExp('+',
                                                                        new HeapReading(new VarExp("v")),
                                                                        new ValueExp(new IntValue(5))
                                                                )
                                                        ),
                                                        new NopStmt()
                                                )

                                        )
                                )
                        )
                );

        IStatement stmt9 =
                new CompStmt(
                        new VarDeclStmt("v", new RefType(new IntType())),
                        new CompStmt(
                                new HeapAllocation("v", new ValueExp(new IntValue(20))),
                                new CompStmt(
                                        new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                        new CompStmt(
                                                new HeapAllocation("a", new VarExp("v")),
                                                new CompStmt(
                                                        new HeapAllocation("v", new ValueExp(new IntValue(30))),
                                                        new CompStmt(
                                                                new PrintStmt(
                                                                        new HeapReading(new HeapReading(new VarExp("a")))
                                                                ),
                                                                new NopStmt()
                                                        )

                                                )
                                        )
                                )
                        )
                );

        IStatement stmt10 =
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new IntType())),
                                new CompStmt(
                                        new AssignStmt("v", new ValueExp(new IntValue(10))),
                                        new CompStmt(
                                                new HeapAllocation("a", new ValueExp(new IntValue(22))),
                                                new CompStmt(
                                                        new Fork(
                                                                new CompStmt(
                                                                        new HeapWriting("a",new ValueExp(new IntValue(30))),
                                                                        new CompStmt(
                                                                                new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                                new CompStmt(
                                                                                        new PrintStmt(new VarExp("v")),
                                                                                        new PrintStmt(new HeapReading(new VarExp("a")))
                                                                                )
                                                                        )
                                                                )
                                                        ),
                                                        new CompStmt(
                                                                new PrintStmt(new VarExp("v")),
                                                                new CompStmt(
                                                                        new PrintStmt(new HeapReading(new VarExp("a"))),
                                                                        new NopStmt()
                                                                )

                                                        )
                                                )
                                        )
                                )
                        )
                );
        lst.add(stmt1);
        lst.add(stmt2);
        lst.add(stmt3);
        lst.add(stmt4);
        lst.add(stmt5);
        lst.add(stmt6);
        lst.add(stmt7);
        lst.add(stmt8);
        lst.add(stmt9);
        lst.add(stmt10);
        return lst;
    }

    public static void main(String[] args) {

        LinkedList<IStatement> statements = new LinkedList<>();
        statements = getStatements();

        try
        {
            statements.get(0).typecheck(new MyDictionary<String, Type>());
            statements.get(1).typecheck(new MyDictionary<String, Type>());
            statements.get(2).typecheck(new MyDictionary<String, Type>());
            statements.get(3).typecheck(new MyDictionary<String, Type>());
            statements.get(4).typecheck(new MyDictionary<String, Type>());
            statements.get(5).typecheck(new MyDictionary<String, Type>());
            statements.get(6).typecheck(new MyDictionary<String, Type>());
            statements.get(7).typecheck(new MyDictionary<String, Type>());
            statements.get(8).typecheck(new MyDictionary<String, Type>());
            statements.get(9).typecheck(new MyDictionary<String, Type>());
        }
        catch (MyException e)
        {
            System.out.println(e.toString());
        }
        PrgState prgState1 = new PrgState(new MyStack<IStatement>(), new MyDictionary<String, Value>(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(), statements.get(0), new Heap<>());
        IRepo repo1 = new Repo(prgState1, "D:\\ubb\\map\\labs\\lab5\\logfile1.txt");
        Controller ctrl1 = new Controller(repo1);

        PrgState prgState2 = new PrgState(new MyStack<IStatement>(), new MyDictionary<String, Value>(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(), statements.get(1), new Heap<>());
        IRepo repo2 = new Repo(prgState2, "D:\\ubb\\map\\labs\\lab5\\logfile2.txt");
        Controller ctrl2 = new Controller(repo2);

        PrgState prgState3 = new PrgState(new MyStack<IStatement>(), new MyDictionary<String, Value>(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(), statements.get(2), new Heap<>());
        IRepo repo3 = new Repo(prgState3, "D:\\ubb\\map\\labs\\lab5\\logfile3.txt");
        Controller ctrl3 = new Controller(repo3);

        PrgState prgState4 = new PrgState(new MyStack<IStatement>(), new MyDictionary<String, Value>(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(), statements.get(3), new Heap<>());
        IRepo repo4 = new Repo(prgState4, "D:\\ubb\\map\\labs\\lab5\\logfile4.txt");
        Controller ctrl4 = new Controller(repo4);

        PrgState prgState5 = new PrgState(new MyStack<IStatement>(), new MyDictionary<String, Value>(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(), statements.get(4), new Heap<>());
        IRepo repo5 = new Repo(prgState5, "D:\\ubb\\map\\labs\\lab5\\logfile.txt");
        Controller ctrl5 = new Controller(repo5);

        PrgState prgState6 = new PrgState(new MyStack<IStatement>(), new MyDictionary<String, Value>(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(), statements.get(5), new Heap<>());
        IRepo repo6 = new Repo(prgState6, "D:\\ubb\\map\\labs\\lab5\\logfile.txt");
        Controller ctrl6 = new Controller(repo6);

        PrgState prgState7 = new PrgState(new MyStack<IStatement>(), new MyDictionary<String, Value>(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(), statements.get(6), new Heap<>());
        IRepo repo7 = new Repo(prgState7, "D:\\ubb\\map\\labs\\lab5\\logfile.txt");
        Controller ctrl7 = new Controller(repo7);

        PrgState prgState8 = new PrgState(new MyStack<IStatement>(), new MyDictionary<String, Value>(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(), statements.get(7), new Heap<>());
        IRepo repo8 = new Repo(prgState8, "D:\\ubb\\map\\labs\\lab5\\logfile.txt");
        Controller ctrl8 = new Controller(repo8);

        PrgState prgState9 = new PrgState(new MyStack<IStatement>(), new MyDictionary<String, Value>(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(), statements.get(8), new Heap<>());
        IRepo repo9 = new Repo(prgState9, "D:\\ubb\\map\\labs\\lab5\\logfile.txt");
        Controller ctrl9 = new Controller(repo9);

        PrgState prgState10 = new PrgState(new MyStack<IStatement>(), new MyDictionary<String, Value>(), new MyList<Value>(), new FileTable<StringValue, BufferedReader>(), statements.get(9), new Heap<>());
        IRepo repo10 = new Repo(prgState10, "D:\\ubb\\map\\labs\\lab5\\logfile.txt");
        Controller ctrl10 = new Controller(repo10);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", statements.get(0).toString(), ctrl1));
        menu.addCommand(new RunExample("2", statements.get(1).toString(), ctrl2));
        menu.addCommand(new RunExample("3", statements.get(2).toString(), ctrl3));
        menu.addCommand(new RunExample("4", statements.get(3).toString(), ctrl4));
        menu.addCommand(new RunExample("5", statements.get(4).toString(), ctrl5));
        menu.addCommand(new RunExample("6", statements.get(5).toString(), ctrl6));
        menu.addCommand(new RunExample("7", statements.get(6).toString(), ctrl7));
        menu.addCommand(new RunExample("8", statements.get(7).toString(), ctrl8));
        menu.addCommand(new RunExample("9", statements.get(8).toString(), ctrl9));
        menu.addCommand(new RunExample("10", statements.get(9).toString(), ctrl10));
        menu.show();
    }
}

