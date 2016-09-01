<html>
<head>
<!-- Force latest IE rendering engine or ChromeFrame if installed -->
<!--[if IE]>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<![endif]-->
<meta charset="utf-8">
<title>jQuery File Upload Demo</title>
<meta name="description" content="File Upload widget with multiple file selection, drag&amp;drop support, progress bars, validation and preview images, audio and video for jQuery. Supports cross-domain, chunked and resumable file uploads and client-side image resizing. Works with any server-side platform (PHP, Python, Ruby on Rails, Java, Node.js, Go etc.) that supports standard HTML form file uploads.">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap styles -->
<link rel="stylesheet" href="<%= request.getContextPath()%>/fileupload/css/bootstrap.min.css">
<!-- blueimp Gallery styles -->
<link rel="stylesheet" href="<%= request.getContextPath()%>/fileupload/css/blueimp-gallery.min.css">
<!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
<link rel="stylesheet" href="<%= request.getContextPath()%>/fileupload/css/fileupload.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/fileupload/css/fileupload-ui.css">
<!-- CSS adjustments for browsers with JavaScript disabled -->
<noscript><link rel="stylesheet" href="<%= request.getContextPath()%>/fileupload/css/fileupload-noscript.css"></noscript>
<noscript><link rel="stylesheet" href="<%= request.getContextPath()%>/fileupload/css/fileupload-ui-noscript.css"></noscript>
</head>
<body>
<div class="container">
    <!-- The file upload form used as target for the file upload widget -->
    <form id="fileupload" action="<%= request.getContextPath()%>/FileUploadServlet" method="POST" enctype="multipart/form-data">
        <!-- Redirect browsers with JavaScript disabled to the origin page -->
        <noscript><input type="hidden" name="redirect" value="<%= request.getContextPath()%>/FileUploadServlet"></noscript>
        <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
        <div class="row fileupload-buttonbar">
            <div class="col-lg-7">
                <!-- The fileinput-button span is used to style the file input field as button -->
                <span class="btn btn-success fileinput-button">
                    <i class="glyphicon glyphicon-plus"></i>
                    <span>添加文件</span>
                    <input type="file" name="files[]" multiple>
                </span>
                <button type="submit" class="btn btn-primary start">
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>开始上传</span>
                </button>
                <button type="reset" class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>取消上传</span>
                </button>
                <button type="button" class="btn btn-danger delete">
                    <i class="glyphicon glyphicon-trash"></i>
                    <span>删除</span>
                </button>
                <input type="checkbox" class="toggle">
                <!-- The loading indicator is shown during file processing -->
                <span class="fileupload-loading"></span>
            </div>
            <!-- The global progress information -->
            <div class="col-lg-5 fileupload-progress fade">
                <!-- The global progress bar -->
                <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                    <div class="progress-bar progress-bar-success" style="width:0%;"></div>
                </div>
                <!-- The extended global progress information -->
                <div class="progress-extended">&nbsp;</div>
            </div>
        </div>
        <!-- The table listing the files available for upload/download -->
        <table role="presentation" class="table table-striped"><tbody class="files"></tbody></table>
    </form>
</div>
<!-- The blueimp Gallery widget -->
<div id="blueimp-gallery" class="blueimp-gallery blueimp-gallery-controls" data-filter=":even">
    <div class="slides"></div>
    <h3 class="title"></h3>
    <a class="prev">‹</a>
    <a class="next">›</a>
    <a class="close">×</a>
    <a class="play-pause"></a>
    <ol class="indicator"></ol>
