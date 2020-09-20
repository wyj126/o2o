$(function (){
	//从Url中获取productId
	var productId = getQueryString('productId');
	//通过productId获取商品信息的url
	var infoUrl = '/o2o/shopadmin/getProductById=' + productId;
	//获取当前店铺设定的商品类别列表的Url
	var categoryUrl = '/o2o/shopadmin/getProductCategoryList';
	//更新商品信息的Url
	var productPostUrl = '/o2o/shopadmin/modifyProduct';

	//标识是添加还是编辑
	var isEdit = false;
	if (productId){
		//编辑
		getInfo(productId);
		isEdit = true;
	}else{
		getCategory();
		productPostUrl = '/o2o/shopadmin/addProduct';
	}
	
	//获取商品信息并赋值给表单
	function getInfo(){
		$.getJSON(infoUrl,function(data){
			if (data.success){
				//从JSON中获取product信息并赋值给表单
				var product = data.product;
				$('product-name').val(product.productName);
				$('product-desc').val(product.productDesc);
				$('priority').val(priority);
				$('normal-price').val(product.normalPrice);
				$('promotion-price').val(product.promotionPrice);
				var optionHtml = '';
				var optionArr = data.productCategoryList;
				var optionSelected = product.productCategory.productCategoryId;
				optionArr.map(function(item,index){
					var isSelect = optionSelected === item.productCategoryId ? 'selected' : '';
					optionHtml += '<option data-value="'
								+ item.productCategoryId
								+ '"'
								+ isSelect
								+ '>'
								+ item.productCategoryName
								+ '</option>';
				});
				$('#category').html(optionHtml);
			}
		});
	}
	
	function getCategory(){
		$.getJSON(categoryUrl, function(data){
			if (data.success){
				var productCategoryList = data.productCategoryList;
				var optionHtml = '';
				productCategoryList.map(function(item, index){
					optionHtml += '<option data-value="'
								+ item.productCategoryId
								+ '">'
								+ item.productCategoryName
								+ '</option>';
				});
				$('#category').html(optionHtml);
			}
		});
	} 
	
	$('.detail-img-div').on('change','detail-img:last-child',function(){
		if ($('.detail-img').length < 6){
			$('.detail-img').append('<input type="file" class="detail-img">');
		}
		
	});
	
	$('.submit').click(function(){
		var product = {};
		product.productName = $('#product-name').val();
		product.productDesc = $('#product-desc').val();
		product.priority = $('#priority').val();
		product.normalPrice = $('#normal-price').val();
		product.promotionPrice = $('#promotion-price').val();
		product.productCategory = {
			productCategoryId : $('category').find('option').not(
				function(){
					return !this.selected;
				}).data('value')
		};
		product.productId = productId;
		
		var thumbnail = $('$small-img')[0].files[0];
		console.log(thumbnail);
		var formData = new FormData();
		$('.detail-img').map(function(item, index){
			if ($('.detail-img')[index].files.length > 0){
				formData.append('productImgId'+index,$('.detail-ing')[index].files[0]);
			}
		});
		formData.append('productStr',JSON.stringify(product));
		var verityCodeActal = $('#j_captcha').val();
		if (!verityCodeActal){
			$.toast('请输入验证码');
			return;
		}
		formData.append('verityCodeActal',verityCodeActal);
		$.ajax({
			url : productPostUrl,
			data : formData,
			contentType : false,
			processdData : false,
			cache : false,
			success : function(){
				if (data.success){
					$.toast("提交成功");
					$('#captcha_img').click();
				}else {
					$.toast("提交成功");
					$('#captcha_img').click();
				}
			}
			
		});
	});
	
});