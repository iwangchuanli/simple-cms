/**
 * @description: share
 * @author: ishang
 * @update: ishang_pan (2020-06-16 09:25)
 */
 
$(function(){
	var _webUrl = window.location.href;
	
	if($('#j-wxqrcode').length > 0){
		var wxqrcode = new QRCode(document.getElementById("j-wxqrcode"), {
			width : 80,
			height : 80
		});
		wxqrcode.makeCode(_webUrl);
	}

	$('.j-share a').click(function(){
		var _type = $(this).attr('data-type');
		var _title = $(document).attr("title");
		var _pic = $('.j-fontContent').find('img:first').attr('src') || '';
		var newsObj = {
			url: _webUrl,
			title: _title,
			pic: _pic
		}
		if(_type == 'tsina'){
			window.open("http://service.weibo.com/share/share.php?url=" + encodeURIComponent(newsObj.url) + "%230-tsina&title=" + encodeURIComponent(newsObj.title) + "&summery=&pic=" + encodeURIComponent(newsObj.pic));
		}else if(_type == 'qzone'){
			window.open("http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=" + encodeURIComponent(newsObj.url) + "&title=" + encodeURIComponent(newsObj.title) + "&pics=" + encodeURIComponent(newsObj.pic));
		}else{
			$('.weixin-share-open').show();
		}
	});
	
	$('.weixin-close').click(function(){
		$('.weixin-share-open').hide();
	})
})