/*
 * 注册
 */
function regist(){
	
	var hint = $("#regist_hint");
	var user_id=$("#user_id").val();
	var user_name=$("#user_name").val();
	var user_psw=$("#user_psw").val();
	var confirm_psw=$("#confirm_psw").val();
		
	if ($.trim(user_id) == "" || $.trim(user_name) == "" ||
			$.trim(user_psw) == "" || $.trim(confirm_psw) == "") {
		
		hint.html("信息未填写完整");
        return ;
    }
	
	if(user_psw!=confirm_psw){
		hint.html("两次密码输入不一致");
        return ;
	}
	
	if(user_name.length>6){
		hint.html("姓名为1-6个字符");
        return ;
	}
	
	$.post("./Regist",
		{
		'user_id': user_id,
		'user_name': user_name,
		'user_psw': user_psw
		},
		function(data){
			if($.trim(data)=="success"){
				window.location.replace("./newbie.jsp?sectionN=0");
			}else{
				hint.html(data);					
			}
		});

	return false;
}


/*
* 
* 提交代码
*/
function submit(prb_id,undetermined_id,type,prb_idx){
	
	var code = $("#code").val();
	var language=$("#language").val();
			
	if ($.trim(language) == "") {
		alert("代码不能为空");
        return ;
    }
	
	$.post("./Submit",
		{
		'code': code,
		'language': language,
		'prb_id': prb_id,
		'undetermined_id': undetermined_id,
		'type' : type,
		'prb_idx' : prb_idx
		},function(data){
			if($.trim(data)!=""){
				alert(data);
			}
		});
}


/*
 *	申请赛季
 */
function apply(obj,sea_id){
	var msg="确定要报名["+$(obj).children('h4').text()+"]吗？";
	if(confirm(msg)){
		$.post("./Season?req=apply",
			{
			'sea_id':sea_id
			});
		obj.parentNode.removeChild(obj);
	}  
}

/*
 *  审批赛季申请
 */
function approveSeason(sea_id,user_id,operator,obj){

	$.post("../Manager?req=approveSeason",
			{
			'sea_id':sea_id,
			'user_id':user_id,
			'operator':operator
			},function(data){
				if($.trim(data)=="success"){
					var div = obj.parentNode;
					
					if(operator==1){
						div.innerHTML = "已通过";
					}else{
						div.innerHTML = "已拒绝";
					}	
					
				}else{
					alert(data);
				}
			});
}


/*
 *   结算rating
 */
function calRating(cot_id){
	var msg="确定将这场比赛计入rating么？";
	if(confirm(msg)){
		$.post("../Season?req=calRating",
			{
			'cot_id':cot_id
			},
			function(data){
				alert(data);
			});
	}	
}


/*
 *   链接题目
 */
function addProblem(){
		
	if($("#oj_id").val()==""){
		alert("题号不能为空");
		return ;
	}

	$.post("../Manager?req=addProblem",
		{
		'oj_name':$("#oj_name").val(),
		'oj_id':$("#oj_id").val()
		},
		function(data){
			var json_str = $.trim(data);
			var prb = new Function("return" + json_str)();
			
			if(prb.errno=='1'){
				alert("此题已经存在题库"); return ;
			}else if(prb.errno=='2'){
				alert("JSON格式错误，请查看错误日志"); return ;
			}else if(prb.errno=='3'){
				alert("抓取失败，请查看错误日志"); return ;
			}
			
			$("#title").val(prb.title);
			$("#problemDetail").val(prb.problemDetail);
			$("#inputDetail").val(prb.inputDetail);
			$("#outputDetail").val(prb.outputDetail);
			$("#sampleInput").val(prb.sampleInput);
			$("#sampleOutput").val(prb.sampleOutput);
			$("#hintDetail").val(prb.hintDetail);			
			$("#timeLimit").val(prb.timeLimit);
			$("#memoryLimit").val(prb.memoryLimit);
			$("#submit_params").val(prb.submit_params);
			
		});
}


/*
 *   保存题目
 */
