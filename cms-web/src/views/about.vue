<template>
    <div>
        <div class="is-main f-mb15">
            <div class="container">
                <section class="m-location f-mb10">
                    <span>当前位置：</span>
                    <a href="/">网站首页</a> &gt;
                    <a href="/content/channel/5fe009923db32aab1c7b23c7/">关于我们</a>
                </section>


                <div class="m-pgpdbox1">
                    <div class="m-detailbox">
                        <h1 class="u-lgtit text-center f-mb15 f-md-mb10">{{ siteInfo.name }}</h1>
                        <div class="m-dtfuns f-clearfix">
                            <div class="u-wzinfo f-fl f-md-fn">
                                <span>站点：{{ siteInfo.domain }}</span>
                                <span>昵称：{{ siteInfo.nickName }}</span>
<!--                                <span>浏览：{{ siteInfo.views }} 次</span>-->
                            </div>
                            <div class="share-main f-fr">
                                <div class="share-bar j-share">
                                    <a class="share-tsina" data-type="tsina" title="分享到新浪微博" href="javascript:;"></a>
                                    <a class="share-weixin" data-type="weixin" title="分享到微信" href="javascript:;"></a>
                                    <a class="share-qzone" data-type="qzone" title="分享到QQ空间" href="javascript:;"></a>
                                </div>
                                <div class="weixin-share-open">
                                    <div class="weixin-wrap">
                                        <div class="share-qrcode" id="j-wxqrcode"></div>
                                        <div class="weixin-text">
                                            <p>用微信扫描二维码<br>分享至好友和朋友圈</p>
                                        </div>
                                        <span class="weixin-close">×</span>
                                    </div>
                                </div>
                            </div>
                            <div class="u-fontsize f-fr f-md-fn">
                                【字体大小：
                                <a href="javascript:doZoom(20)">大</a>
                                <a href="javascript:doZoom(16)">中</a>
                                <a href="javascript:doZoom(12)">小</a>】
                            </div>
                        </div>
                        <div class="m-dtfonts">
                            <div class="m-dttexts f-clearfix j-fontContent" id="zoom" v-html="siteInfo.detail"></div>
                        </div>
                        <div class="m-dtsxqh f-clearfix">
                            <ul>
                                <li class="f-fl u-dtprev"></li>
                                <li class="f-fr u-dtnext"></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import store from '@/store'

export default {
    name: "detail",
    data() {
        return {
            siteInfo: {}
        }
    },
    created() {
        document.title = '关于我们'
        this.siteInfo = JSON.parse(sessionStorage.getItem("SITEINFO"))
        console.log(this.siteInfo)
    }
}
</script>

<style scoped>
</style>
