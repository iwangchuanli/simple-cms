/**
 * @description: 分页
 * @author: ishang_pan
 * @update: ishang_pan (2019-08-29 10:57)
 */

$(function () {
    var current  = parseInt($("pagination").attr("currentpage"));
    var count = parseInt($("pagination").attr("pagecount"));
    var total = parseInt($("pagination").attr("total"));
	if(count < 1){
		count = 1;
	}
	if(current > count){
		location.href = '/404.html';
	}
    if(count > 1){
        //$("#pagination").append('<ul id="paging" class="m-pagination pull-right f-clearfix"><li class="page-info"><a>'+current+' / '+count+'</a></li></ul>');
        $("#pagination").append('<ul id="paging" class="m-pagination f-clearfix"></ul>');
        if(current > 1){
            $("#paging").append('<li><a onclick="pageChange(1)">首页</a></li><li><a onclick="pageChange('+(current-1)+')">上页</a></li>');
        }else{
            $("#paging").append('<li class="disabled"><a>首页</a></li><li class="disabled"><a>上页</a></li>');
        }
        if(count <= 5 || (count > 5 && current < 3)) {
            for (var i = 1; i <= count && i <= 5; i++) {
                if (current == i) {
                    $("#paging").append('<li class="active"><a>' + i + '</a></li>');
                } else {
                    $("#paging").append('<li><a onclick="pageChange(' + i + ')">' + i + '</a></li>');
                }
            }
        }else if(count > 5 && (count-current) < 3){
            for (var i = count-4; i <= count; i++) {
                if (current == i) {
                    $("#paging").append('<li class="active"><a>' + i + '</a></li>');
                } else {
                    $("#paging").append('<li><a onclick="pageChange(' + i + ')">' + i + '</a></li>');
                }
            }
        }else if(count > 5 && current >= 3 && (count-current) >= 3){
            $("#paging").append('<li><a onclick="pageChange('+(current-2)+')">'+(current-2)+'</a></li>'
                +'<li><a onclick="pageChange('+(current-1)+')">'+(current-1)+'</a></li>'
                +'<li class="active"><a>'+current+'</a></li>'
                +'<li><a onclick="pageChange('+(current-0+1)+')">'+(current-0+1)+'</a></li>'
                +'<li><a onclick="pageChange('+(current-0+2)+')">'+(current-0+2)+'</a></li>');
        }
        if(current < count){
            $("#paging").append('<li><a onclick="pageChange('+(current-0+1)+')">下页</a></li><li><a onclick="pageChange('+count+')">末页</a></li>');
        }else{
            $("#paging").append('<li class="disabled"><a>下页</a></li><li class="disabled"><a>末页</a></li>');
        }
        $("#paging").append('<li class="u-redirect">共'+count+'页 到第 <input type="text" name="" id="redirect_page" onKeyPress="gopage(this.value,'+count+')" value="'+current+'"> 页</li>'
            +'<li class="u-btn"><a type="button" onclick="pageChange($(redirect_page).val(),'+count+');">确定</a></li>');
        $("#paging").append('<li class="page-info"><a>共' + total + '条</a></li>');
    }
});

//直达页面input，按回车键即可跳转
function gopage(page,totalpage) {
    var url = window.location.href;
    if (event.keyCode == 13) {
        if (Math.abs(page) > totalpage) page = totalpage;
        if(url.indexOf("page_") > -1) {
            url = url.replace(/page_[1-9][0-9]*/,"page_"+page);
        }else if (url.indexOf("page=") == -1) {
            if (url.indexOf("?") == -1) {
                url = url + "?page=" + page;
            }else{
                url = url + "&page=" + page;
            }
            url = url.replace(/page=[1-9][0-9]*/,"page="+page);
        }else if(url.indexOf("page=") > -1){
            url = url.replace(/page=[1-9][0-9]*/,"page="+page);
        }
        location.href = url;
    }
}

//点击跳转
function pageChange(page,totalpage){
    var url = window.location.href;
    if (Math.abs(page) > totalpage) page = totalpage;
    if(url.indexOf("page_") > -1) {
        url = url.replace(/page_[1-9][0-9]*/,"page_"+page);
    }else if (url.indexOf("page=") == -1) {
        if (url.indexOf("?") == -1) {
            url = url + "?page=" + page;
        }else{
            url = url + "&page=" + page;
        }
        url = url.replace(/page=[1-9][0-9]*/,"page="+page);
    }else if(url.indexOf("page=") > -1){
        url = url.replace(/page=[1-9][0-9]*/,"page="+page);
    }
    location.href = url;
}