function saveProblem(){
				
	$.post("../Manager?req=saveProblem",
		{
		'oj_name':$("#oj_name").val(),
		'oj_id':$("#oj_id").val(),
		'title':$("#title").val(),
		'problemDetail':$("#problemDetail").val(),
		'inputDetail':$("#inputDetail").val(),
		'outputDetail':$("#outputDetail").val(),
		'sampleInput':$("#sampleInput").val(),
		'sampleOutput':$("#sampleOutput").val(),
		'hintDetail':$("#hintDetail").val(),
		'timeLimit':$("#timeLimit").val(),
		'memoryLimit':$("#memoryLimit").val(),
		'submit_params':$("#submit_params").val()
		},
		function(data){
			alert(data);
		});

}

/*
 *   更新题目
 */
function updProblem(){
				
	$.post("../Manager?req=updProblem",
		{
		'prb_id':$("#prb_id").val(),
		'title':$("#title").val(),
		'problemDetail':$("#problemDetail").val(),
		'inputDetail':$("#inputDetail").val(),
		'outputDetail':$("#outputDetail").val(),
		'sampleInput':$("#sampleInput").val(),
		'sampleOutput':$("#sampleOutput").val(),
		'hintDetail':$("#hintDetail").val(),
		},
		function(data){
			alert(data);
		});

}


/*
 *   选中任务模版
 */
function chooseTaskTemplate(task_tpl_id,obj){
	$("#task_tpl_id").val(task_tpl_id);
	
	var tag_p = obj.lastChild;
	var str = tag_p.innerHTML.match("{.*}");
	var arry = str[0].substr(1,str[0].length-2).split(",");
	
	var HTML = "";
	
	for(var i=0;i<arry.length;i++){
		var str = '<li class="Left"><label class="checkbox-inline"><input type="checkbox">';
		str += arry[i].replace(" ","") + '</label></li>';
		HTML += str;
	}
	
	$("#check_box_set").html(HTML);	
	$("#chooseTplModal").modal('hide');	
	//$(window.parent.document).find("#manage_page").height($("body").height());
}


/*
 *   查询任务模版
 */
function findTaskTemplate(){
	
	var tpl_name = $("#tpl_name_input").val();
	
	$.post("../Manager?req=findTaskTpl",
			{
			'tpl_name':tpl_name
			},
			function(data){
				$("#tpl_list").html(data);
			});

}


/*
 *   模糊查询队员通过名字，并将生成的HTML插入到列表中
 */
function findUser(){
	
	var user_name = $("#user_name_input").val();
	
	$.post("../Manager?req=findUser",
			{
			'user_name':user_name
			},
			function(data){
				$("#user_list").html(data);
			});

}

/*
 *   查询队员通过user_id
 */
function findUserById(){
	var user_id = $("#user_id").val();
	
	$.post("../Manager?req=findUserById",
			{
			'user_id':user_id
			},
			function(data){
				var json_str = $.trim(data);
				var user = new Function("return"+json_str)();
	
				if(user.errno=="0"){				
					$("#user_name").val(user.name);
					$("#user_type").val(user.type);		
					$("#user_psw").val("");							
				}else if(user.errno=="1"){
					alert("不存在此用户");
				}
			});
}

/*
 *   修改用户信息
 */
function modifyUser(){
	var user_id = $("#user_id").val();
	var user_name = $("#user_name").val();
	var user_type = $("#user_type").val();		
	var user_psw = $("#user_psw").val();
	
	$.post("../Manager?req=modifyUser",
			{
			 'user_id' : user_id,
			 'user_name' : user_name,
			 'user_type' : user_type,	
			 'user_psw' : user_psw
			},
			function(data){
				alert(data);
			});
}

/*
 *   添加需要完成任务的队员
 */
