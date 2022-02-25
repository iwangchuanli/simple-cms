<template>
    <div class="login-container">
        <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">

            <div class="title-container">
                <h3 class="title">系 统 登 陆</h3>
            </div>

            <el-form-item prop="username">
                <span class="svg-container">
                  <svg-icon icon-class="user" />
                </span>
                <el-input ref="username" v-model="loginForm.username" placeholder="账 号" name="username"
                          type="text" tabindex="1" auto-complete="on"/>
            </el-form-item>

            <el-form-item prop="password">
                <span class="svg-container">
                  <svg-icon icon-class="password" />
                </span>
                <el-input :key="passwordType" ref="password" v-model="loginForm.password" :type="passwordType"
                    placeholder="密 码" name="password" tabindex="2" auto-complete="on" @keyup.enter.native="handleLogin" />
                <span class="show-pwd" @click="showPwd">
                    <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
                </span>
            </el-form-item>

            <el-form-item>
                <span class="svg-container"><svg-icon icon-class="user" /></span>
                <el-input v-model="loginForm.verifyCode" name="verifyCode" type="text" width="200px">
                    <div slot="suffix">
                        <el-image class="code-img" :src="codeImg" fit="cover" @click="getCode"></el-image>
                    </div>
                </el-input>
            </el-form-item>

            <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="handleLogin">Login</el-button>

<!--            <div class="tips">-->
<!--                <span style="margin-right:20px;">username: admin</span>-->
<!--                <span> password: any</span>-->
<!--            </div>-->

        </el-form>
    </div>
</template>

<script>
import * as userApi from '@/api/user'
import * as commonApi from '@/api/common'

export default {
    name: 'Login',
    data() {
        const validateUsername = (rule, value, callback) => {
            if (value===undefined||value===null) {
                callback(new Error('Please enter the correct user name'))
            } else {
                callback()
            }
        }
        const validatePassword = (rule, value, callback) => {
            if (value.length < 6) {
                callback(new Error('The password can not be less than 6 digits'))
            } else {
                callback()
            }
        }
        return {
            loginForm: {
                username: '',
                password: '',
                verifyCode: ''
            },
            loginRules: {
                username: [{ required: true, trigger: 'blur', validator: validateUsername }],
                password: [{ required: true, trigger: 'blur', validator: validatePassword }]
            },
            loading: false,
            passwordType: 'password',
            redirect: undefined,
            codeImg: '',
            verifyCode: ''
        }
    },
    watch: {
        $route: {
            handler: function(route) {
                this.redirect = route.query && route.query.redirect
            },
            immediate: true
        }
    },
    created() {
        this.getCode()
    },
    methods: {
        getCode() { // 获取验证码
            commonApi.getVerify().then(res => {
                this.verifyCode = res.code
                this.codeImg = res.codeImg
            })
        },
        showPwd() {
            if (this.passwordType === 'password') {
                this.passwordType = ''
            } else {
                this.passwordType = 'password'
            }
            this.$nextTick(() => {
                this.$refs.password.focus()
            })
        },
        handleLogin() { // 登陆
            // if(this.loginForm.verifyCode !== this.verifyCode) {
            //     this.$message('验证码错误');
            //     this.getCode()
            // }
            this.$refs.loginForm.validate(valid => {
                if (valid) {
                    this.loading = true

                    userApi.login(this.loginForm).then(res => {
                        this.$store.commit("user/SET_TOKEN",res.cmsToken)
                        this.$store.commit("user/SET_USERINFO",res.details)
                        console.log(this.$store.getters.userInfo)
                        this.$router.push({path:'/'})
                        this.loading = false
                    }).catch(rej => {
                        this.loading = false
                    })
                } else {
                    console.log('error submit!!')
                    return false
                }
            })
        }
    }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg:#283443;
$light_gray:#fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
    .login-container .el-input input {
        color: $cursor;
    }
}

/* reset element-ui css */
.login-container {
    .el-input {
        display: inline-block;
        height: 47px;
        width: 85%;

        input {
            background: transparent;
            border: 0px;
            -webkit-appearance: none;
            border-radius: 0px;
            padding: 12px 5px 12px 15px;
            color: $light_gray;
            height: 47px;
            caret-color: $cursor;

            &:-webkit-autofill {
                box-shadow: 0 0 0px 1000px $bg inset !important;
                -webkit-text-fill-color: $cursor !important;
            }
        }
    }

    .el-form-item {
        border: 1px solid rgba(255, 255, 255, 0.1);
        background: rgba(0, 0, 0, 0.1);
        border-radius: 5px;
        color: #454545;
    }
}
</style>

<style lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;

.login-container {
    min-height: 100%;
    width: 100%;
    background-color: $bg;
    overflow: hidden;

    .login-form {
        position: relative;
        width: 520px;
        max-width: 100%;
        padding: 160px 35px 0;
        margin: 0 auto;
        overflow: hidden;
    }

    .tips {
        font-size: 14px;
        color: #fff;
        margin-bottom: 10px;

        span {
            &:first-of-type {
                margin-right: 16px;
            }
        }
    }

    .svg-container {
        padding: 6px 5px 6px 15px;
        color: $dark_gray;
        vertical-align: middle;
        width: 30px;
        display: inline-block;
    }

    .title-container {
        position: relative;

        .title {
            font-size: 26px;
            color: $light_gray;
            margin: 0px auto 40px auto;
            text-align: center;
            font-weight: bold;
        }
    }

    .show-pwd {
        position: absolute;
        right: 10px;
        top: 7px;
        font-size: 16px;
        color: $dark_gray;
        cursor: pointer;
        user-select: none;
    }

    .code-img {
        width: 100px;
        height: 51px;
    }

}
</style>
