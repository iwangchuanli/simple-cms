<template>
    <div>
        <el-table :data="siteList">
            <el-table-column sortable prop="id" label="编号"></el-table-column>
            <el-table-column sortable prop="name" label="名称"></el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
            <el-table-column label="logo">
                <template slot-scope="scope">
                    <el-image :src="scope.row.logo"></el-image>
                </template>
            </el-table-column>
            <el-table-column prop="beian" label="备案号"></el-table-column>
            <el-table-column prop="domain" label="域名"></el-table-column>
            <el-table-column label="状态">
                <template slot-scope="scope">
                    <el-tag type="success" v-if="scope.row.status==1">启用</el-tag>
                    <el-tag type="info" v-if="scope.row.status==0">禁用</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="180">
                <template slot-scope="scope">
                    <el-button type="primary" v-if="editFlag" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button type="primary" v-if="selectFlag" @click="handleSelect(scope.row)">选择</el-button>
                </template>
            </el-table-column>
        </el-table>

    </div>
</template>

<script>
export default {
    name: "siteTable",
    props: {
        siteList: {
            type: Array
        },
        siteForm: {
            type: Object
        },
        editFlag: {
            type: Boolean,
            default: false
        },
        checkFlag: {
            type: Boolean,
            default: false
        },
        selectFlag: {
            type: Boolean,
            default: false
        }
    },
    methods: {
        handleEdit(item) {
            this.$emit("tableEmit", {type: "edit", data: item})
        },
        handleSelect(item) {
            this.$emit("tableEmit", {type: "select", data: item})
        },
    }
}
</script>

<style scoped>

</style>
