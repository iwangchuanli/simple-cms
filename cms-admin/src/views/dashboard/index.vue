<template>
    <el-card>
        <el-divider content-position="left" v-if="indexData.site!==undefined">{{indexData.site.name}}</el-divider>
        <el-divider content-position="left" v-else>内容网站管理系统</el-divider>
        <div v-if="indexData.site!==undefined">
            <el-button type="primary" plain style="width: 200px;height: 100px;text-align: center;">
                <p>管理员</p>
                {{indexData.admin_total}}
            </el-button>
            <el-button type="primary" plain style="width: 200px;height: 100px;text-align: center;">
                <p>文章总数</p>
                {{indexData.article_total}}
            </el-button>
            <el-button type="primary" plain style="width: 200px;height: 100px;text-align: center;">
                <p>推荐文章</p>
                {{indexData.article_recommend_total}}
            </el-button>
        </div>
        <div v-else>
            <el-button type="primary" plain style="width: 200px;height: 100px;text-align: center;">
                <p>管理员</p>
                {{indexData.admin_total}}
            </el-button>
            <el-button type="primary" plain style="width: 200px;height: 100px;text-align: center;">
                <p>站点</p>
                {{indexData.site_total}}
            </el-button>
            <el-button type="primary" plain style="width: 200px;height: 100px;text-align: center;">
                <p>文章总数</p>
                {{indexData.article_total}}
            </el-button>
        </div>
    </el-card>
</template>

<script>
import * as siteApi from '@/api/site'
export default {
    name: "index",
    data(){
        return{
            indexData:{}
        }
    },
    created() {
        this.getIndex()
    },
    methods:{
        getIndex(){
            siteApi.adminIndex({}).then(res=>{
                console.log(res)
                this.indexData =res
            })
        }
    }
}
</script>

<style scoped>

</style>
