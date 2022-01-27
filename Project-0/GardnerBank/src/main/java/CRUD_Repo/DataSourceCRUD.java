package CRUD_Repo;

import CustomLists.CustomListInterface;

//Interface specifying required functions for Object Repos, which allow objects to perform minimum CRUD operations:
//Create, Read, Update, Delete
//Changed return type to boolean, so to return whether the operation was successful
public interface DataSourceCRUD<T> {
    //Method to insert a single row into a table
    public boolean create(T t);

    //Method to read a single row into an object matching the given identifier
    public boolean read(Integer id, T t);

    //Method to read all rows from the table into a custom list
    public boolean readAll(CustomListInterface<T> t);

    //Method to read information from a custom list of objects and store formatted strings into a custom list
    public boolean readAllStrings(CustomListInterface<String> s);

    //Method to update a single row in a table
    public boolean update(T t);

    //Method to delete a single row in a table
    public boolean delete(Integer id);
}
