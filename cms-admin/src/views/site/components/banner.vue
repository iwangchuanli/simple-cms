<template>
    <div>
        <el-dialog title="轮播图管理" :visible.sync="show" width="50%" :before-close="handleClose">
            <img v-for="(imgUrl,index) in siteItem.banner" :src="imgUrl" style="width: 200px;margin: 5px;"
                 @click="deleteOneSlideShow(index)" title="鼠标点击可选择删除"/>
            <image-upload :show="uploadFlag" @uploadEmit="uploadEmit" :key="new Date() + ((Math.random() * 1000).toFixed(0) + '')" ></image-upload>
            <div class="image-slot">
                <el-button type="success" @click="uploadFlag=true">上传</el-button>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="handleClose">取 消</el-button>
                <el-button type="primary" @click="handleSubmit">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="提示" :visible.sync="deleteSlideShowVisible" width="30%">
            <span>是否确认删除？</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="deleteSlideShowVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteSlideShowConfirm">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import ImageUpload from '@/components/ImageUpload'

export default {
    name: "banner",
    components: {
        ImageUpload
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
            deleteSlideShowVisible: false,
            currentDeleteSlideShowIndex: 0,
            uploadFlag: false
        };
    },
    created() {
    },
    methods: {
        handleClose() {
            this.$emit("bannerEmit", {
                type: "close",
                data: {}
            });
        },
        handleSubmit() {
            this.$emit("bannerEmit", {
                type: "submit",
                data: this.siteItem
            });
        },
        uploadEmit(options) {
            if (options.type === "success") {
                this.siteItem.banner.push(options.data)
            }
            this.uploadFlag = false
        },
        deleteOneSlideShow(index) {
            this.deleteSlideShowVisible = true;
            this.currentDeleteSlideShowIndex = index;
        },
        deleteSlideShowConfirm() {
            this.siteItem.banner.splice(this.currentDeleteSlideShowIndex, 1);
            this.deleteSlideShowVisible = false;
        },

    }
}
</script>

<style scoped>

</style>
