<template>
    <div>
        <el-dialog
            title="提示"
            :visible.sync="show"
            width="75%"
            :before-close="handleClose">
            <el-card>
                <el-form :model="siteItem" label-width="80px">
                    <el-form-item label="站点简介">
                        <el-input v-model="siteItem.description"></el-input>
                    </el-form-item>
                    <el-form-item label="站点介绍">
                        <richText :content="siteItem.detail" @handleChange="handleChange"/>
                    </el-form-item>
                </el-form>
            </el-card>
            <span slot="footer" class="dialog-footer">
    <el-button @click="handleClose">取 消</el-button>
    <el-button type="primary" @click="handleSubmit">确 定</el-button>
  </span>
        </el-dialog>
    </div>
</template>

<script>
import Tinymce from '@/components/Tinymce'
import richText from "@/components/myCommon/richText";
export default {
    name: "siteDetail",
    components:{
      Tinymce,richText
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
    data(){
       return{

       }
    },
    methods: {
        handleChange(value){
            this.siteItem.detail = value
        },
        handleClose() {
            this.$emit("siteDetailEmit", {type: "close", data: {}});
        },
        handleSubmit() {
            // console.log(this.siteItem.detail)
            this.$emit("siteDetailEmit", {type: "submit", data: this.siteItem});
        }
    }
}
</script>

<style scoped>

</style>