function addUser(){
	
	//var user_name = $("#ready_user_list").val();
	
	var check_box_set = $("#user_list").find("input");
	
	var HTML = $("#choosed_user_list").html();
	
	for(var i=0;i<check_box_set.length;i++){
		var box = check_box_set[i];
		if(box.checked){	
			HTML += '<li class="Left">';
			HTML += '<h5>' + box.value; 
			HTML += '<button type="button" class="btn btn-danger btn-xs" aria-label="Left Align" onclick="removeUser(this)">';
			HTML += '<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>';
			HTML += '</button></h5></li>';	
		}
	}
	
	$("#choosed_user_list").html(HTML);	
	$("#chooseUserModal").modal('hide');
	//$(window.parent.document).find("#manage_page").height($("body").height());
}


/*
 *   移除已经被选择的队员
 */
function removeUser(own){
	var li_ojb = own.parentNode.parentNode;
	
	li_ojb.parentNode.removeChild(li_ojb);
	
	//$(window.parent.document).find("#manage_page").height($("body").height());
}


/*
 *   布置任务
 */
function assignTask(){
	
	var task_name = $("#task_name").val();
	var task_description = $("#task_description").val();
	var start_time = $("#start_time").val();
	var task_tpl_id = $("#task_tpl_id").val();
	var condition_cnt = $("#condition_cnt").val();
	var user_list = "";
	var condition_mask = 0;
	
	var arry = $("#choosed_user_list").find("h5");
	var hash = {};
	
	for(var i=0;i<arry.length;i++){
		var tmp = arry[i].innerText.match("\\(.*\\)")[0];
		var user_id = tmp.replace("(","").replace(")","");
		
		if(!hash[user_id]){
			if(i!=0) user_list += ";";
			user_list += user_id;
			hash[user_id] = true;
		}
	}
	
	var boxs = $("#check_box_set input");
		
	for(var i=0;i<boxs.length;i++){
		if(boxs[i].checked){
			condition_mask |= (1<<i);
		}
	}
	
	condition_mask |= (condition_cnt << 26);
	
	$.post("../Manager?req=assignTask",
			{
			 'task_name' : task_name,
			 'task_description' : task_description,
			 'start_time' : start_time,
			 'task_tpl_id' : task_tpl_id,
			 'user_list' : user_list,
			 'condition_mask' : condition_mask
			},
			function(data){
				alert(data);
			});
	
}

/*
 *  修改任务
 */
function editTask(){
	
	if(!confirm("如果修改了模版会重置所有人这个任务的完成情况，确认继续？")){ return; }
	
	var task_name = $("#task_name").val();
	var task_id = $("#task_id").val();
	var task_description = $("#task_description").val();
	var start_time = $("#start_time").val();
	var task_tpl_id = $("#task_tpl_id").val();
	var condition_cnt = $("#condition_cnt").val();
	var user_list = "";
	var condition_mask = 0;
	
	var arry = $("#choosed_user_list").find("h5");
	var hash = {};
	
	for(var i=0;i<arry.length;i++){
		var tmp = arry[i].innerText.match("\\(.*\\)")[0];
		var user_id = tmp.replace("(","").replace(")","");
		
		if(!hash[user_id]){
			if(i!=0) user_list += ";";
			user_list += user_id;
			hash[user_id] = true;
		}
	}
	
	var boxs = $("#check_box_set input");
		
	for(var i=0;i<boxs.length;i++){
		if(boxs[i].checked){
			condition_mask |= (1<<i);
		}
	}
	
	condition_mask |= (condition_cnt << 26);
	
	$.post("../Manager?req=editTask",
			{
			 'task_name' : task_name,
			 'task_id' : task_id,
			 'task_description' : task_description,
			 'start_time' : start_time,
			 'task_tpl_id' : task_tpl_id,
			 'user_list' : user_list,
			 'condition_mask' : condition_mask
			},
			function(data){
				alert(data);
			});
	
}



/*
 *   向页面添加题目
 */
function add_prb_to_page(){
	var HTML = $("#problem_set").html();
	var tr_html = $("#first_problem").html(); 
	
	tr_html += '<td><button type="button" class="btn btn-danger bnt-sm" onclick="rm_prb_from_page(this)">删除</button></td>'
	
	HTML += tr_html;
	
	$("#problem_set").html(HTML);
	$(window.parent.document).find("#manage_page").height($("body").height());
}


/*
 *   删除向页面添加题目
 */
