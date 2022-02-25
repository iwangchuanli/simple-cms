<template>
    <div>
        <el-dialog
            title="图片上传"
            :visible.sync="show"
            width="400px"
            :modal="false"
            :before-close="handleClose">
            <el-upload
                class="upload-demo"
                drag
                :action="uploadUrl"
                :on-success="onSuccess"
                :limit="1"
                multiple>
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                <div class="el-upload__tip" slot="tip">上传文件，要求不超过10Mb</div>
            </el-upload>

        </el-dialog>
    </div>
</template>

<script>
import * as commonApi from '@/api/common'

export default {
    name: "index",
    props: {
        show: {
            type: Boolean,
            default: true
        },
        sign: {
            type: String,
            default: ""
        },
    },
    data() {
        return {
            uploadUrl: commonApi.getUploadUrl(),
            dialogImageUrl: ''
        };
    },
    created() {
    },
    methods: {
        handleClose() {
            this.$emit("uploadEmit", {type: "close",sign:this.sign, data: {}})
        },
        onSuccess(file, fileList){
            this.$emit("uploadEmit", {type: "success",sign:this.sign, data: file.data})
            // console.log(file, fileList);
            this.fileList=[]
        },
        handleRemove(file, fileList) {
            console.log(file, fileList);
        },
        handlePictureCardPreview(file) {
            this.dialogImageUrl = file.url;
            this.dialogVisible = true;
        }
    }
}
</script>

<style scoped>

</style>
