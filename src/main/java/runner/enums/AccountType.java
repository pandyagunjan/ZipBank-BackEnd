package runner.enums;

public enum AccountType {
  CHECKING(0.5),INVESTMENT,SAVINGS(1.3);

  private Double interestRate;

  AccountType(){
  }

  AccountType(Double interestRate){
    this.interestRate = interestRate;
  }

  public Double getInterestRate(){
    return interestRate;
  }

}
