<template>
    <div>
        <el-form :model="userInfo" label-width="80px">
            <el-form-item label="账号">
                <el-input disabled v-model="userInfo.username"></el-input>
            </el-form-item>
            <el-form-item label="昵称">
                <el-input v-model="userInfo.nickName"></el-input>
            </el-form-item>
            <el-form-item label="头像">
                <el-image :src="userInfo.avatarUrl" style="width: 128px"  @click="uploadFlag=true">
                    <div slot="error" class="image-slot">
                        <el-button type="success" @click="uploadFlag=true">上传</el-button>
                    </div>
                </el-image>
            </el-form-item>
            <el-form-item label="手机">
                <el-input v-model="userInfo.phone"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
                <el-input v-model="userInfo.email"></el-input>
            </el-form-item>
        </el-form>
        <image-upload :key="new Date() + ((Math.random() * 1000).toFixed(0) + '')" :show="uploadFlag" @uploadEmit="uploadEmit"></image-upload>
    </div>
</template>

<script>
import ImageUpload from '@/components/ImageUpload'
export default {
    name: "userForm",
    components:{
        ImageUpload
    },
    props: {
        userInfo: {},
    },
    data(){
        return{
            uploadFlag:false
        }
    },
    methods:{
        handleUpload(){
          console.log("eeee")
        },
        uploadEmit(options){
            if (options.type==="success"){
                this.userInfo.avatarUrl = options.data
            }
            this.uploadFlag = false
        }
    }
}
</script>

<style scoped>

</style>
