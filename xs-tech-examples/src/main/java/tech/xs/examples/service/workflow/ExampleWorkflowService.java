package tech.xs.examples.service.workflow;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceQuery;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.stereotype.Service;
import tech.xs.examples.domain.entity.Product;
import tech.xs.framework.constant.DefaultConstant;

import javax.annotation.Resource;
import java.util.*;

/**
 * OA产品审批Service
 *
 * @author imsjw
 * Create Time: 2021/2/17
 */
@Service
@Slf4j
public class ExampleWorkflowService {

    /**
     * 认证授权Service
     */
    @Resource
    private IdentityService identityService;

    /**
     * 任务Service
     */
    @Resource
    private TaskService taskService;

    /**
     * 运行时Service
     */
    @Resource
    private RuntimeService runtimeService;

    /**
     * 履历Service
     */
    @Resource
    private HistoryService historyService;

    /**
     * 提交审批
     *
     * @author 沈家文
     * @date: 2021/2/18 16:10
     */
    public ProcessInstance submitApproval(Product product) {
        //设置审批人,通常这里已经全局设置,如果指定提交人,请使用这行代码
        identityService.setAuthenticatedUserId("用户1");
        String approvalNo = DateUtil.format(new Date(), "yyyyMMdd");
        approvalNo = approvalNo + RandomUtil.randomString(5);
        //审批数据
        HashMap<String, Object> variables = new HashMap<>(DefaultConstant.HASH_MAP_SIZE);
        variables.put("product", product);
        variables.put("approvalUserId", "1");

        //添加附件
        //taskService.createAttachment()

        ProcessInstance process = runtimeService.startProcessInstanceByKey("exampleApproval", approvalNo, variables);
        return process;
    }

    public String getMultiApprovalAssignee() {
        return "1";
    }

    /**
     * runtimeService获取审批列表
     *
     * @author 沈家文
     * @date 2021/2/19 16:27
     */
    public List<ProcessInstance> runtimeServiceGetApprovalList() {
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
        //根据流程类型查询
        HashSet<String> definitionKeys = new HashSet<>();
        definitionKeys.add("approval");
        query.processDefinitionKeys(definitionKeys);

        //根据businessKey模糊查询
        query.processInstanceBusinessKeyLike("%20210219%");

        //根据任务开始时间查询
        //查询时间段的开始时间
        query.startedAfter(DateUtil.parse("20210121", "yyyyMMdd"));
        //查询时间段的结束时间
        query.startedBefore(DateUtil.parse("20210225", "yyyyMMdd"));

        List<ProcessInstance> list = query.list();

        return list;
    }

    /**
     * taskService获取任务列表
     *
     * @author 沈家文
     * @date 2021/2/20 11:05
     */
    public List<Task> taskServiceGetTaskList() {
        TaskQuery query = taskService.createTaskQuery();

        //根据流程类型查询
        query.processDefinitionKey("approval");

        //根据businessKey模糊查询
        query.processInstanceBusinessKeyLike("%20210219%");

        //根据ProcessInstanceId查询in
        ArrayList<String> processIds = new ArrayList<>();
        processIds.add("85833ad6-728c-11eb-9830-be7400cf4f03");
        query.processInstanceIdIn(processIds);

        //分页查询
        query.listPage(0, 10);

        //获取查询结果
        List<Task> list = query.list();

        return list;
    }

    /**
     * historyService获取审批列表
     *
     * @author 沈家文
     * @date 2021/2/22 14:03
     */
    public List<HistoricProcessInstance> historyServiceGetApprovalList() {
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();

        //根据流程类型查询
        query.processDefinitionKey("approval");

        //根据businessKey模糊查询
        query.processInstanceBusinessKeyLike("%20210219%");

        //根据流程状态查询
        //已完成
        query.finished();
        //未完成
        query.unfinished();
        //已删除
        query.deleted();

        //根据任务开始时间查询
        //查询时间段的开始时间
        query.startedAfter(DateUtil.parse("20210121", "yyyyMMdd"));
        //查询时间段的结束时间
        query.startedBefore(DateUtil.parse("20210225", "yyyyMMdd"));

        //根据申请人查询
        query.startedBy("用户1");

        //查询结果中包含流程变量
        query.includeProcessVariables();

        //分页查询
        query.listPage(0, 10);

        //获取查询结果
        List<HistoricProcessInstance> list = query.list();
        return list;
    }


}
