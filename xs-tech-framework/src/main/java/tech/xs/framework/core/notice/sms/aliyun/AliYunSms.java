package tech.xs.framework.core.notice.sms.aliyun;

import lombok.Getter;
import lombok.Setter;
import tech.xs.framework.core.notice.sms.Sms;

import java.util.Map;

/**
 * 阿里云短信实体类
 *
 * @author 沈家文
 * @date 2021/9/5 18:46
 */
@Getter
@Setter
public class AliYunSms extends Sms {

    /**
     * 短信模板code
     * 必填项
     */
    private String templateCode;

    /**
     * 短信发送的数据,与模板相对应
     */
    private Map<String, Object> data;

}
