<!doctype html>
<html class="no-js">
<head>
	<link rel="stylesheet" href="${rootPath}/assets/css/amazeui.min.css"/>
	<link rel="stylesheet" href="${rootPath}/assets/css/admin.css">
<script type="text/javascript" src="${rootPath}/ext/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${rootPath}/ext/js/jquery.md5.js"></script>
<script type="text/javascript">
//表单提交
function save(){
	var roleId = $( "#roleId" ).val();
	var roleCode = $( "#roleCode" ).val();
	var roleName = $( "#roleName" ).val();
	var isUsed = $('input:radio[name="isUsed"]:checked').val();
	if( roleName == '' ) {
		window.parent.tips( "角色名称不能为空！" );
		return ;
	}
	if( roleCode == '' ) {
		window.parent.tips( "角色编号，不能为空！" );
		return ;
	}
	$.post( "${rootPath}/sys/role/edit", {
			"roleCode":roleCode, 
			"roleId":roleId,
			"roleName":roleName,
			"isUsed":isUsed
		}, function ( data ) {
			if ( data.state == 1 ) {
				window.parent.location.href="${rootPath}/sys/role/listview";
			} else {
				window.top.tips( data.msg );
			}
	}, "json" );
}

</script>
</head>
<body>
<!-- content start -->
<div class="admin-content">
    <div class="am-cf am-padding">
      	<div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">修改角色</strong> / <small>Personal information</small></div>
    </div>
    <hr/>
    <div class="am-g">
		<form class="am-form am-form-horizontal">
			<input type="hidden" value="${infoRole.roleId}" id="roleId">
			<div class="am-u-sm-12">	        
		        <div class="am-form-group">
		            <label for="parentName" class="am-u-sm-3 am-form-label">父级角色</label>
		            <div class="am-u-sm-9">
		              	<input type="text" id="parentName" value="${infoRole.parentName!'已是最高角色'}" disabled="disabled" >
		            </div>
		        </div>

		        <div class="am-form-group">
		            <label for="roleCode" class="am-u-sm-3 am-form-label">角色编号</label>
		            <div class="am-u-sm-9">
		              	<input type="text" id="roleCode" value="${infoRole.roleCode}" placeholder="填写角色对应的角色编号">
		            </div>
		        </div>

		        <div class="am-form-group">
		            <label for="roleName" class="am-u-sm-3 am-form-label">角色名称</label>
		            <div class="am-u-sm-9">
		              	<input type="text" id="roleName" value="${infoRole.roleName}" placeholder="输入角色名称">
		            </div>
		        </div>
				
		        <div class="am-form-group">
		            <label for="isUsed1" class="am-u-sm-3 am-form-label">是否有效</label>
		            <div class="am-u-sm-9">
		            	 <label class="am-radio-inline">
			                <input type="radio" value="1" <#if infoRole.isUsed=1 > checked="checked" </#if> name="isUsed" > 正常
			            </label>
			            <label class="am-radio-inline">
			                <input type="radio" value="0" <#if infoRole.isUsed=0 > checked="checked" </#if> name="isUsed"> 无效
			            </label>
		            </div>
		        </div>

		         <div class="am-form-group">
		            <div class="am-u-sm-3 am-u-sm-push-3">
		              	<button type="button" onclick="history.go(-1);" class="am-btn am-btn-primary">返回</button>
		            </div>
		            
		            <div class="am-u-sm-6">
		              	<button type="button" onclick="save()" class="am-btn am-btn-primary">修改信息</button>
		            </div>
		        </div>
	      	</div>
	     </form>
    </div>
</div>
<!-- content end -->

<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="${rootPath}/assets/js/polyfill/rem.min.js"></script>
<script src="${rootPath}/assets/js/polyfill/respond.min.js"></script>
<script src="${rootPath}/assets/js/amazeui.legacy.js"></script>
<![endif]-->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="${rootPath}/assets/js/jquery.min.js"></script>
<script src="${rootPath}/assets/js/amazeui.min.js"></script>
<!--<![endif]-->
<script src="${rootPath}/assets/js/app.js"></script>
</body>
</html>
