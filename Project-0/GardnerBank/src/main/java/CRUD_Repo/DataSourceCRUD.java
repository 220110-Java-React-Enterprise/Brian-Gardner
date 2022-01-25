package CRUD_Repo;

//Interface specifying required functions for Object Repos, which allow objects to perform minimum CRUD operations:
//Create, Read, Update, Delete
//Changed return type to boolean, so to return whether the operation was successful
public interface DataSourceCRUD<T> {
    public boolean create(T t);
    public boolean read(Integer id, T t);
    public boolean update(T t);
    public boolean delete(Integer id);
}
