package tech.xs.examples.test.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskLogEntry;
import org.junit.Test;
import tech.xs.examples.domain.entity.Product;
import tech.xs.examples.service.workflow.ExampleWorkflowService;
import tech.xs.examples.test.base.ExampleBaseTest;
import tech.xs.framework.constant.DefaultConstant;

import javax.annotation.Resource;
import java.util.*;


@Slf4j
public class ExampleWorkflowServiceTest extends ExampleBaseTest {

    @Resource
    private ExampleWorkflowService approvalService;

    @Resource
    private RepositoryService repositoryService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private IdentityService identityService;
    @Resource
    private HistoryService historyService;

    /**
     * 测试多人审批
     */
    @Test
    public void testMultiApproval() {
        //提交审批
        Product product = new Product();
        product.setName("产品名称1");
        product.setProdNo("产品编号1");
        identityService.setAuthenticatedUserId("1");
        String approvalNo = DateUtil.format(new Date(), "yyyyMMdd");
        approvalNo = approvalNo + RandomUtil.randomString(5);
        HashMap<String, Object> processVariables = new HashMap<>(DefaultConstant.HASH_MAP_SIZE);
        processVariables.put("product", product);
        processVariables.put("approvalUserId", "1");
        ArrayList<String> userList = new ArrayList<>();
        userList.add("2");
        userList.add("3");
        processVariables.put("assigneeList", userList);
        ProcessInstance process = runtimeService.startProcessInstanceByKey("exampleApproval", approvalNo, processVariables);

        //单人审批
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(process.getProcessInstanceId()).list();
        Task task = taskList.get(0);
        HashMap<String, Object> taskVariables = new HashMap<>(DefaultConstant.HASH_MAP_SIZE);
        taskVariables.put("status", "完成");
        taskService.complete(task.getId(), taskVariables, true);

        //加签
        taskList = taskService.createTaskQuery().processInstanceId(process.getProcessInstanceId()).list();
        task = taskList.get(0);
        print(process, "加签之前");

        userList.add("7");
        userList.add("8");
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processInstanceId(process.getId()).active().list();
        ProcessInstance processInstance = processInstanceList.get(0);
        runtimeService.addMultiInstanceExecution("multiApproval", processInstance.getId(), new HashMap<>());
        runtimeService.addMultiInstanceExecution("multiApproval", processInstance.getId(), new HashMap<>());
        //取消加签
//        runtimeService.deleteMultiInstanceExecution();
        taskList = taskService.createTaskQuery().processInstanceId(process.getProcessInstanceId()).list();
        print(process, "加签结束");
        taskService.complete(taskList.get(0).getId(), taskVariables, true);
        taskService.complete(taskList.get(1).getId(), taskVariables, true);
        taskService.complete(taskList.get(2).getId(), taskVariables, true);

        taskList = taskService.createTaskQuery().processInstanceId(process.getProcessInstanceId()).list();
        print(process, "加签部分人审批之后");

        taskService.complete(taskList.get(0).getId(), taskVariables, true);
        print(process, "加签全部人审批之后");
        return;
    }

    /**
     * 测试完成审批和终止审批
     */
    @Test
    public void testCompleteAndEndTask() {
        //提交审批
        Product product = new Product();
        product.setName("产品名称1");
        product.setProdNo("产品编号1");
        identityService.setAuthenticatedUserId("1");
        String approvalNo = DateUtil.format(new Date(), "yyyyMMdd");
        approvalNo = approvalNo + RandomUtil.randomString(5);
        HashMap<String, Object> processVariables = new HashMap<>(DefaultConstant.HASH_MAP_SIZE);
        processVariables.put("product", product);
        processVariables.put("approvalUserId", "1");
        ProcessInstance process = runtimeService.startProcessInstanceByKey("exampleApproval", approvalNo, processVariables);
        //输出初始任务列表
        print(process, "初始任务列表");

        //审批
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(process.getProcessInstanceId()).list();
        Task task = taskList.get(0);

        HashMap<String, Object> taskVariables = new HashMap<>(DefaultConstant.HASH_MAP_SIZE);
        taskVariables.put("status", "完成");

        taskService.complete(task.getId(), taskVariables, true);
        //输出完成第一个之后的任务列表
        print(process, "完成第一个之后的任务列表");
        //终止流程
        runtimeService.deleteProcessInstance(process.getId(), "删除原因");
        print(process, "终止任务后的列表");
        return;
    }

    private void print(ProcessInstance process, String msg) {
        System.out.println(msg);
        List<HistoricTaskLogEntry> historicTaskLogList = historyService.createHistoricTaskLogEntryQuery()
                .processInstanceId(process.getProcessInstanceId())
                .list();
        System.out.println("--------historicTaskLogList---------");
        historicTaskLogList.forEach((item) -> {
            System.out.println("taskId:" + item.getTaskId() + " taskName:" + item.getData());
        });
        List<HistoricTaskInstance> historicTaskList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(process.getProcessInstanceId())
                .includeTaskLocalVariables()
                .includeProcessVariables()
                .list();
        System.out.println("--------historicTaskList---------");
        historicTaskList.forEach((item) -> {
            System.out.println("taskId:" + item.getId() + " taskName:" + item.getName() + " taskLocalVariables:" + item.getTaskLocalVariables());
        });
        List<Task> taskList = taskService.createTaskQuery()
                .processInstanceId(process.getProcessInstanceId())
                .includeProcessVariables()
                .includeTaskLocalVariables()
                .list();
        System.out.println("--------taskList---------");
        taskList.forEach((item) -> {
            System.out.println("taskId:" + item.getId() + " taskName:" + item.getName() + " taskLocalVariables:" + item.getTaskLocalVariables());
        });
        return;
    }


    /**
     * 提交审批
     *
     * @author 沈家文
     * @date 2021/2/19 16:20
     */
    @Test
    public void testSubmitApproval() {
        Product product = new Product();
        product.setName("产品名称");
        product.setProdNo("产品编号");
        approvalService.submitApproval(product);
        return;
    }

    /**
     * 查询审批列表
     *
     * @author 沈家文
     * @date 2021/2/19 16:28
     */
    @Test
    public void getApprovalList() {
        //方式1
        List<ProcessInstance> processInstances = approvalService.runtimeServiceGetApprovalList();
        printList(processInstances);

        //方式2
        List<HistoricProcessInstance> historicProcessInstances = approvalService.historyServiceGetApprovalList();
        printList(historicProcessInstances);
        return;
    }

    /**
     * 查询任务列表
     *
     * @author 沈家文
     * @date 2021/2/20 11:09
     */
    @Test
    public void getTaskList() {
        List<Task> taskList = approvalService.taskServiceGetTaskList();
        printList(taskList);
        return;
    }

    private void printList(List list) {
        list.forEach((item) -> {
            System.out.println(item);
        });
    }

    /**
     * 完成流程
     *
     * @author imsjw
     * Create Time: 2021/2/18
     */
    @Test
    public void agreeApproval() {
        taskService.complete("fc0387e6-7189-11eb-b5a3-be7400cf4f03");
    }


}
