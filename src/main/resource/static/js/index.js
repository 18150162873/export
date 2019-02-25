$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();
    

});


var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function (ip,port) {
        $('#tb_departments').bootstrapTable({
            url:'/DB/findDBTable',         
            method: 'get',  
            pagination: true,
            showColumns: true,
            exportDataType: 'all',
            striped: true,
            search: true,
            multipleSearch: true,
            pageList: [15, 25, 50, 100, "All"],
            pageSize: 15,
            columns: [{
                checkbox: true
            }, {
                field: 'tableName',
                title: '表名'
            },{
            	file:"操作",
            	title:"操作",
            	formatter:function(){
            		"<span class='glyphicon glyphicon-edit' id ='edit'></span>"
            	var operate =
            		`<div class="btn-group">
						<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">dbInfo 
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#" id="table_info_id">查看表信息</a></li>
							<li><a href="#" id="select_sql_id">mybatis查询sql</a></li>
							<li><a href="#" id="inser_sql">mybatis插入sql</a></li>
		            		<li><a href="#" id="update_sql">mybatis修改sql</a></li>
		            		<li><a href="#" id="delete_sql">mybatis删除sql</a></li>
						</ul>
						</div>
						<div class="btn-group">
						<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">controllerCodeHelp 
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#" id="controllerAdd">新增</a></li>
							<li><a href="#" id="controllerUpd">修改</a></li>
							<li><a href="#" id="controllerFind">查询</a></li>
		            		<li><a href="#" id="controllerDel">删除</a></li>
            				<li><a href="#" id="controller">All</a></li>
						</ul>
						</div>
						<div class="btn-group">
						<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">serviceCodeHelp 
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#" id="serviceAdd">新增</a></li>
							<li><a href="#" id="serviceUpd">修改</a></li>
							<li><a href="#" id="serviceFind">查询</a></li>
		            		<li><a href="#" id="serviceDel">删除</a></li>
            				<li><a href="#" id="service">All</a></li>
						</ul>
						</div>
						<div class="btn-group">
						<button type="button" class="btn btn-primary dropdown-toggle" id="mybatisInterface">mybatisInterface 
						</button>
						</div>
						<div class="btn-group">
						<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">beanCode 
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#" id="beanCode">bean</a></li>
						</ul>
						</div>
						`
            		return operate;
            	},
            	events:{
            		"click #table_info_id":function(e,value,row,index){
            			$("#myModal").modal('show');
            			tableInfo(row.tableName);
            		},
            		"click #select_sql_id":function(e,value,row,index){
            			$("#codeModal").modal('show');
            			var sql = selectSql(row.tableName);
            			$("#code_span").html("");
            			$("#code_span").append(sql);
            		},
            		"click #inser_sql":function(e,value,row,index){
            			$("#codeModal").modal('show');
            			var sql = insertSql(row.tableName);
            			$("#code_span").html("");
            			$("#code_span").append(sql);
            		},
            		"click #update_sql":function(e,value,row,index){
            			$("#codeModal").modal('show');
            			var sql = updateSql(row.tableName);
            			$("#code_span").html("");
            			$("#code_span").append(sql);
            		},
            		"click #delete_sql":function(e,value,row,index){
            			$("#codeModal").modal('show');
            			var sql = delSql(row.tableName);
            			$("#code_span").html("");
            			$("#code_span").append(sql);
            		},
            		"click #controllerAdd":function(e,value,row,index){
            			$("#codeModal").modal('show');
            			var code = controllerAddCode(row.tableName);
            			$("#code_span").html("");
            			$("#code_span").append(code);
            		},
            		"click #controllerUpd":function(e,value,row,index){
            			$("#codeModal").modal('show');
            			var code = controllerUpd(row.tableName);
            			$("#code_span").html("");
            			$("#code_span").append(code);
            		},
            		"click #controllerFind":function(e,value,row,index){
            			$("#codeModal").modal('show');
            			var code = controllerFind(row.tableName);
            			$("#code_span").html("");
            			$("#code_span").append(code);
            		},
            		"click #controllerDel":function(e,value,row,index){
            			$("#codeModal").modal('show');
            			var code = controllerdel(row.tableName);
            			$("#code_span").html("");
            			$("#code_span").append(code);
            		},
            		"click #controller":function(e,value,row,index){
            			$("#codeModal").modal('show');
            			var code = controller(row.tableName);
            			$("#code_span").html("");
            			$("#code_span").append(code);
            		},
            		"click #serviceAdd":function(e,value,row,index){
            			$("#codeModal").modal('show');
            			var code = serviceAdd(row.tableName);
            			$("#code_span").html("");
            			$("#code_span").append(code);
            		},
            		"click #serviceUpd":function(e,value,row,index){
            			$("#codeModal").modal('show');
            			var code = serviceUpd(row.tableName);
            			$("#code_span").html("");
            			$("#code_span").append(code);
            		},
            		"click #serviceFind":function(e,value,row,index){
            			$("#codeModal").modal('show');
            			var code = serviceFind(row.tableName);
            			$("#code_span").html("");
            			$("#code_span").append(code);
            		},
            		"click #serviceDel":function(e,value,row,index){
            			$("#codeModal").modal('show');
            			var code = serviceDel(row.tableName);
            			$("#code_span").html("");
            			$("#code_span").append(code);
            		},
            		"click #service":function(e,value,row,index){
            			$("#codeModal").modal('show');
            			var code = service(row.tableName);
            			$("#code_span").html("");
            			$("#code_span").append(code);
            		},
            		"click #mybatisInterface":function(e,value,row,index){
            			$("#codeModal").modal('show');
            			var code = mybatisInterface(row.tableName);
            			$("#code_span").html("");
            			$("#code_span").append(code);
            		},
            		"click #beanCode":function(e,value,row,index){
            			$("#codeModal").modal('show');
            			var code = beanCode(row.tableName);
            			$("#code_span").html("");
            			$("#code_span").append(code);
            		}
            	}
            }]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            departmentname: $("#txt_search_departmentname").val(),
            statu: $("#txt_search_statu").val()
        };
        return temp;
    };
    return oTableInit;
};