function rm_prb_from_page(own){
	
	var tr = own.parentNode.parentNode;
	
	tr.parentNode.removeChild(tr);
	$(window.parent.document).find("#manage_page").height($("body").height());
}


/*
 * 	创建任务模板
 */
function create_tpl(){
	var task_tpl_name = $("#task_tpl_name").val();
	
	var oj_set = $("#problem_set").find("select");
	var oj_set_param = "";
	
	for(var i=0;i<oj_set.length;i++){
		if(i!=0) oj_set_param += ";";
		oj_set_param += oj_set[i].value;
	}
	
	var prb_set = $("#problem_set").find("input");
	var prb_set_param = "";
	
	for(var i=0;i<prb_set.length;i++){
		if(i!=0) prb_set_param += ";";
		prb_set_param += prb_set[i].value;
	}
	
	$.post("../Manager?req=createTaskTpl",
		{
		 'task_tpl_name' : task_tpl_name,
		 'prb_set' : prb_set_param,
		 'oj_set' : oj_set_param,
		},
		function(data){
			alert(data);
		}
	)
	
}


/*
 * 	通过名字模糊查询赛季信息
 */
function findSeason(){
		
	var sea_name = $("#sea_name_input").val();
	
	$.post("../Manager?req=findSeason",
			{
			'sea_name':sea_name
			},
			function(data){
				$("#season_list").html(data);
			});
}


/*
 *   添加所选的赛季至比赛创建页面
 */
function addSeason(){
	var check_box_set = $("#season_list").find("input");
	
	var HTML = $("#choosed_sea_list").html();
	
	for(var i=0;i<check_box_set.length;i++){
		var box = check_box_set[i];
		if(box.checked){	
			HTML += '<li class="choosed_sea_list">';
			HTML += '<h5>' + box.value; 
			HTML += '<button type="button" class="btn btn-danger btn-xs" aria-label="Left Align" onclick="removeSeason(this)">';
			HTML += '<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>';
			HTML += '</button></h5></li>';	
		}
	}
	
	$("#choosed_sea_list").html(HTML);	
	$("#chooseSeasonModal").modal('hide');
	$(window.parent.document).find("#manage_page").height($("body").height());
}


/*
 *   移除已选的赛季从页面中
 */
function removeSeason(own){
	var li_ojb = own.parentNode.parentNode;
	
	li_ojb.parentNode.removeChild(li_ojb);
	
	$(window.parent.document).find("#manage_page").height($("body").height());
}


/*
 *	 创建比赛
 */
function createContest(){
	
	var cot_name = $("#cot_name").val();
	var start_time = $("#start_time").val();
	var end_time = $("#end_time").val();
	var sea_id_list = "";
	var arry = $("#choosed_sea_list").find("h5");
	var hash = {};
	
	for(var i=0;i<arry.length;i++){
		var tmp = arry[i].innerText.match("\\[.*\\]")[0];
		var sea_id = tmp.replace("\[","").replace("\]","");		
		if(!hash[sea_id]){
			if(i!=0) sea_id_list += ";";
			sea_id_list += sea_id;
			hash[sea_id] = true;
		}
	}
	
	var oj_set = $("#problem_set").find("select");
	var oj_set_param = "";
	
	for(var i=0;i<oj_set.length;i++){
		if(i!=0) oj_set_param += ";";
		oj_set_param += oj_set[i].value;
	}
	
	var prb_set = $("#problem_set").find("input");
	var prb_set_param = "";
	
	for(var i=0;i<prb_set.length;i++){
		if(i!=0) prb_set_param += ";";
		prb_set_param += prb_set[i].value;
	}

	$.post("../Manager?req=createContest",
		{
		'cot_name' : cot_name,
		'start_time' : start_time, 
		'end_time' : end_time,
		'sea_id_list' : sea_id_list,
		'oj_set' : oj_set_param,
		'prb_set' : prb_set_param
		},
		function(data){
			alert(data);
		}
	)
	
}

/*
 *	 编辑比赛
 */
