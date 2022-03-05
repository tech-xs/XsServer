import {request, METHOD} from '@/utils/request'

const SERVICE_BASE_URL = process.env.VUE_APP_API_BASE_URL + '/${requestPath}'

const URL = {
    ADD: `${r'${SERVICE_BASE_URL}'}/add`,
    DELETE_ID_LIST: `${r'${SERVICE_BASE_URL}'}/delete/idList`,
    MODIFY: `${r'${SERVICE_BASE_URL}'}/modify/id`,
    LIST_PAGE: `${r'${SERVICE_BASE_URL}'}/list/page`,
}

export async function add(data) {
    return request(URL.ADD, METHOD.PUT, data)
}

export async function deleteList(idList) {
    return request(URL.DELETE_ID_LIST, METHOD.POST, {
        idList: idList
    })
}

export async function modify(data) {
    return request(URL.MODIFY, METHOD.POST, data)
}

export async function listPage(data) {
    return request(URL.LIST_PAGE, METHOD.GET, data)
}
