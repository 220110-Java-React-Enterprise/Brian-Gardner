package CRUD_Repo;

import CustomLists.CustomListInterface;

//Interface for required methods of joint object Repos - those obtained from joint tables
//where the primary key is a composite key
public interface JointDataSourceCRUD<T> {
    //Method to insert a single row into a table
    public boolean create(T t);

    //Method to read a single row into an object matching the given identifier
    public boolean read(Integer idOne, Integer idTwo, T t);

    //Method to read all rows belonging to a single id with field name specifying which id
    public boolean read(Integer id, String fieldName, CustomListInterface<T> t);

    //Method to read information from a custom list of objects and store formatted strings into a custom list
    //Where all objects match a certain id; field specified by fieldType
    public boolean readStrings(Integer id, String fieldName, CustomListInterface<String> s);

    //Method to read all rows from the table into a custom list
    public boolean readAll(CustomListInterface<T> t);

    //Method to read information from a custom list of objects and stored formatted strings into a custom list
    public boolean readAllStrings(CustomListInterface<String> s);

    //Method to update a single row in a table
    public boolean update(T t);

    //Method to delete a single row in a table
    public boolean delete(Integer idOne, Integer idTwo);
}
