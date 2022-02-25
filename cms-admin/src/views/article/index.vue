<template>
    <el-card class="content">
        <el-card>
            <el-form :model="searchObj" inline>
                <el-form-item label="分类">
                    <el-select v-model="searchObj.cateId" placeholder="请选择分类">
                        <el-option
                            :key="-1"
                            label="全部"
                            :value="-1">
                        </el-option>
                        <el-option-group
                            v-for="group in cateTree"
                            :key="group.id"
                            :label="group.name">
                            <el-option
                                v-for="item in group.childs"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                            </el-option>
                        </el-option-group>
                    </el-select>
                </el-form-item>
                <el-form-item label="标题">
                    <el-input v-model="searchObj.name"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="success" @click="getArticleList">查 询</el-button>
                    <el-button @click="handleReset">重 置</el-button>
                    <el-button type="primary" @click="handleAdd">添 加</el-button>
                </el-form-item>
            </el-form>
        </el-card>
        <el-divider content-position="left">文章信息</el-divider>
        <el-card>
            <table-content :article-list="articleList" :cate-tree="cateTree" :edit-flag="true"  :del-flag="true" @articleTableEmit="articleTableEmit"/>

            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                           layout="total, sizes, prev, pager, next, jumper"
                           :current-page="searchObj.page" :page-size="searchObj.pageSize" :total="searchObj.total"
                           :page-sizes="[10, 15, 20]">
            </el-pagination>
        </el-card>

        <el-dialog title="文章编辑" :visible.sync="dialogVisible" width="80%" :before-close="handleClose">

            <editArticle :article-item="articleItem" :key="articleItem.id" :cateTree="cateTree" :dialog-visible="dialogVisible" />

            <span slot="footer" class="dialog-footer">
                <el-button @click="handleClose">取 消</el-button>
                <el-button type="primary" @click="handleSubmit">确 定</el-button>
            </span>
        </el-dialog>
    </el-card>

</template>

<script>
import * as articleApi from '@/api/article'
import * as cateApi from '@/api/cate'

import tableContent from "./components/tabalContent"
import editArticle from "./components/editArticle"

export default {
    name: "index.vue",
    components: {
        tableContent,
        editArticle
    },
    data() {
        return {
            searchObj: {
                page: 0,
                pageSize: 10,
                siteId: this.$store.getters.userInfo.siteId,
                cateId: -1,
                name: "",
                pages: 0,
                total: 0
            },
            articleList: [],
            articleItem: {},
            dialogVisible: false,

            editFlag: true,
            activeIndex: 0,
            total: 0,
            cateTree: [],
        }
    },
    created() {
        this.getArticleList()
        this.getTree()
    },
    methods: {
        getTree() {
            cateApi.tree({siteId: this.$store.getters.userInfo.siteId}).then(res => {
                this.cateTree = res
            })
        },
        getArticleList() {  // 获取文章列表
            articleApi.page(this.searchObj).then(res => {
                this.articleList = res.content
                this.searchObj.page = res.page
                this.searchObj.pageSize = res.pageSize
                this.searchObj.pages = res.pages
                this.searchObj.total = res.total
            })
        },
        handleAdd() {
            this.dialogVisible = true
            this.articleItem = {id: -1,files:[]}
        },
        articleTableEmit(options) {
            if (options.type === "edit") {
                this.articleItem = options.data
                this.articleItem.cateId = '' + this.articleItem.cateId
                this.articleItem.files = this.articleItem.files==null||this.articleItem.files===""?[]:JSON.parse(this.articleItem.files)
                this.dialogVisible = true
            }
            if (options.type === "delete"){
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', { // 弹窗提醒
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    articleApi.del({ articleId: options.data.id }).then(res => {   // 发送请求
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        })
                        this.getArticleList()
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    })
                })
            }
        },
        handleSubmit() {
            console.log(this.articleItem)
            this.articleItem.files = JSON.stringify(this.articleItem.files)
            if (this.articleItem.cateId===undefined||this.articleItem.time===undefined||this.articleItem.title===undefined){
                this.$message({message:"请完善信息",type:"warning"})
                return
            }
            articleApi.update(this.articleItem).then(res => {
                this.$message({
                    type: 'success',
                    message: '编辑成功!'
                })
                this.handleClose()
                this.getArticleList()
            })
        },

        handleCurrentChange(page) { // 页面改变时
            this.searchObj.page = page
            this.getArticleList()
        },
        handleSizeChange(pageSize) {    // 页面尺寸改变
            this.searchObj.page = 0
            this.searchObj.pageSize = pageSize
            this.getArticleList()
        },
        handleReset() {
            this.searchObj.page = 0
            this.searchObj.pageSize = 10
            this.searchObj.cateId = -1
            this.searchObj.name = ""
            this.getArticleList()
        },
        handleClose() {
            this.dialogVisible = false
            this.articleItem = {}
        }
    }
}
</script>

<style scoped>
</style>
