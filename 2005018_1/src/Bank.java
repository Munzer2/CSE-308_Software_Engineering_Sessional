import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class Bank{
    private List<Account> accounts;
    private Set<String> accountNames;
    private List<Loan> PendingLoans;
    private double InitialFund;
    private List<Employee> EmployeeAccounts;
    private Set<String> EmployeeNames;
    private Bank() {
        accounts = new ArrayList<>();
        accountNames = new HashSet<>();
        EmployeeNames = new HashSet<>();
        PendingLoans = new ArrayList<>();
        EmployeeAccounts = new ArrayList<>();
        InitialFund = 1000000;
    }

    public void openEmployeeAccount(String name,String type){
        if(!EmployeeNames.contains(name)){
            EmployeeNames.add(name);
            if(type.equalsIgnoreCase("Cashier")){
                Cashier newAccount = new Cashier(name);
                EmployeeAccounts.add(newAccount);
            }
            else if( type.equalsIgnoreCase("Managing Director")){
                MD newAccount = new MD(name);
                EmployeeAccounts.add(newAccount);
            }
            else
            {
                Officer newAccount = new Officer(name);
                EmployeeAccounts.add(newAccount);
            }
        }
    }
    public boolean openAccount(String accountType, String name, double initialDeposit) {
        if(!accountNames.contains(name)){
            if(accountType.equalsIgnoreCase("Fixed_Deposit")){
                try{
                    Fixed_deposit newAccount = new Fixed_deposit(name, initialDeposit);
                    accounts.add(newAccount);
                    accountNames.add(name);
                    return true;
                }
                catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                    return false;
                }
            }
            else if(accountType.equalsIgnoreCase("Saving")){
                 Saving newAccount = new Saving(name,initialDeposit);
                 accounts.add(newAccount);
                 accountNames.add(name);
                 return true;
            }
            else if(accountType.equalsIgnoreCase("Student")){
                Student newAccount = new Student(name,initialDeposit);
                accounts.add(newAccount);
                accountNames.add(name);
                return true;
            }
            else return false;
        }
        else return false;
    }

    public void FixInterestRateofAllAccounts(){
        for(Account account: accounts){
            if(account instanceof Fixed_deposit){
                FixedDepositInterestRate((Fixed_deposit)account);
            }
            else if(account instanceof Student){
                StudentInterestRate((Student)account);
            }
            else{
                SavingsInterestRate((Saving)account);
            }
        }
    }

    public void FixedDepositInterestRate(Fixed_deposit account){
        InitialFund -= account.QueryDeposit()*(account.getRate());
        account.setBalance(account.QueryDeposit()*(1+account.getRate()));
    }
    public void SavingsInterestRate(Saving account){
        InitialFund -= account.QueryDeposit()*(account.getRate());
        account.setBalance(account.QueryDeposit()*(1+account.getRate()));
    }
    public void StudentInterestRate(Student account){
        InitialFund -= account.QueryDeposit()*(account.getRate());
        account.setBalance(account.QueryDeposit()*(1+account.getRate()));
    }
    ///Here I need to change this function a bit. Will handle it when loan handling takes place.
    public void ServiceChargeDeductionAllAccounts(){
        for(Account account:accounts){
            if(account instanceof Student) continue;
            account.setBalance(account.QueryDeposit()-500.0);
            InitialFund += 500.0;
        }
    }
    public void AllAccountYearIncrement(){
        for(Account account: accounts){
            account.yearIncreament();
        }
        FixInterestRateofAllAccounts();
        ServiceChargeDeductionAllAccounts();
        AllAccountLoanDeduction();
    }

    public void AllAccountLoanDeduction(){
        for(Account account: accounts){
            account.setBalance(account.QueryDeposit() - account.getReceivedLoan()*0.10);
            InitialFund += account.getReceivedLoan()*0.10;
        }
    }

    public abstract class Account{
        final private String name;
        final private String type;
        private double balance ;
        private int clock;
        private double InterestRate;
        private boolean LoanStatus;
        private double receivedLoan;

        public Account(String name,String type, double initialBalance){
            this.name = name;
            this.type = type;
            this.balance = initialBalance;
            this.LoanStatus = false;
            this.receivedLoan = 0.00;
        }

        public void setReceivedLoan(double receivedLoan) {
            this.receivedLoan = receivedLoan;
        }
        public double getReceivedLoan() { return this.receivedLoan; }

        public void setLoanStatus(boolean bool) { this.LoanStatus = bool; }
        public boolean getLoanStatus() { return this.LoanStatus; }

        public boolean deposit(double ammount){
            this.balance += ammount;
            InitialFund+= ammount;
            return true;
        }

        public abstract boolean withdraw(double ammount);
        public abstract boolean requestLoan(double ammount);

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public void yearIncreament(){
            clock++;
        }
        public int seeClock() { return this.clock; }
        public void setBalance(double value){
            this.balance = value;
        }
        public double QueryDeposit(){
            return this.balance;
        }
        public void setRate(double value) { this.InterestRate = value; }
        public double getRate() { return this.InterestRate; }
    }

    public class Fixed_deposit extends Account{
        public Fixed_deposit(String name,double init_balance) {
            super(name, "Fixed_deposit",init_balance);
            if(init_balance >= 100000 ){
                this.setRate(0.15);
                System.out.println("Account created.");
            }
            else{
                throw new IllegalArgumentException("Initial deposit should be at least 100000.Acoount creation failed.");
            }
        }

        @Override
        public boolean requestLoan(double value) {
            if(value > 100000 ){
                return false;
            }
            else{
                if(!this.getLoanStatus())
                {
                    PendingLoans.add(new Loan(this.getName(),value));
                    this.setLoanStatus(true);
                    return true;
                }
                else return false;
            }
        }
        @Override
        public boolean deposit(double ammount){
            if(ammount >= 50000){
                this.setBalance(this.QueryDeposit()+ammount);
                InitialFund += ammount;
                return true;
            }
            else{
                return false;
            }
        }
        @Override
        public boolean withdraw(double value){
            if(this.seeClock() > 0){
                if(value <= this.QueryDeposit() ){
                    this.setBalance(this.QueryDeposit()-value);
                    InitialFund -= value;
                    System.out.println("Successfull withdrawal complete.");
                    return true;
                }
                else{
                    System.out.println("Not enough balance");
                    return false;
                }
            }
            else{
                System.out.println("Account not matured yet.");
                return false;
            }
        }
    }
    public class Student extends Account{
        public Student(String name,double initialDeposit){
            super(name,"Student",initialDeposit);
            this.setRate(0.05);
        }
        @Override
        public boolean withdraw(double ammount){
            if(ammount <= 10000){
                if(this.QueryDeposit() >= ammount){
                    this.setBalance(this.QueryDeposit() - ammount);
                    InitialFund -= ammount;
                    System.out.println("Successfull withdrawal complete.");
                    return true;
                }
                else{
                    System.out.println("Not enough balance");
                    return false;
                }
            }
            else
            {
                System.out.println("Cannot withdraw more than 10000.");
                return false;
            }
        }
        @Override
        public boolean requestLoan(double ammount){
            if(ammount <= 1000 && !this.getLoanStatus())
            {
                PendingLoans.add(new Loan(this.getName(),ammount));
                this.setLoanStatus(true);
                return true;
            }
            else return false;
        }
    }
    public class Saving extends Account{
        public Saving(String name, double initialDeposit){
            super(name,"Saving",initialDeposit);
            this.setRate(0.10);
        }
        @Override
        public boolean withdraw(double ammount){
            if(this.QueryDeposit()-ammount>= 1000){
                this.setBalance(this.QueryDeposit()-ammount);
                InitialFund -= ammount;
                System.out.println("Successfull withdrawal complete.");
                return true;
            }
            else{
                System.out.println("Cannot withdraw the given ammount.");
                return false;
            }
        }
        @Override
        public boolean requestLoan(double ammount){
            if(ammount <= 10000 && !this.getLoanStatus())
            {
                this.setLoanStatus(true);
                PendingLoans.add(new Loan(this.getName(),ammount));
                return true;
            }
            else return false;
        }
    }
    public class Employee{
        final private String name;
        private String Job;

        public Employee(String name, String position){
            this.name = name;
            this.Job = position;
        }
        public String getEmployeeName() { return this.name; }
    }

    public class Cashier extends Employee{
        public Cashier(String name){
            super(name,"Cashier");
        }
        public void LookUp(Account account){
            System.out.println(account.QueryDeposit());
        }
    }

    public class Officer extends Employee{
        public Officer(String name){
            super(name,"Officer");
        }
        public boolean ApproveLoan(Loan loan_details){
            String name = loan_details.getName();
            for(Account account: accounts){
                if(account.getName().equalsIgnoreCase(name)){
                    double value = loan_details.getRequestedLoanAmmount();
                    if(value < InitialFund){
                        InitialFund-= value;
                        account.setReceivedLoan(account.getReceivedLoan()+value);
                        account.setBalance(account.QueryDeposit()+value);
                        account.setLoanStatus(false);
                        return true;
                    }
                    else break;
                }
            }
            return false;
        }
    }

    public class MD extends Employee{
        public MD(String name){
            super(name,"Managing Director");
        }
        public boolean ApproveLoan(Loan loan_details){
            String name = loan_details.getName();
            for(Account account: accounts){
                if(account.getName().equalsIgnoreCase(name)){
                    double value = loan_details.getRequestedLoanAmmount();
                    if(value < InitialFund){
                        InitialFund-= value;
                        account.setReceivedLoan(account.getReceivedLoan()+value);
                        account.setBalance(account.QueryDeposit()+value);
                        account.setLoanStatus(false);
                        return true;
                    }
                    else break;
                }
            }
            return false;
        }
        public void ChangeInterestRates(String AccountType, double newInterestRate){
            for(Account account: accounts){
                if(account.getType().equalsIgnoreCase(AccountType)){
                    account.setRate(newInterestRate);
                }
            }
        }
        public void SeeInternalFund(){
            System.out.println("Current internal fund is: " + InitialFund);
        }
    }

    public class Loan{
        private double loanAmmount;
        final private String UserName;
        public Loan(String name, double ammount){
            this.loanAmmount = ammount;
            this.UserName = name;
        }
        public String getName() { return this.UserName; }
        public double getRequestedLoanAmmount() { return this.loanAmmount; }
        public void setLoan(double value){
            this.loanAmmount = value;
        }
    }

    public static void main(String[] args) throws IOException {
        Bank bank = new Bank();
        bank.openEmployeeAccount("MD","Managing Director");
        bank.openEmployeeAccount("S1","Officer");
        bank.openEmployeeAccount("S2","Officer");
        bank.openEmployeeAccount("C1","Cashier");
        bank.openEmployeeAccount("C2","Cashier");
        bank.openEmployeeAccount("C3","Cashier");
        bank.openEmployeeAccount("C4","Cashier");
        bank.openEmployeeAccount("C5","Cashier");
        String Input = "";
        File in = new File("Input.txt");
        FileWriter out = new FileWriter("Output.txt");
        out.write("Bank Created; MD, S1, S2, C1, C2, C3, C4, C5 created\n");
        Scanner scanner = new Scanner(in);
        int currType = 0;
        String currClient = "";
        String currEmployee = "";
        while(scanner.hasNextLine()){
            Input = scanner.nextLine();
            String[] inputs = Input.split(" ");
            switch(inputs[0]){
                ///Create done.
                case "Create":
                    if(bank.openAccount(inputs[2],inputs[1],Double.parseDouble(inputs[3]))){
                        out.write(inputs[2] + " acccount for " + inputs[1] + " created; Initial balance is " + inputs[3] + "\n");
                        currClient = inputs[1];
                        currType = 1;
                    }
                    else
                    {
                        out.write("Account creation failed.\n");
                    }
                    break;
                    ///Deposit done.
                case "Deposit":
                    if(currType == 0){
                        out.write("Invalid command.\n");
                    }
                    else if(currType == 2){
                        out.write("Invalid command.\n");
                    }
                    else{
                        for(Account account: bank.accounts){
                            if(currClient.equals(account.getName())){
                                if(account instanceof Fixed_deposit){
                                    Bank.Fixed_deposit temp = (Fixed_deposit)account;
                                    if(temp.deposit(Double.parseDouble(inputs[1]))){
                                        out.write(inputs[1] + " deposited: Current balance is : " + temp.QueryDeposit() + "\n");
                                    }
                                    else{
                                        out.write("Invalid deposit.\n");
                                    }
                                }
                                else{
                                    account.deposit(Double.parseDouble(inputs[1]));
                                    out.write(inputs[1] + " deposited; Current balance is: " + account.QueryDeposit()+ "\n");
                                }
                                break;
                            }
                        }
                    }
                    break;
                    ///See done.
                case "See":
                    if(currType == 0){
                        out.write("Invalid command.\n");
                    }
                    else if(currType == 1){
                        out.write("You don’t have permission for this operation.\n");
                    }
                    else{
                        for(Employee emp: bank.EmployeeAccounts){
                            if(currEmployee.equals(emp.getEmployeeName())){
                                if(emp instanceof MD){
                                    out.write("The total Internal Fund is: " + bank.InitialFund + ".\n");
                                }
                                else{
                                    out.write("You don't have permission to do this operation.\n");
                                }
                                break;
                            }
                        }
                    }
                    break;
                    ///Lookup done.
                case "Lookup":
                    if(currType == 0){
                        out.write("Invalid command.\n");
                    }
                    else if(currType == 1){
                        out.write("Invalid command.\n");
                    }
                    else{
                        if(!bank.accountNames.contains(inputs[1])){
                            out.write("No user found with this name.\n");
                        }
                        else{
                            for(Account account: bank.accounts){
                                if(account.getName().equalsIgnoreCase(inputs[1])){
                                    out.write(inputs[1] + "'s current balance is: " + account.QueryDeposit() + "; Loan: " + account.getReceivedLoan() + ".\n");
                                    break;
                                }
                            }
                        }
                    }
                    break;
                    ///INC done;
                case "INC":
                    bank.AllAccountYearIncrement();
                    out.write("One year passed.\n");
                    break;
                    ///Query Done.
                case "Query":
                    if(currType == 0){
                        out.write("Invalid command.\n");
                    }
                    else if(currType == 2){
                        out.write("Invalid command.\n");
                    }
                    else{
                        for(Account account: bank.accounts){
                            if(account.getName().equals(currClient)){
                                out.write("Current balance is: " + account.QueryDeposit()+" ;Loan: " + account.getReceivedLoan()+ "\n");
                                break;
                            }
                        }
                    }
                    break;
                    ///Close done.
                case "Close":
                    if(currType == 0){
                        out.write("Invalid command.\n");
                    }
                    else
                    {
                        currType = 0;
                        if(currEmployee.isEmpty()){
                            out.write("Transaction closed for " + currClient + ".\n");
                            currClient = "";
                        }
                        else{
                            out.write("Transaction closed for " + currEmployee + ".\n");
                            currEmployee = "";
                        }
                    }
                    break;
                    ///Open done.
                case "Open":
                    if(currType == 0)
                    {
                        if(bank.accountNames.contains(inputs[1])){
                            currType = 1;
                            currClient = inputs[1];
                        }
                        else if(bank.EmployeeNames.contains(inputs[1])){
                            currType = 2;
                            currEmployee = inputs[1];
                        }
                        out.write("Welcome, " + inputs[1]);
                        if(currType == 2){
                            if(bank.PendingLoans.size() > 0){
                                out.write("; There are loans pending for approval.\n");
                            }
                            else out.write(".\n");
                        }
                        else out.write(".\n");
                    }
                    else{
                        out.write("One user already in use.\n");
                    }
                    break;
                    ///Approve done.
                case "Approve":
                    if(currType == 0){
                        out.write("Invalid command.\n");
                    }
                    else if(currType == 1){
                        out.write("Invalid command.\n");
                    }
                    else{
                        if(bank.PendingLoans.size()> 0){
                            for(Employee emp: bank.EmployeeAccounts){
                                if(emp.getEmployeeName().equals(currEmployee)){
                                    if(emp instanceof Cashier){
                                        out.write("You don't have permission for this operation.\n");
                                    }
                                    else{
                                        boolean ok;
                                        String LoanUser;
                                        if(emp instanceof Officer){
                                            Officer temp = (Officer)emp;
                                            ok = temp.ApproveLoan(bank.PendingLoans.get(0));
                                            LoanUser = bank.PendingLoans.get(0).getName();
                                            bank.PendingLoans.remove(0);
                                        }
                                        else{
                                            MD temp = (MD)emp;
                                            ok = temp.ApproveLoan(bank.PendingLoans.get(0));
                                            LoanUser = bank.PendingLoans.get(0).getName();
                                            bank.PendingLoans.remove(0);
                                        }
                                        if(ok) out.write("Loan for " + LoanUser + " approved.\n");
                                        else out.write("Loan for " + LoanUser + " denied.\n");
                                    }
                                    break;
                                }
                            }
                        }
                        else out.write("No loans Pending.\n");
                    }
                    break;
                    ///Change done.
                case "Change":
                    if(currType == 0){
                        out.write("Invalid command.\n");
                    }
                    else if(currType == 1){
                        out.write("Invalid command.\n");
                    }
                    else{
                        for(Employee emp: bank.EmployeeAccounts){
                            if(currEmployee.equals(emp.getEmployeeName())){
                                if(emp instanceof MD){
                                    MD temp = (MD)emp;
                                    temp.ChangeInterestRates(inputs[1], Double.parseDouble(inputs[2])/ 100.0);
                                    out.write("Interest rate changed for " + inputs[1] + ".\n");
                                }
                                else{
                                    out.write("You can't do this operation.\n");
                                }
                                break;
                            }
                        }
                    }
                    break;
                    ///Request done.
                case "Request":
                    if(currType == 0){
                        out.write("Invalid command.\n");
                    }
                    else if(currType == 2){
                        out.write("Invalid command.\n");
                    }
                    else{
                        for(Account account: bank.accounts){
                            if(account.getName().equals(currClient)){
                                boolean ok;
                                if(account instanceof Fixed_deposit){
                                    Fixed_deposit temp = (Fixed_deposit)account;
                                    ok = temp.requestLoan(Double.parseDouble(inputs[1]));
                                }
                                else if( account instanceof Student){
                                    Student temp = (Student)account;
                                    ok = temp.requestLoan(Double.parseDouble(inputs[1]));
                                }
                                else{
                                    Saving temp = (Saving)account;
                                    ok = temp.requestLoan(Double.parseDouble(inputs[1]));
                                }
                                if(ok){
                                    out.write("Loan request successful, sent for approval.\n");
                                }
                                else out.write("Loan request denied.\n");
                                break;
                            }
                        }
                    }
                    break;
                    ///Withdraw done.
                case "Withdraw":
                    if(currType == 0){
                        out.write("Invalid command.\n");
                    }
                    else if(currType == 2){
                        out.write("Invalid command.\n");
                    }
                    else{
                        for(Account account:bank.accounts){
                            if(account.getName().equals(currClient)){
                                boolean ok;
                                if(account instanceof Fixed_deposit){
                                    Fixed_deposit temp = (Fixed_deposit)account;
                                    ok = temp.withdraw(Double.parseDouble(inputs[1]));
                                }
                                else if(account instanceof  Student){
                                    Student temp = (Student)account;
                                    ok = temp.withdraw(Double.parseDouble(inputs[1]));
                                }
                                else{
                                    Saving temp = (Saving) account;
                                    ok = temp.withdraw(Double.parseDouble(inputs[1]));
                                }
                                if(ok){
                                    out.write("Withdrawal complete. Current balance: " + account.QueryDeposit()+ ".\n");
                                }
                                else{
                                    out.write("Invalid transaction. Current balance: " + account.QueryDeposit() + ".\n");
                                }
                                break;
                            }
                        }
                    }
                    break;
                case "Look-02":
                    for(Account account: bank.accounts){
                        System.out.println(account.getName());
                    }
                    break;
                case "Look-01":
                    for(Employee emp: bank.EmployeeAccounts){
                        System.out.println(emp.getEmployeeName());
                    }
                    break;
                case "Look-03":
                    for(Loan loan: bank.PendingLoans){
                        System.out.println(loan.getName() + " " + loan.getRequestedLoanAmmount());
                    }
                    break;
                default:
                    System.out.println("Invalid command.\n");
                    break;
            }
        }
        out.close();
    }
}





