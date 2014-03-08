package by.roodxx.api.deserializator;

/**
 * @author: roodxx
 */
public interface ITypeParser<Type> {

    Type parse(byte[] data) throws Exception;
}
