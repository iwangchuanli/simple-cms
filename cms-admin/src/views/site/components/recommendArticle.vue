<template>
    <div>
        <el-dialog
            title="首页置顶文章"
            :visible.sync="show"
            width="85%"
            :before-close="handleClose">
            <table-content :article-list="recommendArticles" :cate-tree="cateTree" :del-flag="true" @articleTableEmit="articleTableEmit"></table-content>

            <el-button @click="handleClose">取 消</el-button>
            <el-button type="primary" @click="handleSubmit">提 交</el-button>
            <el-divider content-position="left">选择置顶文章</el-divider>
            <el-form :model="searchForm" inline>
                <el-form-item label="分类">
                    <el-select v-model="searchForm.cateId" placeholder="请选择分类">
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
                    <el-input v-model="searchForm.name"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="success" @click="getArticleList">查 询</el-button>
                </el-form-item>
            </el-form>
            <table-content :article-list="searchArticles" :cate-tree="cateTree" :select-flag="true"  @articleTableEmit="searchArticleTableEmit"></table-content>

        </el-dialog>
    </div>
</template>

<script>
import * as articleApi from '@/api/article'
import tableContent from '@/views/article/components/tabalContent'
import * as cateApi from "@/api/cate";

export default {
    name: "recommendArticle",
    components: {
        tableContent
    },
    props: {
        show: {
            type: Boolean,
            default: false
        },
        siteItem: {
            type: Object
        }
    },
    data() {
        return {
            cateTree: [],
            recommendArticles: [],
            searchArticles: [],
            searchForm: {page: 0, pageSize: 10, cateId: -1, name: "", siteId: this.$store.getters.userInfo.siteId}
        }
    },
    created() {
        this.getTree()
    },
    watch:{
      siteItem:function (newValue,oldValue) {
          if (newValue.recommendArticle!==undefined&&newValue.recommendArticle.length>0 && this.recommendArticles.length===0){
              this.handleArticles()
          }
      }
    },
    methods: {
        getTree() {
            cateApi.tree({siteId: this.$store.getters.userInfo.siteId}).then(res => {
                this.cateTree = res
            })
        },
        getArticleList() {
            articleApi.page(this.searchForm).then(res => {
                this.searchArticles = res.content
                this.searchForm.page = res.page
                this.searchForm.pageSize = res.pageSize
            })
        },
        handleArticles() {
            if (this.siteItem.recommendArticle.length>0){
                let articleIds = this.siteItem.recommendArticle
                for (let i = 0; i < articleIds.length; i++) {
                    articleApi.item({articleId: articleIds[i]}).then(res => {
                        this.recommendArticles.push(res)
                    })
                }
            }
        },
        articleTableEmit(options){
            if (options.type==="delete"){
                console.log(options.data)
                this.siteItem.recommendArticle.push(options.data.id)
                let delIndex = null
                for (let i = 0; i < this.siteItem.recommendArticle.length; i++) {
                    if (this.siteItem.recommendArticle[i]===options.data){
                        delIndex = i
                        break
                    }
                }
                this.siteItem.recommendArticle.splice(delIndex,1)
                for (let i = 0; i < this.recommendArticles.length; i++) {
                    if (this.recommendArticles[i].id===options.data){
                        delIndex = i
                        break
                    }
                }
                this.recommendArticles.splice(delIndex,1)
                this.siteItem.recommendArticle=this.siteItem.recommendArticle.filter(res=>{return res!==undefined})
                // console.log(this.siteItem.recommendArticle)
            }
        },
        searchArticleTableEmit(options){
            if (options.type==="select"){
                let articleIds = this.siteItem.recommendArticle
                for (let i = 0; i < articleIds.length; i++) {
                    if (options.data.id===articleIds[i]){
                        this.$message({message:"已选择",type:"success"})
                        return
                    }
                }
                this.recommendArticles.push(options.data)
                this.siteItem.recommendArticle.push(options.data.id)
                this.searchArticles = []
            }
        },
        handleClose() {
            this.$emit("recommendArticleEmit", {type: "close", data: {}});
        },
        handleSubmit() {
            this.$emit("recommendArticleEmit", {type: "submit", data: this.siteItem});
        }
    }
}
</script>

<style scoped>

</style>
