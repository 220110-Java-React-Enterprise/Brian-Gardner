package CRUD_Repo;

//Interface for required methods of joint object Repos - those obtained from joint tables
//where the primary key is a composite key
public interface JointDataSourceCRUD<T> {
    public boolean create(T t);
    public boolean read(Integer idOne, Integer idTwo, T t);
    public boolean update(T t);
    public boolean delete(Integer idOne, Integer idTwo);
}
