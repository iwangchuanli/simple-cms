<template>
    <el-card class="content">
        <el-card>
            <el-form :model="siteForm" inline>
                <el-form-item label="名称">
                    <el-input v-model="siteForm.name"></el-input>
                </el-form-item>
                <el-form-item label="状态">
                    <el-select v-model="siteForm.status" placeholder="请选择状态">
                        <el-option label="全部" :value="-1"></el-option>
                        <el-option label="禁用" :value="0"></el-option>
                        <el-option label="启用" :value="1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="search">搜索</el-button>
                    <el-button @click="reset">重置</el-button>
                    <el-button type="success" @click="handleAdd">新增站点</el-button>
                </el-form-item>
            </el-form>
        </el-card>
        <el-card>
            <site-table :site-list="siteList" :site-form="siteForm" :edit-flag="true"
                        @tableEmit="handleTableEmit"></site-table>
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="siteForm.page"
                :page-sizes="[10, 20, 100, 200]"
                :page-size="siteForm.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="siteForm.total">
            </el-pagination>
        </el-card>

        <el-dialog
            title="编辑"
            :visible.sync="dialogVisible"
            width="70%"
            :before-close="dialogClose">
            <site-item v-if="siteItem" :site-item="siteItem"></site-item>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogClose">取 消</el-button>
                <el-button type="primary" @click="handleSubmit">确 定</el-button>
            </span>
        </el-dialog>

    </el-card>
</template>

<script>
import * as siteApi from '@/api/site'
import siteItem from "@/views/site/components/siteItem";
import siteTable from "@/views/site/components/siteTable";

export default {
    name: "platform",
    components: {
        siteItem, siteTable
    },
    data() {
        return {
            siteForm: {
                page: 0, pageSize: 10, pages: 0, total: 0,
                name: "", status: -1
            },
            siteList: [],
            siteItem: {},

            dialogVisible: false,
        }
    },
    created() {
        this.getPage()
    },
    methods: {
        getPage() {
            siteApi.page(this.siteForm).then(res => {
                this.siteList = res.content
                this.siteForm.page = res.page
                this.siteForm.pages = res.pages
                this.siteForm.pageSize = res.pageSize
                this.siteForm.total = res.total
                // console.log(res)
            })
        },
        search() {
            this.getPage()
        },
        reset() {
            this.siteForm = {page: 0, pageSize: 10, pages: 0, total: 0, name: "", status: -1}
            this.getPage()
        },
        handleAdd() {
            this.dialogVisible = true
            this.siteItem = {id: -1}
        },
        handleTableEmit(options) {
            // console.log(options)
            if (options.type === "edit") {
                this.dialogVisible = true
                this.siteItem = options.data
            }
        },
        handleSubmit() {
            if (this.siteItem.name === undefined || this.siteItem.address === undefined || this.siteItem.logo === undefined || this.siteItem.phone === undefined) {
                this.$message({message: '请完善信息！', type: 'warning'});
                return;
            }
            siteApi.saveOrUpdate(this.siteItem).then(res => {
                console.log(res)
                this.$message({message: '提交完成！', type: 'success'});
                this.dialogVisible = false
                this.siteItem = {}
                this.getPage()
            })
        },
        dialogClose() {
            this.dialogVisible = false
            this.siteItem = {}
        },
        handleSizeChange(val){
            this.siteForm.pageSize = val
            this.getPage()
        },
        handleCurrentChange(val) {
            this.siteForm.page = val
            this.getPage()
        }
    }
}
</script>

<style scoped>

</style>
