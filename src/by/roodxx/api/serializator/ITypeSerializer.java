package by.roodxx.api.serializator;

/**
 * @author: roodxx
 */
public interface ITypeSerializer<Type> {

    byte[] serialize(Type value) throws Exception;
}
