<template>
    <div class="rich-text">
        <el-button plain @click="uploadFlag=true">添加图片</el-button>
        <el-button plain @click="linkFlag=true">添加超链接</el-button>
        <vue-tinymce v-model="text" :setting="setting" :setup="setup"/>
        <image-upload :show="uploadFlag" :key="new Date() + ((Math.random() * 1000).toFixed(0) + '')" @uploadEmit="uploadEmit"></image-upload>

        <el-dialog
            title="添加超链接"
            :visible.sync="linkFlag"
            width="45%"
            :modal="false"
            :before-close="linkClose">
            <el-form :model="link" label-width="80px">
                <el-form-item label="标题">
                    <el-input v-model="link.title"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="link.path"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                    <el-button @click="linkClose">取 消</el-button>
                    <el-button type="primary" @click="insertLink">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import ImageUpload from '@/components/ImageUpload'

export default {
    name: "richText",
    components: {
        ImageUpload
    },
    props: {
        id: {
            type: String,
            default: function () {
                return 'vue-tinymce-' + +new Date() + ((Math.random() * 1000).toFixed(0) + '')
            }
        },
        content: {
            type: String
        }
    },
    data() {
        return {
            setting: {
                id: this.id,
                menubar: false,
                toolbar: "undo redo | fullscreen | formatselect alignleft aligncenter alignright alignjustify | numlist bullist   | bold italic underline strikethrough | indent outdent | superscript subscript | removeformat |",
                toolbar_drawer: "sliding",
                quickbars_selection_toolbar: "removeformat | bold italic underline strikethrough | fontsizeselect forecolor backcolor",
                plugins: "link image media table lists fullscreen quickbars",
                language: 'zh_CN', //本地化设置
                height: 550
            },
            text: '',
            editor: null,
            uploadFlag: false,
            linkFlag: false,
            link: {
                path: "", title: ""
            }
        }
    },
    created() {
        this.text = this.content != null ? this.content : ""
    },
    watch: {
        text(newValue) {
            // console.log(newValue)
            this.$emit('handleChange', newValue)
        }
    },
    methods: {
        insertPic(path, key) {
            this.editor.focus();
            this.editor.selection.setContent(this.editor.dom.createHTML('img', {src: path, 'data-key': key}));
        },
        insertLink() {
            let path = this.link.path
            let title = this.link.title
            this.linkClose()
            let html = `<a href="` + path + `" title="` + title + `">` + title + `</a>`
            // console.log(html)
            this.editor.focus();
            this.editor.selection.setContent(html);
        },
        uploadEmit(options) {
            if (options.type === "success") {
                this.insertPic(options.data, new Date() + ((Math.random() * 1000).toFixed(0) + ''))
            }
            this.uploadFlag = false
        },
        handleLink() {
            this.linkFlag = true
            this.link = {}
        },
        linkClose() {
            this.linkFlag = false
            this.link = {}
        },
        setup(editor) {
            // console.log(editor)
            this.editor = editor
        },
    }
}
</script>

<style scoped>

</style>
