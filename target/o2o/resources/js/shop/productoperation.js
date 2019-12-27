$(function() {
    // 从 URL 中获取 shopId 参数的值
    var productId = getQueryString('productId');
    // 通过 productId 获取商品信息的 URL
    var infoUrl = '/o2o/shopadmin/getproductbyid?productId=' + productId;
    // 获取当前店铺设定的商品类别列表的 URL
    var categoryUrl = '/o2o/shopadmin/getproductcategorylist';
    // 更新商品信息的 URL
    var productPostUrl = '/o2o/shopadmin/modifyproduct';
    // isEdit 标识符用于判断本次是添加还是编辑操作
    var isEdit = false;
    if(productId) {
        // 编辑操作
        getInfo(productId);
        isEdit = true;
    } else {
        // 添加操作
        getCategory();
        productPostUrl = '/o2o/shopadmin/addproduct';
    }

    // 获取需要编辑的商品的商品信息，并赋值给表单
    function getInfo(id) {
        $
            .getJSON(
                infoUrl,
                function (data) {
                    if(data.success) {
                        // 从返回的 JSON 中获取 product 对象的信息，并赋值给表单
                        var product = data.product;
                        $('#product-name ').val(product.productName);
                        $("#product-desc").val(product.productDesc);
                        $("#priority").val(product.priority);
                        $("#normal-price").val(product.normalPrice);
                        $("#promotion-price").val(product.promotionPrice);

                        // 获取原本的商品类别及该店铺的所有商品类别列表
                        var optionHtml = '';
                        var optionArr = data.productCategoryList;
                        var optionSelected = product.productCategory.productCategoryId;

                        // 生成前端的 HTML 商品类别列表，并默认选择编辑前的商品类别
                        optionArr
                            .map(function (item, index) {
                                var isSelect = optionSelected === item.productCategoryId ? 'selected'
                                    :'';
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
                }
            );
    }

    // 为商品添加操作提供该店铺下的所有商品类别列表
    function getCategory() {
        $
            .getJSON(
                categoryUrl,
                function (data) {
                if (data.success) {
                    var productCategoryList = data.data;
                    var optionHtml = '';
                    productCategoryList.map(function (item, index) {
                        optionHtml += '<option data-value="'
                            + item.productCategoryId + '">'
                            + item.productCategoryName + '</option>';
                    });
                    $('#category').html(optionHtml);
                }
            });
    }

    // 针对商品详情图控件组，若该控件的最后一个元素发生了变化
    // 且控件总数未达到6个，则生成一个新的文件上传控件
    $('.detail-img-div').on('change', '.detail-img:last-child', function () {
        if ($('.detail-img').length < 6) {
            $('#detail-img').append('<input type="file" class="detail-img">');
        }
    });

    // 提交按钮的事件相应，分别对商品添加和编辑操作做不同相应
    $("#submit").click(
        function () {
            // 创建商品 json 对象，并从表单里面获取对应的属性值
            var product = {};
            product.productName = $('#product-name').val();
            product.productDesc = $('#product-desc').val();
            product.priority = $('#priority').val();
            product.normalPrice = $('#normal-price').val();
            product.promotionPrice = $('#promotion-price').val();

            // 获取选定的商品类别值
            product.productCategory = {
                productCategoryId : $('#category').find('option').not(
                    function () {
                        return !this.selected;
                    }
                ).data('value')
            };
            product.productId = productId;

            // 获取略缩图文件流
            var thumbnail = $('#small-img')[0].files[0];
            // 生成表单对象，用于接收参数并传递给后台
            var formData = new FormData();
            formData.append("thumbnail", thumbnail);
            // 遍历商品详情图控制，获取里面的文件流
            $('.detail-img').map(
                function (index, item) {
                    // 判断该控件是否已经选择了文件
                    if ($('.detail-img')[index].files.length > 0) {
                        // 将第 i 个文件流赋值给 key 为 productImg i 的表单键值对中
                        formData.append('productImg' + index,
                            $('.detail-img')[index].files[0]);
                    }
                }
            );
            // 将 product json 对象转成字符流保存至表单对象 key 为 productStr 的键值对中
            formData.append('productStr', JSON.stringify(product));
            // 获取表单里输入的验证码
            var verifyCodeActual = $('#j_captcha').val();
            if(!verifyCodeActual) {
                $.toast('请输入验证码');
                return;
            }
            formData.append("verifyCodeActual", verifyCodeActual);
            // 将数据提交至后台处理相关操作
            $.ajax({
                url: productPostUrl,
                type: 'POST',
                data: formData,
                contentType: false,
                processData: false,
                cache: false,
                success : function (data) {
                    if(data.success) {
                        $.toast('提交成功！');
                        $('#captcha_img').click();
                    } else {
                        $.toast('提交失败！');
                        $('#captcha_img').click();
                    }
                }
            })
        }
    );
});