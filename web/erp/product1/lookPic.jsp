<%
    String productId = request.getParameter("productId");
    String staffId = request.getParameter("staffId");
    String root = request.getContextPath();
    String baseRoot = "http://"+request.getLocalAddr()+":"+request.getLocalPort();
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!--[if IE]>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <![endif]-->
    <meta charset="utf-8">
    <title>blueimp Gallery</title>
    <meta name="description" content="blueimp Gallery is a touch-enabled, responsive and customizable image and video gallery, carousel and lightbox, optimized for both mobile and desktop web browsers. It features swipe, mouse and keyboard navigation, transition effects, slideshow functionality, fullscreen support and on-demand content loading and can be extended to display additional content types.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/fileupload/css/blueimp-gallery.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/fileupload/css/blueimp-gallery-indicator.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/fileupload/css/demo.css">

    <script src="<%= request.getContextPath()%>/fileupload/js/blueimp-helper.js"></script>
    <script src="<%= request.getContextPath()%>/fileupload/js/blueimp-gallery.min.js"></script>
    <script src="<%= request.getContextPath()%>/fileupload/js/blueimp-gallery-fullscreen.js"></script>
    <script src="<%= request.getContextPath()%>/fileupload/js/blueimp-gallery-indicator.js"></script>
    <script src="<%= request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
    <script src="<%= request.getContextPath()%>/fileupload/js/jquery.blueimp-gallery.min.js"></script>

    <script type="text/javascript">
        var baseRoot = '<%= baseRoot%>';
        var root = '<%= root%>';
        var staffId = '<%= staffId%>';
        var productId = '<%= productId%>';

        $(function () {
            'use strict';
            // Load demo images from flickr:
            $.ajax({
                url: root + "/FileDownloadServlet",
                type: 'post',
                cache: false,
                dataType: 'json',
                traditional: true,
                data: {
                    param: 'query',
                    productId: productId
                },
                success: function (result) {
                    var carouselLinks = [], linksContainer = $('#links');
                    // Add the demo images as links with thumbnails to the page:
                    $.each(result, function (index, photo) {
                        var a = $('<a/>').append($('<img>').prop('src', baseRoot + photo.thumbnailurl))
                                .prop('href', baseRoot + photo.url)
                                .prop('title', photo.name)
                                .attr('data-gallery', '');
                        // 每行最多为9张照片
                        if ((index+1)%5==0) {
//                            console.log(index);
                            a.append('<br>');
                        }
                        a.appendTo(linksContainer);
                        carouselLinks.push({
                            href: baseRoot + photo.url,
                            title: photo.name
                        });
                    });
                    // Initialize the Gallery as image carousel:
                    blueimp.Gallery(carouselLinks, {
                        container: '#blueimp-gallery',
                        carousel: true
                    });
                },
                error: function () {
                    alert("网络错误！")
                }
            });

        });
    </script>
</head>
<body>
<!-- The container for the list of example images -->
<div id="links" class="links"></div>
<!-- The Gallery as lightbox dialog, should be a child element of the document body -->
<div id="blueimp-gallery" class="blueimp-gallery">
    <div class="slides"></div>
    <h3 class="title"></h3>
    <a class="prev">‹</a>
    <a class="next">›</a>
    <a class="close">×</a>
    <a class="play-pause"></a>
    <ol class="indicator"></ol>
</div>
</body>
</html>