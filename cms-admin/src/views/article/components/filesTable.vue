<template>
    <el-table :data="files">
        <el-table-column label="文件地址">
            <template slot-scope="scope">
                {{scope.row}}
            </template>
        </el-table-column>
        <el-table-column label="操作">
            <template slot-scope="scope">
                <el-button @click="handleCheck(scope.row)">查看</el-button>
                <el-button @click="handleDelete(scope.$index)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
</template>

<script>
export default {
    name: "filesTable",
    props:{
        files:{
            type:Array,
            default:null
        }
    },
    methods:{
        handleCheck(item){
            this.$emit("filesTableEmit",{type:"check",data:item})
        },
        handleDelete(index){
            this.$emit("filesTableEmit",{type:"delete",data:index})
        }
    }
}
</script>

<style scoped>

</style>