var ButtonInit = function () {
    var oInit = new Object();
    var postdata = {};

    oInit.Init = function () {
        //初始化页面上面的按钮事件
    };

    return oInit;
};

var tableInfo = function(tableName){
	 $('#tableDetail').bootstrapTable('destroy');
	 $('#tableDetail').bootstrapTable({
         url:"/DB/tableInfo", 
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
         height: 500, 
         columns: [{
             checkbox: true
         }, {
             field: 'column_name',
             title: '列名'
         }, {
             field: 'column_type',
             title: '类型'
         }, {
             field: 'column_comment',
             title: '描述'
         }]
     });
}

var selectSql = function(tableName){
	var selectSql = "";
	$.ajax({
		url:"/codeHelp/createSelect?tableName="+tableName,
		async:false,
		type:"post",
		success:function(response){
			selectSql = response;
		}
	})
	return selectSql;
}

var insertSql = function(tableName){
	var insertSql = "";
	$.ajax({
		url:"/codeHelp/createInsert?tableName="+tableName,
		async:false,
		type:"post",
		success:function(response){
			insertSql = response;
		}
	})
	return insertSql;
}

var updateSql = function(tableName){
	var updateSql = "";
	$.ajax({
		url:"/codeHelp/updateSql?tableName="+tableName,
		async:false,
		type:"post",
		success:function(response){
			updateSql = response;
		}
	})
	return updateSql;
}

var delSql = function(tableName){
	var delSql = "";
	$.ajax({
		url:"/codeHelp/delSql?tableName="+tableName,
		async:false,
		type:"post",
		success:function(response){
			delSql = response;
		}
	})
	return delSql;
}

var controllerdel = function(tableName){
	var code = "";
	$.ajax({
		url:"/codeHelp/createControllerdel?tableName="+tableName,
		async:false,
		type:"post",
		success:function(response){
			code = response;
		}
	})
	return code;
}

var controllerUpd = function(tableName){
	var code = "";
	$.ajax({
		url:"/codeHelp/createControllerUpd?tableName="+tableName,
		async:false,
		type:"post",
		success:function(response){
			code = response;
		}
	})
	return code;
}

var controllerFind = function(tableName){
	var code = "";
	$.ajax({
		url:"/codeHelp/createControllerFind?tableName="+tableName,
		async:false,
		type:"post",
		success:function(response){
			code = response;
		}
	})
	return code;
}

var controllerAddCode = function(tableName){
	var code = "";
	$.ajax({
		url:"/codeHelp/createControllerCodeAdd?tableName="+tableName,
		async:false,
		type:"post",
		success:function(response){
			code = response;
		}
	})
	return code;
}

var controller = function(tableName){
	var code = "";
	$.ajax({
		url:"/codeHelp/controller?tableName="+tableName,
		async:false,
		type:"post",
		success:function(response){
			code = response;
		}
	})
	return code;
}

var serviceAdd = function(tableName){
	var code = "";
	$.ajax({
		url:"/codeHelp/serviceAdd?tableName="+tableName,
		async:false,
		type:"post",
		success:function(response){
			code = response;
		}
	})
	return code;
}

var serviceDel = function(tableName){
	var code = "";
	$.ajax({
		url:"/codeHelp/serviceDel?tableName="+tableName,
		async:false,
		type:"post",
		success:function(response){
			code = response;
		}
	})
	return code;
}

var serviceUpd = function(tableName){
	var code = "";
	$.ajax({
		url:"/codeHelp/serviceUpd?tableName="+tableName,
		async:false,
		type:"post",
		success:function(response){
			code = response;
		}
	})
	return code;
}

var serviceFind = function(tableName){
	var code = "";
	$.ajax({
		url:"/codeHelp/serviceFind?tableName="+tableName,
		async:false,
		type:"post",
		success:function(response){
			code = response;
		}
	})
	return code;
}

var service = function(tableName){
	var code = "";
	$.ajax({
		url:"/codeHelp/service?tableName="+tableName,
		async:false,
		type:"post",
		success:function(response){
			code = response;
		}
	})
	return code;
}
var mybatisInterface = function(tableName){
	var code = "";
	$.ajax({
		url:"/codeHelp/mybatisInterface?tableName="+tableName,
		async:false,
		type:"post",
		success:function(response){
			code = response;
		}
	})
	return code;
}
var beanCode = function(tableName){
	var code = "";
	$.ajax({
		url:"/codeHelp/createBean?tableName="+tableName,
		async:false,
		type:"post",
		success:function(response){
			code = response;
		}
	})
	return code;
}

function getParam(paramName) { 
    paramValue = "", isFound = !1; 
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) { 
        arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0; 
        while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramValue = arrSource[i].split("=")[1], isFound = !0), i++ 
    } 
    return paramValue == "" && (paramValue = null), paramValue 
}