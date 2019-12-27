$(function () {
    // 防止重复加载标识符
    var loading = false;
    // 默认一页返回的商品数
    var pageSize = 10;
    // 列出商品列表的 url
    var listUrl = '/o2o/frontend/listproductsbyshop';
    // 默认的页码
    var pageNum = 1;
    // 从地址栏中获取 shopId
    var shopId = getQueryString('shopId');
    var productCategoryId = '';
    var productName = '';
    // 获取本店信息以及商品类别信息列表的 url
    var searchDivUrl = '/o2o/frontend/listshopdetailpageinfo?shopId=' + shopId;
    // 渲染出店铺基本信息以及商品类别列表以供搜索
    getSearchDivData();
    // 预先加载商品信息，默认 10 条
    addItems(pageSize, pageNum);

    // 给兑换礼品的 a 标签赋值<兑换礼品的 url>，属于 2.0 版本
    // $('#exchangelist').attr('href', '/o2o/frontend/awardlist?shopId=' + shopId);

    /**
     *
     * 获取本店铺信息以及商品类别信息列表
     */
    function getSearchDivData() {
        var url = searchDivUrl;
        $.getJSON(url, function (data) {
            if (data.success) {
                var shop = data.shop;
                $('#shop-cover-pic').attr('src', shop.shopImg);
                $('#shop-update-time').html(new Date(shop.lastEditTime).Format("yyyy-MM-dd"));
                $('#shop-name').html(shop.shopName);
                $('#shop-desc').html(shop.shopDesc);
                $('#shop-addr').html(shop.shopAddr);
                $('#shop-phone').html(shop.phone);
                // 获取后台返回的该店铺的商品列表
                var productCategoryList = data.productCategoryList;
                var html = '';
                // 遍历商品列表，生成可以点击搜索相应商品类别下的商品 a 标签
                productCategoryList.map(function (item, index) {
                    html += '<a href="#" class="button" data-product-search-id='
                         + item.productCategoryId
                         + '>'
                         + item.productCategoryName
                         + '</a>';
                });
                // 将商品类别 a 标签绑定到相应的 html 组件中
                $('#shopdetail-button-div').html(html);
            }
        });
    };

    function addItems(pageSize, pageIndex) {
        // 拼接出查询 URL，赋空值默认就去掉这个条件的限制，有值就代表按这个条件去查询
        var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
                + pageSize + '&productCategoryId=' + productCategoryId
                + '&productName= ' + productName + '&shopId=' + shopId;
        // 设定加载符号，若还在后台读取数据则不能再此访问后台，避免多次加载
        loading = true;
        // 访问后台获取相应条件下的商品列表
        $.getJSON(url, function (data) {
            if (data.success) {
                // 获取当前条件下商品的总数
                var maxItems = data.count;
                var html = '';
                // 遍历商品列表，拼接出卡片集合
                data.productList.map(function(item, index) {
                    html += '' + '<div class="card" data-product-id='
                         + item.productId + '>'
                         + '<div class="card-header">' + item.productName
                         + '</div>' + '<div class="card-content">'
                         + '<div class="list-block media-list">' + '<ul>'
                         + '<li class="item-content">'
                         + '<div class="item-media">' + '<img src="'
                         + item.imgAddr + '" width="44">' + '</div>'
                         + '<div class="item-inner">'
                         + '<div class="item-subtitle">' + item.productDesc
                         + '</div>' + '</div>' + '</li>' + '</ul>'
                         + '</div>' + '</div>' + '<div class="card-footer">'
                         + '<p class="color-gray">'
                         + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                         + '更新</p>' + '<span>点击查看</span>' + '</div>'
                         + '</div>';
                });
                // 将卡片集合添加进目标 html 组件中
                $('.list-div').append(html);
                // 获取目前为止已显示的卡片总数，包含之前已经加载的
                var total = $('.list-div .card').length;
                // 若总数达到跟按照此条件查询条件列出来的总数一致，则停止后台的加载
                if(total >= maxItems) {
                    // 隐藏提示符
                    $('.infinite-scroll-preloader').hide();
                } else {
                    $('.infinite-scroll-preloader').show();
                }
                // 否则页码加一，继续加载出新的店铺
                pageNum += 1;
                // 加载结束，可以再次加载了
                loading = false;
                // 刷新页面，显示新加载的店铺
                $.refreshScroller();
            }
        })
    };

    // 下滑屏幕自动进行分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function () {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });

    // 选择心的商品类别之后，重置页码，清空原先的商品列表，按照新的类别去查询
    $('#shopdetail-button-div').on('click', '.button', function (e) {
        // 获取商品 ID
        productCategoryId = e.target.dataset.productSearchId;
        if(productCategoryId) {
            // 若之前已经选定了别的 category，则移除其选定效果，改选成新的
            if($(e.target).has('button-fill')) {
                $(e.target).removeClass('button-fill');
                productCategoryId = '';
            } else {
                $(e.target).addClass('button-fill').siblings().remove('button-fill');
            }
            $('.list-div').empty();
            pageNum = 1;
            addItems(pageSize, pageNum);
        }
    });

    // 点击商品的卡片进入该商品的详情页
    $('.list-div').on('click', '.card', function (e) {
        var productId = e.currentTarget.dataset.productId;
        window.location.href = '/o2o/frontend/productdetail?productId=' + productId;
    });

    // 需要查询的商品名字发生变化后，重置页码，清空原先的商品列表，按照新的名字查询
    $('#search').on('input', function (e) {
        productName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    // 点击打开右侧栏
    $('#me').click(function () {
        $.openPanel('#panel-right-demo');
    });

    $.init();

})