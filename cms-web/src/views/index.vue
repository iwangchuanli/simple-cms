<template>
    <div>
        <!--banner on-->
        <div>
            <el-carousel height="380px" arrow="always">
                <el-carousel-item v-for="(item, index) in banner" :key="index">
                    <img :src="item" style="width: 100%;height: 100%;">
                </el-carousel-item>
            </el-carousel>
        </div>
        <!--banner off-->

        <div class="is-main">
            <div class="container" v-if="flag">
                <div class="m-work f-mb20">
                    <a target="_blank" class="u-workt f-db" v-if="recommendArticle.length > 0">
                        {{ recommendArticle[0].title }}
                    </a>
                    <div class="u-workc row f-clearfix">
                        <div class="u-workl col-md-6">
                            <el-carousel height="380px" arrow="always" v-if="recommendArticle.length > 1">
                                <el-carousel-item :key="index" :label="index + 1" v-for="(item, index) in recommendArticle">
                                    <a>
                                        <img style="width: 100%;height: 100%" :src="item.header" alt="">
                                    </a>
                                </el-carousel-item>
                            </el-carousel>
                        </div>

                        <div class="u-workr f-md-ha col-md-6">
                            <div class="u-workrt">
                                <ul class="f-clearfix">
                                    <li v-for="(recommendItem, recommendIndex) in recommendCate" :key="recommendIndex" v-if=" recommendIndex < 4" @click="handleRecommend(recommendIndex)">
                                        <a target="_blank" class="f-db" :class="recommendIndex == activeIndex ? 'active' : '' ">{{ recommendItem.name }}</a>
                                    </li>
                                </ul>
                            </div>
                            <div class="u-workroutc">
                                <div class="u-workrc">
                                    <div class="u-workrclist f-clearfix" v-for="(articleItem, articleIndex) in articleList" v-if="articleIndex < 4" @click="handeleNavigate(articleItem)">
                                        <div class="u-time f-fl">
                                            <p>{{ formDay(articleItem.createTime) }}</p>
                                            <p>{{ formDate(articleItem.createTime) }}</p>
                                        </div>
                                        <div class="u-workrc1 f-fl">
                                            <a target="_blank">{{ articleItem.title }}</a>
                                            <p>{{ articleItem.description }}</p>
                                        </div>
                                        <div class="u-more f-fr">
                                            <a target="_blank" class="f-db"></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="m-adv f-mb20 visible-lg visible-md c-shine f-pr f-oh">
                    <img src="@/assets/images/20210108090736.EPUgNvaerTyG.png" alt="">
                </div>

                <div class="m-listhome row" v-if="parityFlag">
                    <div class="col-md-6 f-mb20" v-for="(normalItem, normalIndex) in normalCate" :key="normalIndex">
                        <div class="u-listhome f-md-ha">
                            <div class="u-listhomet f-clearfix f-mb10">
                                <a class="u-listhometl f-db f-fl">{{ normalItem.name }}</a>
                                <a class="u-listhometr f-db f-fr" @click="handleNavigate(normalItem, -1)">MORE+</a>
                            </div>
                            <div class="u-listhomec">
                                <ul>
                                    <li class="f-clearfix" v-for="(articleItem, articleIndex) in normalItem.articleList" :key="articleIndex" @click="handeleNavigate(articleItem)">
                                        <a :title="articleItem.title" target="_blank" class="f-db f-fl">
                                            {{ articleItem.title }}
                                        </a>
                                        <span class="f-fr">{{ formDate(articleItem.createTime) }}</span>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="m-listhome row" v-if="!parityFlag">
                    <div class="col-md-6 f-mb20" v-for="(normalItem, normalIndex) in normalCate" :key="normalIndex" v-if="normalIndex < normalCate.length - 1">
                        <div class="u-listhome f-md-ha">
                            <div class="u-listhomet f-clearfix f-mb10">
                                <a class="u-listhometl f-db f-fl">{{ normalItem.name }}</a>
                                <a class="u-listhometr f-db f-fr" @click="handleNavigate(normalItem, -1)">MORE+</a>
                            </div>
                            <div class="u-listhomec">
                                <ul>
                                    <li class="f-clearfix" v-for="(articleItem, articleIndex) in normalItem.articleList" :key="articleIndex" @click="handeleNavigate(articleItem)">
                                        <a :title="articleItem.title" target="_blank" class="f-db f-fl">
                                            {{ articleItem.title }}
                                        </a>
                                        <span class="f-fr">{{ formDate(articleItem.createTime) }}</span>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="m-jianshe f-mb20 f-pr f-oh" v-if="!parityFlag">
                    <div class="u-listhomet f-clearfix f-mb10">
                        <a class="u-listhometl f-db f-fl">{{ lastNormalCate.name }}</a>
                        <a class="u-listhometr f-db f-fr" @click="handleNavigate(lastNormalCate, -1)">MORE+</a>
                    </div>
                    <div class="u-jianshe">
                        <ul class="f-clearfix">
                            <li v-for="(articleItem, articleIndex) in lastNormalCate.articleList" :key="articleIndex" v-if="articleIndex < 2" @click="handeleNavigate(articleItem)">
                                <a :title="lastNormalCate" target="_blank" class="f-db">
                                    <div class="u-jiansheimg">
                                        <img :src="articleItem.header" alt="">
                                    </div>
                                <p>{{ articleItem.title }}</p>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>

            </div>
        </div>
    </div>
</template>

<script>
import '../assets/js/qrcode.min.js'

import * as api from '@/api/api'

export default {
    name: "index",
    data() {
        return {
            companyInfo: {},
            articleList: [],
            recommendCate: [],
            normalCate: [],
            recommendArticle: [],
            flag: false,
            activeIndex: 0,
            parityFlag: true,   // 默认偶数
            lastNormalCate: {}
        }
    },
    created() {

        let meta = document.createElement("meta")
        meta.id = 'index'
        meta.content = 'sbmklsb'


        document.title = '首页'
        this.companyInfo = this.$store.state.siteInfo
        api.indexData({siteId: this.$store.getters.siteId}).then(res => {
            console.log(res)
            this.recommendArticle = res.recommendArticle
            this.normalCate = res.normalCate
            if((this.normalCate.length % 2) !== 0) {
                this.parityFlag = false
                this.lastNormalCate = this.normalCate[this.normalCate.length - 1]
            }

            this.recommendCate = res.recommendCate
            if(this.recommendCate.length === 0) {
                this.articleList = []
            } else {
                this.articleList = this.recommendCate[0].articleList
            }

            this.flag = true
        })
    },
    methods: {
        handleRecommend(recommendIndex) {
            this.activeIndex = recommendIndex
            this.articleList = this.recommendCate[recommendIndex].articleList
        },
        handeleNavigate(item) {
            this.$router.push({
                path: `/detail/${item.id}`
            })
        },
        handleNavigate(item, index) {
            this.$store.commit('SET_ACTIVEIDDEX', index)
            this.$store.commit('SET_ACTIVEID', item.id)
            this.$router.push({
                path: `/channel/${item.id}`,
            })
        }
    },
    computed: {
        formDate() {    // JS 不同时间格式转换(ISO时间&时间戳转北京时间)
            return date => {

                return date.substr(0,10)
            }
        },
        formDay() {
            return day => {
                let dt = new Date(day)
                dt = dt.getDate()
                return dt < 10 ? '0' + dt : dt;
            }
        },
        banner() {
            return this.$store.getters.siteInfo.banner
        }
    }
}
</script>

<style scoped>

.active {
    color: #005B84;
}

</style>
