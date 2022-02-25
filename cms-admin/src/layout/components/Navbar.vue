<template>
    <div class="navbar">
        <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar"/>

        <breadcrumb class="breadcrumb-container"/>

        <div class="right-menu">
            <el-dropdown class="avatar-container" trigger="click">
                <div class="avatar-wrapper">
                    <img :src="avatar" class="user-avatar">
                    <i class="el-icon-caret-bottom"/>
                </div>
                <el-dropdown-menu slot="dropdown" class="user-dropdown">
                    <router-link to="/">
                        <el-dropdown-item>
                            首页
                        </el-dropdown-item>
                    </router-link>
                    <el-dropdown-item divided @click.native="isShowModifyPwdDialog = true">修改密码</el-dropdown-item>
                    <el-dropdown-item divided>
                        <span style="display:block;" @click="dialogVisible=true">个人资料</span>
                    </el-dropdown-item>
                    <el-dropdown-item divided @click.native="logout">
                        <span style="display:block;">退出</span>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
        <el-dialog :visible.sync="isShowModifyPwdDialog" title="修改密码" width="400px">
            <el-form label-width="70px" size="small" :model="user" ref="modifyForm">
                <el-form-item label="原密码" prop="oldPwd" :rules="[ { required: true, message: '请输入原密码', trigger: 'blur' },]">
                    <el-input v-model="user.oldPwd"></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="newPwd" :rules="[ { required: true, message: '请输入新密码', trigger: 'blur' },]">
                    <el-input v-model="user.newPwd"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button @click="isShowModifyPwdDialog = false">取消</el-button>
                    <el-button type="primary" @click="modifyPwd('modifyForm')">确定</el-button>
                </el-form-item>
            </el-form>

        </el-dialog>

        <el-dialog
            title="个人资料"
            :visible.sync="dialogVisible"
            width="60%"
            :before-close="handleClose">
            <user-form :user-info="userInfo"></user-form>
            <span slot="footer" class="dialog-footer">
            <el-button @click="handleClose">取 消</el-button>
            <el-button type="primary" @click="handleSubmit">确 定</el-button>
          </span>
        </el-dialog>
    </div>
</template>

<script>
import {mapGetters} from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import * as userApi from '../../api/user'
import userForm from "@/views/user/components/userForm";

export default {
    components: {
        Breadcrumb,
        Hamburger, userForm
    },
    computed: {
        ...mapGetters([
            'sidebar'
        ]),
        avatar() {
            return this.$store.getters.userInfo !== undefined ? this.$store.getters.userInfo.avatarUrl : require('@/assets/layout/default_avatar.jpg')
        }
    },
    data() {
        return {
            dialogVisible: false,
            userInfo: this.$store.getters.userInfo,
            isShowModifyPwdDialog: false,
            user:{
                userId:this.$store.getters.userInfo.id,
                oldPwd:"",
                newPwd:""
            }
        }
    },
    methods: {
        modifyPwd(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    userApi.modifyPwd(this.user).then(res => {
                        this.$message.success("修改密码成功")
                        this.$router.push('/login')
                    }).catch(err => {
                        this.$message.error(err);
                    })
                } else {
                    return false
                }
            })
        },

        getItem() {
            userApi.item({}).then(res => {
                this.$store.commit('user/SET_USERINFO', res)
            })
        },
        handleSubmit() {
            userApi.update(this.userInfo).then(res => {
                this.$message({message: "修改成功", type: "success"})
                this.$store.commit('user/SET_USERINFO', res)
                this.dialogVisible = false
            })
        },
        handleClose() {
            this.dialogVisible = false
            this.getItem()
        },
        toggleSideBar() {
            this.$store.dispatch('app/toggleSideBar')
        },
        async logout() {
            // await this.$store.dispatch('user/logout')
            // this.$router.push(`/login?redirect=${this.$route.fullPath}`)
            this.$store.commit('user/SET_TOKEN', {})
            this.$store.commit('user/SET_USERINFO', {})
            this.$router.push(`/login?redirect=${this.$route.fullPath}`)
        }
    }
}
</script>

<style lang="scss" scoped>
.navbar {
    height: 50px;
    overflow: hidden;
    position: relative;
    background: #fff;
    box-shadow: 0 1px 4px rgba(0, 21, 41, .08);

    .hamburger-container {
        line-height: 46px;
        height: 100%;
        float: left;
        cursor: pointer;
        transition: background .3s;
        -webkit-tap-highlight-color: transparent;

        &:hover {
            background: rgba(0, 0, 0, .025)
        }
    }

    .breadcrumb-container {
        float: left;
    }

    .right-menu {
        float: right;
        height: 100%;
        line-height: 50px;

        &:focus {
            outline: none;
        }

        .right-menu-item {
            display: inline-block;
            padding: 0 8px;
            height: 100%;
            font-size: 18px;
            color: #5a5e66;
            vertical-align: text-bottom;

            &.hover-effect {
                cursor: pointer;
                transition: background .3s;

                &:hover {
                    background: rgba(0, 0, 0, .025)
                }
            }
        }

        .avatar-container {
            margin-right: 30px;

            .avatar-wrapper {
                margin-top: 5px;
                position: relative;

                .user-avatar {
                    cursor: pointer;
                    width: 40px;
                    height: 40px;
                    border-radius: 10px;
                }

                .el-icon-caret-bottom {
                    cursor: pointer;
                    position: absolute;
                    right: -20px;
                    top: 25px;
                    font-size: 12px;
                }
            }
        }
    }
}
</style>
