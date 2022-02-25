<template>
    <div id="user-info">
        <div class="left-content">
            <el-image style="height: 100px;width: 100px;border-radius: 50%;box-shadow: 0 0 10px #e6e6e6"
                      :src="avatar" fit="contain" />
        </div>

        <div class="right-content">

            <div class="info-item">
                <div class="info-header-text">昵称：</div>
                <div class="info-right-content">
                    <el-input v-model="userInfo.nickName" placeholder="请输入昵称" :disabled="flag"></el-input>
                </div>
            </div>

            <div class="info-item">
                <div class="info-header-text">角色：</div>
                <div class="info-right-content">
                    <el-input v-model="role" placeholder="请输入昵称" :disabled="flag"></el-input>
                </div>
            </div>

            <div class="info-item">
                <div class="info-header-text">账号：</div>
                <div class="info-right-content">
                    <el-input v-model="userInfo.username" placeholder="请输入账号" :disabled="flag"></el-input>
                </div>
            </div>

            <div class="info-item">
                <div class="info-header-text">密码：</div>
                <div class="info-right-content">
                    <el-input v-model="userInfo.password" placeholder="请输入密码" :disabled="flag"></el-input>
                </div>
            </div>

            <div class="info-item">
                <div class="info-header-text">号码：</div>
                <div class="info-right-content">
                    <el-input v-model="userInfo.phone" placeholder="请输入号码" :disabled="flag"></el-input>
                </div>
            </div>

            <div class="info-item">
                <div class="info-header-text">邮箱：</div>
                <div class="info-right-content">
                    <el-input v-model="userInfo.email" placeholder="请输入号码" :disabled="flag"></el-input>
                </div>
            </div>

            <div class="info-item">
                <div class="info-header-text">编辑：</div>
                <div class="info-right-content">
                    <el-button type="danger" @click="handleActiveFlag">修改信息</el-button>
                    <el-button type="info" @click="handleUpdate">确 认</el-button>
                </div>
            </div>

        </div>
    </div>
</template>

<script>
import * as userApi from '@/api/user'

export default {
    name: "userInfo",
    props: {
        userInfo: {
            type: Object
        }
    },
    data() {
        return {
            flag: true
        }
    },
    // created() {
    //     console.log(this.userInfo)
    // },
    methods: {
        handleActiveFlag() {
            this.flag = false
        },
        handleUpdate() {
            userApi.update(this.userInfo).then(res => {
                this.flag = true
                console.log(res)
            })
        }
    },
    computed: {
        avatar() {
            return this.userInfo.avatarUrl ? this.userInfo.avatarUrl :  require('@/assets/layout/default_avatar.jpg')
        },
        role() {
            switch (this.userInfo.role) {
                case 0:
                    return '超级管理员'
                case 1:
                    return '管理员'
                case 2:
                    return '编辑'
                case 3:
                    return '用户'
                default:
                    console.log('error')
            }
        }
    }
}
</script>

<style scoped>

#user-info {
    display: flex;
    justify-content: space-between;
    margin: 25px;
    padding: 25px;
    border-bottom: 1px solid #e6e6e6;
    background-color: #f8f8f8;
}

/* 左 */
.left-content {
    width: 180px;
}

/*.left-content-nickname {*/
/*    font-size: 18px;*/
/*    font-weight: bold;*/
/*    width: 175px;*/
/*    margin: 15px;*/
/*    overflow: hidden;*/
/*    text-overflow: ellipsis;*/
/*    white-space: nowrap;*/
/*}*/

/* 右 */
.right-content {
    width: calc(100% - 180px);
}

.info-item {
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 15px;
    color: gray;
    line-height: 25px;
    margin: 15px 0;
}

.info-header-text {
    width: 50px;
    font-size: 16px;
    color: #333;
    font-weight: bold;
}

.info-right-content {
    width: calc(100% - 55px);
}

</style>
