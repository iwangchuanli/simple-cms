<template>
    <el-card>
        <el-form ref="form" :model="articleItem" label-width="100px">
            <el-form-item label="标题">
                <el-input v-model="articleItem.title"></el-input>
            </el-form-item>
            <el-form-item label="作者">
                <el-input v-model="articleItem.author"></el-input>
            </el-form-item>
            <el-form :model="articleItem" inline label-width="100px">
                <el-form-item label="分类选择">
                    <el-select v-model="articleItem.cateId" placeholder="请选择分类">
                        <el-option-group
                            v-for="group in cateTree"
                            :key="group.id"
                            :label="group.name">
                            <el-option
                                v-for="item in group.childs"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                            </el-option>
                        </el-option-group>
                    </el-select>
                </el-form-item>
                <el-form-item label="发布时间">
                    <el-date-picker
                        v-model="articleItem.time"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
            </el-form>
            <el-form-item label="头图">
                <el-image :src="articleItem.header" style="width: 256px" @click="uploadPicFlag=true">
                    <div slot="error" class="image-slot">
                        <el-button type="success" @click="uploadPicFlag=true">上传</el-button>
                    </div>
                </el-image>
            </el-form-item>
            <el-form :model="articleItem" inline label-width="100px">
                <el-form-item label="浏览量">
                    <el-input v-model="articleItem.views"></el-input>
                </el-form-item>
                <el-form-item label="首页推荐">
                    <el-switch
                        v-model="articleItem.recommend"
                        :active-value="1"
                        :inactive-value="0"
                        active-color="#13ce66"
                        inactive-color="#ff4949">
                    </el-switch>
                </el-form-item>
                <el-form-item label="状态">
                    <el-switch
                        v-model="articleItem.status"
                        :active-value="1"
                        :inactive-value="0"
                        active-color="#13ce66"
                        inactive-color="#ff4949">
                    </el-switch>
                </el-form-item>
            </el-form>
            <el-form-item label="文件">
                <el-button @click="uploadFilesFlag=true">上传</el-button>
                <files-table v-if="articleItem.files.length>0" :files="articleItem.files" @filesTableEmit="filesTableEmit"></files-table>
                <!--                <el-input v-model="articleItem.description"></el-input>-->
            </el-form-item>
            <el-form-item label="简介">
                <el-input v-model="articleItem.description"></el-input>
            </el-form-item>
            <el-form-item label="富文本">
                <richText :key="articleItem.id" :content="articleItem.detail" @handleChange="handleChange"
                          :dialog-visible="dialogVisible"/>
            </el-form-item>

        </el-form>

        <image-upload :show="uploadPicFlag" :sign="'header'" :key="new Date() + ((Math.random() * 1000).toFixed(0) + '')" @uploadEmit="uploadEmit"></image-upload>
        <image-upload :show="uploadFilesFlag" :sign="'files'" :key="new Date() + ((Math.random() * 1000).toFixed(0) + '')" @uploadEmit="uploadEmit"></image-upload>
    </el-card>
</template>

<script>
import richText from "@/components/myCommon/richText"
import ImageUpload from '@/components/ImageUpload'
import filesTable from "@/views/article/components/filesTable";

export default {
    name: "editArticle",
    components: {
        richText, ImageUpload, filesTable
    },
    props: {
        articleItem: {
            type: Object,
            default: null
        },
        cateTree: {
            type: Array
        },
        dialogVisible: {
            type: Boolean
        }
    },
    data() {
        return {
            content: '',
            uploadPicFlag: false,
            uploadFilesFlag: false
        }
    },
    methods: {
        uploadEmit(options) {
            this.uploadPicFlag = false
            this.uploadFilesFlag = false
            if (options.type === "success" && options.sign === "header") {
                this.articleItem.header = options.data
            }
            if (options.type === "success" && options.sign === "files") {
                if (this.articleItem.files.length>0){
                    for (let i = 0; i < this.articleItem.files; i++) {
                        if (this.articleItem.files[i]===options.data){
                            return
                        }
                    }
                }
                this.articleItem.files.push(options.data)
            }
            // console.log(options)
        },
        filesTableEmit(options){
            if (options.type==="check"){
                window.open(options.data)
            }
            if (options.type==="delete"){
                this.articleItem.files.splice(options.data,1)
            }
        },
        handleChange(text) {
            // console.log(text)
            this.articleItem.detail = text
        }
    }
}
</script>

<style scoped>

.edit-article {
    padding: 25px;
    box-shadow: 0 0 10px #e6e6e6;
}

.edit-article-header {
    font-weight: bold;
    font-size: 25px;
    margin: 0 0 25px;
    text-align: center;
}

.edit-article-footer {
    margin: 25px 25px 0;
    display: flex;
    justify-content: center;
}

</style>
