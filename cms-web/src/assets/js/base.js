/**
 * @description: 网站公共脚本
 * @author: ishang_pan
 * @update: ishang_pan (2020-06-16 09:25)
 */
import $ from 'jquery'
//设为首页

//加入收藏
function AddFavorite(sURL, sTitle) {
    sURL = encodeURI(sURL);
    try{
        window.external.addFavorite(sURL, sTitle);
    }catch(e) {
        try{
            window.sidebar.addPanel(sTitle, sURL, "");
        }catch (e) {
            alert("加入收藏失败，请使用Ctrl+D进行添加,或手动在浏览器里进行设置.");
        }
    }
}

//选项卡
$(function(){
    $('.tit-switch').children().children("li").mouseover(function(){
        $(this).addClass('u-active').siblings().removeClass('u-active');
        $(this).parent().parent().siblings('.list-switch').hide().eq($(this).index()).show();
    });
});

//返回顶部
$(function () {
    $('.j-goTop').click(function(){
        $('body,html').animate({scrollTop:0},800);
        return false;
    });
});

//下拉二级导航
	$(".nav li").hover(function () {
		$(this).find("dl").stop(false, true).slideToggle(500);
	});
	
// 查询显示
	$(".m-search1").click(function(){
		$(".text").slideToggle(500);
		$(".submit").slideToggle(500);
		$(this).toggleClass("u-close");
	});
//友情链接
	$(function(){
		$(".u-links dl").each(function(){
			var s=$(this);
			var z=parseInt(s.css("z-index"));
			var dt=s.children("dt");
			var dd=s.children("dd");
			var _show=function(){dd.slideDown(200);s.css("z-index",z+1);};
			var _hide=function(){dd.slideUp(200);s.css("z-index",z);};
			dt.click(function(){dd.is(":hidden")?_show():_hide();});
			dd.find("a").click(function(){_hide();});
			$("body").click(function(i){ !$(i.target).parents(".u-links dl").first().is(s) ? _hide():"";});
		})
	});