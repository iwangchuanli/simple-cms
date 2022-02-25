<template>
    <div>
        <div class="is-main f-mb15">
            <div class="container">
                <section class="m-location f-mb10">
                    <span>当前位置：</span>
                    <a href="/">网站首页</a> &gt;
                    <a href="/content/channel/5fe009923db32aab1c7b23c7/">文章详情</a>
                </section>

                <div class="m-pgpdbox1">
                    <div class="m-detailbox">
                        <h1 class="u-lgtit text-center f-mb15 f-md-mb10">{{ articleDetail.title }}</h1>
                        <div class="m-dtfuns f-clearfix">
                            <div class="u-wzinfo f-fl f-md-fn">
                                <span>发布时间：{{ articleDetail.time }}</span>
                                <span>作者：{{ articleDetail.author }}</span>
                                <span>浏览：{{ articleDetail.views }} 次</span>
                            </div>
<!--                            <div class="share-main f-fr">-->
<!--                                <div class="share-bar j-share">-->
<!--                                    <a class="share-tsina" data-type="tsina" title="分享到新浪微博" href="javascript:;"></a>-->
<!--                                    <a class="share-weixin" data-type="weixin" title="分享到微信" href="javascript:;"></a>-->
<!--                                    <a class="share-qzone" data-type="qzone" title="分享到QQ空间" href="javascript:;"></a>-->
<!--                                </div>-->
<!--                                <div class="weixin-share-open">-->
<!--                                    <div class="weixin-wrap">-->
<!--                                        <div class="share-qrcode" id="j-wxqrcode"></div>-->
<!--                                        <div class="weixin-text">-->
<!--                                            <p>用微信扫描二维码<br>分享至好友和朋友圈</p>-->
<!--                                        </div>-->
<!--                                        <span class="weixin-close">×</span>-->
<!--                                    </div>-->
<!--                                </div>-->
<!--                            </div>-->
<!--                            <div class="u-fontsize f-fr f-md-fn">-->
<!--                                【字体大小：-->
<!--                                <a href="javascript:doZoom(20)">大</a>-->
<!--                                <a href="javascript:doZoom(16)">中</a>-->
<!--                                <a href="javascript:doZoom(12)">小</a>】-->
<!--                            </div>-->
                        </div>
                        <div class="m-dtfonts">
                            <div class="m-dttexts f-clearfix j-fontContent" id="zoom" v-html="articleDetail.detail"></div>
                        </div>
                        <div class="m-dtdownload f-mb15">
                            <ul>
                            </ul>
                        </div>
                        <div class="m-btfuns f-clearfix f-mb15">
                            <ul class="f-fr">
                                <li class="u-gotp j-goTop">
                                    <a :href="item" target="_blank" v-for="(item, index) in articleDetail.files" :key="index">
                                        {{ '附件' + getFilesName(item) }}
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div class="m-dtsxqh f-clearfix">
                            <ul>
                                <li class="f-fl u-dtprev" @click="handlePrev">
                                    上一条：
                                    <a v-if="prev != null">{{ prev.title }}</a>
                                    <a v-else>没有上一条了</a>
                                </li>
                                <li class="f-fr u-dtnext" @click="handleNext">
                                    下一条：
                                    <a v-if="next != null">{{ next.title }}</a>
                                    <a v-else>没有下一条了</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import * as api from '@/api/api'

export default {
    name: "detail",
    data() {
        return {
            articleDetail: {},
            next: {},
            prev: {}
        }
    },
    created() {
        this.activeId = this.$route.params.id
        this.getArticleDetail(this.$route.params.id)
    },
    methods: {
        getArticleDetail(id) {
            api.articleItem({ articleId: id }).then(res => {

                let head = document.getElementsByTagName("head")    // 修改meta
                let meta = document.createElement("meta")
                meta.content = res.description

                if(this.$store.state.meta != null) {
                    head[0].replaceChild(meta, this.$store.state.meta)
                }else {
                    head[0].appendChild(meta)
                }
                this.$store.state.meta = meta



                document.title = res.title      // 修改title
                console.log(res)
                this.articleDetail = res
                if(this.articleDetail.files != null) {
                    this.articleDetail.files = JSON.parse(this.articleDetail.files)
                }
                this.next = res.next
                this.prev = res.prev
            })
        },
        handleNext() {
            // this.getArticleDetail(this.next.id)
            this.$store.commit('SET_ACTIVEARTIVLEID', this.next.id)
            this.$router.push({
                path: `/detail/${this.next.id}`
            })
        },
        handlePrev() {
            // this.getArticleDetail(this.prev.id)
            this.$store.commit('SET_ACTIVEARTIVLEID', this.prev.id)
            this.$router.push({
                path: `/detail/${this.prev.id}`
            })
        }
    },
    computed: {
        getFilesName() {
            return item => {
                return item.substring(item.length - 25)
            }
        }
    },
    watch: {
        '$store.state.activeArticleId': function(newValue) {
            this.getArticleDetail(newValue)
        }
    }
}
</script>

<style scoped>
</style>
