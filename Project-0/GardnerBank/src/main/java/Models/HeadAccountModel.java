package Models;

import CRUD_Repo.AccountRepo;
import CustomLists.CustomArrayList;
import CustomLists.CustomListInterface;

//Subclass extends AccountModel with custom list of sub accounts and related methods
public class HeadAccountModel extends AccountModel {
    //Custom list to hold accounts that fall under the "head" account
    private CustomListInterface<AccountModel> subAccounts;

    //No args constructor
    public HeadAccountModel() { }

    //Constructor with all parameters
    public HeadAccountModel(Integer id, AccountType type, Double balance, Integer headID, String description,
                            CustomListInterface<AccountModel> subAccounts) {
        super(id, type, balance, headID, description);
        this.subAccounts = subAccounts;
    }

    //Constructor that takes in superclass as parameter and calls superclass constructor
    public HeadAccountModel(AccountModel accountModel) {
        super(accountModel.id, accountModel.type, accountModel.balance, accountModel.headID, accountModel.description);
    }

    //Getter/setter methods to access and change private member variables
    public CustomListInterface<AccountModel> getSubAccounts() {
        return subAccounts;
    }

    public void setSubAccounts(CustomListInterface<AccountModel> subAccounts) {
        this.subAccounts = subAccounts;
    }

    //Function to initially call recursive setSubAccounts function, verifying the base account is head account
    //i.e. headId is 0
    public void setSubAccounts(AccountRepo accountRepo) {
        if (this.headID == 0) {
            setSubAccountsRecursive(accountRepo);
        }
    }

    //Function to recursively set subAccounts and subAccounts of any HeadAccountModels within it
    //using AccountRepo object passed in
    public void setSubAccountsRecursive(AccountRepo accountRepo) {
        //Empty sub accounts before inserting models to avoid duplicates
        this.subAccounts = new CustomArrayList<>();

        //Run if reading table data to this.subAccounts is successful
        if (accountRepo.readSubAccounts(this, this.subAccounts)) {
            for (int i = 0; i < this.subAccounts.size(); i++) {
                if (subAccounts.get(i).getAccountType() == AccountType.HEAD) {
                    subAccounts.get(i).setSubAccountsRecursive(accountRepo);
                }
            }
        }
    }

    //Function to turn sub account list into list of strings for display
    public boolean getSubAccountsStrings(CustomListInterface<String> subAccountStrings) {
        subAccountStrings.add(this.toString());
        if (this.headID == 0) {
            this.getSubAccountsStringsRecursive(subAccountStrings, 0);
            return true;
        }
        return false;
    }

    public boolean getSubAccountsStringsRecursive(CustomListInterface<String> subAccountStrings, int tabIndex) {
        String tmpString;

        for (int x = 0; x < this.subAccounts.size(); x++) {
            tmpString = "";

            for (int y = 0; y < tabIndex; y++) {
                tmpString += " ";
            }
            tmpString += this.getSubAccounts().get(x);

            subAccountStrings.add(tmpString);
            if (this.getSubAccounts().get(x).getAccountType() == AccountType.HEAD) {
                getSubAccountsStringsRecursive(subAccountStrings, tabIndex + 1);
            }
        }

        return true;
    }
}
