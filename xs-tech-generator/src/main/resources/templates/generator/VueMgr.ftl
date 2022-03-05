<template>
    <div>
        <a-card>
            <vxe-grid
                    ref="table"
                    :slots="{form:'form'}"
                    :toolbar-config="tableToolbar"
                    :columns="tableColumn"
                    :pager-config="tablePage"
                    :proxy-config="tableProxy"
                    @toolbar-button-click="tableToolbarClick">
                <template v-slot:option="{row}">
                    <vxe-button @click="modifyRowBtnClick(row)" content="修改"></vxe-button>
                </template>
                <template v-slot:form>
                    <a-form-model layout="inline">
                        <a-form-model-item label="编号" prop="likeCode" placeholder="请输入编号">
                            <a-input v-model="queryFormData.likeCode" allowClear></a-input>
                        </a-form-model-item>
                        <a-form-model-item label="名称" prop="likeName" placeholder="请输入名称">
                            <a-input v-model="queryFormData.likeName" allowClear></a-input>
                        </a-form-model-item>
                        <a-form-model-item>
                            <a-button type="primary" @click="queryTableData">
                                查询
                            </a-button>
                        </a-form-model-item>
                    </a-form-model>
                </template>
            </vxe-grid>
        </a-card>
        <${addModalComponentName} ref="addModal" @successCallback="addSuccessCallback"></${addModalComponentName}>
        <${modifyModalComponentName} ref="modifyModal" :formData="modifyModalData"
                       @successCallback="modifySuccessCallback"></${modifyModalComponentName}>
    </div>
</template>

<script>

    import ${addModalComponentName} from "@/pages/${componentDirName}/${addModalComponentName}";
    import ${modifyModalComponentName} from "@/pages/${componentDirName}/${modifyModalComponentName}";
    import {deleteList, listPage} from "@/services/sysRole";
    import XEUtils from "xe-utils";

    export default {
        name: "${componentName}",
        components: {${addModalComponentName}, ${modifyModalComponentName}},
        data() {
            return {
                queryFormData: {
                    likeCode: '',
                    likeName: '',
                },
                modifyModalData: undefined,
                tablePage: {
                    total: 0,
                    currentPage: 1,
                },
                tableColumn: [
                    {type: 'checkbox', width: 50, align: 'center'},
                    {field: 'code', title: '编号'},
                    {field: 'name', title: '名称'},
                    {field: 'remark', title: '描述'},
                    {
                        title: '选项', width: 80, headerAlign: 'left', align: "center", slots: {
                            default: "option"
                        }
                    },
                ],
                tableToolbar: {
                    buttons: [
                        {code: 'add', name: '新增'},
                        {code: 'delete', name: '删除'},
                    ],
                },
                tableProxy: {
                    ajax: {
                        query: ({page}) => {
                            let queryData = XEUtils.clone(this.queryFormData, true)
                            queryData.pageIndex = page.currentPage;
                            queryData.pageSize = page.pageSize;
                            return listPage(queryData)
                        },
                        delete: ({body}) => {
                            let idList = [];
                            body.removeRecords.forEach((item) => {
                                idList.push(item.id)
                            })
                            return new Promise((resolve, reject) => {
                                deleteList(idList).then(res => {
                                    if (res.data.code === 0) {
                                        resolve(res)
                                    } else {
                                        reject(new Error(res.data.msg))
                                    }
                                }).catch(error => {
                                    reject(error)
                                })
                            });
                        }
                    }
                },
            }
        },
        methods: {
            queryTableData() {
                this.$refs.table.commitProxy('query')
            },
            addSuccessCallback() {
                this.queryTableData();
            },
            modifySuccessCallback() {
                this.queryTableData();
            },
            modifyRowBtnClick(row) {
                this.modifyModalData = XEUtils.clone(row, true);
                this.$refs.modifyModal.show();
            },
            tableToolbarClick({code}) {
                switch (code) {
                    case 'add': {
                        this.$refs.addModal.show();
                        return;
                    }
                    default: {
                        return
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
