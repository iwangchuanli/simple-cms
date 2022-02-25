<template>
    <el-card class="content">
        <el-card>
            <el-button @click="bannerFlag=true">首页banner</el-button>
            <el-button @click="recommendArticleFlag=true">首页推荐文章</el-button>
            <el-button @click="siteDetailFlag=true">站点介绍</el-button>
        </el-card>
        <el-divider content-position="left">{{ siteItem.name }}</el-divider>
        <el-card>
            <site-item v-if="siteItem" :site-item="siteItem"></site-item>
            <span class="footer">
                 <el-button type="success" @click="handleSubmit">提交</el-button>
                <el-button @click="handleReset">重置</el-button>
            </span>
        </el-card>

        <banner :site-item="siteItem" :show="bannerFlag" @bannerEmit="bannerEmit"></banner>
        <recommend-article :site-item="siteItem" :show="recommendArticleFlag" @recommendArticleEmit="recommendArticleEmit"></recommend-article>
        <site-detail :site-item="siteItem" :show="siteDetailFlag" @siteDetailEmit="siteDetailEmit"></site-detail>
    </el-card>
</template>

<script>
import * as siteApi from '@/api/site'
import siteItem from "@/views/site/components/siteItem";
import banner from "@/views/site/components/banner";
import recommendArticle from "@/views/site/components/recommendArticle";
import siteDetail from "@/views/site/components/siteDetail";


export default {
    name: "user-index",
    components: {
        siteItem,banner,recommendArticle,siteDetail
    },
    data() {
        return {
            siteItem: {},
            bannerFlag:false,
            recommendArticleFlag:false,
            siteDetailFlag:false
        }
    },
    created() {
        this.getItem()
    },
    methods: {
        getItem() {
            siteApi.item({siteId: this.$store.state.user.userInfo.siteId}).then(res => {
                // console.log(res)
                this.siteItem = res
                this.siteItem.banner = this.siteItem.banner==null||this.siteItem.banner===""?[]:JSON.parse(this.siteItem.banner)
                this.siteItem.recommendArticle = this.siteItem.recommendArticle==null||this.siteItem.recommendArticle===""?[]:JSON.parse(this.siteItem.recommendArticle)
            })
        },
        handleReset() {
            this.getItem()
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
        bannerEmit(options){
            if(options.type==="submit"){
                this.siteItem = options.data
                this.siteItem.banner = JSON.stringify(this.siteItem.banner)
                siteApi.updateBanner(this.siteItem).then(res=>{
                    this.$message({message: '提交完成！', type: 'success'});
                })
            }
            this.bannerFlag = false
        },
        recommendArticleEmit(options){
            if(options.type==="submit"){
                this.siteItem = options.data
                this.siteItem.recommendArticle = JSON.stringify(this.siteItem.recommendArticle)
                siteApi.updateRecommend(this.siteItem).then(res=>{
                    this.$message({message: '提交完成！', type: 'success'});
                })
            }
            this.recommendArticleFlag = false
        },
        siteDetailEmit(options){
            if(options.type==="submit"){
                this.siteItem = options.data
                siteApi.updateDetail(this.siteItem).then(res=>{
                    this.$message({message: '提交完成！', type: 'success'});
                })
            }
            this.siteDetailFlag = false
        },
    }
}
</script>

<style scoped>

</style>
