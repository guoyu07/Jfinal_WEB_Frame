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
	var userName = $( "#userName" ).val();
	var userId = $( "#userId" ).val();
	var email = $( "#email" ).val();
	var idCard = $( "#idCard" ).val();
	var sortNo = $( "#sortNo" ).val();
	var addr = $( "#addr" ).val();
	var mobile = $( "#mobile" ).val();
	var birthday = $( "#birthday" ).val();
	var roleId = $( "#roleId" ).val();
	var sex = $('input:radio[name="sex"]:checked').val();
	var isUsed = $('input:radio[name="isUsed"]:checked').val();
	if( userName == '' ) {
		window.parent.tips( "用户名不能为空！" );
		return ;
	}
	if( roleId == '' ) {
		window.parent.tips( "角色信息，不能为空！" );
		return ;
	}
	
	$.post( "${rootPath}/sys/user/edit", {
			"userName":userName, 
			"userId":userId, 
			"email":email, 
			"idCard":idCard,
			"sortNo":sortNo,
			"addr":addr,
			"mobile":mobile,
			"birthday":birthday,
			"roleId":roleId,
			"sex":sex,
			"isUsed":isUsed
		}, function ( data ) {
			if ( data.state == 1 ) {
				location.href="${rootPath}/sys/user/listview";
			} else {
				window.parent.tips( data.msg );
			}
	}, "json" );
}

</script>
</head>
<body>
<!-- content start -->
<div class="admin-content">
    <div class="am-cf am-padding">
      	<div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">个人资料</strong> / <small>Personal information</small></div>
    </div>
    <hr/>
    <div class="am-g">
		<form class="am-form am-form-horizontal">
		 	<div class="am-u-sm-12 am-u-md-6 am-u-md-push-6">
	        
		        <div class="am-form-group">
		           	<label for="mobile" class="am-u-sm-3 am-form-label">手机号码 </label>
		           	<div class="am-u-sm-9">
		           	 	<input type="hidden" id="userId" value="${infoUser.userId}">
		            	<input type="text" id="mobile" value="${infoUser.mobile}" placeholder="输入你的电话号码 ">
		           </div>
		        </div>        
				<div class="am-form-group">
				 	<label for="birthday" class="am-u-sm-3 am-form-label">生日</label>
					<div class="am-u-sm-9">
					  	<input type="text" id="birthday" value="${infoUser.birthday}" class="am-form-field" placeholder="请选择" data-am-datepicker="{theme: 'success'}" readonly/>
					</div>
				 </div>
				 
				 <div class="am-form-group">
				 	<label for="user-role" class="am-u-sm-3 am-form-label">角色</label>
				 	<div class="am-u-sm-9" id="roleSelect">
					 	<select data-am-selected='{btnSize: 'sm'}' name='roleId' id='roleId'>
							 <#list roles as role>
							 	<option value="${role.roleId}" 
							 		<#if infoUser.roleId=role.roleId > selected = "selected" </#if>>${role.roleName}
							 	</option>
							 </#list>
					 	</select>
					</div>
				 </div>
				 
				 <div class="am-form-group">
					<label for="user-role" class="am-u-sm-3 am-form-label">性别</label>
					<div class="am-u-sm-9">			      
			             <label class="am-radio-inline">
			                <input type="radio" value="1" <#if infoUser.sex=1 > checked="checked" </#if> name="sex"> 男
			             </label>
			             <label class="am-radio-inline">
			                <input type="radio" value="0" <#if infoUser.sex=0 > checked="checked" </#if> name="sex"> 女
			             </label>
					</div>
				</div>
				
				<div class="am-form-group">
					<label for="user-role" class="am-u-sm-3 am-form-label">状态</label>
					<div class="am-u-sm-9">			      
			            <label class="am-radio-inline">
			                <input type="radio" value="1" <#if infoUser.isUsed=1 > checked="checked" </#if> name="isUsed" > 正常
			            </label>
			            <label class="am-radio-inline">
			                <input type="radio" value="0" <#if infoUser.isUsed=0 > checked="checked" </#if> name="isUsed"> 无效
			            </label>
					</div>
				</div>
			 
	      	</div>
	      
			<div class="am-u-sm-12 am-u-md-6 am-u-md-pull-6">
	        
		        <div class="am-form-group">
		            <label for="userName" class="am-u-sm-3 am-form-label">姓名</label>
		            <div class="am-u-sm-9">
		              	<input type="text" id="userName" value="${infoUser.userName}" placeholder="输入你的名字，让我们记住你。">
		            </div>
		        </div>

		        <div class="am-form-group">
		            <label for="email" class="am-u-sm-3 am-form-label">电子邮件</label>
		            <div class="am-u-sm-9">
		              	<input type="email" id="email" value="${infoUser.email}" placeholder="邮箱你懂得...">
		            </div>
		        </div>

		        <div class="am-form-group">
		            <label for="idCard" class="am-u-sm-3 am-form-label">身份证</label>
		            <div class="am-u-sm-9">
		              	<input type="text" id="idCard" value="${infoUser.idCard}" placeholder="输入你的身份证号码">
		            </div>
		        </div>
				
		        <div class="am-form-group">
		            <label for="addr" class="am-u-sm-3 am-form-label">地址</label>
		            <div class="am-u-sm-9">
		              	<input type="text" id="addr" value="${infoUser.addr}" placeholder="输入你的地址">
		            </div>
		        </div>

		          
		        <div class="am-form-group">
		            <label for="sortNo" class="am-u-sm-3 am-form-label">排序号</label>
		            <div class="am-u-sm-9">
		              	<input type="text" id="sortNo" value="${infoUser.sortNo}" placeholder="排序，越大越靠前">
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
