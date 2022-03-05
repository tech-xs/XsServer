package tech.xs.framework.exception;


import tech.xs.framework.base.BaseException;

/**
 * @author 沈家文
 * @date 2021-56-13 10:56
 */
public class DocException extends BaseException {

    public DocException(String msg) {
        super(msg);
    }

    public DocException(String uri, String msg) {
        super("uri:[" + uri + "] msg:" + msg);
    }

}
