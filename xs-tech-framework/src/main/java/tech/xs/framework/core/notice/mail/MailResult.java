package tech.xs.framework.core.notice.mail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 邮件发送结果
 *
 * @author 沈家文
 * @date 2021/9/6 18:40
 */
@Getter
@Setter
@ToString
public class MailResult {

    /**
     * 结果code
     */
    private Integer code;

    /**
     * 消息内容
     */
    private String msg;

    public MailResult() {
    }

    public MailResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
}
