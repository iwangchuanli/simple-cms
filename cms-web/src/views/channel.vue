<template>
    <div>
        <div class="is-main f-mb15">
            <div class="container">
                <section class="m-location f-mb10">
                    <span>当前位置：</span>
                    <a href="/">网站首页</a> &gt;
                    <a >{{ cateInfo.name }}</a>
                </section>
                <div class="m-pgpdbox1">

                    <div class="row f-mlrf10">
                        <section class="col-md-3 f-plr10 m-listlf f-md-mb15">
                            <div class="g-listlf">
                                <h1 class="hidden-sm hidden-xs u-tit">
                                    <span></span>
                                    {{ cateInfo.name }}
                                </h1>
                                <h2 class="visible-sm visible-xs u-tit">{{ cateInfo.name }}</h2>
                                <ul>
                                    <li v-for="(item, index) in childList" :class=" index == activeIndex ? 'u-active' : ''" @click="handleSelectChild(index)">
                                        <a :title="item.name">{{ item.name }}</a>
                                    </li>
                                </ul>
                            </div>
                        </section>

                        <section class="col-md-9 f-plr10 m-listrg">
                            <div class="m-titstyle1 f-clearfix f-mb5">
                                <span class="u-tit f-fl" v-if="activeIndex < 0">{{ cateInfo.name }}<em></em></span>
                                <span class="u-tit f-fl" v-else>{{ childList[activeIndex].name }}<em></em></span>
                            </div>
                            <div class="m-cglist m-liststyle1 f-md-mb15">
                                <ul>
                                    <li v-for="(item, index) in cateArticleList" :key="index" @click="handeleNavigate(item)">
                                        <span>{{ formDate(item.createTime) }}</span>
                                        <a :title="item.name" target="_blank">{{ item.title }}</a>
                                    </li>
                                </ul>
                            </div>
                            <div class="text-right m-paginationyun7">
                                <div id="pagination" class="f-fr">
<!--                                    <pagination currentpage="1" pagesize="15" pagecount="1" total="3"></pagination>-->
                                </div>
                            </div>
                        </section>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import * as api from '@/api/api'

export default {
    name: "channel",
    data() {
        return {
            cateInfo: {},
            childList: [],
            cateArticleList: [],
            activeIndex: -1
        }
    },
    created() {
        this.loading()
    },
    methods: {
        loading() {
            this.$store.getters.cateList.forEach((item, index) => {
                if(this.$store.state.activeId === item.id) {
                    this.cateInfo = this.$store.getters.cateList[index]
                    document.title = this.cateInfo.name
                }
            })
            this.childList = this.cateInfo.childs
            if(this.$store.state.activeIndex === -1) {
                api.cateArticle({
                    siteId: this.$store.getters.siteId,
                    pCateId: this.$store.state.activeId,
                    size: 100
                }).then(res => {
                    this.cateArticleList = res
                })
            }else {
                this.handleSelectChild(this.$store.state.activeIndex)
            }
        },
        handleSelectChild(index) {
            console.log(index)
            console.log(this.childList[index])
            this.activeIndex = index
            api.articlePage({
                page: 0,
                pageSize: 100,
                siteId: this.childList[index].siteId,
                cateId: this.childList[index].id
            }).then(res => {
                console.log(res)
                this.cateArticleList = res.content
            })
        },
        handeleNavigate(item) {
            this.$router.push({
                path: `/detail/${item.id}`
            })
        }
    },
    watch: {
        '$store.state.activeId': function(newValue) {
            this.activeIndex = -1
            this.loading()
        }
    },
    computed: {
        formDate() {    // JS 不同时间格式转换(ISO时间&时间戳转北京时间)
            return date => {
                return date.substr(0,10)
            }
        }
    }
}
</script>

<style scoped>
</style>
