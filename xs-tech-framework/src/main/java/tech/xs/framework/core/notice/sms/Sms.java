package tech.xs.framework.core.notice.sms;

import lombok.Getter;
import lombok.Setter;

/**
 * 短信发送实体类
 *
 * @author 沈家文
 * @date 2021/9/5 18:21
 */
@Getter
@Setter
public abstract class Sms {

    /**
     * 手机号
     */
    protected String phone;

}
