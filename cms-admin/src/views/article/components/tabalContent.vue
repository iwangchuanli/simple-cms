<template>
    <div class="table-content">
        <el-table :data="articleList" style="width: 100%">
            <el-table-column sortable prop="author" label="作者"></el-table-column>
            <el-table-column sortable prop="title" label="标题"></el-table-column>
            <el-table-column sortable prop="cateId" label="分类">
                <template slot-scope="scope">
                    <div v-for="(group,index) in cateTree" :key="index">
                        <div v-for="(item,index) in  group.childs" :key="index">
                            <p v-if="item.id==scope.row.cateId">{{ item.name }}</p>
                        </div>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="header" label="头图">
                <template slot-scope="scope">
                    <el-image :src="scope.row.header" style="width: 100px"></el-image>
                </template>
            </el-table-column>
            <el-table-column prop="description" label="简介" show-overflow-tooltip></el-table-column>
            <el-table-column sortable prop="time" label="发布时间"></el-table-column>
            <el-table-column sortable prop="views" label="浏览量"></el-table-column>
            <el-table-column sortable prop="recommend" label="推荐">
                <template slot-scope="scope">
                    <el-tag type="danger" v-if="scope.row.recommend==1">推荐</el-tag>
                </template>
            </el-table-column>
            <el-table-column sortable prop="status" label="状态">
                <template slot-scope="scope">
                    <el-tag type="success" v-if="scope.row.status==1">启用</el-tag>
                    <el-tag type="info" v-if="scope.row.status==0">禁用</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" min-width="180px">
                <template slot-scope="scope">
                    <el-button v-if="editFlag" @click="handleEdit(scope.row)" type="primary" size="small">编 辑</el-button>
                    <el-button v-if="delFlag" @click="handleDel(scope.row)" type="danger" size="small">删 除</el-button>
                    <el-button v-if="selectFlag" @click="handleSelect(scope.row)" type="danger" size="small">选择</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
import * as articleApi from '@/api/article'

import editArticle from "./editArticle"

export default {
    name: "tableContent",
    components: {
        editArticle
    },
    props: {
        articleList: {
            type: Array
        },
        cateTree: {
            type: Array
        },
        editFlag:{
            type:Boolean,
            default:false
        },
        delFlag:{
            type:Boolean,
            default:false
        },
        selectFlag:{
            type:Boolean,
            default:false
        }
    },
    data() {
        return {
            disabledFlag: false,    // 是否禁用
            dialogVisible: false,   // 弹出框
            articleItem: {},   // 文章单条
        }
    },
    methods: {
        handleEdit(item) {  // 发起编辑
            // console.log(item)
            this.$emit('articleTableEmit', {type: "edit", data: item})
        },
        handleDel(item) { // 删除
            this.$emit('articleTableEmit', {type: "delete", data: item})
        },
        handleSelect(item) {
            this.$emit('articleTableEmit', {type: "select", data: item})
        }
    }
}
</script>

<style scoped>
</style>
