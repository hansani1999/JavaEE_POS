package dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<C,T,ID>{
    ArrayList<T> getAll(C c) throws SQLException;

    T search(C c,ID id) throws SQLException;

    boolean save(C c,T t) throws SQLException;

    boolean update(C c,T t) throws SQLException;

    boolean delete(C c,ID id) throws SQLException;
}
