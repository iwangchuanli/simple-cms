<template>
    <el-card  class="content">
        <el-card>
            <el-divider content-position="left">站点用户信息管理</el-divider>
        </el-card>
        <el-card>
            <user-table v-if="this.$store.getters.userInfo.role!==undefined&&this.$store.getters.userInfo.role===0"></user-table>
<!--            <user-info :user-info="item" v-for="(item, index) in userinfoList" :key="index" />-->

        </el-card>
    </el-card>
</template>

<script>
import userTable from "@/views/user/components/userTable";
import userInfo from "@/views/user/components/userInfo";

import * as userApi from "@/api/user"

export default {
    name: 'Dashboard',
    components: {
        userInfo,userTable
    },
    created() {
    },
    data() {
        return {
            searchObj: {
                "page": 0,
                "pageSize": 10,
                "username": "",
                "status": -1
            },
            userinfoList: [],   // 用户信息列表
        }
    },
    methods: {
    }
}
</script>

<style scoped>

.dashboard-index {
    margin: 30px;
}

.dashboard-index-top {
    margin: 0 20px 20px;
}


</style>