function editContest(){
	
	var cot_name = $("#cot_name").val();
	var cot_id = $("#cot_id").val();
	var start_time = $("#start_time").val();
	var end_time = $("#end_time").val();
	var prb_list = $("#prb_list").val();
	var sea_id_list = "";
	var arry = $("#choosed_sea_list").find("h5");
	var hash = {};
	
	for(var i=0;i<arry.length;i++){
		var tmp = arry[i].innerText.match("\\[.*\\]")[0];
		var sea_id = tmp.replace("\[","").replace("\]","");		
		if(!hash[sea_id]){
			if(i!=0) sea_id_list += ";";
			sea_id_list += sea_id;
			hash[sea_id] = true;
		}
	}
	
	$.post("../Manager?req=editContest",
		{
		'cot_name' : cot_name,
		'cot_id' : cot_id,
		'start_time' : start_time, 
		'end_time' : end_time,
		'sea_id_list' : sea_id_list,
		'prb_list' : prb_list
		},
		function(data){
			alert(data);
		}
	)
	
}

/*
 * 创建赛季
 */
function createSeason(){
	
	$.post("../Manager?req=createSeason",
		{
		'sea_name' : $("#sea_name").val(),
		'sea_len' : $("#sea_duration").val(),
		'sea_des' : $("#sea_desc").val()
		},
		function(data){
			alert(data);
		}
	)

}


/*
 * 编辑赛季
 */
function editSeason(){
	
	$.post("../Manager?req=editSeason",
		{
		'sea_name' : $("#sea_name").val(),
		'sea_len' : $("#sea_duration").val(),
		'sea_des' : $("#sea_desc").val(),
		'sea_id' : $("#sea_id").val()
		},
		function(data){
			alert(data);
		}
	)

}


/*
 * 保存闯关配置
 */
function saveSection(){
	var oj_list = $("select");
	var oj_id_list = $("input");
	var oj_params = "";
	var oj_id_params = "";
	
	for(var i=0;i<oj_list.length;i++){
		if(i!=0) oj_params += ";";
		oj_params += oj_list[i].value;
	}
	
	for(var i=0;i<oj_id_list.length;i++){
		if(i!=0) oj_id_params += ";";
		oj_id_params += oj_id_list[i].value;
	}
	
	$.post("../Manager?req=saveSection",
			{
			'oj_list' : oj_params,
			'oj_id_list' : oj_id_params
			},
			function(data){
				alert(data);
			}
		)
	
}


/*
 * 
 * 	查询用户数据统计
 */
function queryUserRecord(){
	
	var user_id = $("#user_id").val();
	
	$.post("../Manager?req=queryUserRecord",
			{
			'user_id' : user_id
			},
			function(data){
				var json_str = $.trim(data);
				
				var ojb = new Function("return"+json_str)();
				
				if(ojb.errno=="1"){alert("不存在此用户");return;}
				
				$("#user_name").text(ojb.user_name);
				$("#total_task").text(ojb.total_task);
				$("#total_problem").text(ojb.total_problem);
				$("#best_rank").text(ojb.best_rank);
				$("#best_score").text(ojb.best_score);
				$("#total_score").text(ojb.total_score);
				
				$(window.parent.document).find("#manage_page").height($("body").height()+20);
			}
		)	
}


/*
 * 编辑任务模版
 */
function editTaskTemplate(){
	
	$.post("../Manager?req=editTaskTemplate",
			{
				'task_tpl_name' : $("#task_tpl_name").val(), 
				'prb_list' : $("#prb_list").val(), 
				'task_tpl_id' : $("#task_tpl_id").val()
			},
			function(data){
				alert(data);				
			}
		)	
	
}

/*
 * 查找题目
 */
function queryProblem(){
	
	$.post("../Manager?req=queryProblem",
			{
				'problem' : $("#problem").val()
			},
			function(data){
				var json_str = $.trim(data);
				var obj = new Function("return"+json_str)();
				
				if(obj.errno==0){
					window.open("../problem.jsp?type=-1&prb_id="+obj.prb_id); 
				}else{
					alert(obj.errmsg);
				}
			}
		)	
}