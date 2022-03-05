package tech.xs.framework.core.notice.mail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 邮件实体
 *
 * @author 沈家文
 * @date 2021/9/3 20:34
 */
@Getter
@Setter
@ToString
public class Mail {

    /**
     * 收件人
     */
    private String to;

    /**
     * 邮件标题
     * 必填项
     */
    private String title;

    /**
     * 邮件内容
     * 必填项
     */
    private String content;

    /**
     * 是否是Html
     */
    private boolean isHtml;

}
