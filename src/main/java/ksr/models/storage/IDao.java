package ksr.models.storage;

public interface IDao<T> extends AutoCloseable {

    T read() throws Exception;

    void write(T in) throws Exception;
}
