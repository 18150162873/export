$(document).ready(function(){
	tableInit();
})

$(document).on("click","#addBasis",function(){
	$("#confirm_upd").attr("style","display:none;");
	$("#confirm_add").removeAttr("style");
	$("#myModal").modal("show");
})

$(document).on("click","#confirm_add",function(){
	var pageData = $("#addForm").serializeArray();
	var submitData = {};
	
	pageData.forEach(function(e){
		submitData[e.name] = e.value;
	});
	
	$.ajax({
		url:"/basis/addBasis",
		data:{data:JSON.stringify(submitData)},
		type:"post",
		async:false,
		success:function(response){
			if("200"==response.status){
				tableInit();
				$("#myModal").modal("hide");
			}else{
				alert("新增失败！");
			}
		},
		error:function(){
			alert("操作失败！")
		}
	});
})


var tableInit = function(tableName){
	 $('#table_db').bootstrapTable('destroy');
	 $('#table_db').bootstrapTable({
         url:"/basis/findBasis", 
         method: 'post', 
         queryParams:{"tableName":tableName},
         toolbar: '#toolbar',   
         striped: true,                      
         cache: false,                       
         pagination: true,                   
         sortable: false,                     
         sortOrder: "asc",                         
         pageNumber:1,                       
         pageSize: 10,                       
         pageList: [10, 25, 50, 100],        
         strictSearch: true,
         showColumns: true,                  
         minimumCountColumns: 2,             
         clickToSelect: true,
         onLoadSuccess:function(response){
        	 return response.data;
         },
         columns: [{
             checkbox: true
         }, {
             field: 'ip',
             title: 'ip'
         }, {
             field: 'port',
             title: '端口'
         }, {
             field: 'user',
             title: '用户'
         }, {
             field: 'password',
             title: '密码'
         }, {
             field: 'remark',
             title: '备注'
         }, {
             field: 'createrTime',
             title: '创建时间'
         },
         {
             field: '操作',
             title: '操作',
             formatter:function(value,row,index){
            	 return `
            	 		<a href="#">
				         	<span class="glyphicon glyphicon-edit" id="updBasis"></span>
				        </a>
            	 		<a href="#">
				          <span class="glyphicon glyphicon-minus" id="delBasis"></span>
				        </a>
		            	 <a href="#">
		            	 <span class="glyphicon glyphicon-th" id="DBList"></span>
		            	 </a>
				        `;
             },
             events:{
            	 "click #delBasis":function(e,value,row,index){
            		 delBasis(row);
         		},
         		"click #updBasis":function(e,value,row,index){
         			updBasis(row);
         		},
         		"click #DBList":function(e,value,row,index){
         			showDBList(row);
         		}
             }
         }]
     });
}


var delBasis = function(row){
	if(""==row.id){
		alert("id不能为空");
		return ;
	}
	
	var ids = [];
	ids.push(row.id);
	$.ajax({
		url:"/basis/delBasis",
		data:{data:JSON.stringify(ids)},
		type:"post",
		async:false,
		success:function(response){
			if("200"==response.status){
				tableInit();
			}else{
				alert(response.note);
			}
		},
		error:function(){
			alert("操作失败！");
		}
	})
}

var updBasis = function(row){
	if(""==row.id){
		alert("id不能为空");
		return ;
	}
	
	addDataToForm(row);
	$("#confirm_add").attr("style","display:none;");
	$("#confirm_upd").removeAttr("style");
	$("#myModal").modal("show");
	
}

var addDataToForm = function(row){
	$("#ip_input").val(row.ip);
	$("#port_input").val(row.port);
	$("#user_input").val(row.user);
	$("#password_input").val(row.password);
	$("#remark_input").val(row.remark);
	$("#defaultDb_input").val(row.defaultDb);
	$("#id_input").val(row.id);
}

$(document).on("click","#confirm_upd",function(){
	var pageData = $("#addForm").serializeArray();
	var submitData = {};
	
	pageData.forEach(function(e){
		submitData[e.name] = e.value;
	});
	
	updSubmit(submitData);
})

var updSubmit = function(row){
	$.ajax({
		url:"/basis/updBasis",
		data:{data:JSON.stringify(row)},
		type:"post",
		async:false,
		success:function(response){
			if("200"==response.status){
				$("#myModal").modal("hide")
				tableInit();
			}else{
				alert(response.note);
			}
		},
		error:function(){
			alert("操作失败！");
		}
	});
}


var showDBList=function(row){
	var DBList = [];
	
	$.ajax({
		url:"/DB/findAllDB",
		data:{data:JSON.stringify(row)},
		type:"post",
		async:false,
		success:function(response){
			DBList = response;
		},
		erroe:function(){
			alert("error");
		}
	})
	
	var showDB = "";
	
	DBList.forEach(function(e){
		var password = row.password.replace("#","%23")
		showDB = showDB+`<div><a class="btn btn-primary btn-lg btn-block" href="/page/index?ip=${row.ip}&port=${row.port}&db=${e}&password=${password}&user=${row.user}" role="button">${e}</a></div>`;
	});
	
	$("#showDB").html(showDB);
	$("#dbModal").modal("show");
}