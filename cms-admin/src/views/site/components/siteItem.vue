<template>
    <div>
        <el-form :data="siteItem" label-width="80px">
            <el-form-item label="名称" required>
                <el-input v-model="siteItem.name"></el-input>
            </el-form-item>
            <el-form-item label="别称">
                <el-input v-model="siteItem.nickName"></el-input>
            </el-form-item>
            <el-form-item label="地址" required>
                <el-input v-model="siteItem.address"></el-input>
            </el-form-item>
            <el-form-item label="logo" required>
                <el-image :src="siteItem.logo" style="width: 128px" @click="uploadFlag=true">
                    <div slot="error" class="image-slot">
                        <el-button type="success" @click="uploadFlag=true">上传</el-button>
                    </div>
                </el-image>
            </el-form-item>
            <el-form-item label="手机" required>
                <el-input v-model="siteItem.phone"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
                <el-input v-model="siteItem.email"></el-input>
            </el-form-item>
            <el-form-item label="备案号">
                <el-input v-model="siteItem.beian"></el-input>
            </el-form-item>
            <el-form-item label="域名">
                <el-input v-model="siteItem.domain"></el-input>
            </el-form-item>
        </el-form>
        <image-upload :key="new Date() + ((Math.random() * 1000).toFixed(0) + '')" :show="uploadFlag" @uploadEmit="uploadEmit"></image-upload>
    </div>
</template>

<script>
import ImageUpload from '@/components/ImageUpload'
export default {
    name: "item",
    components:{
        ImageUpload
    },
    props: {
        siteItem: {
            type: Object
        }
    },
    data() {
        return {
            uploadFlag:false
        }
    },
    created() {
    },
    methods:{
        uploadEmit(options){
            if (options.type==="success"){
                this.siteItem.logo = options.data
            }
            this.uploadFlag = false
        }
    }
}
</script>

<style scoped>

</style>
