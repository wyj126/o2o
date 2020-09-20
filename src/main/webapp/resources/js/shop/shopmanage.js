$(function(){
	var shopId = getQueryString('shopId');
	var shopInfoUrl = '/o2o/shopadmin/getManagementInfo?shopId=' + shopId;
	$.getJSON(shopInfoUrl,function(data){
		if (data.redirect){
			window.location.href = data.url;
		}else{
			if (data.shopId != null){
				shopId = data.shopId;
			}
			$('#shopInfo').attr('href','/o2o/shopadmin/shopedit?shopId=' + shopId);
		}
	});
});