</div>
<!-- The template to display files available for upload -->
<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td>
            <span class="preview"></span>
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            {% if (file.error) { %}
                <div><span class="label label-danger">错误</span> {%=file.error%}</div>
            {% } %}
        </td>
        <td>
            <p class="size">{%=o.formatFileSize(file.size)%}</p>
            {% if (!o.files.error) { %}
                <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
            {% } %}
        </td>
        <td>
            {% if (!o.files.error && !i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start">
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>开始</span>
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>取消</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        <td>
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img src="{%=file.thumbnailUrl%}"></a>
                {% } %}
            </span>
        </td>
        <td>
            <p class="name">
                {% if (file.url) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
                {% } else { %}
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div><span class="label label-danger">错误</span> {%=file.error%}</div>
            {% } %}
        </td>
        <td>
            <span class="size">{%=o.formatFileSize(file.size)%}</span>
        </td>
        <td>
            {% if (file.deleteUrl) { %}
                <button class="btn btn-danger delete" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                    <i class="glyphicon glyphicon-trash"></i>
                    <span>删除</span>
                </button>
                <input type="checkbox" name="delete" value="1" class="toggle">
            {% } else { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>取消</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>


<!--<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>-->
<script src="<%= request.getContextPath()%>/fileupload/js/jquery-1.7.1.min.js"></script>
<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
<script src="<%= request.getContextPath()%>/fileupload/js/vendor/jquery.ui.widget.js"></script>
<!-- The Templates plugin is included to render the upload/download listings -->
<script src="<%= request.getContextPath()%>/fileupload/js/tmpl.min.js"></script>
<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
<script src="<%= request.getContextPath()%>/fileupload/js/load-image.min.js"></script>
<!-- The Canvas to Blob plugin is included for image resizing functionality -->
<script src="<%= request.getContextPath()%>/fileupload/js/canvas-to-blob.min.js"></script>
<!-- blueimp Gallery script -->
<script src="<%= request.getContextPath()%>/fileupload/js/jquery.blueimp-gallery.min.js"></script>
<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
<script src="<%= request.getContextPath()%>/fileupload/js/jquery.iframe-transport.js"></script>
<!-- The basic File Upload plugin -->
<script src="<%= request.getContextPath()%>/fileupload/js/fileupload.js"></script>
<!-- The File Upload processing plugin -->
<script src="<%= request.getContextPath()%>/fileupload/js/fileupload-process.js"></script>
<!-- The File Upload image preview & resize plugin -->
<script src="<%= request.getContextPath()%>/fileupload/js/fileupload-image.js"></script>
<!-- The File Upload validation plugin -->
<script src="<%= request.getContextPath()%>/fileupload/js/fileupload-validate.js"></script>
<!-- The File Upload user interface plugin -->
<script src="<%= request.getContextPath()%>/fileupload/js/fileupload-ui.js"></script>
<!-- The main application script -->
<script type="text/javascript">
	/*
		注： 
			1. 事件触发顺序：fileupload.js[_onAdd]
							->fileupload-ui.js[add]
							->fileupload-process.js[process]
							->fileupload-image.js[loadImageMetaData]
							->fileupload-image.js[loadImage]
							->fileupload-image.js[resizeImage]
							->fileupload-image.js[saveImage]
							->fileupload-image.js[saveImageMetaData]
							->fileupload-image.js[resizeImage]
							->fileupload-image.js[setImage]
							->fileupload-validate.js[validate]
							->fileupload-process.js[process]
							->fileupload-ui.js[_renderUpload]----注： 回到ui.js add 方法
							->...这里ui.js里的方法都是在之前add方法里面调用的
							->fileupload-ui.js[_transition]
							
	*/
	var uploader = $('#fileupload');
	$(function () {
		'use strict';
	
		// Initialize the jQuery File Upload widget:
		uploader.fileupload({
			
			// Uncomment the following to send cross-domain cookies:
			//xhrFields: {withCredentials: true},
			'url': '<%= request.getContextPath()%>/FileUploadServlet',
			'method': 'POST',
			'autoUpload': false,
			'limitMultiFileUploads': 2, // 限定最多两个文件
			'limitConcurrentUploads': 1, // 限定同时上传N个文件
			'maxFileSize': 3 * 1024 * 1024,
			'maxNumberOfFiles': 40,
			'acceptFileTypes': /(\.|\/)(gif|jpe?g|png|JPG)$/i,
        	'previewMaxWidth': 50,
        	'previewMaxHeight': 50
		});
		
		uploader.bind('fileuploadfailed', function (e, data) {
            e.p(data);
		});
		uploader.bind('fileuploadadded', function (e, data) {
			alert('my-add');
			//if(!data.files.valid) {
				//uploader.find('.files .cancel').click();
			//}
		});
		uploader.bind('fileuploadchange', function (e, data) {
			alert('my-change');
			//uploader.find('.files').empty();
		});
		
		uploader.bind('fileuploaddone', function(e, data) {
			alert('上传成功');
		});
        uploader.bind('fileuploaddestroyed', function(e, data) {
//            console.log(data);
            alert('删除成功');
        });
	});
</script>
<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
<!--[if (gte IE 8)&(lt IE 10)]>
<script src="<%= request.getContextPath()%>/fileupload/js/cors/jquery.xdr-transport.js"></script>
<![endif]-->
</body> 
</html>