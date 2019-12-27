$(function () {
    // 从地址栏 url 中获取 productId
    var productId = getQueryString('productId');
    // 获取商品信息 Id
    var productUrl = '/o2o/frontend/listproductdetailpageinfo?productId=' + productId;
    // 访问后台获取该商品的信息并渲染
    $.getJSON(productUrl, function (data) {
        if(data.success) {
            // 获取商品信息
            var product = data.product;

            /*
             * 给商品信息相关 html 控件赋值
             */

            // 商品略缩图
            $('#product-img').attr('src', product.imgAddr);
            // 商品更新时间
            $('#product-time').text(
                new Date(product.lastEditTime).Format("yyyy-MM-dd")
            );
            // 商品名称
            $('#product-name').text(product.productName);
            // 商品简介
            $('#product-desc').text(product.productDesc);
            // 商品价格展示逻辑，主要判断原价现价是否为空
            if (product.normalPrice != undefined && product.promotionPrice != undefined) {
                // 如果原价和现价都不为空则都显示
                $('#price').show();
                $('#normalPrice').html(
                    '<del>' + '¥' + product.normalPrice + '</del>'
                );
                $('#promotionPrice').text('¥' + product.promotionPrice);
            } else if (product.normalPrice != undefined && product.promotionPrice == undefined) {
                // 只显示原价的情况
                $('#price').show();
                $('#normal-price').html(
                    '<del>' + '¥' + product.normalPrice + '</del>'
                );
            } else if (product.normalPrice == undefined && product.promotionPrice != undefined) {
                $('#promotionPrice').text('¥' + product.promotionPrice);
            }
            var imgListHtml = '';
            // 遍历商品详情图列表，并批量生成 img 标签
            product.productImgList.map(function (item, index) {
                imgListHtml += '<div><img src=""'
                            + item.imgAddr
                            + '"width=100%"/></div>';
            });
            $('#imgList').html(imgListHtml);
        }
    });

    // 点击打开右侧栏
    $('#me').click(function () {
        $.openPanel('#panel-right-demo');
    });

    $.init();
})