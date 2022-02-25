<template>
    <div>
        <el-form :model="userForm" label-width="80px" inline>
            <el-form-item label="账号">
                <el-input v-model="userForm.username"></el-input>
            </el-form-item>
            <el-form-item label="状态">
                <el-switch
                    v-model="userForm.status"
                    :active-value="1"
                    :inactive-value="0"
                    active-color="#13ce66"
                    inactive-color="#ff4949">
                </el-switch>
            </el-form-item>
            <el-form-item>
                <el-button type="success" @click="search">搜索</el-button>
                <el-button type="info" @click="reset">重置</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="userList" border>
            <el-table-column prop="id" label="编号"></el-table-column>
            <el-table-column prop="username" label="账号"></el-table-column>
            <el-table-column label="头像">
                <template slot-scope="scope">
                    <el-image :src="scope.row.avatarUrl"></el-image>
                </template>
            </el-table-column>
            <el-table-column prop="nickName" label="昵称"></el-table-column>
            <el-table-column prop="phone" label="手机"></el-table-column>
            <el-table-column prop="role" label="角色">
                <template slot-scope="scope">
                    <el-tag type="info" v-if="scope.row.role===0">Master</el-tag>
                    <el-tag type="danger" v-if="scope.row.role===1">管理员</el-tag>
                    <el-tag type="warning" v-if="scope.row.role===2">编辑</el-tag>
                    <el-tag v-if="scope.row.role===3">用户</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="siteId" label="站点">
                <template slot-scope="scope">
                    <div v-if="scope.row.siteInfo!==undefined&&scope.row.siteInfo!==null">{{ scope.row.siteInfo.name }}</div>
                    <el-tag type="info" v-else>无</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="status" label="状态"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="userForm.page"
            :page-sizes="[10, 20, 100, 200]"
            :page-size="userForm.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="userForm.total">
        </el-pagination>

        <el-dialog
            title="用户编辑"
            :visible.sync="dialogVisible"
            width="60%"
            :before-close="dialogClose">
            <user-form :user-info="userItem"></user-form>

            <el-card v-if="userItem.siteInfo!==undefined&&userItem.siteInfo!==null">
                <el-divider content-position="left">站点信息</el-divider>
                <el-image :src="userItem.siteInfo.logo" style="width: 128px;"></el-image>
                {{ userItem.siteInfo.name }}({{ userItem.siteInfo.address }})
            </el-card>
            <el-form :model="userItem" label-width="80px">
                <el-form-item label="角色">
                    <el-radio-group v-model="userItem.role">
                        <el-radio :label="1">管理员</el-radio>
                        <el-radio :label="2">编辑</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item>
                    <el-button @click="dialogClose">取 消</el-button>
                    <el-button @click="siteDialogVisible=true">选择站点</el-button>
                    <el-button @click="handleSubmitRole">确认</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog
            title="站点选择"
            :visible.sync="siteDialogVisible"
            width="60%"
            :before-close="dialogClose">
            <el-form :model="siteForm" inline label-width="80px">
                <el-form-item label="站点名称">
                    <el-input v-model="siteForm.name"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="getSitePage">搜索</el-button>
                </el-form-item>
            </el-form>
            <site-table :site-list="siteList" :select-flag="true" @tableEmit="handleTableEmit"></site-table>
        </el-dialog>
    </div>
</template>

<script>
import * as userApi from '@/api/user'
import * as siteApi from '@/api/site'
import userForm from "@/views/user/components/userForm";
import siteTable from "@/views/site/components/siteTable";

export default {
    name: "userTable",
    components: {
        userForm,siteTable
    },
    data() {
        return {
            userForm: {
                page: 0, pageSize: 10, username: "", status: 1, pages: 0, total: 0
            },
            userList: [],
            userItem: {},
            dialogVisible: false,
            siteDialogVisible: false,
            siteForm: {
                page: 0, pageSize: 10, name: "", status: 1
            },
            siteList:[]
        }
    },
    created() {
        this.getPage()
    },
    methods: {
        getPage() {
            userApi.page(this.userForm).then(res => {
                console.log(res)
                this.userList = res.content
                this.userForm.page = res.page
                this.userForm.pageSize = res.pageSize
                this.userForm.total = res.total
                this.userForm.pages = res.pages
            })
        },
        getSitePage(){
            siteApi.page(this.siteForm).then(res=>{
                this.siteList = res.content
            })
        },
        search() {
            this.getPage()
        },
        reset() {
            this.userForm = {page: 0, pageSize: 10, username: "", status: 1}
            this.getPage()
        },
        handleEdit(item) {
            this.dialogVisible = true
            this.userItem = item
            console.log(item)
        },
        handleSizeChange(val) {
            this.userForm.pageSize = val
            this.getPage()
        },
        handleCurrentChange(val) {
            this.userForm.page = val
            this.getPage()
        },
        dialogClose() {
            this.siteDialogVisible=false
            this.dialogVisible = false
            this.userItem = {}
        },
        handleTableEmit(options){
            if (options.type === "select") {
                this.userItem.siteInfo = options.data
                this.siteList = []
            }
            this.siteDialogVisible = false
        },
        handleSubmitRole() {
            if (this.userItem.siteInfo===undefined||this.userItem.siteInfo===null){
                this.$message({message:"请完善信息",type:"warning"})
                return;
            }
            userApi.updateRole({id:this.userItem.id,role:this.userItem.role,siteId:this.userItem.siteInfo.id}).then(res=>{
                console.log(res)
                this.$message({message:"修改成功",type:"success"})
                this.getPage()
                this.dialogClose()
            })
        }
    }
}
</script>

<style scoped>

</style>
