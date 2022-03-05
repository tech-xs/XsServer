package tech.xs.framework.core.notice.sms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 短信发送结果
 *
 * @author 沈家文
 * @date 2021/9/5 18:57
 */
@Getter
@Setter
@ToString
public class SmsResult {

    /**
     * 结果code
     */
    private Integer code;

    /**
     * 消息内容
     */
    private String msg;

    public SmsResult() {
    }

    public SmsResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
