package tech.xs.system.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.xs.framework.base.BaseException;
import tech.xs.framework.core.notice.mail.MailNotice;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.system.domain.bo.notice.MailTestBo;

import javax.annotation.Resource;

@Validated
@RestController
@RequestMapping("/sys/notice")
public class NoticeController extends BaseSysController {

    @Resource
    private MailNotice mailNotice;

    /**
     * 测试邮件发送功能
     *
     * @return
     */
    @PostMapping("/mail/test")
    public ApiResult mailTest(@RequestBody MailTestBo bo) {
        try {
            mailNotice.test(bo.getTo());
        } catch (Exception e) {
            BaseException resException = new BaseException("邮件发送异常");
            resException.setRawException(e);
            resException.setResponseStackMsg(true);
            throw resException;
        }
        return ApiResult.success();
    }

}
