<template>
    <el-card class="content">
        <el-card>
            <el-button type="primary" @click="handleAdd">新增分类</el-button>
        </el-card>
        <el-divider content-position="left">分类管理</el-divider>
        <el-card>
            <el-table :data="cateTree"
                      row-key="id"
                      border
                      :tree-props="{children: 'childs', hasChildren: 'hasChildren'}">
                <el-table-column prop="name" label="名称"></el-table-column>
                <el-table-column prop="status" label="状态">
                    <template slot-scope="scope">
                        <el-tag type="success" v-if="scope.row.status===1">启用</el-tag>
                        <el-tag type="info" v-if="scope.row.status===0">禁用</el-tag>
                        <el-tag type="danger" v-if="scope.row.recommend===1">推荐</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="sort" label="排序"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button type="danger" @click="handleDelete(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <el-dialog
            title="分类编辑"
            :visible.sync="dialogVisible"
            width="60%"
            :before-close="dialogClose">
            <el-form :data="cateItem" label-width="80px">
                <el-form-item label="名称">
                    <el-input v-model="cateItem.name"></el-input>
                </el-form-item>
                <el-form-item label="上级分类">
                    <el-select v-model="cateItem.pid" clearable placeholder="请选择">
                        <el-option
                            key="0"
                            label="一级分类"
                            value="0">
                        </el-option>
                        <el-option
                            v-for="item in cateTree"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="首页推荐">
                    <el-switch
                        v-model="cateItem.recommend"
                        :active-value="1"
                        :inactive-value="0"
                        active-color="#13ce66"
                        inactive-color="#ff4949">
                    </el-switch>
                </el-form-item>
                <el-form-item label="状态">
                    <el-switch
                        v-model="cateItem.status"
                        :active-value="1"
                        :inactive-value="0"
                        active-color="#13ce66"
                        inactive-color="#ff4949">
                    </el-switch>
                </el-form-item>
                <el-form-item label="排序">
                    <el-input v-model="cateItem.sort"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogClose">取 消</el-button>
    <el-button type="primary" @click="handleSubmit">确 定</el-button>
  </span>
        </el-dialog>
    </el-card>
</template>

<script>
import * as cateApi from '@/api/cate'

export default {
    name: "category",
    data() {
        return {
            cateTree: [],
            dialogVisible: false,
            cateItem: {}
        }
    },
    created() {
        this.getTree()
    },
    methods: {
        getTree() {
            cateApi.tree({siteId: this.$store.getters.userInfo.siteId}).then(res => {
                // console.log(res)
                this.cateTree = res
            })
        },
        handleAdd() {
            this.dialogVisible = true
            this.cateItem = {id: -1}
        },
        handleEdit(item) {
            this.dialogVisible = true
            this.cateItem = item
            this.cateItem.pid = "" + this.cateItem.pid
        },
        handleDelete(item) {
            this.$confirm('确认删除此分类, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                cateApi.del({cateId: item.id}).then(res => {
                    this.$message({message: "删除成功！", type: "success"})
                    this.getTree()
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        dialogClose() {
            this.dialogVisible = false
            this.cateItem = {}
        },
        handleSubmit() {
            // console.log(this.cateItem)
            this.cateItem.siteId = this.$store.getters.userInfo.siteId
            if (this.cateItem.pid === undefined || this.cateItem.pid === "" || this.cateItem.name === undefined) {
                this.$message({message: "请完善信息！", type: "warning"})
                return;
            }
            cateApi.saveOrUpdate(this.cateItem).then(res => {
                this.$message({message: "提交成功！", type: "success"})
                this.getTree()
                this.dialogVisible = false
            })
        }
    }
}
</script>

<style scoped>

</style>
